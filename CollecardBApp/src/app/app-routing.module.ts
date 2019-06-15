import { CrudComponent } from './component/crud/crud.component';
import { PieChartComponent } from './component/pie-chart/pie-chart.component';
import { BarChartComponent } from './component/bar-chart/bar-chart.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DonutChartComponent } from './component/donut-chart/donut-chart.component';
import { RadarChartComponent } from './component/radar-chart/radar-chart.component';

const routes: Routes = [
  { path: 'bar-chart', component: BarChartComponent},
  { path: 'donut-chart', component: DonutChartComponent},
  { path: 'radar-chart', component: RadarChartComponent},
  { path: 'pie-chart', component: PieChartComponent},
  { path: 'crud-users', component: CrudComponent},
  { path: '**', component: BarChartComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
