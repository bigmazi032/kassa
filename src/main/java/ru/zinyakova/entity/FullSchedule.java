package ru.zinyakova.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class FullSchedule {
    private Long id;
    private Date date;
    private Theatre theatre;
    private Performance performance;
}
