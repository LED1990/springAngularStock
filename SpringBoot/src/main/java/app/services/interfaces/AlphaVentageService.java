package app.services.interfaces;

import app.model.StockData;

import java.util.List;

public interface AlphaVentageService {
    List<StockData> getIntradayData(int interval, String symbol);
}
