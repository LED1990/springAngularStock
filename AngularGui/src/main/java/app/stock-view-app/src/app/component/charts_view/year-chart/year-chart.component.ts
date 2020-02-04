import {Component, Input, OnInit} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {formatDate} from "@angular/common";

@Component({
  selector: 'app-year-chart',
  templateUrl: './year-chart.component.html',
  styleUrls: ['./year-chart.component.css']
})
export class YearChartComponent implements OnInit {

  @Input() symbol: string;
  source: any;
  dataAdapter: any;

  constructor() { }

  ngOnInit() {
    this.prepareChart();
  }

  private prepareChart() {
    this.source =
      {
        datatype: 'json',
        datafields: [
          {name: 'symbol'},
          {name: 'date'},
          {name: 'open'},
          {name: 'close'},
          {name: 'high'},
          {name: 'low'}
        ],
        url: environment.springServerUrl + '/v1/stock/data/charts/year?symbol='.concat(this.symbol)
      };

    this.dataAdapter = new jqx.dataAdapter(this.source, {
      async: false,
      autoBind: true,
      loadError: (xhr: any, status: any, error: any) => {
        alert('Error loading "' + this.source.url + '" : ' + error);
      }
    });

  }

  toolTipCustomFormatFn = (value: any, itemIndex: any, serie: any, group: any, categoryValue: any, categoryAxis: any) => {
    return '<DIV style="text-align:left"><b>Date: ' +
      formatDate(categoryValue, 'yyyy/MM/dd', 'en-US') +
      '</b><br />' +
      formatDate(categoryValue, 'HH:mm', 'en-US') +
      '</b><br />Open price: $' + value.open +
      '</b><br />Close price: $' + value.close +
      '</b><br />Low price: $' + value.low +
      '</b><br />High price: $' + value.high +
      '</DIV>';
  };
  padding: any = {left: 5, top: 5, right: 5, bottom: 5};
  xAxis: any =
    {
      dataField: 'date',
      labels: {
        rotationPoint: 'topright',
        formatFunction: function (value) {
          return formatDate(value, 'yyyy/MM/dd', 'en-US');
        }
      },
      type: 'basic',
      baseUnit: 'day',
      valuesOnTicks: true,
      interval: 1,
      rangeSelector: {
        padding: {left: 25, right: 10, top: 10, bottom: 10},
        backgroundColor: 'white',
        dataField: 'close',
        baseUnit: 'day',
        serieType: 'area',
        gridLines: {visible: true},
        labels:
          {
            formatFunction: (value: any) => {
              return formatDate(value, 'yyyy/MM/dd', 'en-US');
            }
          }
      }
    };
  seriesGroups: any[] =
    [
      {
        type: 'candlestick',
        columnsMaxWidth: 5,
        columnsMinWidth: 5,
        toolTipFormatFunction: this.toolTipCustomFormatFn,
        valueAxis:
          {
            description: 'S&P 500<br>'
          },
        series: [
          {
            dataFieldClose: 'close',
            dataFieldOpen: 'open',
            dataFieldHigh: 'high',
            dataFieldLow: 'low',
            displayText: '',
          }
        ]
      },
      // {
      //   type: 'line',
      //   valueAxis:
      //     {
      //       position: 'right',
      //       title: {text: '<br>Daily Volume'},
      //       gridLines: {visible: false},
      //       // labels: {
      //       //   formatFunction: (value: string) => {
      //       //     return (parseInt(value) / 1000000) + 'M';
      //       //   }
      //       // }
      //     },
      //   series: [
      //     {
      //       dataField: 'close',
      //       displayText: 'Volume',
      //       lineWidth: 1
      //     }
      //   ]
      // }
    ];

}
