package app.services;

import app.dao.StockSymbolsDao;
import app.model.StockSymbol;
import app.services.interfaces.FinancialModelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FinancialModelingServiceImpl implements FinancialModelingService {

    private static final String ALL_SYMBOLS_URL = "/company/stock/list";
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
            stockSymbolsDao.deleteAll();
            stockSymbolsDao.saveAll(optionalStockSymbols.get());
        }
    }

    private static class SymbolsWrapper {
        List<StockSymbol> symbolsList;

        public SymbolsWrapper() {
            this.symbolsList = new ArrayList<>();
        }

        public List<StockSymbol> getSymbolsList() {
            return symbolsList;
        }

        public void setSymbolsList(List<StockSymbol> symbolsList) {
            this.symbolsList = symbolsList;
        }
    }
}
