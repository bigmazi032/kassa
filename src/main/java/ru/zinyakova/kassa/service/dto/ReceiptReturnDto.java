package ru.zinyakova.kassa.service.dto;

import lombok.Data;

import java.sql.Date;
@Data
public class ReceiptReturnDto {
    private Long id;
    private Long receipt_id;
    private Date date_return;
    private Long summa_return;
}
