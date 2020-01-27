import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {SearchCriteria} from "../model/SearchCriteria";
import {Observable} from "rxjs";
import {StockData} from "../model/StockData";

@Injectable({
  providedIn: 'root'
})
export class StockSearchService {

  private url = 'http://localhost:9091/api/v1/stock';

  private httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private httpClient: HttpClient) { }

  getStockDataUsingSearchCriteria(criteria: SearchCriteria): Observable<StockData[]>{
    const mapping = '/data/search/criteria';
    return this.httpClient.post<StockData[]>(this.url.concat(mapping), criteria, this.httpOptions);
  }
}
