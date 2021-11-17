package ru.zinyakova.kassa.dao;

import ru.zinyakova.kassa.entity.ReceiptItemReturn;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class ReceiptItemReturnDao {
    private static DataSource dataSource = DaoFactory.getDataSource();

    private static String CREATE_RECEIPT_ITEM_RETURN_SQL = "INSERT  receipt_item_return( receipt_return_id, receipt_item_id, quantity, summa_item_return ) \n" +
            "VALUES (?,?,?,?)";

    public ReceiptItemReturn createNewReceiptItemReturn(ReceiptItemReturn item) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RECEIPT_ITEM_RETURN_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, item.getReceipt_return_id());
            preparedStatement.setLong(2, item.getItem_id());
            preparedStatement.setLong(3, item.getQuantity_return());
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

    private final String GET_ITEMS_OF_RECEIPT_SQL = "select r.id,\n" +
            "       r.receipt_return_id receiptId,\n" +
            "       r.receipt_item_id itemId,\n" +
            "       r.quantity quantity,\n" +
            "       r.summa_item_return summa_return\n" +
            "from receipt_item_return r\n" +
            "where r.id = ?";

    public ArrayList<ReceiptItemReturn> getItemsReturnOfReceipt(Long receiptId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ITEMS_OF_RECEIPT_SQL);
            preparedStatement.setLong(1, receiptId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<ReceiptItemReturn> items = new ArrayList<>();
            while (resultSet.next()) {
                ReceiptItemReturn i = new ReceiptItemReturn();
                i.setReceipt_return_id(resultSet.getLong("receiptId"));
                i.setItem_id(resultSet.getLong("itemId"));
                i.setSumma(resultSet.getLong("summa_return"));
                i.setQuantity_return(resultSet.getLong("quantity"));
                items.add(i);
            }
            return items;
        } catch (SQLException e) {
            throw new IllegalStateException("No connection", e);
        }
    }

    private String GET_PREV_ITEM_BY_ID_SQL = "select r.receipt_item_id id\n" +
            "from receipt_item_return r\n" +
            "where r.id = ?";

    public long getPrevItemById(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PREV_ITEM_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getLong("id");
        }catch (SQLException e) {
        throw new IllegalStateException("Error during execution.", e);
        }
    }
}

