package ru.zinyakova.kassa.view;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import ru.zinyakova.kassa.service.dto.SchedulerDto;
import ru.zinyakova.kassa.service.dto.ReceiptItemDto;
import ru.zinyakova.kassa.service.dto.SeatStatusDto;

public class ReceiptItemTblView {
    private final SimpleStringProperty theatreName;
    private final SimpleStringProperty performanceName;
    private final SimpleStringProperty dateStr;
    private final SimpleStringProperty seatCategoryName;
    private final SimpleLongProperty quantity;
    private final SimpleLongProperty summ;

    public ReceiptItemTblView(ReceiptItemDto receiptItemDto) {
        SeatStatusDto seat = receiptItemDto.getSeat();
        SchedulerDto scheduler = seat.getScheduler();
        this.theatreName = new SimpleStringProperty(scheduler.getTheatre().getName());
        this.performanceName = new SimpleStringProperty(scheduler.getPerformance().getName());
        this.dateStr = new SimpleStringProperty(scheduler.getDate().toString());
        this.seatCategoryName = new SimpleStringProperty(seat.getSeatCategory().getName());
        this.quantity = new SimpleLongProperty(receiptItemDto.getQuantitySeats());
        this.summ = new SimpleLongProperty(seat.getPrice() * receiptItemDto.getQuantitySeats());
    }


    public String getTheatreName() {
        return theatreName.get();
    }


    public void setTheatreName(String theatreName) {
        this.theatreName.set(theatreName);
    }

    public String getPerformanceName() {
        return performanceName.get();
    }


    public void setPerformanceName(String performanceName) {
        this.performanceName.set(performanceName);
    }

    public String getDateStr() {
        return dateStr.get();
    }


    public void setDateStr(String dateStr) {
        this.dateStr.set(dateStr);
    }

    public String getSeatCategoryName() {
        return seatCategoryName.get();
    }


    public void setSeatCategoryName(String seatCategoryName) {
        this.seatCategoryName.set(seatCategoryName);
    }

    public long getQuantity() {
        return quantity.get();
    }

    public void setQuantity(long quantity) {
        this.quantity.set(quantity);
    }

    public long getSumm() {
        return summ.get();
    }

    public void setSumm(long summ) {
        this.summ.set(summ);
    }
}
