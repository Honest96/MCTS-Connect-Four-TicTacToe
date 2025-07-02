import { Routes } from '@angular/router';
import { TicTacToeComponent } from './components/tic-tac-toe/tic-tac-toe.component';
// import { AppComponent } from './app.component';
// import { ConnectFourComponent } from './components/connect-four/connect-four.component';
// import { ConnectFourComponent } from './connect-four.component';

export const routes: Routes = [
  { path: '', redirectTo: '/tic-tac-toe', pathMatch: 'full' },
  { path: 'tic-tac-toe', component: TicTacToeComponent },
//   { path: 'connect-four', component: ConnectFourComponent }
];
