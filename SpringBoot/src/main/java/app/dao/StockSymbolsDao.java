package app.dao;

import app.model.StockSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockSymbolsDao extends JpaRepository<StockSymbol, Long> {
}
