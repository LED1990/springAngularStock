package app.controllers;


import app.dao.interfaces.jpa.StockDataSearchDao;
import app.model.SearchCriteria;
import app.model.StockData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 36000)
@RestController
public class StockSearchingController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private StockDataSearchDao stockDataDao;

    @Autowired
    public StockSearchingController(StockDataSearchDao stockDataDao) {
        this.stockDataDao = stockDataDao;
    }

    @PostMapping("/api/v1/stock/data/search/criteria")
    public List<StockData> getSotckDatausingCriteria(@RequestBody SearchCriteria criteria) {
        Optional<List<StockData>> result = stockDataDao.findStockDataUsingCriteria(criteria);
        return result.orElseGet(result::get);
    }


//    // todo search weekle year, half year utc in separete methods
//    @GetMapping("/api/v1/stock/search")
//    public ResponseEntity<StockData> getStockDataByCriteria(@RequestBody SearchCriteria searchCriteria) {
//        List<StockData> result = financialModelingService.
//
//    }


}
