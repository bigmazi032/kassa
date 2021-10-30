package ru.zinyakova;

import ru.zinyakova.dao.DaoFactory;
import ru.zinyakova.dao.TheatreDao;
import ru.zinyakova.entity.Theatre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {


//    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cashbox?" +
//                    "user=devel&password=qwerty");
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
//                    System.out.println("Закрываем соединение");
//                } catch (SQLException e) {
//                    System.out.println("Упс. Ошибка при закрытии конекта");
//                }
//            }
//
//        }
//    }

    private static TheatreDao theatreDao = new TheatreDao();

    public static void main(String[] args) {
        Theatre theatre1 = new Theatre();
        theatre1.setName("ТЮЗ");
        theatreDao.create(theatre1);

        Theatre theatre2 = new Theatre();
        theatre2.setName("Театр на Таганке");
        theatreDao.create(theatre2);

        Theatre theatre = new Theatre();
        theatre.setName("Драматический театр");

        // создаем запись в таблице
        Theatre theatreDb = theatreDao.create(theatre);
        if (theatreDb != null) {
            System.out.println("Создана запись театра id = " + theatreDb.getId());

            // пробуем получить запись по id
            Theatre theatreGet = theatreDao.get(theatreDb.getId());
            if (theatreGet !=null) {
                System.out.println("Получена запись. id = " + theatreGet.getId() + " Название = " + theatreGet.getName());
            }
        }

        // получаем запись по названию
        Theatre theatreTuz = theatreDao.getByName("ТЮЗ");
        if (theatreTuz !=null) {
            System.out.println("Получена запись ТЮЗа. id = " + theatreTuz.getId());
        }
    }
}
