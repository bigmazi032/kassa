package ru.zinyakova.kassa.dao;

import ru.zinyakova.kassa.entity.SeatStatus;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class SeatStatusDao {
    private static DataSource dataSource = DaoFactory.getDataSource();

    //показать категории мест, цены и свободные места для данной
    private final String SHOW_SEAT_CATEGORY_SQL = "SELECT s.id    id,\n" +
            "       sc.name name,\n" +
            "       s.price price,\n" +
            "       s.free  free\n" +
            "FROM seats_status s\n" +
            "         JOIN seat_category sc on s.seat_category_id = sc.id\n" +
            "         JOIN schedule s2 on s.schedule_id = s2.id\n" +
            "         JOIN theatre t on s2.theatre_id = t.id\n" +
            "         JOIN performance p on p.id = s2.performance_id\n" +
            "WHERE s2.id = ?\n";

    public ArrayList<SeatStatus> getSeatstatusBySheduleId(Long idD) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_SEAT_CATEGORY_SQL);
            preparedStatement.setLong(1, idD);
            ResultSet resultSet = preparedStatement.executeQuery();
            // создаем структуру ArrayList
            ArrayList<SeatStatus> seats = new ArrayList<>();
            while (resultSet.next()) {
                String nameS = resultSet.getString("name");
                Long id = resultSet.getLong("id");
                Long price = resultSet.getLong("price");
                Long free = resultSet.getLong("free");
                SeatStatus s = new SeatStatus();
                s.setName(nameS);
                s.setId(id);
                s.setPrice(price);
                s.setFree(free);
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
            SeatStatus seatStatus = new SeatStatus();
            seatStatus.setFree(resultSet.getLong("free"));
            seatStatus.setId(resultSet.getLong("id"));
            seatStatus.setTotal(resultSet.getLong("total"));
            seatStatus.setPrice(resultSet.getLong("price"));
            seatStatus.setName(resultSet.getString("name"));
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
}
