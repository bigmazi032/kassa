package ru.zinyakova.kassa.entity;

import lombok.Data;


@Data
public class SeatStatus {
    private Long id;
    private Long scheduleId;
    private Long seatCategoryId;
    private Long price;
    private Long total;
    private Long free;
}
