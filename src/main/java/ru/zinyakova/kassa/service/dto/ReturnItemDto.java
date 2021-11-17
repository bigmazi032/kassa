package ru.zinyakova.kassa.service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReturnItemDto {
    private ReceiptItemDto receiptItemDto;
    private Long returnQuantity;
    private LocalDate dateReturn = LocalDate.now();
}
