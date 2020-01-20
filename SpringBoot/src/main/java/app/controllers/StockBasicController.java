package app.controllers;

import app.model.StockSymbol;
import app.services.interfaces.FinancialModelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class StockBasicController {

    private FinancialModelingService financialModelingService;

    @Autowired
    public StockBasicController(FinancialModelingService financialModelingService) {
        this.financialModelingService = financialModelingService;
    }

    @GetMapping("/api/v1/stock/symbols")
    public List<StockSymbol> getAllStockSymbols(){
        Optional<List<StockSymbol>> symbols = financialModelingService.getAllSymbols();
        return symbols.orElse(null);
    }

    @GetMapping("/api/v1/stock/symbols/update")
    public String getStockSymbolsUpdate(){
        financialModelingService.updateStockSymbols();
        return "Success";
    }
}
