package app.services.interfaces;

import app.model.StockData;
import app.model.StockSymbol;

import java.util.List;
import java.util.Optional;

public interface FinancialModelingService {
    Optional<List<StockSymbol>> getAllSymbols();

    Optional<List<StockData>> getStockData(String symbol);
}
