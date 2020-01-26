import {Component, OnInit, ViewChild} from '@angular/core';
import {SearchCriteria} from "../../model/SearchCriteria";
import {FormBuilder, FormGroup} from "@angular/forms";
import {StockSearchService} from "../../services/stock-search.service";
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {StockData} from "../../model/StockData";

@Component({
  selector: 'app-criteria-search',
  templateUrl: './criteria-search.component.html',
  styleUrls: ['./criteria-search.component.css']
})
export class CriteriaSearchComponent implements OnInit {

  stockDataColumns: string[] = ['symbol', 'open', 'close', 'high', 'low', 'change', 'changePercent'];
  stockDataSource = new MatTableDataSource<StockData>([]);
  @ViewChild('stockDataPaginator', {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(private fb: FormBuilder,
              private stockSearchService: StockSearchService) {

  }

  ngOnInit() {
    this.stockDataSource.paginator = this.paginator;
    this.stockDataSource.sort = this.sort;
  }

  searchForm: FormGroup = this.createSearchCriteriaForm({
    maxPrice: 100,
    minPrice: 98
  });


  getSerachFormValue() {
    return this.searchForm.value as SearchCriteria;
  }

  createSearchCriteriaForm(model: SearchCriteria): FormGroup {
    return this.fb.group(model);
  }

  search(): void {
    console.log('hello');
    this.stockSearchService.getStockDataUsingSearchCriteria(this.getSerachFormValue()).subscribe(value => {
      console.log(value);
      this.stockDataSource.data = value;
    });
  }
}
