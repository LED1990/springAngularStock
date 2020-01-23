package app.controllers;

import app.components.WeeklyDataComponent;
import app.model.StockData;
import app.services.interfaces.AlphaVentageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 36000)
@RestController
public class StockController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private WeeklyDataComponent weeklyDataComponent;
    private AlphaVentageService alphaVentageService;

    @Autowired
    public StockController(WeeklyDataComponent weeklyDataComponent, AlphaVentageService alphaVentageService) {
        this.weeklyDataComponent = weeklyDataComponent;
        this.alphaVentageService = alphaVentageService;
    }

    @GetMapping("/api/v1/stock/weekly/data")
    public List<StockData> getWeeklyData(@RequestParam(name = "symbol") String symbol) {
        return weeklyDataComponent.getDataFromWeek(symbol);
    }

    @GetMapping("/api/v1/stock/intraday")
    public List<StockData> getIntradayData(@RequestParam(name = "symbol") String symbol,
                                           @RequestParam(name = "interval") int interval) {
        return alphaVentageService.getIntradayData(interval, symbol);
    }

}
