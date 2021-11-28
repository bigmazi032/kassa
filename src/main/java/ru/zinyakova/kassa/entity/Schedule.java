package ru.zinyakova.kassa.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Schedule {
    private Long theatre_id;
    private Long performance_id;
    private Long id;
    private Date date;
}
