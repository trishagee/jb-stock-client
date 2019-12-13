package com.mechanitis.demo.stockui;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockUiApplication {

    public static void main(String[] args) {
        Application.launch(ChartApplication.class, args);
    }

}
