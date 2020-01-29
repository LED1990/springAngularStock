package app.dao.interfaces.jpa;

import app.model.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface StockDataDao extends JpaRepository<StockData, Long> {

    @Query("delete from StockData where date < :date")
    @Modifying
    @Transactional
    void removeOldStockData(@Param("date") Date date);

}
