package ru.zinyakova.dao;

import ru.zinyakova.entity.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;


public class ReceiptDao {

    private static DataSource dataSource = DaoFactory.getDataSource();

    private final String CREATE_RECEIPT_SQL = "INSERT receipt (date, summa)\n" +
            "VALUES (?, ?)";

    public Receipt createNewReceipt(Receipt r){
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RECEIPT_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, r.getDate());
            preparedStatement.setLong(2, r.getSumma());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating theatre failed, no rows affected.");
            }
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    r.setId(resultSet.getLong(1));
                } else {
                    throw new SQLException("Creating receipt failed, no ID obtained.");
                }
            } return r;
        }catch (SQLException e){
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String SHOW_RECEIPT_SQL = "select r.id id,\n" +
            "       r.date date,\n" +
            "       r.summa sum\n" +
            "from receipt r\n" +
            "where r.id = ?";

    public Receipt getReceiptById( Long id){
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_RECEIPT_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Receipt receipt = null;
            while (resultSet.next()) {
                Long rId = resultSet.getLong("id");
                Date rDate= resultSet.getDate("date");
                Long sum = resultSet.getLong("sum");
                receipt = new Receipt();
                receipt.setId(rId);
                receipt.setDate(rDate);
                receipt.setSumma(sum);
            }
            return receipt;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private  final String UPDATE_RECEIPT_SQL = "UPDATE receipt\n" +
            "SET summa = ?\n" +
            "WHERE receipt.id = ?";

    public int updateReceipt(Receipt receipt){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RECEIPT_SQL);
            preparedStatement.setLong(1,receipt.getSumma());
            preparedStatement.setLong(2,receipt.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0){
                throw new SQLException("Updating receipt failed, no rows affected.");
            }
            return affectedRows;
        }catch ( SQLException e){
            throw new IllegalStateException("No connection", e);
        }
    }

    private String GET_FULL_RECEIPT_INFO_BY_ID_SQL = "SELECT r.summa receipt_summa,\n" +
            "       it.id item_id,\n" +
            "       r.id receipt_id,\n" +
            "       r.date datePurchase,\n" +
            "       s.id schedule_id,\n" +
            "       ss.id seat_status_id,\n" +
            "       t.id theatre_id,\n" +
            "       t.name theatre,\n" +
            "       p.id play_id,\n" +
            "       p.name performance,\n" +
            "       s.date performanceDate,\n" +
            "       sc.id seat_category_id,\n" +
            "       sc.name seat,\n" +
            "       it.quantity quantity,\n" +
            "       ss.price price,\n" +
            "        ss.total total,\n" +
            "       ss.free,\n" +
            "       it.summa summa\n" +
            "FROM receipt_item it\n" +
            "JOIN receipt r on r.id = it.receipt_id\n" +
            "JOIN seats_status ss on it.seats_status_id = ss.id\n" +
            "JOIN seat_category sc on ss.seat_category_id = sc.id\n" +
            "JOIN schedule s on s.id = ss.schedule_id\n" +
            "JOIN performance p on p.id = s.performance_id\n" +
            "JOIN theatre t on t.id = s.theatre_id\n" +
            "WHERE receipt_id = ?";

    public FullReceipt getFullReceipt (Long receiptId){
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_FULL_RECEIPT_INFO_BY_ID_SQL);
            preparedStatement.setLong(1, receiptId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet == null){
                return null;
            }
            FullReceipt receipt = new FullReceipt();
            while (resultSet.next()) {
                Theatre newTheatre = new Theatre();
                newTheatre.setId(resultSet.getLong("theatre_id"));
                newTheatre.setName(resultSet.getString("theatre"));
                Performance play = new Performance();
                play.setId(resultSet.getLong("play_id"));
                play.setName(resultSet.getString("performance"));
                FullSchedule sc = new FullSchedule();
                sc.setPerformance(play);
                sc.setTheatre(newTheatre);
                sc.setId(resultSet.getLong("schedule_id"));
                sc.setDate(resultSet.getDate("performanceDate"));
                SeatCategory seat = new SeatCategory();
                seat.setId(resultSet.getLong("seat_category_id"));
                seat.setName(resultSet.getString("seat"));
                FullSeatStatus seatStatus = new FullSeatStatus();
                seatStatus.setId(resultSet.getLong("seat_status_id"));
                seatStatus.setSchedule(sc);
                seatStatus.setPrice(resultSet.getLong("price"));
                seatStatus.setSeatCategory(seat);
                seatStatus.setTotal(resultSet.getLong("total"));
                seatStatus.setFree(resultSet.getLong("free"));
                FullReceiptItem item = new FullReceiptItem();
                item.setId(resultSet.getLong("item_id"));
                item.setSeatStatus(seatStatus);
                item.setReceiptId(resultSet.getLong("receipt_id"));
                item.setQuantity(resultSet.getLong("quantity"));
                item.setSumma(resultSet.getLong("summa"));
                receipt.setId(resultSet.getLong("receipt_id"));
                receipt.getItems().add(item);
                receipt.setDate(resultSet.getDate("datePurchase"));
                receipt.setSumma(resultSet.getLong("receipt_summa"));
            }
            return receipt;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }
}
