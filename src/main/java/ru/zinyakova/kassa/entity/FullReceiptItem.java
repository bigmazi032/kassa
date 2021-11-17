package ru.zinyakova.kassa.entity;

import lombok.Data;

@Data
public class FullReceiptItem {
    private Long id;
    private Long receiptId;
    private FullSeatStatus seatStatus;
    private Long quantity;
    private Long summa;
}
