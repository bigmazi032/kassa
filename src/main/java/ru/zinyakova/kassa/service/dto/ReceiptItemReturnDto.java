package ru.zinyakova.kassa.service.dto;

import lombok.Data;

@Data
public class ReceiptItemReturnDto {
    private Long id;
    private Long item_id;
    private Long receipt_return_id;
    private Long quantity_return;
    private Long summa;
}
