package app.services;

import app.dao.interfaces.mongo.MongoDailyStockDataDao;
import app.dao.interfaces.mongo.MongoIntraDayStockDataDao;
import app.utils.enums.TimeSeries;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

//@SpringBootTest //runs spring context
@ExtendWith(MockitoExtension.class)
class MongoStockDataServiceImplTest {

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
}