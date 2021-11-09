package ru.zinyakova.service;


import ru.zinyakova.service.dto.*;

import java.util.List;

public interface BuyTicketService {
    List<TheatreDto> getTheatreFromScheduler();
    List<PerformanceDto> getPerformanceFromScheduler(TheatreDto theatreDto);
    List<SchedulerDto> getScheduler(TheatreDto theatreDto, PerformanceDto performanceDto);
    List<SeatCategoryDto> getSeatCategories (SchedulerDto scheduler);
    SeatStatusDto getSeatStatuses (SchedulerDto scheduler, SeatCategoryDto seatCategory);
    ReceiptDto createReceipt(ReceiptDto receiptDto);
    ReceiptItemDto createReceiptItem (ReceiptItemDto receiptItem);
}
