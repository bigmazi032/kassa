package ru.zinyakova.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Receipt {
    private Long id;
    private Date date;
    private Long summa;
}
