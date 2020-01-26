package app.controllers;

import app.model.SearchCriteria;
import app.model.StockData;
import app.services.interfaces.AlphaVentageService;
import app.services.interfaces.StockDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 36000)
@RestController
public class StockDataController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AlphaVentageService alphaVentageService;
    private StockDataService stockDataService;


    @Autowired
    public StockDataController(StockDataService stockDataService, AlphaVentageService alphaVentageService) {
        this.stockDataService = stockDataService;
        this.alphaVentageService = alphaVentageService;
    }

    @GetMapping("/api/v1/stock/data/intraday")//todo poprawic w angularze
    public List<StockData> getIntradayData(@RequestParam(name = "symbol") String symbol,
                                           @RequestParam(name = "interval") int interval) {
        return alphaVentageService.getIntradayData(interval, symbol);
    }

    @GetMapping("/api/v1/stock/data/symbols/update")
    public String getStockSymbolsUpdate() {
        stockDataService.updateStockSymbols();
        return "Success";
    }

    /**
     * just for tests :) dont use this method with wide time range :) !!!
     * I was testing how many data can handle my PC before OutOfMemory and....
     * more than 240 000 saved entries and external service failed :) it is fine :)
     * @param searchCriteria searching criteria
     * @return string
     */
    @GetMapping("/api/v1/stock/data/update")
    public String getStockDataUpdate(@RequestBody SearchCriteria searchCriteria) {
        stockDataService.updateStockData(searchCriteria);
        return "Success";
    }
}
