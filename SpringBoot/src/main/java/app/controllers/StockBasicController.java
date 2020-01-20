package app.controllers;

import app.components.StockData;
import app.model.SearchCriteria;
import app.model.StockSymbol;
import app.services.interfaces.FinancialModelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class StockBasicController {

    private FinancialModelingService financialModelingService;
    private StockData stockData;

    @Autowired
    public StockBasicController(FinancialModelingService financialModelingService, StockData stockData) {
        this.financialModelingService = financialModelingService;
        this.stockData = stockData;
    }

    @GetMapping("/api/v1/stock/symbols")
    public List<StockSymbol> getAllStockSymbols(){
        Optional<List<StockSymbol>> symbols = financialModelingService.getAllSymbols();
        return symbols.orElse(null);
    }

    @GetMapping("/api/v1/stock/symbols/update")//do wywalenia
    public String getStockSymbolsUpdate(){
        financialModelingService.updateStockSymbols();
        return "Success";
    }

    @GetMapping("/api/v1/stock/symbols/byprice")//do wywalenia
    public String getStockSymbolsByPrice(@Param("min") Float min, @Param("max") Float max){
        SearchCriteria searchCriteria = new SearchCriteria(min, max);
        stockData.findSymbolsByPriceRange(searchCriteria).ifPresent(strings -> stockData.getAllPriceDataFromLastWeek(strings));
        return "Success";
    }

}
