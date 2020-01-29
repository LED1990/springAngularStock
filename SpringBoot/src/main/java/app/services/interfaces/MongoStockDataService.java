package app.services.interfaces;

import app.model.StockData;
import app.utils.enums.TimeSeries;

import java.util.List;

public interface MongoStockDataService {
    boolean saveStockDataToCollection(TimeSeries timeSeries, String symbol, List<StockData> data);
}
