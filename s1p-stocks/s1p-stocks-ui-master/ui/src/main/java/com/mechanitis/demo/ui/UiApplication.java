package com.mechanitis.demo.ui;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class UiApplication {

    public static void main(String[] args) {
    	log.info("UiApplication main !");
        Application.launch(StockChartApplication.class, args);
    }

}
