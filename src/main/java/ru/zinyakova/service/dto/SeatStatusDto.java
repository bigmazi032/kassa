package ru.zinyakova.service.dto;

import lombok.Data;

@Data
public class SeatStatusDto {
    private Long id;
    private SchedulerDto scheduler;
    private SeatCategoryDto seatCategory;
    private Long free;
    private Long total;
    private Long price;
}
