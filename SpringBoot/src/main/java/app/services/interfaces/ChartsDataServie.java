package app.services.interfaces;

import app.model.StockData;

import java.util.List;
import java.util.Optional;

public interface ChartsDataServie {
    Optional<List<StockData>> getWeekData(String symbol);

    Optional<List<StockData>> getMonthData(String symbol);

    Optional<List<StockData>> getSixMonthsData(String symbol);

    Optional<List<StockData>> getLastYearData(String symbol);
}
