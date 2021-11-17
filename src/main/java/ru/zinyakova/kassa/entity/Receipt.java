package ru.zinyakova.kassa.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class Receipt {
    private Long id;
    private Date date;
    private BigDecimal summa; // сумма скидки
}
