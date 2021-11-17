package ru.zinyakova.entity;

import lombok.Data;

import java.sql.Date;
import java.util.ArrayList;

@Data
public class FullReceipt {
    private Long id;
    private Date date;
    private Long summa;
    private ArrayList<FullReceiptItem> items = new ArrayList<>();
}
