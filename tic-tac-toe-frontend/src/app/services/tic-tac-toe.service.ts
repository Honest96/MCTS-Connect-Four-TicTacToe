import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicTacToeService {
  private baseUrl = 'http://localhost:8080/api/tic-tac-toe';

  constructor(private http: HttpClient) {}

  startGame() {
    return this.http.post(`${this.baseUrl}/start`, {});
  }

  makeMove(row: number, col: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/move?row=${row}&col=${col}`, {});
  }

  getAIMove(board:number[][], player:number) {
    return this.http.post(`${this.baseUrl}/ai-move`, { board, player });
  }

  getBoard(): Observable<any> {
    return this.http.get(`${this.baseUrl}/board`);
  }

  getStatus(): Observable<any> {
    return this.http.get(`${this.baseUrl}/status`);
  }
}