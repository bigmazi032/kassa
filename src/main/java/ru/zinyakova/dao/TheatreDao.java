package ru.zinyakova.dao;

import ru.zinyakova.entity.Theatre;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// https://metanit.com/java/database/2.6.php

public class TheatreDao {

    private static DataSource dataSource = DaoFactory.getDataSource();

    private final String CREATE_THEATRE_SQL =
            "INSERT  theatre (name) " +
                    "VALUES (?)";

    public int create (Theatre theatre) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_THEATRE_SQL);
            preparedStatement.setString(1, theatre.getName());
            int row = preparedStatement.executeUpdate();
            return row;
        }catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    public Theatre get (Long id) {
        return null;
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
}
