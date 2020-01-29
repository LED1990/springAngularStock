package app.services;

import app.dao.interfaces.jpa.StockDataDao;
import app.dao.interfaces.jpa.StockSymbolsDao;
import app.model.SearchCriteria;
import app.services.interfaces.FinancialModelingService;
import app.services.interfaces.StockDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class StockDataServiceImpl implements StockDataService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final StockDataDao stockDataDao;
    private final StockSymbolsDao stockSymbolsDao;
    private final FinancialModelingService financialModelingService;

    @Autowired
    public StockDataServiceImpl(StockDataDao stockDataDao, StockSymbolsDao stockSymbolsDao, FinancialModelingService financialModelingService) {
        this.stockDataDao = stockDataDao;
        this.stockSymbolsDao = stockSymbolsDao;
        this.financialModelingService = financialModelingService;
    }

    @Override
    public void updateStockSymbols() {
        stockSymbolsDao.deleteAllInBatch();
        financialModelingService.getAllSymbols().ifPresent(stockSymbolsDao::saveAll);
    }

    @Override
    public void updateStockData(SearchCriteria searchCriteria) {
        if ((searchCriteria.getMaxPrice() == null) || (searchCriteria.getMinPrice() == null)) {
            throw new IllegalArgumentException("Prices are required to reduce number of results and get financial data");
        }

        stockDataDao.removeOldStockData(Date.from(LocalDate.now().minusDays(2).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        List<String> symbols = stockSymbolsDao.getSymbolsUsingPriceAndSymbol(searchCriteria.getMinPrice(), searchCriteria.getMaxPrice());

        if (symbols == null || symbols.isEmpty()) {
            logger.debug("No symbols in selected price range");
            return;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        logger.debug("Getting data from service");
        for (String symbol : symbols
        ) {
            executorService.execute(() -> financialModelingService.getStockData(symbol).ifPresent(stockWeekData -> saveDataToDb(stockWeekData, symbol)));
        }
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(20, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error("", e);
            executorService.shutdownNow();
        }
        if (executorService.isShutdown() || executorService.isTerminated()) {
            logger.debug("Stock data update finished");
        }
    }

    private void saveDataToDb(List<app.model.StockData> data, String symbol) {
        logger.debug("Saving data to DB for symbol: " + symbol);
        stockDataDao.saveAll(data);
    }
}
