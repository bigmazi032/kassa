package ru.zinyakova.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Data
public class ReceiptDto {
    private Long id;
    private Date date;
    private List<ReceiptItemDto> receiptItems = new ArrayList<>();
    private Long summa;
    private BigDecimal summaWithDiscount;

    public void addReceiptItem (ReceiptItemDto receiptItemDto) {
        this.receiptItems.add(receiptItemDto);
    }
}
