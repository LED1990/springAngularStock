package app.services;

import app.model.wrappers.StockDataWrapper;
import app.model.wrappers.SymbolsWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinancialModelingServiceImplTest {

    private final String symbol = "SMB";

    @InjectMocks
    private FinancialModelingServiceImpl financialModelingService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getAllSymbolsShouldReturnEmptyBadCode() {
        when(restTemplate.getForEntity("null/company/stock/list", SymbolsWrapper.class)).thenReturn(prepareInvalidResponse(true));
        Optional result = financialModelingService.getAllSymbols();
        assertFalse(result.isPresent());
    }

    @Test
    void getAllSymbolsShouldReturnEmptyNoBody() {
        when(restTemplate.getForEntity("null/company/stock/list", SymbolsWrapper.class)).thenReturn(prepareInvalidResponse(false));
        Optional result = financialModelingService.getAllSymbols();
        assertFalse(result.isPresent());
    }

    @Test
    void getAllSymbolsShouldReturnList() {
        when(restTemplate.getForEntity("null/company/stock/list", SymbolsWrapper.class)).thenReturn(prepareValidResponse());
        Optional result = financialModelingService.getAllSymbols();
        assertTrue(result.isPresent());
    }

    @Test
    void getStockDataShouldReturnEmptyBadCode() {
        when(restTemplate.getForEntity(prepareDataUrl(), StockDataWrapper.class)).thenReturn(prepareInvalidResponseStockData());
        Optional result = financialModelingService.getStockData(symbol);
        assertFalse(result.isPresent());
    }

    @Test
    void getStockDataShouldReturnEmptyNoData() {
        when(restTemplate.getForEntity(prepareDataUrl(), StockDataWrapper.class)).thenReturn(prepareValidResponseStockData(false));
        Optional result = financialModelingService.getStockData(symbol);
        assertFalse(result.isPresent());
    }

    @Test
    void getStockDataShouldReturnList() {
        when(restTemplate.getForEntity(prepareDataUrl(), StockDataWrapper.class)).thenReturn(prepareValidResponseStockData(true));
        Optional result = financialModelingService.getStockData(symbol);
        assertTrue(result.isPresent());
    }

    private String prepareDataUrl() {
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now().minusDays(1);
        return String.format("null/historical-price-full/%s?from=%s&to=%s", symbol, from, to);
    }


    private ResponseEntity<SymbolsWrapper> prepareInvalidResponse(boolean invalidStatus) {
        ResponseEntity<SymbolsWrapper> responseEntity = null;
        if (invalidStatus) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        if (!invalidStatus) {//invalid body
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        }
        return responseEntity;
    }

    private ResponseEntity<SymbolsWrapper> prepareValidResponse() {
        SymbolsWrapper symbolsWrapper = new SymbolsWrapper();
        symbolsWrapper.setSymbolsList(new ArrayList<>());
        return new ResponseEntity<>(symbolsWrapper, HttpStatus.OK);
    }

    private ResponseEntity<StockDataWrapper> prepareInvalidResponseStockData() {
        ResponseEntity<StockDataWrapper> responseEntity = null;
        responseEntity = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        return responseEntity;
    }

    private ResponseEntity<StockDataWrapper> prepareValidResponseStockData(boolean historicalDataPresent) {
        ResponseEntity<StockDataWrapper> responseEntity = null;
        if (!historicalDataPresent) {
            responseEntity = new ResponseEntity<>(new StockDataWrapper(), HttpStatus.OK);
        }
        if (historicalDataPresent) {
            StockDataWrapper stockDataWrapper = new StockDataWrapper();
            stockDataWrapper.setHistorical(new ArrayList<>());
            responseEntity = new ResponseEntity<>(stockDataWrapper, HttpStatus.OK);
        }
        return responseEntity;
    }
}