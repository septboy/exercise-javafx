package com.mechanitis.demo.ui;

import static javafx.collections.FXCollections.observableArrayList;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

import com.mechanitis.demo.client.StockClient;
import com.mechanitis.demo.client.StockPrice;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class StockController {

    @FXML
    private LineChart<String, Double> chart;
    
    private final StockClient stockClient;
    
    
    StockController(final StockClient stockClient) {
        this.stockClient = stockClient;
        log.info("StockController constructor !");
    }

    //this is the controller, and what it does is wires stuff together
    @FXML
    public void initialize() {
    	log.info("StockController.initialize !");
    	
    	ObservableList<XYChart.Series<String, Double>> data = FXCollections.observableArrayList();
    	
        String symbol1 = "SYMBOL1";
        String symbol2 = "SYMBOL2";
        
        chart.setData(data);
        
        StockPriceConsumer stockPriceConsumer = new StockPriceConsumer(symbol1);
        data.add(stockPriceConsumer.getSeries());
        stockClient.pricesFor(symbol1).subscribe(stockPriceConsumer);
        
        StockPriceConsumer stockPriceConsumer2 = new StockPriceConsumer(symbol2);
        data.add(stockPriceConsumer2.getSeries());
        stockClient.pricesFor(symbol2).subscribe(stockPriceConsumer2);
        
    }

    
    public static class StockPriceConsumer implements Consumer<StockPrice> {

    	private String symbol ;
    	// XYChart.Data<String, Double> : A single data item with data for 2 axis charts
        private final ObservableList<XYChart.Data<String, Double>> seriesData = observableArrayList();
        // A named series of data items
        Series<String, Double> series = new XYChart.Series<>(symbol, seriesData);

        StockPriceConsumer(String symbol){
        	this.symbol = symbol ;
        }
        
		public Series<String, Double> getSeries() {
			return series;
		}

		@Override
		public void accept(StockPrice stockPrice) {
			log.info("StockPriceConsumer.accept !");
	        Platform.runLater(
	        	() ->
	            seriesData.add(
	            	new XYChart.Data<>(
	            		String.valueOf(stockPrice.getTime().getSecond())
	            		,stockPrice.getPrice()
	            	)
	            )
	        );
			
		}
    	
    }
}
