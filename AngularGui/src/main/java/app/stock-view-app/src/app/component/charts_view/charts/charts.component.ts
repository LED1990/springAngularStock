import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent implements OnInit {

  private symbol: string;

  constructor(private route: ActivatedRoute, private router: Router) {
    this.checkEvent(this.router);
  }

  ngOnInit() {
  }

  private checkEvent(r: Router) {
    r.events.subscribe(value => {
      if (value instanceof NavigationEnd) {
        this.symbol = this.route.snapshot.paramMap.get('symbol');
      }
    })
  }

}
