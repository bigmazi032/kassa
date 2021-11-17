package ru.zinyakova.entity;

import lombok.Data;

@Data
public class ReceiptItemReturn {
private Long id;
private Long item_id;
private Long receipt_return_id;
private Long quantity_return;
private Long summa;
}
