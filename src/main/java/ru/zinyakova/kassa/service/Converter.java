package ru.zinyakova.kassa.service;

import ru.zinyakova.kassa.entity.*;
import ru.zinyakova.kassa.service.dto.*;

import java.sql.Date;
import java.util.ArrayList;

public class Converter {
    public static TheatreDto toTheatreDto(Theatre theatre){
        TheatreDto theatreDto = new TheatreDto();
        theatreDto.setId(theatre.getId());
        theatreDto.setName(theatre.getName());
        return theatreDto;
    }

    public static Theatre toTheatreEntity (TheatreDto theatreDto){
        Theatre theatre = new Theatre();
        theatre.setId(theatreDto.getId());
        theatre.setName(theatreDto.getName());
        return theatre;
    }

    public static PerformanceDto toPerformanceDto(Performance p){
        PerformanceDto pDto = new PerformanceDto();
        pDto.setId(p.getId());
        pDto.setName(p.getName());
        return  pDto;
    }

    public static Performance toPerformanceEntity(PerformanceDto performanceDto){
        Performance p = new Performance();
        p.setId(performanceDto.getId());
        p.setName(performanceDto.getName());
        return  p;
    }

    public static SchedulerDto scheduleToSchedulerDto (FullSchedule sc){
        SchedulerDto scDto = new SchedulerDto();
        scDto.setId(sc.getId());
        scDto.setTheatre(toTheatreDto(sc.getTheatre()));
        scDto.setPerformance(toPerformanceDto(sc.getPerformance()));
        scDto.setDate(sc.getDate().toLocalDate());
        return scDto;
    }

    public static SeatCategory toSeatCategoryEntity (SeatCategoryDto seat){
        SeatCategory sc = new SeatCategory();
        sc.setId(seat.getId());
        sc.setName(seat.getName());
        return sc;
    }

    public static SeatCategoryDto toSeatCategoryDto (SeatCategory seat){
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
        ssDto.setSeatCategory(toSeatCategoryDto(ss.getSeatCategory()));
        return ssDto;
    }

    public static SeatStatus toSeatStatusEntity(SeatStatusDto seat) {
        SeatStatus seatStatus = new SeatStatus();
        seatStatus.setId(seat.getId());
        seatStatus.setSeatCategoryId(seat.getSeatCategory().getId());
        seatStatus.setScheduleId(seat.getScheduler().getId());
        seatStatus.setFree(seat.getFree());
        seatStatus.setTotal(seat.getTotal());
        seatStatus.setPrice(seat.getPrice());
        return seatStatus;
    }

    public static ReceiptItemDto receiptItemToReceiptItemDto (FullReceiptItem item){
        ReceiptItemDto itemDto = new ReceiptItemDto();
        itemDto.setId(item.getId());
        itemDto.setSeat(seatStatusToSeatStatusDto(item.getSeatStatus()));
        itemDto.setQuantitySeats(item.getQuantity());
        itemDto.setReceiptId(item.getReceiptId());
        return itemDto;
    }

    public static ReceiptItem toReceiptItemEntity (ReceiptItemDto item){
        ReceiptItem ri = new ReceiptItem();
        ri.setId(item.getId());
        ri.setSeatStatusId(item.getSeat().getId());
        ri.setQuantity(item.getQuantitySeats());
        ri.setReceiptId(item.getReceiptId());
        ri.setSumma(item.getSumWithDiscount());
        return ri;
    }

    public static ReceiptDto receiptToReceiptDto (FullReceipt receipt){
        ReceiptDto rDto = new ReceiptDto();
        rDto.setId(receipt.getId());
        rDto.setDiscountSum(receipt.getSumma());
        rDto.setDate(receipt.getDate().toLocalDate());
        ArrayList<FullReceiptItem> items = receipt.getItems();
        for (FullReceiptItem i: items){
           rDto.addReceiptItem( receiptItemToReceiptItemDto(i));
        }
        return rDto;
    }

    public static Receipt toReceiptEntity (ReceiptDto receipt){
        Receipt r = new Receipt();
        r.setId(receipt.getId());
        r.setSumma(receipt.getDiscountSum());
        r.setDate(Date.valueOf(receipt.getDate()));
        return r;
    }
}
