package app.utils;

import app.model.StockData;
import app.utils.enums.TimeSeries;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {

    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public static List<StockData> convertJsonStringToStockDataList(String response, Integer interval, String symbol, TimeSeries timeSeries) {
        if (response == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        JSONObject root = new JSONObject(response);
        JSONObject seriesData;
        SimpleDateFormat dateFormat;
        switch (timeSeries) {
            case INTRADAY:
                seriesData = (JSONObject) root.get(String.format("Time Series (%dmin)", interval));
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                break;
            case DAILY:
                seriesData = (JSONObject) root.get("Time Series (Daily)");
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                break;
            case WEEKLY:
                seriesData = (JSONObject) root.get("Weekly Time Series");
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                break;
            default:
                throw new IllegalArgumentException("Invalid time series");
        }
        String key;
        JSONObject value;
        Iterator<String> iterator = seriesData.keys();
        List<StockData> result = new ArrayList<>();
        while (iterator.hasNext()) {
            key = iterator.next();
            value = (JSONObject) seriesData.get(key);
            try {
                result.add(new StockData(null, symbol, dateFormat.parse(key),
                        Float.parseFloat(String.valueOf(value.get("1. open"))),
                        Float.parseFloat(String.valueOf(value.get("4. close"))),
                        Float.parseFloat(String.valueOf(value.get("2. high"))),
                        Float.parseFloat(String.valueOf(value.get("3. low"))),
                        null, null, null));
            } catch (ParseException e) {
                logger.error("", e);
            }
        }
        return result;
    }
}
