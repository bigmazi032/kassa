package ru.zinyakova;

import ru.zinyakova.dao.*;
import ru.zinyakova.entity.*;

import java.sql.Date;
import java.util.ArrayList;


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
//
//    public static void main(String[] args) {
//        Theatre theatre1 = new Theatre();
//        theatre1.setName("ТЮЗ");
//        theatreDao.create(theatre1);
//
//        Theatre theatre2 = new Theatre();
//        theatre2.setName("Театр на Таганке");
//        theatreDao.create(theatre2);
//
//        Theatre theatre = new Theatre();
//        theatre.setName("Драматический театр");
//
//        // создаем запись в таблице
//        Theatre theatreDb = theatreDao.create(theatre);
//        if (theatreDb != null) {
//            System.out.println("Создана запись театра id = " + theatreDb.getId());
//
//            // пробуем получить запись по id
//            Theatre theatreGet = theatreDao.get(theatreDb.getId());
//            if (theatreGet !=null) {
//                System.out.println("Получена запись. id = " + theatreGet.getId() + " Название = " + theatreGet.getName());
//            }
//        }
//
//        // получаем запись по названию
//        Theatre theatreTuz = theatreDao.getByName("ТЮЗ");
//        if (theatreTuz !=null) {
//            System.out.println("Получена запись ТЮЗа. id = " + theatreTuz.getId());
//        }
//    }
    private static ScheduleDao schedule = new ScheduleDao();
    private static SeatStatusDao seatStatus = new SeatStatusDao();
    public static ReceiptDao receiptDao = new ReceiptDao();
    public static ReceiptItemDao receiptItemDao = new ReceiptItemDao();

    public static void main(String[] args) {
        ArrayList<Theatre> allTheatres = schedule.getAllTheatres();
        for (int i = 0; i< allTheatres.size(); i++) {
            System.out.println("id: "+ allTheatres.get(i).getId() + " name: " + allTheatres.get(i).getName() );
        }
        ArrayList<Performance> performances = schedule.getPerformanceByTheatre(1l);
        for (Performance p: performances) { // форма записи для обхода коллекций
            System.out.println("id: "+ p.getId() + " name: " + p.getName() );
        }
        ArrayList<Schedule> schedules = schedule.getDateByTP(1l, 4l);
        for (Schedule s: schedules){
            System.out.println("id: "+ s.getId() + " date: " + s.getDate());
        }
        ArrayList<SeatStatus> seatStatuses = seatStatus.getSeatstatusBySheduleId(3l);
        for(SeatStatus s : seatStatuses){
            System.out.println("id: " + s.getId() + " name: " + s.getName() + " price: " + s.getPrice() + " free: " + s.getFree());
        }
        Receipt receipt = new Receipt();
        Date date = Date.valueOf("2020-11-12");
        receipt.setDate( date);
        receipt.setSumma(0l);
        receipt = receiptDao.createNewReceipt(receipt);

        receipt = receiptDao.getReceiptById(receipt.getId());
        System.out.println(receipt);
        receipt.setSumma(400l);
        int check = receiptDao.updateReceipt(receipt);

        ReceiptItem item = new ReceiptItem();
        item.setReceiptId(7l);
        item.setQuantity(0l);
        item.setSeatStatusId(8l);
        item.setSumma(0l);
        item = receiptItemDao.createNewReceiptItem(item);
        item.setQuantity(2l);
        item.setSumma(13l);
        System.out.println(item);
        int check2 = receiptItemDao.updateReceiptItem(item);
    }
}
