package app.components;

import app.dao.StockSymbolsDao;
import app.dao.StockWeekDataDao;
import app.model.SearchCriteria;
import app.model.StockWeekData;
import app.services.interfaces.FinancialModelingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class StockData {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private StockSymbolsDao stockSymbolsDao;
    private StockWeekDataDao stockWeekDataDao;
    private FinancialModelingService financialModelingService;

    @Autowired
    public StockData(StockSymbolsDao stockSymbolsDao, StockWeekDataDao stockWeekDataDao, FinancialModelingService financialModelingService) {
        this.stockSymbolsDao = stockSymbolsDao;
        this.stockWeekDataDao = stockWeekDataDao;
        this.financialModelingService = financialModelingService;
    }

    /**
     * finding symbols stock symbols which are meeting search requirements
     * @return return
     */
    public Optional<List<String>> findSymbolsByPriceRange(SearchCriteria searchCriteria){
        List<String> result;
        result = stockSymbolsDao.getSymbolsUsingPriceRange(searchCriteria.getMinPrice(), searchCriteria.getMaxPrice());
        if (result == null || result.isEmpty()){
            logger.debug("No stocks are in required price range");
            return Optional.empty();
        }
        return Optional.of(result);
    }

    public void getAllPriceDataFromLastWeek(List<String> symbols){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        stockWeekDataDao.changeStatus(false);
        logger.debug("Getting weekly data from service");
        for (String symbol: symbols
             ) {
            executorService.execute(() -> financialModelingService.getLastWeekData(symbol).ifPresent(stockWeekData -> saveWeekData(stockWeekData, symbol)));
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.MINUTES)){
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error("", e);
            executorService.shutdownNow();
        }
        if (executorService.isShutdown() || executorService.isTerminated()){
            logger.debug("Removing entries with no interest");
            stockWeekDataDao.deleteUsingStatus(false);
        }
    }

    private void saveWeekData(List<StockWeekData> data, String symbol){
        logger.debug("Saving data to DB for symbol: " + symbol);
        stockWeekDataDao.deleteUsingSymbol(symbol);
        stockWeekDataDao.saveAll(data);
    }



}
