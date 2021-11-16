package ru.zinyakova.dao;

import ru.zinyakova.entity.Receipt;
import ru.zinyakova.entity.ReceiptReturn;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;



public class ReceiptReturnDao {

    private static DataSource dataSource = DaoFactory.getDataSource();

    private String CREATE_RECEIPT_RETURN_SQL = "INSERT receipt_return (receipt_id, date_return, summa_return)\n" +
            "VALUES (?, ?, ?)";

    public ReceiptReturn createReceiptReturn (ReceiptReturn r){
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RECEIPT_RETURN_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, r.getReceipt_id());
            preparedStatement.setDate(2, r.getDate_return());
            preparedStatement.setLong(3, r.getSumma_return());
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

    private  final String UPDATE_RECEIPT_RETURN_SQL = "UPDATE receipt_return\n" +
            "SET summa_return = ?\n" +
            "WHERE receipt_return.id = ?";

    public int updateReceiptReturn(ReceiptReturn receipt){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RECEIPT_RETURN_SQL);
            preparedStatement.setLong(1,receipt.getSumma_return());
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
}
