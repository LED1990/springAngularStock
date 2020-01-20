package app.services;

import app.dao.StockSymbolsDao;
import app.model.StockSymbol;
import app.model.StockWeekData;
import app.model.wrappers.LastWeekDataWrapper;
import app.model.wrappers.SymbolsWrapper;
import app.services.interfaces.FinancialModelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FinancialModelingServiceImpl implements FinancialModelingService {

    private static final String ALL_SYMBOLS_URL = "/company/stock/list";
    private static final String LAST_WEEK_DATA_URL = "/historical-price-full/%s?from=%s&to=%s";
    private RestTemplate restTemplate;
    private StockSymbolsDao stockSymbolsDao;

    @Value("${financialModUrl}")
    private String url;

    @Autowired
    public FinancialModelingServiceImpl(RestTemplate restTemplate, StockSymbolsDao stockSymbolsDao) {
        this.restTemplate = restTemplate;
        this.stockSymbolsDao = stockSymbolsDao;
    }

    @Override
    public Optional<List<StockSymbol>> getAllSymbols() {
        ResponseEntity<SymbolsWrapper> responseEntity = restTemplate.getForEntity(url + ALL_SYMBOLS_URL, SymbolsWrapper.class);
        if (responseEntity.getStatusCodeValue() == 200 && responseEntity.getBody() != null) {
            return Optional.ofNullable(responseEntity.getBody().getSymbolsList());
        }
        return Optional.empty();
    }

    @Override
    public void updateStockSymbols() {
        Optional<List<StockSymbol>> optionalStockSymbols = getAllSymbols();
        //if relations to any other tables will appear I will have to change update approach !
        if (optionalStockSymbols.isPresent()) {
            stockSymbolsDao.deleteAllInBatch();
            stockSymbolsDao.saveAll(optionalStockSymbols.get());
        }
    }

    public Optional<List<StockWeekData>> getLastWeekData(String symbol){
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate now = LocalDate.now();
        ResponseEntity<LastWeekDataWrapper> responseEntity = restTemplate.getForEntity(String.format(url + LAST_WEEK_DATA_URL, symbol, from.toString(), now.toString()), LastWeekDataWrapper.class);
        if (responseEntity.getStatusCodeValue() == 200 && responseEntity.getBody() != null){
            if (responseEntity.getBody().getHistorical() != null){
                responseEntity.getBody().getHistorical().forEach(stockWeekData -> {
                    if (stockWeekData != null){
                        stockWeekData.setSymbol(symbol);
                        stockWeekData.setActual(true);
                    }
                });
                return Optional.ofNullable(responseEntity.getBody().getHistorical());
            }
        }
        return Optional.empty();
    }
}
