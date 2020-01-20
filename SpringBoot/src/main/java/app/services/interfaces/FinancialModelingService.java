package app.services.interfaces;

import app.model.StockSymbol;
import app.model.StockWeekData;

import java.util.List;
import java.util.Optional;

public interface FinancialModelingService {
    Optional<List<StockSymbol>> getAllSymbols();
    Optional<List<StockWeekData>> getLastWeekData(String symbol);
    void updateStockSymbols();
}
