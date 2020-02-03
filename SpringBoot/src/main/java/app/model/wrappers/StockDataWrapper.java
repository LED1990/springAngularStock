package app.model.wrappers;

import app.model.StockData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDataWrapper {
    private String symbol;
    private List<StockData> historical;
}
