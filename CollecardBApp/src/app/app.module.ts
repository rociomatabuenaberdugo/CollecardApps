import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { ChartsModule } from 'ng2-charts';
import { AngularFireDatabaseModule } from 'angularfire2/database'
import { AngularFireModule } from 'angularfire2';
import { environment } from '../environments/environment';
import { FormsModule} from '@angular/forms'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { ToastrModule } from 'ngx-toastr';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BarChartComponent } from './component/bar-chart/bar-chart.component';
import { DonutChartComponent } from './component/donut-chart/donut-chart.component';
import { RadarChartComponent } from './component/radar-chart/radar-chart.component';
import { PieChartComponent } from './component/pie-chart/pie-chart.component';
import { CrudComponent } from './component/crud/crud.component';
import { UsersComponent } from './component/crud/users/users.component';
import { UserListComponent } from './component/crud/users/user-list/user-list.component';
import { UserComponent } from './component/crud/users/user/user.component';

import { UserService } from './services/user.service'

@NgModule({
  declarations: [
    AppComponent,
    BarChartComponent,
    DonutChartComponent,
    RadarChartComponent,
    PieChartComponent,
    CrudComponent,
    UsersComponent,
    UserListComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ChartsModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFireDatabaseModule,
    FormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
