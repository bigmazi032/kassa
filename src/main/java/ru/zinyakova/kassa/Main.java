package ru.zinyakova.kassa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.zinyakova.kassa.dao.DaoFactory;
import ru.zinyakova.kassa.dao.SurveyDao;
import ru.zinyakova.kassa.service.SurveyService;

import java.io.IOException;

public class Main  /*extends Application */  {

     //@Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cash_box.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 640);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        //launch();
        DaoFactory.getDataSource();
        SurveyService surveyService = new SurveyService();
        //SurveyDao surveyDao = new SurveyDao();
       // surveyDao.report();
        System.out.println("1000 :");
        surveyService.test(1000);
       //  System.out.println("10000 :");
        //  surveyService.testOptimization(10000);
        // System.out.println("100000 :");
        //surveyService.testOptimization(100000);

    }
}

