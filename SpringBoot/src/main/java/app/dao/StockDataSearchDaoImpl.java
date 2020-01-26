package app.dao;

import app.dao.interfaces.StockDataSearchDao;
import app.model.SearchCriteria;
import app.model.StockData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


@Repository
public class StockDataSearchDaoImpl implements StockDataSearchDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<StockData>> findStockDataUsingCriteria(SearchCriteria searchCriteria) {
        if(searchCriteria == null){
            throw new IllegalArgumentException("Invalid data");
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StockData> criteriaQuery = criteriaBuilder.createQuery(StockData.class);

        Root<StockData> stockDataRoot = criteriaQuery.from(StockData.class);
        Predicate minPrice = criteriaBuilder.greaterThan(stockDataRoot.get("close"), searchCriteria.getMinPrice());
        Predicate maxPrice = criteriaBuilder.lessThan(stockDataRoot.get("close"), searchCriteria.getMaxPrice());
        Predicate price = criteriaBuilder.and(minPrice, maxPrice);
        criteriaQuery.where(price);
        return Optional.ofNullable(entityManager.createQuery(criteriaQuery).getResultList());
    }
}
