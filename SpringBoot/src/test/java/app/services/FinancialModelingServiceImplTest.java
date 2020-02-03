package app.services;

import app.model.wrappers.StockDataWrapper;
import app.model.wrappers.SymbolsWrapper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinancialModelingServiceImplTest {

    private static final String URL = "http://URL";
    private static final String ALL_SYMBOLS_URL = "/company/stock/list";
    private static final String FULL_DATA_URL = "/historical-price-full/%s?from=%s&to=%s";
    private static final String SYMBOL = "symbol";

    @InjectMocks
    private FinancialModelingServiceImpl financialModelingService;

    @Mock
    private RestTemplate restTemplate;


    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(financialModelingService, "url", URL);
    }

    @Test
    void getAllSymbolsShouldReturnNothing() {
        when(restTemplate.getForEntity(URL + ALL_SYMBOLS_URL, SymbolsWrapper.class)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_GATEWAY));
        Assert.assertTrue(financialModelingService.getAllSymbols().isEmpty());
    }

    @Test
    void getAllSymbolsShouldReturnResponse() {
        when(restTemplate.getForEntity(URL + ALL_SYMBOLS_URL, SymbolsWrapper.class)).thenReturn(new ResponseEntity<>(new SymbolsWrapper(new ArrayList<>()), HttpStatus.OK));
        Assert.assertTrue(financialModelingService.getAllSymbols().isPresent());
    }

    @Test
    void getStockDataShouldReturnNothing() {
        LocalDate date = LocalDate.now().minusDays(1);
        when(restTemplate.getForEntity(String.format(URL + FULL_DATA_URL, SYMBOL, date, date), StockDataWrapper.class)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_GATEWAY));
        Assert.assertTrue(financialModelingService.getStockData(SYMBOL).isEmpty());
    }

    @Test
    void getStockDataShouldReturnNothingIfResponseWithoutBody() {
        LocalDate date = LocalDate.now().minusDays(1);
        when(restTemplate.getForEntity(String.format(URL + FULL_DATA_URL, SYMBOL, date, date), StockDataWrapper.class)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        Assert.assertTrue(financialModelingService.getStockData(SYMBOL).isEmpty());
    }

    @Test
    void getStockDataShouldReturnNothingIfBodyWithoutData() {
        LocalDate date = LocalDate.now().minusDays(1);
        when(restTemplate.getForEntity(String.format(URL + FULL_DATA_URL, SYMBOL, date, date), StockDataWrapper.class)).thenReturn(new ResponseEntity<>(new StockDataWrapper(), HttpStatus.OK));
        Assert.assertTrue(financialModelingService.getStockData(SYMBOL).isEmpty());
    }

    @Test
    void getStockDataShouldReturnStockData() {
        LocalDate date = LocalDate.now().minusDays(1);
        when(restTemplate.getForEntity(String.format(URL + FULL_DATA_URL, SYMBOL, date, date), StockDataWrapper.class)).thenReturn(new ResponseEntity<>(new StockDataWrapper(SYMBOL, new ArrayList<>()), HttpStatus.OK));
        Assert.assertTrue(financialModelingService.getStockData(SYMBOL).isPresent());
    }
}