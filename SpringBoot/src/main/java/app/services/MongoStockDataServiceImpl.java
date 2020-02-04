package app.services;

import app.dao.interfaces.mongo.MongoDailyStockDataDao;
import app.dao.interfaces.mongo.MongoIntraDayStockDataDao;
import app.model.StockData;
import app.model.mongodb.DailyStockData;
import app.model.mongodb.IntraDayStockData;
import app.services.interfaces.MongoStockDataService;
import app.utils.enums.TimeSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MongoStockDataServiceImpl implements MongoStockDataService {

    private final MongoDailyStockDataDao mongoDailyStockDataDao;
    private final MongoIntraDayStockDataDao mongoIntraDayStockDataDao;

    @Autowired
    public MongoStockDataServiceImpl(MongoDailyStockDataDao mongoDailyStockDataDao, MongoIntraDayStockDataDao mongoIntraDayStockDataDao) {
        this.mongoDailyStockDataDao = mongoDailyStockDataDao;
        this.mongoIntraDayStockDataDao = mongoIntraDayStockDataDao;
    }

    @Override
    public boolean saveStockDataToCollection(TimeSeries timeSeries, String symbol, List<StockData> data) {
        switch (timeSeries) {
            case WEEKLY:
                IntraDayStockData intraDayStockData = new IntraDayStockData(symbol, new Date(), data);
                mongoIntraDayStockDataDao.save(intraDayStockData);
                break;
            case DAILY:
                DailyStockData dailyStockData = new DailyStockData(symbol, new Date(), data);
                mongoDailyStockDataDao.save(dailyStockData);
                break;
            default:
                return false;
        }
        return true;
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
