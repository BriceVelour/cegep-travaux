package cegepst.com;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        char[][] grille = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        char joueurActuel = 'X';
        boolean jeuTerminé = false;
        Scanner scanner = new Scanner(System.in);

        while (!jeuTerminé) {
            afficherGrille(grille);
            int[] coup = obtenirCoupJoueur(scanner, grille);
            grille[coup[0]][coup[1]] = joueurActuel;

            if (estGagnant(grille, joueurActuel)) {
                afficherGrille(grille);
                System.out.println("Le joueur " + joueurActuel + " gagne!");
                jeuTerminé = true;
            } else {
                joueurActuel = (joueurActuel == 'X') ? 'O' : 'X';
            }
        }

        scanner.close();
    }

    public static void afficherGrille(char[][] grille) {
        System.out.println("  0 1 2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(grille[i][j]);
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("  -----");
        }
    }

    public static int[] obtenirCoupJoueur(Scanner scanner, char[][] grille) {
        int ligne, colonne;
        System.out.print("Entrez votre coup (ligne et colonne) : ");
        ligne = scanner.nextInt();
        colonne = scanner.nextInt();
        while (ligne < 0 || ligne >= 3 || colonne < 0 || colonne >= 3 || grille[ligne][colonne] != ' ') {
            System.out.println("Ce coup n'est pas valide");
            System.out.print("Entrez votre coup (ligne et colonne) : ");
            ligne = scanner.nextInt();
            colonne = scanner.nextInt();
        }
        return new int[]{ligne, colonne};
    }

    public static boolean estGagnant(char[][] grille, char joueur) {
        for (int i = 0; i < 3; i++) {
            if ((grille[i][0] == joueur && grille[i][1] == joueur && grille[i][2] == joueur) ||
                    (grille[0][i] == joueur && grille[1][i] == joueur && grille[2][i] == joueur)) {
                return true;
            }
        }
        return false;
    }
}

