package ru.zinyakova;

import ru.zinyakova.dao.DaoFactory;
import ru.zinyakova.dao.TheatreDao;
import ru.zinyakova.entity.Theatre;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {
//    public static void main(String[] args) {
//        Connection connection = null;
//        try {
//            connection = DaoFactory.getDataSource().getConnection();
//            if (connection != null) {
//                System.out.println("Поздравляю. Вы подключены к БД");
//            } else {
//                System.out.println("Упс. Что то пошло не так");
//            }
//        } catch (SQLException e) {
//            System.out.println("Упс. Ошибка при подключении");
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    System.out.println("Упс. Ошибка при закрытии конекта");
//                }
//            }
//
//        }
//    }

    private static TheatreDao theatreDao = new TheatreDao();

    public static void main(String[] args) {
        Theatre theatre = new Theatre();
        theatre.setName("Театр Маяковского");
        Theatre theatreDb = theatreDao.create(theatre);
        if (theatreDb != null) {
            System.out.println("Создана запись театра id = " + theatreDb.getId());
        }

        Theatre theatreTuz = theatreDao.getByName("ТЮЗ");
        if (theatreTuz !=null) {
            System.out.println("Получена запись ТЮЗа. id = " + theatreTuz.getId());
        }
    }
}
