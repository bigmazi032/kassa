package ru.zinyakova.kassa.view;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import ru.zinyakova.kassa.service.dto.ReceiptItemDto;

import java.util.Objects;

public class ReturnTicketField extends Pane {
    Label npp;
    Label ticket;
    TextField returnQuantity;
    private final ReceiptItemDto receiptItem;

    public ReturnTicketField(int number, ReceiptItemDto receiptItemDto) {
        this.receiptItem = receiptItemDto;
        this.setPrefHeight(31.0);
        this.setPrefWidth(800.0);
        npp = new Label(String.valueOf(number));
        npp.setLayoutX(14.0);
        npp.setLayoutY(6.0);
        String theatre = receiptItem.getSeat().getScheduler().getTheatre().getName();
        String performance = receiptItem.getSeat().getScheduler().getPerformance().getName();
        String date = receiptItem.getSeat().getScheduler().getDate().toString();
        String seatCategory = receiptItem.getSeat().getSeatCategory().getName();
        String quantitySeats = receiptItem.getQuantitySeats().toString();
        ticket = new Label(theatre + ", "
                + performance +", " + date + ", " + seatCategory+ ", " + quantitySeats + "мест");
        ticket.setLayoutX(83.0);
        ticket.setLayoutY(6.0);
        returnQuantity = new TextField();
        returnQuantity.setLayoutX(698.0);
        returnQuantity.setLayoutY(6.0);
        returnQuantity.setPrefWidth(64.0);
        this.getChildren().add(npp);
        this.getChildren().add(ticket);
        this.getChildren().add(returnQuantity);
    }

    public Long getQuantityToReturn() {
        if (Objects.isNull(returnQuantity.getText()) || returnQuantity.getText().isEmpty()) {
            return 0L;
        } else {
            return Long.parseLong(returnQuantity.getText());
        }

    }

    public ReceiptItemDto getReceiptItemDto() {
        return receiptItem;
    }
}

