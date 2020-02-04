package app.services;

import app.dao.interfaces.mongo.MongoDailyStockDataDao;
import app.dao.interfaces.mongo.MongoIntraDayStockDataDao;
import app.model.StockData;
import app.model.mongodb.DailyStockData;
import app.model.mongodb.IntraDayStockData;
import app.util.DateToLocalDateConverter;
import app.utils.enums.TimeSeries;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

//@SpringBootTest //runs spring context
@ExtendWith(MockitoExtension.class)
class MongoStockDataServiceImplTest {

    private static final String SYMBOL = "abcd";

    @InjectMocks
    private MongoStockDataServiceImpl mongoStockDataService;

    @Mock
    private MongoDailyStockDataDao mongoDailyStockDataDao;
    @Mock
    private MongoIntraDayStockDataDao mongoIntraDayStockDataDao;

    @Test
    void saveStockDataToCollectionShouldReturnFalse() {
        Assert.assertFalse(mongoStockDataService.saveStockDataToCollection(TimeSeries.INTRADAY, null, null));
    }

    @Test
    void saveStockDataToCollectionDailyShouldReturnTrue() {
        when(mongoDailyStockDataDao.save(any())).thenReturn(null);
        Assert.assertTrue(mongoStockDataService.saveStockDataToCollection(TimeSeries.DAILY, null, null));
        verify(mongoIntraDayStockDataDao, never()).save(any());
    }

    @Test
    void saveStockDataToCollectionWeeklyShouldReturnTrue() {
        when(mongoIntraDayStockDataDao.save(any())).thenReturn(null);
        Assert.assertTrue(mongoStockDataService.saveStockDataToCollection(TimeSeries.WEEKLY, null, null));
        verify(mongoDailyStockDataDao, never()).save(any());
    }

    @ParameterizedTest
    @EnumSource(TimeSeries.class)
    void getStockDataShouldReturnNothing(TimeSeries timeSeries) {
        Assert.assertFalse(mongoStockDataService.getStockData(timeSeries, SYMBOL).isPresent());
    }

    @ParameterizedTest
    @EnumSource(
            value = TimeSeries.class,
            names = {"DAILY", "WEEKLY"}
    )
    void getStockDataShouldReturnIntradayData(TimeSeries timeSeries) {
        when(mongoIntraDayStockDataDao.findById(SYMBOL)).thenReturn(prepareMongoIntradayStockData());
        verify(mongoDailyStockDataDao, times(0)).findById(any());
        Assert.assertTrue(mongoStockDataService.getStockData(timeSeries, SYMBOL).isPresent());
    }

    @ParameterizedTest
    @EnumSource(
            value = TimeSeries.class,
            names = {"MONTH", "YEAR"}
    )
    void getStockDataShouldReturnDailyData(TimeSeries timeSeries) {
        when(mongoDailyStockDataDao.findById(SYMBOL)).thenReturn(prepareMongoDailyStockData());
        verify(mongoIntraDayStockDataDao, times(0)).findById(any());
        Assert.assertTrue(mongoStockDataService.getStockData(timeSeries, SYMBOL).isPresent());
    }


    private Optional<IntraDayStockData> prepareMongoIntradayStockData() {
        IntraDayStockData intraDayStockData = new IntraDayStockData(SYMBOL, null, getStockData());
        return Optional.of(intraDayStockData);
    }

    private Optional<DailyStockData> prepareMongoDailyStockData() {
        DailyStockData dailyStockData = new DailyStockData(SYMBOL, null, getStockData());
        return Optional.of(dailyStockData);
    }

    private List<StockData> getStockData() {
        List<StockData> list = new ArrayList<>();
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 40; i++) {
            list.add(new StockData(null, SYMBOL, DateToLocalDateConverter.convertLocalDateToDate(date.minusDays(i)), null, null, null, null, null, null, null));
        }
        return list;
    }
}