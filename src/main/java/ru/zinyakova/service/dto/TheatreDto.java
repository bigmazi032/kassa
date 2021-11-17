package ru.zinyakova.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TheatreDto extends NamedDto{
    public TheatreDto(Long id, String name) {
        super(id, name);
    }
    public TheatreDto(){};
}
