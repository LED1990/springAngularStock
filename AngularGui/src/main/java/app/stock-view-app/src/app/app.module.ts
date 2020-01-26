import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {jqxChartModule} from "jqwidgets-ng/jqxchart";

import {MenuComponent} from './component/menu/menu.component';
import {WeekChartComponent} from "./component/week-chart/week-chart.component";
import {MaterialModule} from "./material/material.module";
import {ChartsComponent} from './component/charts/charts.component';
import {AppRoutesModule} from "./component/routes/app-routes.module";
import {CriteriaSearchComponent} from './component/criteria-search/criteria-search.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    WeekChartComponent,
    MenuComponent,
    ChartsComponent,
    CriteriaSearchComponent
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
