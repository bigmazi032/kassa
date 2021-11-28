package ru.zinyakova.kassa.service;


import ru.zinyakova.kassa.dao.SurveyDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SurveyService {
    SurveyDao serveyDao = new SurveyDao();

    public void test(int quantity){
        try {
            serveyDao.createTestTable();
            fillTable(quantity);
            ArrayList<String> nameTheatres = generateRandomList(10);
            ArrayList<String> nameManagers = generateRandomList(10);
            ArrayList<Long> ids = new ArrayList<>();
            ids.add((long)(Math.random()*quantity));
            ids.add((long)(Math.random()*quantity));
            ids.add((long)(Math.random()*quantity));
            ids.add((long)(Math.random()*quantity));
            ids.add((long)(Math.random()*quantity));
            // делаю тесты

            System.out.println("Добавление записи " + format(serveyDao.createTheatre("TestNameTheatre", "TestNameTheatre")));
            System.out.println("Добавление группы записей " + format(serveyDao.createAFewTheatres(nameTheatres, nameManagers)));
            System.out.println("Поиск записи по ключевому полю " + format(serveyDao.searchByPrimaryKey((long)(Math.random()*quantity))));
            System.out.println("Поиск записи по не ключевому полю " + format(serveyDao.searchByNotPrimaryKey("Большой театр")));
            System.out.println("Поиск записи по маске " + format(serveyDao.searchByMask("Большой")));
            System.out.println("Изменение записи (определение изменяемой записи по ключевому полю) " + format(serveyDao.update((long)(Math.random()*quantity), "Ivanov")));
            System.out.println("Изменение записи (определение изменяемой записи по не ключевому полю) " + format(serveyDao.updateByNotPrimary("Ivanov", "Petrov")));
            System.out.println("Удаление записи (определение удаляемой записи по ключевому полю) " + format(serveyDao.deleteTheatre((long)(Math.random()*quantity))));
            System.out.println("Удаление записи (определение удаляемой записи по не ключевому полю) " + format(serveyDao.deleteTheatreByNotPrimaryKey("Большой театр")));
            System.out.println("Удаление группы записей " + format(serveyDao.deleteGroupTheatre(ids)));
        } catch (Exception e) {
        } finally {
             serveyDao.deleteTestTable();
        }
    }

    public void testManyTimes(int quantity){
        try {

            // делаю тесты
            long add = 0, groupAdd = 0, searchPr = 0, searchNotPr = 0, searchMask = 0, changePr= 0, changeNotPr= 0, delPr = 0, delNotPr = 0, delGroup = 0;
            for (int i = 0; i<100; i++) {
                serveyDao.createTestTable();
                fillTable(quantity);
                ArrayList<String> nameTheatres = generateRandomList(10);
                ArrayList<String> nameManagers = generateRandomList(10);
                ArrayList<Long> ids = new ArrayList<>();
                ids.add((long)(Math.random()*quantity));
                ids.add((long)(Math.random()*quantity));
                ids.add((long)(Math.random()*quantity));
                ids.add((long)(Math.random()*quantity));
                ids.add((long)(Math.random()*quantity));
                add = add + serveyDao.createTheatre("TestNameTheatre", "TestNameTheatre");
                groupAdd = groupAdd + serveyDao.createAFewTheatres(nameTheatres, nameManagers);
                searchPr = searchPr + serveyDao.searchByPrimaryKey((long) (Math.random() * quantity));
                searchNotPr = searchNotPr + serveyDao.searchByNotPrimaryKey("Большой театр");
                searchMask = searchMask + serveyDao.searchByMask("Большой");
                changePr = changePr + serveyDao.update((long) (Math.random() * quantity), "Ivanov");
                changeNotPr = changeNotPr+ serveyDao.updateByNotPrimary("Ivanov", "Petrov");
                delPr = delPr+serveyDao.deleteTheatre((long) (Math.random() * quantity));
                delNotPr = delNotPr+ serveyDao.deleteTheatreByNotPrimaryKey("Большой театр");
                delGroup = delGroup + serveyDao.deleteGroupTheatre(ids);
                serveyDao.deleteTestTable();
            }
            add = add/100;
            groupAdd = groupAdd/100;
            searchPr = searchPr/100;
            searchNotPr = searchNotPr/100;
            searchMask = searchMask/100;
            changePr = changePr/100;
            changeNotPr = changeNotPr/100;
            delPr = delPr/100;
            delNotPr = delNotPr/100;
            delGroup = delGroup / 100;

            System.out.println("Добавление записи " + format(add));
            System.out.println("Добавление группы записей " + format(groupAdd));
            System.out.println("Поиск записи по ключевому полю " + format(searchPr));
            System.out.println("Поиск записи по не ключевому полю " + format(searchNotPr));
            System.out.println("Поиск записи по маске " + format(searchMask));
            System.out.println("Изменение записи (определение изменяемой записи по ключевому полю) " + format(changePr));
            System.out.println("Изменение записи (определение изменяемой записи по не ключевому полю) " + format(changeNotPr));
            System.out.println("Удаление записи (определение удаляемой записи по ключевому полю) " + format(delPr));
            System.out.println("Удаление записи (определение удаляемой записи по не ключевому полю) " + format(delNotPr));
            System.out.println("Удаление группы записей " + format(delGroup));

        } catch (Exception e) {
        } finally {
            serveyDao.deleteTestTable();
        }
    }

    public void testOptimization (int amount){
        try {
            serveyDao.createTestTable();
            fillTable(amount);
            for (int i = 0; i < 99800; i++) {
                serveyDao.deleteTheatre((long) i);
            }
            System.out.println(format(serveyDao.optimization()));
        } catch (Exception e) {
        } finally {
            serveyDao.deleteTestTable();
        }

    }

    public String format(long time){
        double l = time / 1e9;
        return String.format("%.15f", l);
    }

    public ArrayList<String> generateRandomList(int amount){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        ArrayList<String> names = new ArrayList<>();
        for(int x=1;x<=amount;x++) {
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < targetStringLength; i++) {
                int randomLimitedInt = leftLimit + random.nextInt(rightLimit - leftLimit + 1);
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            names.add(generatedString);
        }
        return names;
    }

    public void fillTable (int amount){
        ArrayList<String> names = generateRandomList(amount);
        int rand = (int)(Math.random()*amount);
        names.set(rand, "Большой театр");
        ArrayList<String> managers = generateRandomList(amount);
        serveyDao.createAFewTheatres(names, managers);
    }


}
