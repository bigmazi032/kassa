package ru.zinyakova.kassa.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiptItem {
    private Long id;
    private Long receiptId;
    private Long seatStatusId;
    private Long quantity;
    private BigDecimal summa; // сумма со скидкой
}
