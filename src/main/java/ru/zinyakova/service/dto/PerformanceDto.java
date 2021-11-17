package ru.zinyakova.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
public class PerformanceDto extends NamedDto{
    public PerformanceDto(Long id, String name) {
        super(id, name);
    }
    public PerformanceDto(){};
}
