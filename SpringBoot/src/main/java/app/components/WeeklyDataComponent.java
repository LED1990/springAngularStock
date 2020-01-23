package app.components;

import app.dao.StockWeekDataDao;
import app.model.StockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeeklyDataComponent {

    private StockWeekDataDao stockWeekDataDao;

    @Autowired
    public WeeklyDataComponent(StockWeekDataDao stockWeekDataDao) {
        this.stockWeekDataDao = stockWeekDataDao;
    }

    public List<StockData> getDataFromWeek(String symbol){
        return stockWeekDataDao.selectDataUsingSymbol(symbol);
    }
}
