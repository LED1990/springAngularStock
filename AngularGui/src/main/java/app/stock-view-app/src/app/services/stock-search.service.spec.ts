import {TestBed} from '@angular/core/testing';

import {StockSearchService} from './stock-search.service';

describe('StockSearchService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: StockSearchService = TestBed.get(StockSearchService);
    expect(service).toBeTruthy();
  });
});
