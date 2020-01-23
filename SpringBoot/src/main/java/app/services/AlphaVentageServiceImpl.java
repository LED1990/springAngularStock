package app.services;

import app.model.StockData;
import app.services.interfaces.AlphaVentageService;
import app.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;

@Service
public class AlphaVentageServiceImpl implements AlphaVentageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${alphavantageUrl}")
    private String url;

    @Value("${alphavantageToken}")
    private String token;

    private static final String INTRA_DAY_URL = "/query?function=TIME_SERIES_INTRADAY&symbol=%s&interval=%s&apikey=%s";
    private RestTemplate restTemplate;

    public AlphaVentageServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<StockData> getIntradayData(int interval, String symbol) {
        if (interval == 1 || interval == 5 || interval == 15 || interval == 30 || interval == 60) {
            String response = restTemplate.getForObject(String.format(url + INTRA_DAY_URL, symbol, String.valueOf(interval).concat("min"), token), String.class);
            List<StockData> result = JsonUtils.convertJsonStringToStockDataList(response, interval, symbol);
            result.sort(Comparator.comparing(StockData::getDate));
            return result;
        }
        throw new IllegalArgumentException("Wrong interval value");
    }


}
