package app.services;

import app.dao.interfaces.mongo.MongoDailyStockDataDao;
import app.dao.interfaces.mongo.MongoIntraDayStockDataDao;
import app.model.StockData;
import app.services.interfaces.MongoDataService;
import app.utils.enums.TimeSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoDataServiceImpl implements MongoDataService {

    private MongoDailyStockDataDao mongoDailyStockDataDao;
    private MongoIntraDayStockDataDao mongoIntraDayStockDataDao;

    @Autowired
    public MongoDataServiceImpl(MongoDailyStockDataDao mongoDailyStockDataDao, MongoIntraDayStockDataDao mongoIntraDayStockDataDao) {
        this.mongoDailyStockDataDao = mongoDailyStockDataDao;
        this.mongoIntraDayStockDataDao = mongoIntraDayStockDataDao;
    }

    @Override
    public Optional<List<StockData>> getStockData(TimeSeries timeSeries, String symbol) {
        List<StockData> result = new ArrayList<>();
        switch (timeSeries) {
            case DAILY:
            case WEEKLY:
                mongoIntraDayStockDataDao.findById(symbol).ifPresent(intraDayStockData -> result.addAll(intraDayStockData.getSotckData()));
                break;
            case MONTH:
            case YEAR:
                mongoDailyStockDataDao.findById(symbol).ifPresent(dailyStockData -> result.addAll(dailyStockData.getSotckData()));
                break;
        }
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }


}
