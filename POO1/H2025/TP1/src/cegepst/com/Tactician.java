package cegepst.com;

import java.util.Scanner;

public class Tactician extends Power {
    public Tactician(Player player) {
        super(player);
    }

    @Override
    public void activate(Grid grid) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Batman utilise TACTICIAN pour échanger un symbole de Superman !");
        int playerRow, playerCol, targetRow, targetCol;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Entrez la ligne de votre symbole (1-4): ");
            playerRow = scanner.nextInt() - 1;
            System.out.print("Entrez la colonne de votre symbole (1-4): ");
            playerCol = scanner.nextInt() - 1;
            System.out.print("Entrez la ligne du symbole de Superman à échanger (1-4): ");
            targetRow = scanner.nextInt() - 1;
            System.out.print("Entrez la colonne du symbole de Superman à échanger (1-4): ");
            targetCol = scanner.nextInt() - 1;

            if (playerRow >= 0 && playerRow < 4 && playerCol >= 0 && playerCol < 4 &&
                    targetRow >= 0 && targetRow < 4 && targetCol >= 0 && targetCol < 4) {

                if (grid.getSymbol(playerRow, playerCol) == 'B' && grid.getSymbol(targetRow, targetCol) == 'S') {
                    grid.relocateSymbol(playerRow, playerCol, targetRow, targetCol, 'B');
                    grid.relocateSymbol(targetRow, targetCol, playerRow, playerCol, 'S');
                    validInput = true;
                } else {
                    System.out.println("Positions invalides pour l'échange.");
                }
            } else {
                System.out.println("Coordonnées invalides, veuillez entrer des valeurs entre 1 et 4.");
            }
        }
    }
}
