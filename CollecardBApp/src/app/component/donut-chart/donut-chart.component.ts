import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-donut-chart',
  templateUrl: './donut-chart.component.html',
  styleUrls: ['./donut-chart.component.css']
})
export class DonutChartComponent implements OnInit {

  public donutChartLabels = ['Sales Q1', 'Sales Q2', 'Sales Q3', 'Sales Q4'];

  public donutChartData = [120, 150, 190, 80];

  public donutChartType = 'doughnut';
  
  constructor() { }

  ngOnInit() {
  }

}
