package app.services;

import app.model.StockData;
import app.model.StockSymbol;
import app.model.wrappers.StockDataWrapper;
import app.model.wrappers.SymbolsWrapper;
import app.services.interfaces.FinancialModelingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${financialModUrl}")
    private String url;

    private static final String ALL_SYMBOLS_URL = "/company/stock/list";

    private static final String FULL_DATA_URL = "/historical-price-full/%s?from=%s&to=%s";

    private final RestTemplate restTemplate;

    @Autowired
    public FinancialModelingServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<List<StockSymbol>> getAllSymbols() {
        ResponseEntity<SymbolsWrapper> responseEntity = restTemplate.getForEntity(url + ALL_SYMBOLS_URL, SymbolsWrapper.class);
        if (responseEntity.getStatusCodeValue() == 200 && responseEntity.getBody() != null) {
            return Optional.ofNullable(responseEntity.getBody().getSymbolsList());
        }
        return Optional.empty();
    }

    /**
     * In my case yesterday data
     *
     * @param symbol symbols to get data
     * @return void
     */
    @Override
    public Optional<List<StockData>> getStockData(String symbol) {
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now().minusDays(1);
        ResponseEntity<StockDataWrapper> responseEntity = restTemplate.getForEntity(String.format(url + FULL_DATA_URL, symbol, from, to), StockDataWrapper.class);
        if (responseEntity.getStatusCodeValue() == 200 && responseEntity.getBody() != null) {
            if (responseEntity.getBody().getHistorical() != null) {
                responseEntity.getBody().getHistorical().forEach(stockData -> {
                    if (stockData != null) {
                        stockData.setSymbol(symbol);
                        stockData.setActual(true);
                    }
                });
                return Optional.ofNullable(responseEntity.getBody().getHistorical());
            }
        }
        return Optional.empty();
    }
}
