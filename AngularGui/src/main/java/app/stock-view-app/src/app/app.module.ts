import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {jqxChartModule} from "jqwidgets-ng/jqxchart";
import {WeekChartComponent} from './components/week-chart/week-chart.component';
import {MenuComponent} from './component/menu/menu.component';

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
    jqxChartModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
