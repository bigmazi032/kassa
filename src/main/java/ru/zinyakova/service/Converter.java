package ru.zinyakova.service;

import ru.zinyakova.entity.*;
import ru.zinyakova.service.dto.*;

import java.util.ArrayList;

public class Converter {
    public static TheatreDto theatreToTheatreDto (Theatre theatre){
        TheatreDto theatreDto = new TheatreDto();
        theatreDto.setId(theatre.getId());
        theatreDto.setName(theatreDto.getName());
        return theatreDto;
    }

    public static PerformanceDto performanceToPerformanceDto (Performance p){
        PerformanceDto pDto = new PerformanceDto();
        pDto.setId(p.getId());
        pDto.setName(p.getName());
        return  pDto;
    }

    public static SchedulerDto scheduleToSchedulerDto (FullSchedule sc){
        SchedulerDto scDto = new SchedulerDto();
        scDto.setId(sc.getId());
        scDto.setTheatre(theatreToTheatreDto(sc.getTheatre()));
        scDto.setPerformance(performanceToPerformanceDto(sc.getPerformance()));
        scDto.setDate(sc.getDate());
        return scDto;
    }

    public static SeatCategoryDto seatCategoryToSeatCategoryDto (SeatCategory seat){
        SeatCategoryDto seatDto = new SeatCategoryDto();
        seatDto.setId(seat.getId());
        seatDto.setName(seat.getName());
        return seatDto;
    }

    public static SeatStatusDto seatStatusToSeatStatusDto (FullSeatStatus ss) {
        SeatStatusDto ssDto = new SeatStatusDto();
        ssDto.setId(ss.getId());
        ssDto.setTotal(ss.getTotal());
        ssDto.setPrice(ss.getPrice());
        ssDto.setFree(ss.getFree());
        ssDto.setScheduler(scheduleToSchedulerDto(ss.getSchedule()));
        ssDto.setSeatCategory(seatCategoryToSeatCategoryDto(ss.getSeatCategory()));
        return ssDto;
    }

    public static ReceiptItemDto receiptItemToReceiptItemDto (FullReceiptItem item){
        ReceiptItemDto itemDto = new ReceiptItemDto();
        itemDto.setId(item.getId());
        itemDto.setSeat(seatStatusToSeatStatusDto(item.getSeatStatus()));
        itemDto.setQuantitySeats(item.getQuantity());
        itemDto.setReceiptId(item.getReceiptId());
        return itemDto;
    }
    public static ReceiptDto receiptToReceiptDto (FullReceipt receipt){
        ReceiptDto rDto = new ReceiptDto();
        rDto.setId(receipt.getId());
        rDto.setSumma(receipt.getSumma());
        rDto.setDate(receipt.getDate());
        ArrayList<FullReceiptItem> items = receipt.getItems();
        for (FullReceiptItem i: items){
           rDto.addReceiptItem( receiptItemToReceiptItemDto(i));
        }
        return rDto;
    }
}
