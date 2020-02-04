import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {HalfYearChartComponent} from './half-year-chart.component';

describe('HalfYearChartComponent', () => {
  let component: HalfYearChartComponent;
  let fixture: ComponentFixture<HalfYearChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HalfYearChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HalfYearChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
