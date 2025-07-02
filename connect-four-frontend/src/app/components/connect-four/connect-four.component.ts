import { Component, OnInit } from '@angular/core';
import { ConnectFourService } from '../../services/connect-four.service';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-connect-four',
  templateUrl: './connect-four.component.html',
  styleUrls: ['./connect-four.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class ConnectFourComponent implements OnInit {
  board: number[][] = Array(6).fill(0).map(() => Array(7).fill(0));
  currentPlayer: number = 1;
  statusMessage: string = 'Ready to play!';
  gameOver: boolean = false;

  constructor(private connectFourService: ConnectFourService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.initializeBoard();
    this.startGame();
  }

  initializeBoard() {
    this.board = Array(6).fill(0).map(() => Array(7).fill(0));
  }

  startGame() {
    this.gameOver = false;
    this.statusMessage = 'Starting game...';
    this.connectFourService.startGame().subscribe({
      next: (response: any) => {
        console.log('Start game response:', response);
        if (response && response.board) {
          this.board = response.board;
          this.currentPlayer = response.currentPlayer;
          this.statusMessage = response.message;
        } else {
          console.error('Invalid response format:', response);
          this.statusMessage = 'Error starting game';
        }
      },
      error: (error) => {
        console.error('Error starting game:', error);
        this.statusMessage = 'Error starting game';
      }
    });
  }

  dropPiece(column: number) {
    if (this.gameOver || this.currentPlayer !== 1) return;
  
    this.statusMessage = 'Making move...';
  
    this.connectFourService.makeMove(column).subscribe({
      next: (response: any) => {
        console.log('Player move response:', response);
  
        if (response.error) {
          this.statusMessage = response.error;
          return;
        }
  
        if (response && response.board) {
          this.board = [...response.board];
          this.currentPlayer = response.currentPlayer;
          this.statusMessage = response.message;
        }
  
        setTimeout(() => {
          if (this.currentPlayer === 2 && !this.gameOver) {
            this.makeAIMove();
          }
        }, 300);
      },
      error: (error) => {
        console.error('Error making move:', error);
        this.statusMessage = 'Error making move';
      }
    });
  }
  

  makeAIMove() {
    if (this.gameOver || this.currentPlayer !== 2) return;
  
    this.statusMessage = 'AI is thinking...';
  
    setTimeout(() => {
      this.connectFourService.getAIMove(this.board, 2).subscribe({
        next: (response: any) => {
          console.log('AI move response:', response);
  
          if (response.error) {
            this.statusMessage = response.error;
            return;
          }
  
          if (!response.board) {
            console.error('AI move API did not return a board:', response);
            this.statusMessage = 'Error: AI move failed';
            return;
          }
  
          this.board = [...response.board];
          this.currentPlayer = response.currentPlayer || 1;
          this.statusMessage = response.message || 'Your turn!';
  
          setTimeout(() => this.cdr.detectChanges(), 100);
  
          if (response.message && (response.message.includes('WIN') || response.message.includes('draw'))) {
            this.gameOver = true;
          }
        },
        error: (error) => {
          console.error('Error getting AI move:', error);
          this.statusMessage = 'Error getting AI move';
        }
      });
    }, 500);
  }
  
  getCellClass(value: number): string {
    return value === 1 ? 'player1' : value === 2 ? 'player2' : '';
  }
}