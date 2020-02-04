package app.services;

import app.model.StockData;
import app.services.interfaces.AlphaVentageService;
import app.services.interfaces.MongoStockDataService;
import app.util.DateToLocalDateConverter;
import app.utils.enums.TimeSeries;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChartsDataServiceImplTest {

    private static final String SYMBOL = "abcd";

    @InjectMocks
    private ChartsDataServiceImpl chartsDataService;

    @Mock
    private AlphaVentageService alphaVentageService;
    @Mock
    private MongoStockDataService mongoStockDataService;

    @Test
    void getWeekDataShouldGetFromMongo() {
        when(mongoStockDataService.getStockData(TimeSeries.WEEKLY, SYMBOL)).thenReturn(prepareStockData());
        LocalDate maxDate = LocalDate.now().minusDays(7);
        Optional<List<StockData>> result = chartsDataService.getWeekData(SYMBOL);
        Assert.assertTrue(result.isPresent());
        verify(alphaVentageService, times(0)).getIntradayData(60, SYMBOL, false);
        result.ifPresent(data -> data.forEach(stockData ->
                Assert.assertTrue(stockData.getDate().after(DateToLocalDateConverter.convertLocalDateToDate(maxDate)))));
    }

    @Test
    void getWeekDataShouldGetFromService() {
        when(mongoStockDataService.getStockData(TimeSeries.WEEKLY, SYMBOL)).thenReturn(Optional.empty());
        when(alphaVentageService.getIntradayData(60, SYMBOL, false)).thenReturn(prepareStockData());
        LocalDate maxDate = LocalDate.now().minusDays(7);
        Optional<List<StockData>> result = chartsDataService.getWeekData(SYMBOL);
        Assert.assertTrue(result.isPresent());
        verify(alphaVentageService, times(1)).getIntradayData(60, SYMBOL, false);
        result.ifPresent(data -> data.forEach(stockData ->
                Assert.assertTrue(stockData.getDate().after(DateToLocalDateConverter.convertLocalDateToDate(maxDate)))));
    }

    @Test
    void getMonthDataShouldGetFromMongo() {
        when(mongoStockDataService.getStockData(TimeSeries.DAILY, SYMBOL)).thenReturn(prepareStockData());
        LocalDate maxDate = LocalDate.now().minusMonths(1);
        Optional<List<StockData>> result = chartsDataService.getMonthData(SYMBOL);
        Assert.assertTrue(result.isPresent());
        verify(alphaVentageService, times(0)).getIntradayData(60, SYMBOL, true);
        result.ifPresent(data -> data.forEach(stockData ->
                Assert.assertTrue(stockData.getDate().after(DateToLocalDateConverter.convertLocalDateToDate(maxDate)))));
    }

    @Test
    void getMonthDataShouldGetFromService() {
        when(mongoStockDataService.getStockData(TimeSeries.DAILY, SYMBOL)).thenReturn(Optional.empty());
        when(alphaVentageService.getIntradayData(60, SYMBOL, true)).thenReturn(prepareStockData());
        LocalDate maxDate = LocalDate.now().minusMonths(1);
        Optional<List<StockData>> result = chartsDataService.getMonthData(SYMBOL);
        Assert.assertTrue(result.isPresent());
        verify(alphaVentageService, times(1)).getIntradayData(60, SYMBOL, true);
        result.ifPresent(data -> data.forEach(stockData ->
                Assert.assertTrue(stockData.getDate().after(DateToLocalDateConverter.convertLocalDateToDate(maxDate)))));
    }

    @Test
    void getSixMonthsShouldGetDataFromMongo() {
        when(mongoStockDataService.getStockData(TimeSeries.MONTH, SYMBOL)).thenReturn(prepareStockData());
        LocalDate maxDate = LocalDate.now().minusMonths(6);
        Optional<List<StockData>> result = chartsDataService.getSixMonthsData(SYMBOL);
        Assert.assertTrue(result.isPresent());
        verify(alphaVentageService, times(0)).getStockData(SYMBOL, TimeSeries.DAILY);
        result.ifPresent(data -> data.forEach(stockData ->
                Assert.assertTrue(stockData.getDate().after(DateToLocalDateConverter.convertLocalDateToDate(maxDate)))));
    }

    @Test
    void getSixMonthsDataShouldGetFromService() {
        when(mongoStockDataService.getStockData(TimeSeries.MONTH, SYMBOL)).thenReturn(Optional.empty());
        when(alphaVentageService.getStockData(SYMBOL, TimeSeries.DAILY)).thenReturn(prepareStockData());
        LocalDate maxDate = LocalDate.now().minusMonths(6);
        Optional<List<StockData>> result = chartsDataService.getSixMonthsData(SYMBOL);
        Assert.assertTrue(result.isPresent());
        verify(alphaVentageService, times(1)).getStockData(SYMBOL, TimeSeries.DAILY);
        result.ifPresent(data -> data.forEach(stockData ->
                Assert.assertTrue(stockData.getDate().after(DateToLocalDateConverter.convertLocalDateToDate(maxDate)))));
    }

    @Test
    void getLastYearDataShouldGetFromMongo() {
        when(mongoStockDataService.getStockData(TimeSeries.YEAR, SYMBOL)).thenReturn(prepareStockData());
        LocalDate maxDate = LocalDate.now().minusYears(1);
        Optional<List<StockData>> result = chartsDataService.getLastYearData(SYMBOL);
        Assert.assertTrue(result.isPresent());
        verify(alphaVentageService, times(0)).getStockData(SYMBOL, TimeSeries.YEAR);
        result.ifPresent(data -> data.forEach(stockData ->
                Assert.assertTrue(stockData.getDate().after(DateToLocalDateConverter.convertLocalDateToDate(maxDate)))));
    }

    @Test
    void getLastYearDataShouldGetFromService() {
        when(mongoStockDataService.getStockData(TimeSeries.YEAR, SYMBOL)).thenReturn(Optional.empty());
        when(alphaVentageService.getStockData(SYMBOL, TimeSeries.YEAR)).thenReturn(prepareStockData());
        LocalDate maxDate = LocalDate.now().minusYears(1);
        Optional<List<StockData>> result = chartsDataService.getLastYearData(SYMBOL);
        Assert.assertTrue(result.isPresent());
        verify(alphaVentageService, times(1)).getStockData(SYMBOL, TimeSeries.YEAR);
        result.ifPresent(data -> data.forEach(stockData ->
                Assert.assertTrue(stockData.getDate().after(DateToLocalDateConverter.convertLocalDateToDate(maxDate)))));
    }

    @Test
    void getWeekDataShouldGetNoResult() {
        when(mongoStockDataService.getStockData(any(), any())).thenReturn(Optional.empty());
        Assert.assertFalse(chartsDataService.getWeekData(SYMBOL).isPresent());
    }

    @Test
    void getMonthDataShouldGetNoResult() {
        when(mongoStockDataService.getStockData(any(), any())).thenReturn(Optional.empty());
        Assert.assertFalse(chartsDataService.getMonthData(SYMBOL).isPresent());
    }

    @Test
    void getSixMonthsDataShouldGetNoResult() {
        when(mongoStockDataService.getStockData(any(), any())).thenReturn(Optional.empty());
        Assert.assertFalse(chartsDataService.getSixMonthsData(SYMBOL).isPresent());
    }

    @Test
    void getLastYearDataShouldGetNoResult() {
        when(mongoStockDataService.getStockData(any(), any())).thenReturn(Optional.empty());
        Assert.assertFalse(chartsDataService.getLastYearData(SYMBOL).isPresent());
    }


    private Optional<List<StockData>> prepareStockData() {
        List<StockData> list = new ArrayList<>();
        LocalDate date = LocalDate.now();
        int counter = 0;
        for (int i = 0; i < 40; i++) {
            if (i < 20) {
                list.add(new StockData(null, SYMBOL, DateToLocalDateConverter.convertLocalDateToDate(date.minusDays(i)), null, null, null, null, null, null, null));
            }
            if (i > 20) {
                counter++;
                list.add(new StockData(null, SYMBOL, DateToLocalDateConverter.convertLocalDateToDate(date.minusMonths(counter)), null, null, null, null, null, null, null));
            }
        }
        return Optional.of(list);
    }

}