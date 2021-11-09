package ru.zinyakova.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SeatCategoryDto extends NamedDto{
    public SeatCategoryDto(Long id, String name) {
        super(id, name);
    }
    public SeatCategoryDto(){};
}
