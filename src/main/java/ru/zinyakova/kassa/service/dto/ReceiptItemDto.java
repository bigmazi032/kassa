package ru.zinyakova.kassa.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
public class ReceiptItemDto {
    private Long id;
    private Long receiptId;
    private SeatStatusDto seat;
    private Long quantitySeats;
    private Long sum;
    private BigDecimal sumWithDiscount;
    private ArrayList<Integer> discounts = new ArrayList<>();
}
