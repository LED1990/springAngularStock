package app.services;

import app.model.StockData;
import app.services.interfaces.AlphaVentageService;
import app.utils.JsonUtils;
import app.utils.enums.TimeSeries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AlphaVentageServiceImpl implements AlphaVentageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${alphavantageUrl}")
    private String url;

    @Value("${alphavantageToken}")
    private String token;

    private static final String INTRA_DAY_URL = "/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=%s&apikey=%s";
    private static final String INTRA_DAY_FULL_URL = "/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=%s&outputsize=full&apikey=%s";
    private static final String DAILY_FULL_URL = "/query?function=TIME_SERIES_DAILY&symbol=%s&outputsize=full&apikey=%s";
    private static final String WEEKLY_URL = "/query?function=TIME_SERIES_WEEKLY&symbol=%s&apikey=%s";
    private final RestTemplate restTemplate;
    private final JsonUtils jsonUtils;

    @Autowired
    public AlphaVentageServiceImpl(RestTemplate restTemplate, JsonUtils jsonUtils) {
        this.restTemplate = restTemplate;
        this.jsonUtils = jsonUtils;
    }

    @Override
    public Optional<List<StockData>> getIntradayData(int interval, String symbol, boolean fullData) {
        if (interval == 1 || interval == 5 || interval == 15 || interval == 30 || interval == 60) {
            String response;
            if (fullData) {
                response = restTemplate.getForObject(String.format(url + INTRA_DAY_FULL_URL, symbol, String.valueOf(interval).concat("min"), token), String.class);
            } else {
                response = restTemplate.getForObject(String.format(url + INTRA_DAY_URL, symbol, String.valueOf(interval).concat("min"), token), String.class);
            }
            List<StockData> result = jsonUtils.convertJsonStringToStockDataList(response, interval, symbol, TimeSeries.INTRADAY);
            result.sort(Comparator.comparing(StockData::getDate));
            return Optional.of(result);
        }
        throw new IllegalArgumentException("Wrong interval value");
    }

    @Override
    public Optional<List<StockData>> getStockData(String symbol, TimeSeries timeSeries) {
        String response = null;
        if (timeSeries.equals(TimeSeries.DAILY)) {
            response = restTemplate.getForObject(String.format(url + DAILY_FULL_URL, symbol, token), String.class);

        } else if (timeSeries.equals(TimeSeries.WEEKLY)) {
            response = restTemplate.getForObject(String.format(url + WEEKLY_URL, symbol, token), String.class);
        }
        List<StockData> result = jsonUtils.convertJsonStringToStockDataList(response, null, symbol, timeSeries);
        result.sort(Comparator.comparing(StockData::getDate));
        return Optional.of(result);
    }
}
