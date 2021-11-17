package ru.zinyakova.kassa.dao;
import ru.zinyakova.kassa.entity.Performance;
import ru.zinyakova.kassa.entity.Schedule;
import ru.zinyakova.kassa.entity.Theatre;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class ScheduleDao {
    // подключились к бд
    private static DataSource dataSource = DaoFactory.getDataSource();


    // показать список театров
    private final String SHOW_THEATRES_SQL = "SELECT t.id id,\n" +
            "       t.name name\n" +
            "FROM theatre t";

    public ArrayList<Theatre> getAllTheatres () {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_THEATRES_SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            // создаем структуру ArrayList
            ArrayList<Theatre> theatres = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nameTheatre = resultSet.getString("name");
                Theatre theatre = new Theatre();
                theatre.setId(id);
                theatre.setName(nameTheatre);
                theatres.add(theatre);
            }
            return theatres;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    // показать выступления по конкретному театру
    private  final String SHOW_PERFORMANCES_SQL = "SELECT p.id   id,\n" +
            "       p.name name\n" +
            "FROM schedule s\n" +
            "         JOIN performance p on s.performance_id = p.id\n" +
            "         JOIN theatre t on s.theatre_id = t.id\n" +
            "WHERE t.id = ?";

    public ArrayList<Performance> getPerformanceByTheatre(Long id){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_PERFORMANCES_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Performance> performances = new ArrayList<>();
            while(resultSet.next()){
                Long id_p = resultSet.getLong("id");
                String name_p = resultSet.getString("name");
                Performance p = new Performance();
                p.setId(id_p);
                p.setName(name_p);
                performances.add(p);
            }
            return  performances;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    // показать даты конкретного выступления в конкреном театре
    private final String SHOW_DATE_SQL = "SELECT s.id   id,\n" +
            "       s.date date\n" +
            "FROM schedule s\n" +
            "         JOIN theatre t on s.theatre_id = t.id\n" +
            "         JOIN performance p on s.performance_id = p.id\n" +
            "WHERE t.id = ?\n" +
            "  AND p.id = ?\n";

    public ArrayList<Schedule> getDateByTP(Long idT, Long idP){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_DATE_SQL);
            preparedStatement.setLong(1, idT);
            preparedStatement.setLong(2, idP);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Schedule> schedules = new ArrayList<>();
            while(resultSet.next()){
                Schedule s = new Schedule();
                s.setId(resultSet.getLong("id"));
                s.setDate(resultSet.getDate("date"));
                schedules.add(s);
            }
            return  schedules;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }
}
