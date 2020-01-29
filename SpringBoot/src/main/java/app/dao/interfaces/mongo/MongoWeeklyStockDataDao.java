package app.dao.interfaces.mongo;

import app.model.mongodb.WeeklyStockData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoWeeklyStockDataDao extends MongoRepository<WeeklyStockData, String> {
}
