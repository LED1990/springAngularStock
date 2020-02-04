package app.services.interfaces;

import app.model.StockData;
import app.utils.enums.TimeSeries;

import java.util.List;
import java.util.Optional;

public interface MongoStockDataService {
    boolean saveStockDataToCollection(TimeSeries timeSeries, String symbol, List<StockData> data);

    Optional<List<StockData>> getStockData(TimeSeries timeSeries, String symbol);
}
