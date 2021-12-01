package ru.zinyakova.kassa.service;


import ru.zinyakova.kassa.dao.SurveyDao;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

public class SurveyService {
    SurveyDao serveyDao = new SurveyDao();

    public void test(int quantity){
        try {
            serveyDao.createTestTable();
            serveyDao.addIndex();
            fillTable(quantity);
            ArrayList<String> nameTheatres = generateRandomList(10);
            ArrayList<String> nameManagers = generateRandomList(10);
            ArrayList<String> nameAddress = generateRandomList(10);
            ArrayList<Long> ids = new ArrayList<>();
            ids.add((long)(Math.random()*quantity));
            ids.add((long)(Math.random()*quantity));
            ids.add((long)(Math.random()*quantity));
            ids.add((long)(Math.random()*quantity));
            ids.add((long)(Math.random()*quantity));
            // делаю тесты

            //Long index;
            int q = 10;
            long time = repeat(q, () -> serveyDao.createTheatre(getOneRndValue(), "Pushkin", "москва"));
            System.out.println("Добавление записи " + format(time));

            time = repeat(q, () -> serveyDao.createAFewTheatres(generateRandomList(10), generateRandomList(10), generateRandomList(10)));
            System.out.println("Добавление группы записей " + format(time));

            System.out.println("");
//             разогрев
            serveyDao.searchByNotPrimaryKey(names.get(getRandomIndex(quantity).intValue()));

            time = repeat(q, () -> serveyDao.searchByIndex(names.get(getRandomIndex(quantity).intValue())));
            System.out.println("Поиск записи по ключевому полю " + format(time));

            time = repeat(q, () -> serveyDao.searchByNotPrimaryKey(managers.get(getRandomIndex(quantity).intValue())));
            System.out.println("Поиск записи по не ключевому полю " + format(time));
            time = repeat(q, () -> serveyDao.searchByMask(names.get(getRandomIndex(quantity).intValue()).substring(1, 1 + getRandomIndex(8).intValue())));
            System.out.println("Поиск записи по маске " + format(time));
            System.out.println("");
            time =  repeat(q, () -> serveyDao.update(names.get(getRandomIndex(quantity).intValue()), getOneRndValue()));
            System.out.println("Изменение записи (определение изменяемой записи по ключевому полю) " + format(time));
            time =  repeat(q, () -> serveyDao.updateByNotPrimary(managers.get(getRandomIndex(quantity).intValue()), getOneRndValue()));
            System.out.println("Изменение записи (определение изменяемой записи по не ключевому полю) " + format(time));
            System.out.println("");
            time =  repeat(q, () -> serveyDao.deleteTheatreByIndex(names.get(getRandomIndex(quantity).intValue())));
            System.out.println("Удаление записи (определение удаляемой записи по ключевому полю) " + format(time));

            time =  repeat(q, () -> serveyDao.deleteTheatreByNotPrimaryKey(addressList.get(getRandomIndex(quantity).intValue())));
            System.out.println("Удаление записи (определение удаляемой записи по не ключевому полю) " + format(time));
            System.out.println("");
            System.out.println("Удаление группы записей " + format(serveyDao.deleteGroupTheatre(ids)));
        } catch (Exception e) {
            throw e;
        } finally {
             serveyDao.deleteTestTable();
        }
    }

    private Long repeat(int k, Supplier<Long> request) {
        long time = 0;
        for (int i = 1; i <= k; i++) {
            Long oneTime = request.get();
            time += oneTime;
        }
        return time/k;
    }

    private Long getRandomIndex(int quantity) {
        return Long.valueOf((long) (Math.random() * quantity));
    }

//    public void testManyTimes(int quantity){
//        try {
//
//            // делаю тесты
//            long add = 0, groupAdd = 0, searchPr = 0, searchNotPr = 0, searchMask = 0, changePr= 0, changeNotPr= 0, delPr = 0, delNotPr = 0, delGroup = 0;
//            for (int i = 0; i<100; i++) {
//                serveyDao.createTestTable();
//                fillTable(quantity);
//                ArrayList<String> nameTheatres = generateRandomList(10, null);
//                ArrayList<String> nameManagers = generateRandomList(10, null);
//                ArrayList<Long> ids = new ArrayList<>();
//                ids.add((long)(Math.random()*quantity));
//                ids.add((long)(Math.random()*quantity));
//                ids.add((long)(Math.random()*quantity));
//                ids.add((long)(Math.random()*quantity));
//                ids.add((long)(Math.random()*quantity));
//                add = add + serveyDao.createTheatre("TestNameTheatre", "TestNameTheatre");
//                groupAdd = groupAdd + serveyDao.createAFewTheatres(nameTheatres, nameManagers);
//                searchPr = searchPr + serveyDao.searchByPrimaryKey((long) (Math.random() * quantity));
//                searchNotPr = searchNotPr + serveyDao.searchByNotPrimaryKey("Большой театр");
//                searchMask = searchMask + serveyDao.searchByMask("Большой");
//                changePr = changePr + serveyDao.update((long) (Math.random() * quantity), "Ivanov");
//                changeNotPr = changeNotPr+ serveyDao.updateByNotPrimary("Ivanov", "Petrov");
//                delPr = delPr+serveyDao.deleteTheatre((long) (Math.random() * quantity));
//                delNotPr = delNotPr+ serveyDao.deleteTheatreByNotPrimaryKey("Большой театр");
//                delGroup = delGroup + serveyDao.deleteGroupTheatre(ids);
//                serveyDao.deleteTestTable();
//            }
//            add = add/100;
//            groupAdd = groupAdd/100;
//            searchPr = searchPr/100;
//            searchNotPr = searchNotPr/100;
//            searchMask = searchMask/100;
//            changePr = changePr/100;
//            changeNotPr = changeNotPr/100;
//            delPr = delPr/100;
//            delNotPr = delNotPr/100;
//            delGroup = delGroup / 100;
//
//            System.out.println("Добавление записи " + format(add));
//            System.out.println("Добавление группы записей " + format(groupAdd));
//            System.out.println("Поиск записи по ключевому полю " + format(searchPr));
//            System.out.println("Поиск записи по не ключевому полю " + format(searchNotPr));
//            System.out.println("Поиск записи по маске " + format(searchMask));
//            System.out.println("Изменение записи (определение изменяемой записи по ключевому полю) " + format(changePr));
//            System.out.println("Изменение записи (определение изменяемой записи по не ключевому полю) " + format(changeNotPr));
//            System.out.println("Удаление записи (определение удаляемой записи по ключевому полю) " + format(delPr));
//            System.out.println("Удаление записи (определение удаляемой записи по не ключевому полю) " + format(delNotPr));
//            System.out.println("Удаление группы записей " + format(delGroup));
//
//        } catch (Exception e) {
//        } finally {
//            serveyDao.deleteTestTable();
//        }
//    }

//    public void testOptimization (int amount){
//        try {
//            serveyDao.createTestTable();
//            fillTable(amount);
//            for (int i = 0; i < 99800; i++) {
//                serveyDao.deleteTheatreById((long) i);
//            }
//            System.out.println(format(serveyDao.optimization()));
//        } catch (Exception e) {
//        } finally {
//            serveyDao.deleteTestTable();
//        }
//
//    }

    public String format(long time){
        double l = time / 1e9;
        return String.format("%.15f", l);
    }


    static int index = 0;
    public ArrayList<String> generateRandomList(int amount){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        ArrayList<String> names = new ArrayList<>();
        for(int x = 1;x <= amount; x++) {
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + random.nextInt(rightLimit - leftLimit + 1);
                buffer.append((char) randomLimitedInt);
            }
            buffer.append(index++);
            String generatedString = buffer.toString();
            names.add(generatedString);
        }
        return names;
    }

    ArrayList<String> names;
    ArrayList<String> managers;
    ArrayList<String> addressList;

    public void fillTable (int amount){
//        names = generateRandomList(amount, Arrays.asList("Большой театр", "Малый театр", "OOO Средний театр"));
//        managers = generateRandomList(amount, Arrays.asList("Ivanov"));
//        address = generateRandomList(amount, Arrays.asList("питер"));

        names = generateRandomList(amount);
        managers = generateRandomList(amount);
        addressList = generateRandomList(amount);
        serveyDao.createAFewTheatres(names, managers, addressList);
    }

    private String getOneRndValue() {
        return generateRandomList(1).get(0);
    }
}
