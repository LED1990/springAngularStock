package app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"app.dao.interfaces.mongo"})
public class MongoConf {
    //ADDED TO REMOVE CONFLICT BETWEEN SPRING DATA MODULES (MONGO AND JPA)
}
