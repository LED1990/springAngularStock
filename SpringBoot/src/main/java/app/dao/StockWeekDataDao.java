package app.dao;

import app.model.StockWeekData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StockWeekDataDao extends JpaRepository<StockWeekData, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from StockWeekData where symbol = :symbol")
    void deleteUsingSymbol(@Param("symbol") String symbol);

    @Transactional
    @Modifying
    @Query(value = "update StockWeekData set actual = :status")
    void changeStatus(@Param("status") Boolean status);

    @Transactional
    @Modifying
    @Query(value = "delete from StockWeekData where actual = :status")
    void deleteUsingStatus(@Param("status") Boolean symbol);
}
