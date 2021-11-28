package ru.zinyakova.kassa.service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SimpleSchedule {
    private String theareName;
    private LocalDate date;
}
