package cegepst.com;

import java.util.Scanner;

public class HeatVision extends Power {
    public HeatVision(Player player) {
        super(player);
    }

    @Override
    public void activate(Grid grid) {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        boolean validInput = false;
        System.out.println("Superman utilise HEAT VISION pour détruire un symbole de Batman !");
        while (!validInput) {
            System.out.print("Entrez la ligne du symbole à détruire (1-4): ");
            row = scanner.nextInt() - 1;
            System.out.print("Entrez la colonne du symbole à détruire (1-4): ");
            col = scanner.nextInt() - 1;

            if (row >= 0 && row < 4 && col >= 0 && col < 4) {
                if (grid.getSymbol(row, col) == 'B') {
                    grid.removeSymbol(row, col);
                    validInput = true;
                } else {
                    System.out.println("Il n'y a pas de symbole de Batman à cette position.");
                }
            } else {
                System.out.println("Coordonnées invalides, veuillez entrer des valeurs entre 1 et 4.");
            }
        }
    }
}
