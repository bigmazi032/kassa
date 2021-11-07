package ru.zinyakova.dao;

import ru.zinyakova.entity.SeatStatus;
import ru.zinyakova.entity.Receipt;
import ru.zinyakova.entity.Schedule;
import ru.zinyakova.entity.Theatre;

import javax.sql.DataSource;
import java.sql.*;


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
}
