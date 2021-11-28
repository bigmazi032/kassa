package ru.zinyakova.kassa.dao;
import ru.zinyakova.kassa.entity.Performance;
import ru.zinyakova.kassa.entity.Schedule;
import ru.zinyakova.kassa.entity.Theatre;
import ru.zinyakova.kassa.service.dto.SimpleSchedule;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class ScheduleDao {
    // подключились к бд
    private static DataSource dataSource = DaoFactory.getDataSource();


    // показать список театров
    private final String SHOW_THEATRES_SQL = "SELECT DISTINCT\n" +
            "    t.id theatre_id,\n" +
            "    t.name theatre_name\n" +
            "FROM\n" +
            "    schedule s\n" +
            "    JOIN theatre t on s.theatre_id = t.id";

    public ArrayList<Theatre> getAllTheatres () {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_THEATRES_SQL);
            ResultSet resultSet = preparedStatement.executeQuery();
            // создаем структуру ArrayList
            ArrayList<Theatre> theatres = new ArrayList<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("theatre_id");
                String nameTheatre = resultSet.getString("theatre_name");
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

    private final String SHOW_DATE_THEATRE_BY_PLAY_SQL = "SELECT t.name   name,\n" +
            "       s.date date\n" +
            "FROM schedule s\n" +
            "         JOIN theatre t on s.theatre_id = t.id\n" +
            "         JOIN performance p on s.performance_id = p.id\n" +
            "WHERE upper(p.name) LIKE ?";

    public ArrayList<SimpleSchedule> getScheduleByPerfomance (String playName){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_DATE_THEATRE_BY_PLAY_SQL);
            preparedStatement.setString(1, "%" + playName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<SimpleSchedule> schedules = new ArrayList<>();
            while(resultSet.next()){
                SimpleSchedule s = new SimpleSchedule();
                s.setTheareName(resultSet.getString("name"));
                s.setDate(resultSet.getDate("date").toLocalDate());
                schedules.add(s);
            }
            return  schedules;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }
}
