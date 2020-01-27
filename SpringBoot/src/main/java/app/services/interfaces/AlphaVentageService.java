package app.services.interfaces;

import app.model.StockData;
import app.utils.enums.TimeSeries;

import java.util.List;
import java.util.Optional;

public interface AlphaVentageService {
    Optional<List<StockData>> getIntradayData(int interval, String symbol, boolean fullData);

    Optional<List<StockData>> getStockData(String symbol, TimeSeries timeSeries);
}
