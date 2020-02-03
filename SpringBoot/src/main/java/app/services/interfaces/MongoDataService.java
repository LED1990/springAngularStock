package app.services.interfaces;

import app.model.StockData;
import app.utils.enums.TimeSeries;

import java.util.List;
import java.util.Optional;

public interface MongoDataService {
    Optional<List<StockData>> getStockData(TimeSeries timeSeries, String symbol);
}
