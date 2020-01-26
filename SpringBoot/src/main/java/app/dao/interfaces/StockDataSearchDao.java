package app.dao.interfaces;

import app.model.SearchCriteria;
import app.model.StockData;

import java.util.List;
import java.util.Optional;

public interface StockDataSearchDao {
    Optional<List<StockData>> findStockDataUsingCriteria(SearchCriteria searchCriteria);
}
