package app.dao;

import app.model.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StockWeekDataDao extends JpaRepository<StockData, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from StockData where symbol = :symbol")
    void deleteUsingSymbol(@Param("symbol") String symbol);

    @Transactional
    @Modifying
    @Query(value = "update StockData set actual = :status")
    void changeStatus(@Param("status") Boolean status);

    @Transactional
    @Modifying
    @Query(value = "delete from StockData where actual = :status")
    void deleteUsingStatus(@Param("status") Boolean symbol);

    @Query(value = "select s from StockData s where s.symbol = :symbol order by s.date")
    List<StockData> selectDataUsingSymbol(@Param("symbol") String symbol);
}
