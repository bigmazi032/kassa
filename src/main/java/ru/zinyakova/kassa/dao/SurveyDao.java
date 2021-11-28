package ru.zinyakova.kassa.dao;

import ru.zinyakova.kassa.entity.Performance;
import ru.zinyakova.kassa.entity.Receipt;
import ru.zinyakova.kassa.entity.Schedule;
import ru.zinyakova.kassa.entity.Theatre;


import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class SurveyDao {
    private static DataSource dataSource = DaoFactory.getDataSource();

    private final String SEARCH_BY_PRIMARY_KEY_SQL = "select id,\n" +
            "       name ,\n" +
            "       manager\n" +
            "from test_theatre\n" +
            "where id = ?";

    public long searchByPrimaryKey(long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_PRIMARY_KEY_SQL);
            preparedStatement.setLong(1, id);
            long startTime = System.nanoTime();
            preparedStatement.execute();
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String SEARCH_BY_NOT_PRIMARY_KEY_SQL = "select id,\n" +
            "       name,\n" +
            "       manager\n" +
            "from test_theatre\n" +
            "where name = ?";

    public long searchByNotPrimaryKey(String name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_NOT_PRIMARY_KEY_SQL);
            preparedStatement.setString(1, name);
            long startTime = System.nanoTime();
            preparedStatement.execute();
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String SEARCH_BY_MASK_SQL = "select id,\n" +
            "       name,\n" +
            "       manager\n" +
            "from test_theatre\n" +
            "where name like ?";

    public long searchByMask(String text) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_BY_MASK_SQL);
            preparedStatement.setString(1, "%" + text + "%");
            long startTime = System.nanoTime();
            preparedStatement.execute();
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String INSERT_THEATRE_SQL =
            "insert  into test_theatre (name, manager, address)\n" +
                    "values (?, ?, ?)";

    public Long createTheatre(String name, String manager, String address) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_THEATRE_SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, manager);
            preparedStatement.setString(3, address);
            long startTime = System.nanoTime();
            preparedStatement.execute();
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }


    public long createAFewTheatres(ArrayList<String> names, ArrayList<String> managers, ArrayList<String> addresses) {
        try (Connection connection = dataSource.getConnection()) {
            long time = 0;
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_THEATRE_SQL);
            for (int i = 0; i < names.size(); i++) {
                String theatre_name = names.get(i);
                String manager_name = managers.get(i);
                String address = addresses.get(i);


                preparedStatement.setString(1, theatre_name);
                preparedStatement.setString(2, manager_name);
                preparedStatement.setString(3, address);
                preparedStatement.addBatch();

                if ((i > 0 && i % 1000 == 0 )|| i == names.size() - 1) {
                    long startTime = System.nanoTime();
                    preparedStatement.executeBatch();
                    long endTime = System.nanoTime();
                    time = endTime - startTime;
                    time += time;
                }
            }
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }

    }

    private final String UPDATE_THEATRES_SQL =
            "update test_theatre\n" +
                    "set manager = ?\n" +
                    "where id = ?";

    public long update(Long id, String manager_name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_THEATRES_SQL);
            preparedStatement.setString(1, manager_name);
            preparedStatement.setLong(2, id);
            long startTime = System.nanoTime();
            preparedStatement.execute();
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String UPDATE_THEATRE_BY_NOT_PRIMATY_KEY_SQL =
            "update test_theatre\n" +
                    "set name = ?\n" +
                    "where manager = ?";

    public long updateByNotPrimary(String manager, String name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_THEATRE_BY_NOT_PRIMATY_KEY_SQL);
            preparedStatement.setString(2, manager);
            preparedStatement.setString(1, name);
            long startTime = System.nanoTime();
            preparedStatement.execute();
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String DELETE_THEARE_SQL =
            "delete from test_theatre\n" +
                    "where id = ?";

    public long deleteTheatre(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_THEARE_SQL);
            preparedStatement.setLong(1, id);
            long startTime = System.nanoTime();
            preparedStatement.execute();
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String DELETE_THEATRE_BY_ADDRESS_SQL =
            "delete from test_theatre\n" +
                    "where address = ?";

    public long deleteTheatreByNotPrimaryKey(String address) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_THEATRE_BY_ADDRESS_SQL);
            preparedStatement.setString(1, address);
            long startTime = System.nanoTime();
            preparedStatement.execute();
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    public int deleteGroupTheatre(ArrayList<Long> ids) {
        try (Connection connection = dataSource.getConnection()) {
            long time = 0;
            for (Long id : ids) {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_THEARE_SQL);
                preparedStatement.setLong(1, id);
                long startTime = System.nanoTime();
                preparedStatement.execute();
                long endTime = System.nanoTime();
                time += endTime - startTime;
            }
            return ids.size();
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

//    private final String CREATE_TABLE_SQL =
//            "CREATE TABLE test_theatre\n" +
//                    "(id INT PRIMARY KEY AUTO_INCREMENT,\n" +
//                    " name VARCHAR(50) UNIQUE,\n" +
//                    " manager VARCHAR(50)\n" +
//                    ")";

    private final String CREATE_TABLE_SQL = "CREATE TABLE test_theatre\n" +
            "(id SERIAL PRIMARY KEY ,\n" +
            " name VARCHAR(50),\n" +
            " manager VARCHAR(50),\n" +
            " address VARCHAR(50)\n" +
            ")";

    public void createTestTable() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_TABLE_SQL);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String DELETE_TABLE_SQL = "drop table test_theatre";


    public void deleteTestTable() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TABLE_SQL);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }


    private final String UPDATE_THEATRES_NAME_SQL =
            "update test_theatre\n" +
                    "set name = ?\n" +
                    "where id = ?";

    public long updateTheatreName(Long id, String name) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_THEATRES_NAME_SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, id);
            long startTime = System.nanoTime();
            preparedStatement.execute();
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }

    private final String OPTIMIZATION_SQL =
            "OPTIMIZE TABLE cashbox.test_theatre";

    public long optimization() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(OPTIMIZATION_SQL);
            long startTime = System.nanoTime();
            preparedStatement.execute();
            long endTime = System.nanoTime();
            long time = endTime - startTime;
            return time;
        } catch (SQLException e) {
            throw new IllegalStateException("Error during execution.", e);
        }
    }


    private String REPORT_KOEF_SUCCESS_SQL = "select\n" +
            "       t.name theatre,\n" +
            "       p.name performance,\n" +
            "       sc.name seat_category,\n" +
            "       sum((ss.total - ss.free)) as sold,\n" +
            "        sum((ss.total - ss.free)) / sum(ss.total) as succsess\n" +
            "from seats_status ss\n" +
            "         join seat_category sc on ss.seat_category_id = sc.id\n" +
            "         join schedule s on ss.schedule_id = s.id\n" +
            "         join performance p on s.performance_id = p.id\n" +
            "         join theatre t on t.id = s.theatre_id\n" +
            "where s.date > '2021-00-00' and s.date < '2022-00-00'\n" +
            "group by t.name, p.name, sc.name\n" +
            "order by t.name, p.name";


    public void report() throws IOException {
        try (PrintWriter writer = new PrintWriter(new File("test.csv"))) {
            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(REPORT_KOEF_SUCCESS_SQL);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    StringBuilder sb = new StringBuilder();

                    sb.append(resultSet.getString("theatre"));
                    sb.append(',');
                    sb.append(resultSet.getString("performance"));
                    sb.append(',');
                    sb.append(resultSet.getString("seat_category"));
                    sb.append(',');
                    sb.append(resultSet.getLong("sold"));
                    sb.append(',');
                    sb.append(resultSet.getLong("succsess"));
                    sb.append('\n');
                    writer.write(sb.toString());
                }


            } catch (SQLException e) {
                throw new IllegalStateException("Error during execution.", e);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


}
