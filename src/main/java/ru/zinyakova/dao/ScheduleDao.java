package ru.zinyakova.dao;
import ru.zinyakova.entity.Performance;
import ru.zinyakova.entity.Schedule;
import ru.zinyakova.entity.Theatre;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class ScheduleDao {
    // подключились к бд
    private static DataSource dataSource = DaoFactory.getDataSource();


    // показать список театров
    private final String SHOW_THEATRES_SQL =
            "SELECT t.name\n" +
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
    private  final String SHOW_PERFORMANCES_SQL = "SELECT p.name\n" +
            "FROM schedule s\n" +
            "JOIN performance p on s.performance_id = p.id\n" +
            "JOIN theatre t on s.theatre_id = t.id\n" +
            "WHERE t.name = ?\n";

    public ArrayList<Performance> GetPerformanceByTheatre(Long id){
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
    private final String SHOW_DATE_SQL = "SELECT s.date\n" +
            "FROM schedule s\n" +
            "JOIN theatre t on s.theatre_id = t.id\n" +
            "JOIN performance p on s.performance_id = p.id\n" +
            "WHERE t.name = ? AND p.name = ?;\n";

    public ArrayList<Date> GetDateByTP(Long idT, Long idP){
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_DATE_SQL);
            preparedStatement.setLong(1, idT);
            preparedStatement.setLong(2, idP);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Date> dates = new ArrayList<>();
            while(resultSet.next()){
                Date date = resultSet.getDate("date");
                dates.add(date);
            }
            return  dates;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

}