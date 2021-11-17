package ru.zinyakova.kassa.service;


import ru.zinyakova.kassa.service.dto.*;

import java.math.BigDecimal;
import java.util.List;

public interface BuyTicketService {
    List<TheatreDto> getTheatreFromScheduler();
    List<PerformanceDto> getPerformanceFromScheduler(TheatreDto theatreDto);
    List<SchedulerDto> getScheduler(TheatreDto theatreDto, PerformanceDto performanceDto);
    List<SeatCategoryDto> getSeatCategories (SchedulerDto scheduler);
    SeatStatusDto getSeatStatuses (SchedulerDto scheduler, SeatCategoryDto seatCategory);
    ReceiptDto createReceipt(ReceiptDto receiptDto);
    ReceiptItemDto createReceiptItem (ReceiptItemDto receiptItem);
    ReceiptDto closeReceipt(ReceiptDto receipt);
    ReceiptDto getReceiptById(Long id);
    BigDecimal calculateSumToReturn(ReceiptDto receiptForReturn, List<ReturnItemDto> returnItemDtoList);
}
