package app.utils;

import app.model.StockData;
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

    public static List<StockData> convertJsonStringToStockDataList(String response, int interval, String symbol) {
        JSONObject root = new JSONObject(response);
        JSONObject seriesData = (JSONObject) root.get(String.format("Time Series (%dmin)", interval));
        String key;
        JSONObject value;
        Iterator<String> iterator = seriesData.keys();
        List<StockData> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
