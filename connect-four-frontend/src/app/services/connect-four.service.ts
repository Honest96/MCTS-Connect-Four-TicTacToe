import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ConnectFourService {
  private baseUrl = 'http://localhost:8081/api/connect-four';

  constructor(private http: HttpClient) {}

  startGame(): Observable<any> {
    return this.http.post(`${this.baseUrl}/start`, {}).pipe(
      tap(response => console.log('Start game API response:', response)),
      catchError(error => {
        console.error('Start game API error:', error);
        return throwError(() => error);
      })
    );
  }

  makeMove(column: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/move?column=${column}`, {}).pipe(
      tap(response => console.log('Make move API response:', response)),
      catchError(error => {
        console.error('Make move API error:', error);
        return throwError(() => error);
      })
    );
  }

  getAIMove(board: number[][], player: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/ai-move`, {}).pipe(
      tap(response => console.log('AI move API response:', response)),
      catchError(error => {
        console.error('AI move API error:', error);
        return throwError(() => error);
      })
    );
  }
}