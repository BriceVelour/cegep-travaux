package cegepst;

import java.util.Scanner;

public class Game {
    private Scanner scanner;
    private Grid grid;
    private Player playerBatman;
    private Player playerSuperman;
    private Player currentPlayer;
    private boolean gameOver;
    public Game() {
        scanner = new Scanner(System.in);
        grid = new Grid();
        playerBatman = new Player('B');
        playerSuperman = new Player('S');

        if (Math.random() < 0.5) {
            currentPlayer = playerBatman;
        } else {
            currentPlayer = playerSuperman;
        }
        gameOver = false;
    }
    public void start() {
        System.out.println("\u001B[1m\u001B[33mBATMAN vs SUPERMAN – Tic-Tac-Toe 4x4\u001B[0m");
        System.out.println("First player: " + currentPlayer.getSymbol() + "\n");
        while (!gameOver) {
            grid.display();
            currentPlayer.playTurn(scanner, grid);
            if (grid.checkVictory(currentPlayer.getSymbol())) {
                grid.display();
                System.out.println("\u001B[1m\u001B[32mVICTORY! Player " + currentPlayer.getSymbol() + " wins!\u001B[0m");
                gameOver = true;
            } else if (grid.isFull()) {
                grid.display();
                System.out.println("\u001B[1m\u001B[33mDRAW! The grid is full.\u001B[0m");
                gameOver = true;
            } else {
                switchPlayer();
            }
        }
        scanner.close();
    }
    private void switchPlayer() {
        if (currentPlayer == playerBatman) {
            currentPlayer = playerSuperman;
        } else {
            currentPlayer = playerBatman;
        }
    }
}