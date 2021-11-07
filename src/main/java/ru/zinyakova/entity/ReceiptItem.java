package ru.zinyakova.entity;

import lombok.Data;

@Data
public class ReceiptItem {
    private Long id;
    private Long receiptId;
    private Long seatStatusId;
    private Long quantity;
    private Long summa;
}
