package com.mechanitis.demo.ui;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class StockChartApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void start(Stage stage) {
    	log.info("StockChartApplication.start !");
        this.context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void init() {
    	log.info("StockChartApplication.init !");
        this.context = new SpringApplicationBuilder()
                .sources(UiApplication.class)
                .run();
    }

    @Override
    public void stop() {
    	log.info("StockChartApplication.stop !");
        this.context.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
            log.info("StockChartApplication.StageReadyEvent constructor !");
        }

        public Stage getStage() {
        	log.info("StockChartApplication.StageReadyEvent getStage !");
            return (Stage)this.getSource();
        }
    }
}
