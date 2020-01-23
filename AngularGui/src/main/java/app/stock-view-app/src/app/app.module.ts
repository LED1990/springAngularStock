import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {jqxChartModule} from "jqwidgets-ng/jqxchart";

import {MenuComponent} from './component/menu/menu.component';
import {WeekChartComponent} from "./component/week-chart/week-chart.component";
import {MaterialModule} from "./material/material.module";

@NgModule({
  declarations: [
    AppComponent,
    WeekChartComponent,
    MenuComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    jqxChartModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
