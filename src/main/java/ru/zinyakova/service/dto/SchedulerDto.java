package ru.zinyakova.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerDto {
    private Long id;
    private TheatreDto theatre;
    private PerformanceDto performance;
    private Date date;


}
