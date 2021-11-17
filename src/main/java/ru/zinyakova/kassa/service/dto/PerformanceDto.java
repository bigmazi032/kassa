package ru.zinyakova.kassa.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PerformanceDto extends NamedDto{
    public PerformanceDto(Long id, String name) {
        super(id, name);
    }
    public PerformanceDto(){};
}
