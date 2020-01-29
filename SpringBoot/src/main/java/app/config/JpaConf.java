package app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "app.dao.interfaces.jpa")
public class JpaConf {
    //ADDED TO REMOVE CONFLICT BETWEEN SPRING DATA MODULES (MONGO AND JPA)
}
