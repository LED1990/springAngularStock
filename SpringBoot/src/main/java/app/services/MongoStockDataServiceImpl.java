package app.services;

import app.dao.interfaces.mongo.MongoDailyStockDataDao;
import app.dao.interfaces.mongo.MongoWeeklyStockDataDao;
import app.model.StockData;
import app.model.mongodb.DailyStockData;
import app.model.mongodb.WeeklyStockData;
import app.services.interfaces.MongoStockDataService;
import app.utils.enums.TimeSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MongoStockDataServiceImpl implements MongoStockDataService {

    private final MongoDailyStockDataDao mongoDailyStockDataDao;
    private final MongoWeeklyStockDataDao mongoWeeklyStockDataDao;

    @Autowired
    public MongoStockDataServiceImpl(MongoDailyStockDataDao mongoDailyStockDataDao, MongoWeeklyStockDataDao mongoWeeklyStockDataDao) {
        this.mongoDailyStockDataDao = mongoDailyStockDataDao;
        this.mongoWeeklyStockDataDao = mongoWeeklyStockDataDao;
    }

    @Override
    public boolean saveStockDataToCollection(TimeSeries timeSeries, String symbol, List<StockData> data) {
        switch (timeSeries) {
            case WEEKLY:
                WeeklyStockData weeklyStockData = new WeeklyStockData(symbol, new Date(), data);
                mongoWeeklyStockDataDao.save(weeklyStockData);
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
}
