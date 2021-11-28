package ru.zinyakova.kassa.service;

import ru.zinyakova.kassa.dao.*;
import ru.zinyakova.kassa.entity.*;
import ru.zinyakova.kassa.service.dto.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static ru.zinyakova.kassa.service.Converter.*;


public class BuyTicketServiceImpl implements BuyTicketService {
    private TheatreDao theatreDao = new TheatreDao();
    private ScheduleDao scheduleDao = new ScheduleDao();
    private SeatStatusDao seatStatusDao = new SeatStatusDao();
    private ReceiptDao receiptDao = new ReceiptDao();
    private ReceiptItemDao receiptItemDao = new ReceiptItemDao();
    private ReceiptReturnDao receiptReturnDao = new ReceiptReturnDao();
    private ReceiptItemReturnDao receiptItemReturnDao = new ReceiptItemReturnDao();

    @Override
    public List<TheatreDto> getTheatreFromScheduler() {
        ArrayList<TheatreDto> theatreDtos = new ArrayList<>();
        ArrayList<Theatre> allTheatres = scheduleDao.getAllTheatres();
        for (Theatre t: allTheatres){
            TheatreDto theatreDto = new TheatreDto();
            theatreDto.setId(t.getId());
            theatreDto.setName(t.getName());
            theatreDtos.add(theatreDto);
        }
        return theatreDtos;
    }

    @Override
    public List<PerformanceDto> getPerformanceFromScheduler(TheatreDto theatreDto) {
        ArrayList<PerformanceDto> performanceDtos = new ArrayList<>();
        ArrayList<Performance> performances = scheduleDao.getPerformanceByTheatre(theatreDto.getId());
        for (Performance p: performances){
            PerformanceDto performanceDto = new PerformanceDto();
            performanceDto.setId(p.getId());
            performanceDto.setName(p.getName());
            performanceDtos.add(performanceDto);
        }
        return performanceDtos;
    }

    @Override
    public List<SchedulerDto> getScheduler(TheatreDto theatreDto, PerformanceDto performanceDto) {
        ArrayList<SchedulerDto> schedule = new ArrayList<>();
        ArrayList<Schedule> scheduleList = scheduleDao.getDateByTP(theatreDto.getId(), performanceDto.getId());
        for (Schedule s : scheduleList){
            SchedulerDto schedulerDto = new SchedulerDto();
            schedulerDto.setId(s.getId());
            schedulerDto.setDate(s.getDate().toLocalDate());
            schedulerDto.setTheatre(theatreDto);
            schedulerDto.setPerformance(performanceDto);
            schedule.add(schedulerDto);
        }
        return schedule;
    }

    @Override
    public List<SeatCategoryDto> getSeatCategories(SchedulerDto scheduler) {
        ArrayList<SeatCategoryDto> seatDto = new ArrayList<>();
        ArrayList<SeatCategory> seats = seatStatusDao.getSeatCategoriesBySchedulerId(scheduler.getId());
        for (SeatCategory s: seats){
            seatDto.add(toSeatCategoryDto(s));
        }
        return seatDto;
    }

    @Override
    public SeatStatusDto getSeatStatuses(SchedulerDto scheduler, SeatCategoryDto seatCategory) {
            SeatStatus seat = seatStatusDao.getSeatStatusByScheduleAndCategory(scheduler.getId(), seatCategory.getId());
            SeatStatusDto ssd = new SeatStatusDto();
            ssd.setId(seat.getId());
            ssd.setScheduler(scheduler);
            ssd.setSeatCategory(seatCategory);
            ssd.setFree(seat.getFree());
            ssd.setPrice(seat.getPrice());
            ssd.setTotal(seat.getTotal());
        return ssd;
    }

    @Override
    public ReceiptDto createReceipt(ReceiptDto receiptDto) {
        Receipt r = new Receipt();
        r.setId(receiptDto.getId());
        r.setSumma(receiptDto.getDiscountSum());
        r.setDate(Date.valueOf(receiptDto.getDate()));
        r = receiptDao.createNewReceipt(r);
        receiptDto.setId(r.getId());
        return  receiptDto;
    }

    @Override
    public ReceiptItemDto createReceiptItem(ReceiptItemDto receiptItem) {
        // создаем запись в таблице receipt_item
        ReceiptItem r = new ReceiptItem();
        r.setId(receiptItem.getId());
        r.setReceiptId(receiptItem.getReceiptId());
        r.setSeatStatusId(receiptItem.getSeat().getId());
        r.setQuantity(receiptItem.getQuantitySeats());
        r.setSumma(receiptItem.getSumWithDiscount());
        r = receiptItemDao.createNewReceiptItem(r);
        receiptItem.setId(r.getId());
        // уменьшаем количество мест в таблице seat_status
        SeatStatusDto seat = receiptItem.getSeat();
        seat.setFree(seat.getFree() - receiptItem.getQuantitySeats());
        SeatStatus seatStatus = toSeatStatusEntity(seat);
        seatStatusDao.updateFreeSeatsInSeatStaus(seatStatus);
        return receiptItem;
    }

    @Override
    public ReceiptDto closeReceipt(ReceiptDto receipt) {
        // рассчитываем скидки
        calculateSale(receipt);
        // записываем в базу общую скидку по чеку
        receiptDao.updateReceiptSum(toReceiptEntity(receipt));
        updateReceiptItemsInDB(receipt.getReceiptItems());
        // возвращаем значения на фронт
        return receipt;
    }

    private void updateReceiptItemsInDB(List<ReceiptItemDto> receiptItems) {
        // перебираем все айтемы в чеке
        for (ReceiptItemDto receiptItem : receiptItems) {
            // находим нужный айтем в базе
            receiptItemDao.updateReceiptItem(toReceiptItemEntity(receiptItem));
        }
    }

    final static Integer DISCOUNT_10 = 10;
    final static Integer DISCOUNT_10_CONDITION = 10;
    final static Integer DISCOUNT_SECOND = 10;


    private void calculateSale(ReceiptDto receipt) {
        List<ReceiptItemDto> receiptItems = receipt.getReceiptItems();
        // проставляем сумму без скидок
        receiptItems.forEach(ri -> ri.setSum(ri.getQuantitySeats() * ri.getSeat().getPrice()));
        // добавляем скидку за количество
        receiptItems.forEach(ri -> {
            if (ri.getQuantitySeats()>= DISCOUNT_10_CONDITION) {
                ri.getDiscounts().add(DISCOUNT_10);
            }
        });
        // добавляем скидку за второе представление
        // сначала проверяем количество представлений и если больше 2, то  находим самое дорого (на которое не будет распространяться скидка)
        if (receiptItems.size() > 1) {
            // находим самое дорогое
            ReceiptItemDto samoeDorogoe = receiptItems.get(0);
            for (int i = 1; i<receiptItems.size(); i++) {
                if (samoeDorogoe.getSum() < receiptItems.get(i).getSum()) {
                    samoeDorogoe = receiptItems.get(i);
                }
            }
            // ко всем остальным добавляем скидку
            for (ReceiptItemDto ri : receiptItems) {
                if (!ri.equals(samoeDorogoe)) {
                    ri.getDiscounts().add(DISCOUNT_SECOND);
                }
            }
        }
        // считаем скидку и сумму со скидкой
        for (ReceiptItemDto ri : receiptItems) {
            Long sum = ri.getSum();
            // суммируем скидки
            Integer allDiscount = ri.getDiscounts().stream().reduce(Integer::sum).orElse(0);
            // вычисляем сумму скидки
            BigDecimal discount = new BigDecimal(sum * allDiscount / 100).setScale(2, RoundingMode.CEILING);
            // вычисляем сумму без скидки и сохраняем ее
            ri.setSumWithDiscount(new BigDecimal(sum).subtract(discount));
        }
        Long totalSum = receiptItems.stream().map(ReceiptItemDto::getSum).reduce(Long::sum).get();
        BigDecimal sumWithDiscount = receiptItems.stream().map(ReceiptItemDto::getSumWithDiscount).reduce(BigDecimal::add).get();
        BigDecimal totalDiscount = BigDecimal.valueOf(totalSum).subtract(sumWithDiscount);
        receipt.setSum(totalSum);
        receipt.setDiscountSum(totalDiscount);
        receipt.setSumWithDiscount(sumWithDiscount);
    }

    @Override
    public ReceiptDto getReceiptById(Long id) {
        FullReceipt fullReceipt = receiptDao.getFullReceipt(id);
        ReceiptDto receiptDto = receiptToReceiptDto(fullReceipt);
        return receiptDto;
    }

    @Override
    public BigDecimal calculateSumToReturn(ReceiptDto returnReceiptDto, List<ReturnItemDto> returnItemDtoList) {
        //будем обрабатывать каждый возврат по очереди
        System.out.println("Расчитываем сумму к возврату");
        BigDecimal totalSumToReturn = BigDecimal.ZERO;
        for (ReturnItemDto returnItem: returnItemDtoList) {
            // находим айтем для которого уменьшаем значение
            BigDecimal sumToReturn;
            ReceiptItemDto receiptItemDto = returnItem.getReceiptItemDto();
            // если скидки не применялись
            if (returnReceiptDto.getDiscountSum().compareTo(BigDecimal.ZERO) == 0) {
                System.out.println("Скидки не применялись. Пересчет не выполняем");
                // просто возвращаем стоимость мест
                sumToReturn = BigDecimal.valueOf(receiptItemDto.getSeat().getPrice() * returnItem.getReturnQuantity());
                System.out.println("Сумма к возврату = " + sumToReturn);
                // проверяем применение штрафа если за день до представления
                if (returnItem.getDateReturn().isAfter(receiptItemDto.getSeat().getScheduler().getDate().minusDays(1L))) {
                    sumToReturn = sumToReturn.divide(BigDecimal.valueOf(2),RoundingMode.HALF_UP);
                    System.out.println("-50% за поздний возврат. К выплате = " + sumToReturn );
                }
            } else {
                // если были скидки
                System.out.println("Т.к. при покупке применялись скидки выполняем перерасчет");
                Receipt receipt = receiptDao.getReceiptById(returnReceiptDto.getId());
                // необходимо пересчитать скидки
                // сделаать новый объект receipt
                ReceiptDto tempReceipt = new ReceiptDto();
                //подготовить новые объекты айтемов

                for (ReceiptItemDto ri : returnReceiptDto.getReceiptItems()) {
                    ReceiptItemDto tempReceiptItem = new ReceiptItemDto();
                    if (ri.getId() == returnItem.getReceiptItemDto().getId()) {
                        // уменьшаем количество мест
                        tempReceiptItem.setQuantitySeats(ri.getQuantitySeats() - returnItem.getReturnQuantity());
                    } else {
                        tempReceiptItem.setQuantitySeats(ri.getQuantitySeats());
                    }
                    tempReceiptItem.setSeat(ri.getSeat());
                    tempReceipt.getReceiptItems().add(tempReceiptItem);
                }

                BigDecimal lastDiscount = receipt.getSumma();
                System.out.println("Последняя сумма скидки = " + lastDiscount);
                // рассчитываем сумму для возврата
                calculateSale(tempReceipt);
                BigDecimal newDiscount = tempReceipt.getDiscountSum();
                System.out.println("Новая сумма скидки после возврата = " + newDiscount);
                //
                sumToReturn = BigDecimal.valueOf(receiptItemDto.getSeat().getPrice() * returnItem.getReturnQuantity());
                // уменьшаем сумму к возврату на разницу скидок
                sumToReturn = sumToReturn.subtract(lastDiscount.subtract(newDiscount));
                // если получившаяся сумма меньше либо равна нулю, то возврат  равен нулю и уменьшаем скидку на сумму возврата
                if (sumToReturn.compareTo(BigDecimal.ZERO) < 1) {
                    System.out.println("Возвращать нечего, т.к. сумма к возврату не покрывает скидку");
                    sumToReturn = BigDecimal.ZERO;
                    // обновляем скидку с базе в чеке

                    receipt.setSumma(receipt.getSumma().subtract(BigDecimal.valueOf(receiptItemDto.getSeat().getPrice() * returnItem.getReturnQuantity())));
                    receiptDao.updateReceiptSum(receipt);

                } else {
                    // то к возврату оставшая сумма
                    // проверяем применение штрафа если за день до представления
                    if (returnItem.getDateReturn().isAfter(receiptItemDto.getSeat().getScheduler().getDate().minusDays(1L))) {
                        sumToReturn = sumToReturn.divide(BigDecimal.valueOf(2),RoundingMode.HALF_UP);
                        System.out.println("-50% за поздний возврат. К выплате = " + sumToReturn );
                    }
                    // обновляем скидку с базе в чеке
                    receipt.setSumma(newDiscount);
                    receiptDao.updateReceiptSum(receipt);
                }

            }
            totalSumToReturn = totalSumToReturn.add(sumToReturn);
            ReceiptItemDto reItem = new ReceiptItemDto();
            reItem.setReceiptId(returnReceiptDto.getId());
            reItem.setSeat(receiptItemDto.getSeat());
            reItem.setQuantitySeats(-returnItem.getReturnQuantity());
            reItem.setSumWithDiscount(sumToReturn.negate());
            System.out.println("Создаем запись о возврате билета " + reItem);
            createReceiptItem(reItem);
            // добавляем запись в базу с возвратом (отрицательные количество мест и сумма к возврату)
        }
        return totalSumToReturn;
    }

    public ArrayList<SimpleSchedule> getScheduleByPerfomance (String playNameDto){
        return scheduleDao.getScheduleByPerfomance(playNameDto);
    }
}








// вопросы: в последних двух при создании мы возвращаем то  же что и взяли на вход (не создается ничего нового и тд просто вызвали метод из дао слоя)
// в  getSeatStatuses не дописала так как не работает консоль с запросами
// надо дописать запрос в getSeatStatus и саму функцию
