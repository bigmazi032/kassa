package ru.zinyakova.kassa.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Data
public class ReceiptDto {
    private Long id;
    private LocalDate date;
    private List<ReceiptItemDto> receiptItems = new ArrayList<>();
    private Long sum;
    private BigDecimal sumWithDiscount;
    private BigDecimal discountSum;

    public void addReceiptItem (ReceiptItemDto receiptItemDto) {
        this.receiptItems.add(receiptItemDto);
    }
}
