package app.model.wrappers;

import app.model.StockData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LastWeekDataWrapper {
    private String symbol;
    private List<StockData> historical;
}
