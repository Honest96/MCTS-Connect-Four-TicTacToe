# AI Game Platform: Tic Tac Toe & Connect Four
Developed as a final year project for the B.Sc. Computer Science & Software Engineering at Maynooth University. the full-stack web-based AI paltform features Tic Tac Toe & Connect Four, powered by the Monte-Carlo Tree Search (MCTS) algorithm. Built with Java (Spring Boot) for the backend and Angular (TypeScript) for the frontend, the platform demonstrates how AI adapts and performs in different board game complexities.

# Description
This application allows users to play against an AI agent in two popular board games:
Tic Tac Toe (3x3 grid)
Connect Four (7x6 grid)

The AI is trained using Monte-Carlo Tree Search (MCTS), a simulation-based algorithm that dynamically balances exploration and exploitation to select optimal moves. The application runs in the browser and communicates with a backend API to compute moves in real time.

# Features
1. AI Opponent Powered by MCTS: Opponent learns through simulation & backpropagation and also adapts to different game complexities.
2. Two Game Modes: Tic Tac Toe(simple decision tree) & Connect Four (large search tree).
3. Full-Stack Architecture: Java Spring Boot (Backend) & Angular and TypeScript (Frontend).
4. Simulation Performance: Configure number of simulations & UCT parameters & demonstrate Draw/Win/Lose consistency in AI v AI/AI v Human tests.

# Prerequisites
Backend: Java 17 or higher (recommended: Java 23), Maven
Frontend: Node.js (v 18 or later), Angular CLI (v 17 or later)
To install Angular CLI: `npm install -g @angular/cli`

# Getting the App Started
## Connect Four
Backend (Spring Boot): `mvn clean spring-boot:run -f pom2.xml -Dspring-boot.run.arguments=--server.port=8081`
Frontend (Angular): cd connect-four-frontend
`ng serve --port 4201`

## Tic Tac Toe
Backend (Spring Boot): `mvn clean spring-boot:run -Dspring-boot.run.arguments=--server.port=8080`
Fronend (Angular): cd tic-tac-toe-frontend
`ng serve --port 4200`

## Sample Test Results

### AI vs Random Player (100 games):
MCTS AI wins: 94
Draws: 6
### AI vs AI (equal simulations):
Mostly results in draws
### Human vs AI:
Beginner: mostly lost
Intermediate: some draws
Expert: consistent draws

# Thesis Report
For full details, algorithm breakdown and implementation insights, read the full thesis: (./Thesis-Report.pdf)

# Author
Honest Ndlovu
B.Sc (Hons) Computer Science & Softwar Engineering
Maynooth University
https://github.com/Honest96