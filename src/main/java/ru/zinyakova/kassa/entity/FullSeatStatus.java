package ru.zinyakova.kassa.entity;

import lombok.Data;

@Data
public class FullSeatStatus {
    private Long id;
    private FullSchedule schedule;
    private SeatCategory seatCategory;
    private Long price;
    private Long total;
    private Long free;
}
