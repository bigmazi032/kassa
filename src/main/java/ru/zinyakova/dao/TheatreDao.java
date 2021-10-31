package ru.zinyakova.dao;

import ru.zinyakova.entity.Theatre;

import javax.sql.DataSource;
import java.sql.*;

// https://metanit.com/java/database/2.6.php

public class TheatreDao {

    private static DataSource dataSource = DaoFactory.getDataSource();

    private final String CREATE_THEATRE_SQL =
            "INSERT  theatre (name) " +
                    "VALUES (?)";

    public Theatre create (Theatre theatre) {
        try (Connection connection = dataSource.getConnection()) {
            // создаем запрос
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_THEATRE_SQL,
                                                                    Statement.RETURN_GENERATED_KEYS);
            // добавляем в запрос значения праметров
            preparedStatement.setString(1, theatre.getName());
            // запускаем запрос на выполнение
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating theatre failed, no rows affected.");
            }
            // извлекается из таблицы запись, которую мы только что добавили
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    // добавляем в наш объект, который пришел id созданной записи
                    theatre.setId(resultSet.getLong(1));
                }
                else {
                    throw new SQLException("Creating theatre failed, no ID obtained.");
                }
            }
            return theatre;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String GET_THEATRE_BY_ID_SQL =
            "SELECT\n" +
                    "    id,\n" +
                    "    name\n" +
                    "FROM theatre\n" +
                    "WHERE id = ?";
    public Theatre get (Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_THEATRE_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Theatre theatre = null;
            while (resultSet.next()) {
                Long theatreId = resultSet.getLong("id");
                String nameTheatre = resultSet.getString("name");
                theatre = new Theatre();
                theatre.setId(theatreId);
                theatre.setName(nameTheatre);
            }
            return theatre;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String GET_THEATRE_BY_NAME_SQL =
            "SELECT\n" +
                    "    id,\n" +
                    "    name\n" +
                    "FROM theatre\n" +
                    "WHERE name = ?";
    public Theatre getByName (String name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_THEATRE_BY_NAME_SQL);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            Theatre theatre = null;
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String nameTheatre = resultSet.getString("name");
                theatre = new Theatre();
                theatre.setId(id);
                theatre.setName(nameTheatre);
            }
            return theatre;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    public Theatre update (Theatre obj) {
        return null;
    }

    public Theatre delete (Theatre obj) {
        return null;
    }

    private static String GET_THEATRE_BY_NAME =
            "SELECT\n" +
            "    id,\n" +
            "    name\n" +
            "FROM theatre\n" +
            "WHERE name = ?";
}
