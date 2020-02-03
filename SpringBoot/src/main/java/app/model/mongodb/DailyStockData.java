package app.model.mongodb;

import app.model.StockData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "stock_daily_data")//to change collection name
public class DailyStockData implements Serializable {

    @Id
    private String symbol;
    private Date insertDate;
    private List<StockData> sotckData;
}
