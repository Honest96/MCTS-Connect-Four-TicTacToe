import { Routes } from '@angular/router';
import { ConnectFourComponent } from './components/connect-four/connect-four.component';

export const routes: Routes = [
  { path: '', redirectTo: 'connect-four', pathMatch: 'full' },
  { path: 'connect-four', component: ConnectFourComponent }
];