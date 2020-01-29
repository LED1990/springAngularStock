package app.controllers;

import app.model.StockData;
import app.services.interfaces.ChartsDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 36000)
@RestController
public class ChartsController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChartsDataService chartsDataService;

    @Autowired
    public ChartsController(ChartsDataService chartsDataService) {
        this.chartsDataService = chartsDataService;
    }

    @GetMapping("/api/v1/stock/data/charts/week")
    public List<StockData> getWeekData(@RequestParam(name = "symbol") String symbol) {
        Optional<List<StockData>> result = chartsDataService.getWeekData(symbol);
        result.ifPresent(stockData -> Comparator.comparing(StockData::getDate));
        return result.orElse(null);
    }

    @GetMapping("/api/v1/stock/data/charts/month")
    public List<StockData> getMonthData(@RequestParam(name = "symbol") String symbol) {
        Optional<List<StockData>> result = chartsDataService.getMonthData(symbol);
        result.ifPresent(stockData -> Comparator.comparing(StockData::getDate));
        return result.orElse(null);
    }

    @GetMapping("/api/v1/stock/data/charts/halfyear")
    public List<StockData> getSixMonthData(@RequestParam(name = "symbol") String symbol) {
        Optional<List<StockData>> result = chartsDataService.getSixMonthsData(symbol);
        result.ifPresent(stockData -> Comparator.comparing(StockData::getDate));
        return result.orElse(null);
    }

    @GetMapping("/api/v1/stock/data/charts/year")
    public List<StockData> getLastYearData(@RequestParam(name = "symbol") String symbol) {
        Optional<List<StockData>> result = chartsDataService.getLastYearData(symbol);
        result.ifPresent(stockData -> Comparator.comparing(StockData::getDate));
        return result.orElse(null);
    }
}
