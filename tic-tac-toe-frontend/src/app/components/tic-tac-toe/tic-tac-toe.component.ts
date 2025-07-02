import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TicTacToeService } from '../../services/tic-tac-toe.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tic-tac-toe.component.html',
  styleUrl: './tic-tac-toe.component.css',
})
export class TicTacToeComponent {
  board: number[][] = [];

  currentPlayer: string = '';
  statusMessage: string = '';
  humanSymbol: string = 'X';
  aiSymbol: string = 'O';

  mode: 'human' | 'random' = 'human';

  numGames: number = 100;

  simResults: { mctsWins: number; randomWins: number; draws: number } | null =
    null;

  constructor(private tictactoeService: TicTacToeService) {}

  ngOnInit() {
    this.startGame();
  }

  startGame() {
    this.tictactoeService.startGame().subscribe((response: any) => {
      this.board = Array(3)
        .fill(null)
        .map(() => Array(3).fill(0));
      this.statusMessage = response.message;
      this.currentPlayer = response.currentPlayer === 1 ? 'Player 1' : 'AI';

      if (this.currentPlayer === 'AI') {
        this.makeAIMove();
      }
    });
  }

  makeMove(row: number, col: number) {
    if (this.board[row][col] !== 0) {
      this.statusMessage = 'Invalid move. Choose an empty cell.';
      return;
    }

    this.tictactoeService.makeMove(row, col).subscribe((response: any) => {
      this.board = response.currentPlayer;
      this.statusMessage = response.message;

      if (response.status === 'gameOver') {
        this.statusMessage =
          response.result === 1
            ? 'Human Player Wins!'
            : response.result === 2
            ? 'AI Wins!'
            : 'Draw!';
        return;
      } else {
        this.getBoardState();
      }
      
    });
  }

  makeRandomMove(board: number[][], player: number) {
    while (true) {
      const row = Math.floor(Math.random() * 3);
      const col = Math.floor(Math.random() * 3);

      if (board[row][col] === 0) {
        board[row][col] = player;
        break;
      }
    }
  }

  makeAIMove() {
    this.statusMessage = 'AI is making a move...';
    this.tictactoeService.getBoard().subscribe((board: number[][]) => {
      this.board = board;
      this.statusMessage = 'Your turn!';
      this.currentPlayer = 'Player 1';
    });
  }

  getBoardState() {
    this.tictactoeService.getBoard().subscribe((board: number[][]) => {
      this.board = board;
    });
  }

  checkGameStatus(board: number[][]): number {
    const DEFAULT_BOARD_SIZE = 3;

    for (let i=0; i<DEFAULT_BOARD_SIZE; i++) {
      //Row
      if (
        board[i][0] !== 0 &&
        board[i][0] === board[i][1] &&
        board[i][0] === board[i][2]
      ) {
        return board[i][0];
      }

      //Column
      if (
        board[0][i] !== 0 &&
        board[0][i] === board[1][i] &&
        board[0][i] === board[2][i]
      ) {
        return board[0][i];
      }

      //Diagonals
      if (
        board[0][0] !== 0 &&
        board[0][0] === board[1][1] &&
        board[0][0] === board[2][2]
      ) {
        return board[0][0];
      }

      if (
        board[0][2] !== 0 &&
        board[0][2] === board[1][1] &&
        board[0][2] === board[2][0]
      ) {
        return board[0][2];
      }
    }
    const isBoardFull = board.every((row) => row.every((cell) => cell !== 0));
    return isBoardFull ? 0 : -1;
  }
}