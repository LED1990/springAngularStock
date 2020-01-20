package app.dao;

import app.model.StockSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockSymbolsDao extends JpaRepository<StockSymbol, Long> {

    @Query(value = "select symbol from StockSymbol where price > :minPrice and price < :maxPrice order by price")
    List<String> getSymbolsUsingPriceRange(@Param("minPrice") Float minPrice, @Param("maxPrice")Float maxPrice);
}
