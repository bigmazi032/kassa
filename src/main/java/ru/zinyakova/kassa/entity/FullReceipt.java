package ru.zinyakova.kassa.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

@Data
public class FullReceipt {
    private Long id;
    private Date date;
    private BigDecimal summa;
    private ArrayList<FullReceiptItem> items = new ArrayList<>();
}
