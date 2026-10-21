package cegepst.com;

import java.util.Scanner;
import java.util.Random;

public class Game {
    private Grid grid;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public Game() {
        grid = new Grid();
        player1 = new Player("Superman", 'S');
        player2 = new Player("Batman", 'B');

        if (new Random().nextBoolean()) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue au jeu Batman vs Superman : Tic-Tac-Toe !");
        System.out.println("Le joueur " + currentPlayer.getName() + " commence.");

        while (true) {
            grid.display();
            System.out.println(currentPlayer.getName() + " (" + currentPlayer.getSymbol() + "), c'est à vous de jouer.");

            int row;
            int col;
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Entrez la ligne (1-4): ");
                row = scanner.nextInt() - 1;
                System.out.print("Entrez la colonne (1-4): ");
                col = scanner.nextInt() - 1;

                if (row >= 0 && row < 4 && col >= 0 && col < 4) {
                    if (grid.updateCell(row, col, currentPlayer.getSymbol())) {
                        validInput = true;
                    } else {
                        System.out.println("Case déjà occupée, essayez une autre.");
                    }
                } else {
                    System.out.println("Coordonnées invalides, veuillez entrer des valeurs entre 1 et 4.");
                }
            }

            if (checkVictory()) {
                grid.display();
                System.out.println("Félicitations, " + currentPlayer.getName() + " a gagné !");
                break;
            }
            if (checkDraw()) {
                grid.display();
                System.out.println("Match nul !");
                break;
            }
            System.out.println("Voulez-vous utiliser un pouvoir ? (oui/non)");
            String usePower = scanner.next();
            if (usePower.equalsIgnoreCase("oui")) {
                currentPlayer.usePower(grid);
            }
            switchPlayer();
        }
        scanner.close();
    }

    private void switchPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }

    private boolean checkVictory() {
        return grid.checkVictory(currentPlayer.getSymbol());
    }

    private boolean checkDraw() {
        return grid.isFull();
    }
}

