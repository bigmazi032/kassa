package ru.zinyakova.dao;

import ru.zinyakova.entity.Receipt;
import ru.zinyakova.entity.ReceiptItem;
import ru.zinyakova.entity.Schedule;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class ReceiptItemDao {

    private static DataSource dataSource = DaoFactory.getDataSource();

    private static String CREATE_RECEIPT_ITEM_SQL = "INSERT receipt_item (receipt_id, seats_status_id, quantity, summa)\n" +
            "VALUES (?,?,?,?)";

    public ReceiptItem createNewReceiptItem(ReceiptItem item) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RECEIPT_ITEM_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, item.getReceiptId());
            preparedStatement.setLong(2, item.getSeatStatusId());
            preparedStatement.setLong(3, item.getQuantity());
            preparedStatement.setLong(4, item.getSumma());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating theatre failed, no rows affected.");
            }
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    item.setId(resultSet.getLong(1));
                } else {
                    throw new SQLException("Creating receipt failed, no ID obtained.");
                }
            }
            return item;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String SHOW_ITEM_BY_ID_SQL = "SELECT i.id id,\n" +
            "       i.seats_status_id seatStatus,\n" +
            "       i.quantity quantity,\n" +
            "       i.summa summa\n" +
            "FROM receipt_item i\n" +
            "WHERE i.id = ?";

    public ReceiptItem getReceiptItemById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_ITEM_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ReceiptItem item = null;
            while (resultSet.next()) {
                Long rId = resultSet.getLong("id");
                Long seatId = resultSet.getLong("seatStatus");
                Long quantity = resultSet.getLong("quantity");
                Long summa = resultSet.getLong("summa");
                item = new ReceiptItem();
                item.setId(rId);
                item.setSeatStatusId(seatId);
                item.setQuantity(quantity);
                item.setSumma(summa);
            }
            return item;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String UPDATE_RECEIPT_ITEM_SQL = "UPDATE receipt_item i\n" +
            "SET quantity = ?, summa = ?\n" +
            "WHERE i.id = ?";

    public int updateReceiptItem(ReceiptItem item) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RECEIPT_ITEM_SQL);
            preparedStatement.setLong(1, item.getQuantity());
            preparedStatement.setLong(2, item.getSumma());
            preparedStatement.setLong(3, item.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating receipt failed, no rows affected.");
            }
            return affectedRows;
        } catch (SQLException e) {
            throw new IllegalStateException("No connection", e);
        }
    }

    private final String GET_ITEMS_OF_RECEIPT_SQL = "SELECT r.id id,\n" +
            "       r.seats_status_id seat,\n" +
            "       r.quantity quantity,\n" +
            "       r.summa summa\n" +
            "FROM receipt_item r\n" +
            "WHERE r.receipt_id = ?";

    public ArrayList<ReceiptItem> getItemsOfReceipt(Long receiptId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RECEIPT_ITEM_SQL);
            preparedStatement = connection.prepareStatement(GET_ITEMS_OF_RECEIPT_SQL);
            preparedStatement.setLong(1, receiptId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<ReceiptItem> items = new ArrayList<>();
            while(resultSet.next()){
                ReceiptItem i = new ReceiptItem();
                i.setReceiptId(resultSet.getLong("id"));
                i.setSeatStatusId(resultSet.getLong("seat"));
                i.setSumma(resultSet.getLong("summa"));
                i.setQuantity(resultSet.getLong("quantity"));
                items.add(i);
            }
            return items;
        } catch (SQLException e) {
            throw new IllegalStateException("No connection", e);
        }
    }
}
