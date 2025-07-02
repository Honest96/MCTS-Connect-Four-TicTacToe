import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TicTacToeComponent } from './components/tic-tac-toe/tic-tac-toe.component';
import { RouterModule } from '@angular/router';
import { routes } from './app.routes';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [BrowserModule, HttpClientModule, TicTacToeComponent, RouterModule.forRoot(routes)],
  providers: [],
})
export class AppModule {}