package ru.zinyakova.kassa.dao;

import ru.zinyakova.kassa.entity.SeatCategory;
import ru.zinyakova.kassa.entity.SeatStatus;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class SeatStatusDao {
    private static DataSource dataSource = DaoFactory.getDataSource();

    //показать категории мест, цены и свободные места для данной
    private final String SHOW_SEAT_CATEGORY_SQL = "SELECT\n" +
            "       sc.id id,\n" +
            "       sc.name name\n" +
            "FROM seats_status ss\n" +
            "         JOIN seat_category sc on ss.seat_category_id = sc.id\n" +
            "         JOIN schedule s on ss.schedule_id = s.id\n" +
            "WHERE s.id = ?";

    public ArrayList<SeatCategory> getSeatCategoriesBySchedulerId(Long idD) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_SEAT_CATEGORY_SQL);
            preparedStatement.setLong(1, idD);
            ResultSet resultSet = preparedStatement.executeQuery();
            // создаем структуру ArrayList
            ArrayList<SeatCategory> seats = new ArrayList<>();
            while (resultSet.next()) {
                String nameS = resultSet.getString("name");
                Long id = resultSet.getLong("id");
                SeatCategory s = new SeatCategory();
                s.setName(nameS);
                s.setId(id);
                seats.add(s);
            }
            return seats;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    // показать места по афише и категории места
    private final String GET_SEAT_BY_SC_AND_CAT_SQL = "select  s.id id,\n" +
            "       sc.name name,\n" +
            "       s.free free,\n" +
            "        s.total total,\n" +
            "        s.price price\n" +
            "from seats_status s\n" +
            "join seat_category sc on s.seat_category_id = sc.id\n" +
            "where schedule_id = ? and  seat_category_id = ?";

    public SeatStatus getSeatStatusByScheduleAndCategory(Long idSchedule, Long idSeatCategory ){
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SEAT_BY_SC_AND_CAT_SQL);
            preparedStatement.setLong(1, idSchedule);
            preparedStatement.setLong(2, idSeatCategory);
            ResultSet resultSet = preparedStatement.executeQuery();
            SeatStatus seatStatus = null;
            while (resultSet.next()) {
                seatStatus = new SeatStatus();
                seatStatus.setFree(resultSet.getLong("free"));
                seatStatus.setId(resultSet.getLong("id"));
                seatStatus.setTotal(resultSet.getLong("total"));
                seatStatus.setPrice(resultSet.getLong("price"));
            }
            return seatStatus;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String PRICE_BY_SEAT_STATUS_ID_SQL = "select s.price price\n" +
            "from seats_status s\n" +
            "where s.id = ?";

    public Long getPriceBySeatStatusId(Long id){
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(PRICE_BY_SEAT_STATUS_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Long price = 0l;
            while (resultSet.next()) {
                price = resultSet.getLong("price");
            }
            return price;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private String GET_DATE_BY_SEAT_STATUS_ID_SQL = "select s2.date date\n" +
            "from seats_status s\n" +
            "join schedule s2 on s2.id = s.schedule_id\n" +
            "where s.id = ?";

    public Date getDateBySeatStatusId (Long id){
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_DATE_BY_SEAT_STATUS_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Date date = resultSet.getDate("date");
            return date;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }



    }

    private String UPDATE_FREE_SEATS_SQL = "UPDATE seats_status\n" +
            "SET free = ?\n" +
            "WHERE id = ?";

    public int updateFreeSeatsInSeatStaus(SeatStatus seatStatus) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FREE_SEATS_SQL);
            preparedStatement.setLong(1, seatStatus.getFree());
            preparedStatement.setLong(2, seatStatus.getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating seatStatus failed, no rows affected.");
            }
            return affectedRows;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }
}
