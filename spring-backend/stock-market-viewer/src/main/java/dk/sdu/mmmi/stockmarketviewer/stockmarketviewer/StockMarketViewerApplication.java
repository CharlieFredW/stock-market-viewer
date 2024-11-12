package dk.sdu.mmmi.stockmarketviewer.stockmarketviewer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StockMarketViewerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockMarketViewerApplication.class, args);
    }

}
