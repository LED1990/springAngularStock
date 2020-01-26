package app.services.interfaces;

import app.model.SearchCriteria;

public interface StockDataService {
    void updateStockData(SearchCriteria searchCriteria);
    void updateStockSymbols();
}
