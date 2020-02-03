package app.services;

import app.model.StockData;
import app.util.DateToLocalDateConverter;
import app.utils.JsonUtils;
import app.utils.enums.TimeSeries;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AlphaVentageServiceImplTest {

    private static final String URL = "http://URL";
    private static final String TOKEN = "123456789";
    private static final String SYMBOL = "symbol";

    private static final String INTRA_DAY_URL = "/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=%s&apikey=%s";
    private static final String INTRA_DAY_FULL_URL = "/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=%s&outputsize=full&apikey=%s";
    private static final String DAILY_FULL_URL = "/query?function=TIME_SERIES_DAILY&symbol=%s&outputsize=full&apikey=%s";
    private static final String WEEKLY_URL = "/query?function=TIME_SERIES_WEEKLY&symbol=%s&apikey=%s";

    @InjectMocks
    private AlphaVentageServiceImpl alphaVentageService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private JsonUtils jsonUtils;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(alphaVentageService, "url", URL);
        ReflectionTestUtils.setField(alphaVentageService, "token", TOKEN);
    }

    @Test
    void getIntradayDataShouldThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> alphaVentageService.getIntradayData(999, SYMBOL, true));
    }

    @ParameterizedTest
    @MethodSource("getArgumentsFullData")
    void getIntradayDataShouldReturnFullData(int interval, String symbol, boolean fullData) {
        when(restTemplate.getForObject(String.format(URL + INTRA_DAY_FULL_URL, symbol, String.valueOf(interval).concat("min"), TOKEN), String.class)).thenReturn("STRING");
        when(jsonUtils.convertJsonStringToStockDataList(any(), any(), any(), any())).thenReturn(prepareStockData());
        Optional<List<StockData>> result = alphaVentageService.getIntradayData(interval, symbol, fullData);
        Assert.assertTrue(result.isPresent());
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void getIntradayDataShouldReturnData(int interval, String symbol, boolean fullData) {
        when(restTemplate.getForObject(String.format(URL + INTRA_DAY_URL, symbol, String.valueOf(interval).concat("min"), TOKEN), String.class)).thenReturn("STRING");
        when(jsonUtils.convertJsonStringToStockDataList(any(), any(), any(), any())).thenReturn(prepareStockData());
        Optional<List<StockData>> result = alphaVentageService.getIntradayData(interval, symbol, fullData);
        Assert.assertTrue(result.isPresent());
    }

    @ParameterizedTest
    @MethodSource("getStockDataDailyArguments")
    void getStockDataShouldReturnDailyData(String symbol, TimeSeries timeSeries) {
        when(restTemplate.getForObject(String.format(URL + DAILY_FULL_URL, symbol, TOKEN), String.class)).thenReturn("STRING");
        when(jsonUtils.convertJsonStringToStockDataList(any(), any(), any(), any())).thenReturn(prepareStockData());
        Optional<List<StockData>> result = alphaVentageService.getStockData(symbol, timeSeries);
        Assert.assertTrue(result.isPresent());
    }

    @ParameterizedTest
    @MethodSource("getStockDataWeeklyArguments")
    void getStockDataShouldReturnWeeklyData(String symbol, TimeSeries timeSeries) {
        when(restTemplate.getForObject(String.format(URL + WEEKLY_URL, symbol, TOKEN), String.class)).thenReturn("STRING");
        when(jsonUtils.convertJsonStringToStockDataList(any(), any(), any(), any())).thenReturn(prepareStockData());
        Optional<List<StockData>> result = alphaVentageService.getStockData(symbol, timeSeries);
        Assert.assertTrue(result.isPresent());
    }


    private static Stream getArgumentsFullData() {
        return Stream.of(
                Arguments.of(1, SYMBOL, true),
                Arguments.of(5, SYMBOL, true),
                Arguments.of(15, SYMBOL, true),
                Arguments.of(30, SYMBOL, true),
                Arguments.of(60, SYMBOL, true)
        );
    }

    private static Stream getArguments() {
        return Stream.of(
                Arguments.of(1, SYMBOL, false),
                Arguments.of(5, SYMBOL, false),
                Arguments.of(15, SYMBOL, false),
                Arguments.of(30, SYMBOL, false),
                Arguments.of(60, SYMBOL, false)
        );
    }

    private static Stream getStockDataDailyArguments() {
        return Stream.of(
                Arguments.of("symbol", TimeSeries.DAILY)
        );
    }

    private static Stream getStockDataWeeklyArguments() {
        return Stream.of(
                Arguments.of("symbol", TimeSeries.WEEKLY)
        );
    }

    private List<StockData> prepareStockData() {
        List<StockData> result = new ArrayList<>();
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 20; i++) {
            result.add(new StockData(null, SYMBOL, DateToLocalDateConverter.convertLocalDateToDate(date.minusDays(i)), null, null, null, null, null, null, null));
        }
        return result;
    }
}