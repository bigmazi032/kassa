package ru.zinyakova.dao;

import com.zaxxer.hikari.HikariDataSource;


import javax.sql.DataSource;


public class DaoFactory {


    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = getHikariDataSource();
            //initDataBase();
            return dataSource;

        } else {
            return dataSource;
        }
    }

    public static void clearDataSource () {
        dataSource = null;
    }

    private static HikariDataSource getHikariDataSource () {
        // 1) Создать локально базу данных cashbox
        // 2) Добавить пользователя с логином devel и паролем qwerty со всеми привелегиями для работы с cashbox
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(System.getProperty("jdbcUrl", "jdbc:mysql://localhost:3306/cashbox"));
        hikariDataSource.setUsername(System.getProperty("jdbcUsername","devel"));
        hikariDataSource.setPassword(System.getProperty("jdbcPassword","qwerty"));

        return  hikariDataSource;

    }


//    private static void initDataBase () {
//        try {
//            DatabaseConnection connection  = new JdbcConnection(dataSource.getConnection());
//            Database database =
//                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
//            Liquibase liquibase = new Liquibase("liquibase.xml",
//                    new ClassLoaderResourceAccessor(),
//                    database);
//
//            liquibase.update(new Contexts());
//
//        } catch (SQLException | LiquibaseException e) {
//            e.printStackTrace();
//        }
//    }

//    private static UserDao userDao;
//
//    public static UserDao getUserDao() {
//        if (userDao == null) {
//            userDao = new UserDao(getDataSource());
//        }
//        return userDao;
//    }
//
//
//    private static AccountDao accountDAO;
//
//    public static AccountDao getAccountDao() {
//        if (accountDAO == null) {
//            accountDAO = new AccountDao(getDataSource());
//        }
//        return accountDAO;
//    }
//
//
//    private static CategoryTransactionDao categoryTransactionDao;
//
//    public static CategoryTransactionDao getCategoryTransactionDao() {
//        if (categoryTransactionDao == null) {
//            categoryTransactionDao = new CategoryTransactionDao(getDataSource());
//        }
//        return categoryTransactionDao;
//    }
//
//
//    private static TransactionDao transactionDao;
//
//    public static TransactionDao getTransactionDao() {
//        if (transactionDao == null) {
//            transactionDao = new TransactionDao(getDataSource());
//        }
//        return transactionDao;
//    }
//
//
//    private static InternalTransactionDao internalTransactionDao;
//
//    public static InternalTransactionDao getInternalTransactionDao() {
//        if (internalTransactionDao == null) {
//            internalTransactionDao = new InternalTransactionDao(
//                    getDataSource());
//        }
//        return internalTransactionDao;
//    }
}



