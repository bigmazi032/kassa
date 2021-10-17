package ru.zinyakova;

import ru.zinyakova.dao.DaoFactory;
import ru.zinyakova.dao.TheatreDao;
import ru.zinyakova.entity.Theatre;



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
        theatre.setName("Драматический театр2");

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
