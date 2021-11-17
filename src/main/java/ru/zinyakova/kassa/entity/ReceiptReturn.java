package ru.zinyakova.kassa.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class ReceiptReturn {
    private Long id;
    private Long receipt_id;
    private Date date_return;
    private Long summa_return;
}
