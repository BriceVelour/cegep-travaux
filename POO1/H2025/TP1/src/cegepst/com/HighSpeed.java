package cegepst.com;

import java.util.Scanner;

public class HighSpeed extends Power {
    public HighSpeed(Player player) {
        super(player);
    }

    @Override
    public void activate(Grid grid) {
        System.out.println("Superman utilise HIGH SPEED pour jouer deux fois d'affilée !");

        Scanner scanner = new Scanner(System.in);
        int row, col;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Entrez la ligne (1-4) pour le deuxième coup: ");
            row = scanner.nextInt() - 1;
            System.out.print("Entrez la colonne (1-4) pour le deuxième coup: ");
            col = scanner.nextInt() - 1;

            if (row >= 0 && row < 4 && col >= 0 && col < 4) {
                if (grid.updateCell(row, col, player.getSymbol())) {
                    validInput = true;
                } else {
                    System.out.println("Case déjà occupée, essayez une autre.");
                }
            } else {
                System.out.println("Coordonnées invalides, veuillez entrer des valeurs entre 1 et 4.");
            }
        }
    }
}
