import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {jqxChartModule} from "jqwidgets-ng/jqxchart";

import {MenuComponent} from './component/menu/menu.component';
import {WeekChartComponent} from "./component/charts_view/week-chart/week-chart.component";
import {MaterialModule} from "./material/material.module";
import {ChartsComponent} from './component/charts_view/charts/charts.component';
import {AppRoutesModule} from "./component/routes/app-routes.module";
import {CriteriaSearchComponent} from './component/criteria-search/criteria-search.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MonthChartComponent} from './component/charts_view/month-chart/month-chart.component';
import {HalfYearChartComponent} from './component/charts_view/half-year-chart/half-year-chart.component';
import {YearChartComponent} from './component/charts_view/year-chart/year-chart.component';

@NgModule({
  declarations: [
    AppComponent,
    WeekChartComponent,
    MenuComponent,
    ChartsComponent,
    CriteriaSearchComponent,
    MonthChartComponent,
    HalfYearChartComponent,
    YearChartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    jqxChartModule,
    MaterialModule,
    AppRoutesModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
