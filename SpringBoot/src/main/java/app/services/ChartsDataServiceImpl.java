package app.services;

import app.model.StockData;
import app.services.interfaces.AlphaVentageService;
import app.services.interfaces.ChartsDataServie;
import app.util.DateToLocalDateConverter;
import app.utils.enums.TimeSeries;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChartsDataServiceImpl implements ChartsDataServie {

    private AlphaVentageService alphaVentageService;

    public ChartsDataServiceImpl(AlphaVentageService alphaVentageService) {
        this.alphaVentageService = alphaVentageService;
    }

    //todo save data to temp db to not call service all the time
    @Override
    public Optional<List<StockData>> getWeekData(String symbol) {
        Optional<List<StockData>> result = alphaVentageService.getIntradayData(60, symbol, false);
        if (result.isPresent()) {
            LocalDate from = LocalDate.now().minusDays(7);
            return Optional.of(result.get().stream().filter(stockData -> DateToLocalDateConverter.convertDateToLocalDate(stockData.getDate()).isAfter(from)).collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<StockData>> getMonthData(String symbol) {
        Optional<List<StockData>> result = alphaVentageService.getIntradayData(60, symbol, true);
        if (result.isPresent()) {
            LocalDate from = LocalDate.now().minusMonths(1);
            return Optional.of(result.get().stream().filter(stockData -> DateToLocalDateConverter.convertDateToLocalDate(stockData.getDate()).isAfter(from)).collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<StockData>> getSixMonthsData(String symbol) {
        Optional<List<StockData>> result = alphaVentageService.getStockData(symbol, TimeSeries.DAILY);
        if (result.isPresent()) {
            LocalDate from = LocalDate.now().minusMonths(6);
            return Optional.of(result.get().stream().filter(stockData -> DateToLocalDateConverter.convertDateToLocalDate(stockData.getDate()).isAfter(from)).collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<StockData>> getLastYearData(String symbol) {
        Optional<List<StockData>> result = alphaVentageService.getStockData(symbol, TimeSeries.WEEKLY);
        if (result.isPresent()) {
            LocalDate from = LocalDate.now().minusYears(1);
            return Optional.of(result.get().stream().filter(stockData -> DateToLocalDateConverter.convertDateToLocalDate(stockData.getDate()).isAfter(from)).collect(Collectors.toList()));
        }
        return Optional.empty();
    }
}
