package ru.zinyakova.service;

import ru.zinyakova.dao.*;
import ru.zinyakova.entity.*;
import ru.zinyakova.service.dto.*;

import java.util.ArrayList;
import java.util.List;

public class BuyTicketServiceImpl implements BuyTicketService {
    private TheatreDao  theatreDao = new TheatreDao();
    private ScheduleDao scheduleDao = new ScheduleDao();
    private SeatStatusDao seatStatusDao = new SeatStatusDao();
    private ReceiptDao receiptDao = new ReceiptDao();
    private ReceiptItemDao receiptItemDao = new ReceiptItemDao();

    @Override
    public List<TheatreDto> getTheatreFromScheduler() {
        ArrayList<TheatreDto> theatreDtos = new ArrayList<>();
        ArrayList<Theatre> allTeatres = scheduleDao.getAllTheatres();
        for (Theatre t: allTeatres){
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
            schedulerDto.setDate(s.getDate());
            schedulerDto.setTheatre(theatreDto);
            schedulerDto.setPerformance(performanceDto);
            schedule.add(schedulerDto);
        }
        return schedule;
    }

    @Override
    public List<SeatCategoryDto> getSeatCategories(SchedulerDto scheduler) {
        ArrayList<SeatCategoryDto> seatDto = new ArrayList<>();
        ArrayList<SeatStatus> seats = seatStatusDao.getSeatstatusBySheduleId(scheduler.getId());
        for (SeatStatus s: seats){
            SeatCategoryDto scd = new SeatCategoryDto();
            scd.setId(s.getId());
            scd.setName(s.getName());
            seatDto.add(scd);
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
        r.setSumma(receiptDto.getSumma());
        r.setDate(receiptDto.getDate());
        receiptDao.createNewReceipt(r);
        return  receiptDto;
    }

    @Override
    public ReceiptItemDto createReceiptItem(ReceiptItemDto receiptItem) {
        ReceiptItem r = new ReceiptItem();
        r.setId(receiptItem.getId());
        r.setReceiptId(receiptItem.getReceiptId());
        r.setSeatStatusId(receiptItem.getSeat().getId());
        r.setQuantity(receiptItem.getQuantitySeats());
        r.setSumma(0l);
        receiptItemDao.createNewReceiptItem(r);
        return receiptItem;
    }
}

// вопросы: в последних двух при создании мы возвращаем то  же что и взяли на вход (не создается ничего нового и тд просто вызвали метод из дао слоя)
// в  getSeatStatuses не дописала так как не работает консоль с запросами
// надо дописать запрос в getSeatStatus и саму функцию
