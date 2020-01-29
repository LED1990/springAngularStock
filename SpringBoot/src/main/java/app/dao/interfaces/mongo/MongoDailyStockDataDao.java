package app.dao.interfaces.mongo;

import app.model.mongodb.DailyStockData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoDailyStockDataDao extends MongoRepository<DailyStockData, String> {
}
