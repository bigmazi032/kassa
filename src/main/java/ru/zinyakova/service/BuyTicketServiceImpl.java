package ru.zinyakova.service;

import ru.zinyakova.dao.*;
import ru.zinyakova.entity.*;
import ru.zinyakova.service.dto.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.TransferQueue;


public class BuyTicketServiceImpl implements BuyTicketService {
    private TheatreDao  theatreDao = new TheatreDao();
    private ScheduleDao scheduleDao = new ScheduleDao();
    private SeatStatusDao seatStatusDao = new SeatStatusDao();
    private ReceiptDao receiptDao = new ReceiptDao();
    private ReceiptItemDao receiptItemDao = new ReceiptItemDao();
    private ReceiptReturnDao receiptReturnDao = new ReceiptReturnDao();
    private ReceiptItemReturnDao receiptItemReturnDao = new ReceiptItemReturnDao();

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
    @Override
    public ReceiptDto countSales(ReceiptDto receiptDto){
        Receipt r = new Receipt();
        r.setId(receiptDto.getId());
        r.setDate(receiptDto.getDate());
        ArrayList<ReceiptItem> items = receiptItemDao.getItemsOfReceipt(r.getId());
        long summ = 0;
        int amount = items.size();
        for(ReceiptItem item : items){
            long quantity = item.getQuantity();
            long price = seatStatusDao.getPriceBySeatStatusId(item.getSeatStatusId());
            long itemSumm = (long)(quantity*price);
            if (item.getQuantity()>10){
                itemSumm = (long) (itemSumm * 0.9) ;
            }
            item.setSumma(itemSumm);
            summ = summ+itemSumm;
           // r.setSumma(summ);
        }
        if(amount>=2){
            long summAll = 0;
            ReceiptItem maxItem = items.get(0);
                for(ReceiptItem i : items){
                    if(i.getSumma() > maxItem.getSumma()) {
                        maxItem = i;
                    }
                }
               for (ReceiptItem i : items){
                    if(i!= maxItem) {
                        long summa = i.getSumma();
                        i.setSumma((long) (summa * 0.9));
                        summAll = summAll + i.getSumma();
                    }
               }
               r.setSumma(summAll);
            }
        receiptDto.setSumma(r.getSumma());
        return receiptDto;
        }

        @Override
        public ReceiptReturnDto createReceiptReturn (ReceiptReturnDto receipt){
            ReceiptReturn r = new ReceiptReturn();
            r.setId(receipt.getId());
            r.setSumma_return(receipt.getSumma_return());
            r.setDate_return(receipt.getDate_return());
            r.setReceipt_id(receipt.getReceipt_id());
            receiptReturnDao.createReceiptReturn(r);
            return receipt;
        }

        @Override
        public ReceiptItemReturnDto createReceiptItemReturn(ReceiptItemReturnDto item){
            ReceiptItemReturn i = new ReceiptItemReturn();
            i.setId(item.getId());
            i.setItem_id(item.getItem_id());
            i.setSumma(item.getSumma());
            i.setQuantity_return(item.getQuantity_return());
            i.setReceipt_return_id(item.getReceipt_return_id());
            receiptItemReturnDao.createNewReceiptItemReturn(i);
            return item;
        }

        @Override
        public ReceiptReturnDto countSummaReturn ( ReceiptReturnDto receiptDto ){
            ReceiptReturn receiptReturn = new ReceiptReturn();
            receiptReturn.setId(receiptDto.getId());
            ArrayList<ReceiptItemReturn> items = receiptItemReturnDao.getItemsReturnOfReceipt(receiptReturn.getId());
            Long prevReceiptId = receiptReturn.getReceipt_id();
            Receipt prevReceipt = receiptDao.getReceiptById(prevReceiptId);
            ArrayList<ReceiptItem> prevItems = receiptItemDao.getItemsOfReceipt(prevReceiptId);
            long prevItemAmount = prevItems.size();
            long countItemsReturn = items.size();
            long newItemsAmount = prevItemAmount - countItemsReturn;
            long allSumma = 0;
            for (ReceiptItemReturn i: items) {
                long newQuantity = 0;
                long prevItemId = receiptItemReturnDao.getPrevItemById(i.getId());
                ReceiptItem prevItem = receiptItemDao.getReceiptItemById(prevItemId);
                long prevQuantity = prevItem.getQuantity();
                newQuantity = prevQuantity - i.getQuantity_return();
                long price = seatStatusDao.getPriceBySeatStatusId(prevItem.getSeatStatusId());
                long newSummOfItem = price * newQuantity;
                if (newQuantity > 10) {
                    newSummOfItem = (long) (newSummOfItem * 0.9);
                }
                long returnSumm = prevItem.getSumma() - newSummOfItem;
                allSumma = allSumma + returnSumm;
                long summReturn = prevReceipt.getSumma() - allSumma;
                Date playDate = seatStatusDao.getDateBySeatStatusId(prevItem.getSeatStatusId());
                LocalDate playLocalDate = playDate.toLocalDate();
                if (receiptReturn.getDate_return().toLocalDate().isAfter(playLocalDate.minusDays(1))) {
                    receiptDto.setSumma_return(summReturn / 2);
                }
            }
            if (newItemsAmount >= 2){
                long summAll = 0;
                ReceiptItem maxItem = prevItems.get(0);
                for(ReceiptItem i : prevItems){
                    if(i.getSumma() > maxItem.getSumma()) {
                        maxItem = i;
                    }
                }
                for (ReceiptItem i : prevItems){
                    if(i!= maxItem) {
                        long summa = i.getSumma();
                        i.setSumma((long) (summa * 0.9));
                        summAll = summAll + i.getSumma();
                    }
                }
                long summReturn = prevReceipt.getSumma() - summAll;
                receiptDto.setSumma_return(summReturn);
            }
            return receiptDto;
        }



}








// вопросы: в последних двух при создании мы возвращаем то  же что и взяли на вход (не создается ничего нового и тд просто вызвали метод из дао слоя)
// в  getSeatStatuses не дописала так как не работает консоль с запросами
// надо дописать запрос в getSeatStatus и саму функцию
