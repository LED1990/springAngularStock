package app.dao.interfaces.mongo;

import app.model.mongodb.IntraDayStockData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoIntraDayStockDataDao extends MongoRepository<IntraDayStockData, String> {
}
