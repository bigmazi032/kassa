package ru.zinyakova.kassa.service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NamedDto {
    private Long id;
    private String name;
}
