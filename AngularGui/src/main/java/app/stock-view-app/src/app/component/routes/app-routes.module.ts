import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {ChartsComponent} from "../charts/charts.component";
import {CriteriaSearchComponent} from "../criteria-search/criteria-search.component";

const routes: Routes = [
  {path: '', redirectTo: '/search', pathMatch: 'full'},
  {path: 'search', component: CriteriaSearchComponent},
  {path: 'charts/:symbol', component: ChartsComponent},
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutesModule {
}
