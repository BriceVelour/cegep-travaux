package com.cegepst;

import java.util.Random;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                Random random = new Random();
                int meilleurScore = Integer.MAX_VALUE;
                int totalParties = 0;
                int totalVictoires = 0;
                int nombreCible = random.nextInt(101);
                int tentatives = 0;
                boolean joueurGagne = false;

                System.out.println("Devinez le nombre entre 0 et 100:");

                        while (tentatives < 10) {
                                tentatives++;
                                System.out.print("Tentative " + tentatives + ": ");
                                int devine = scanner.nextInt();

                                int difference = (nombreCible - devine);

                                if (difference == 0) {
                                        System.out.println("Félicitations! Vous avez trouvé le nombre en " + tentatives + " tentatives.");
                                        joueurGagne = true;
                                        break;
                                } else if (difference < 10) {
                                        System.out.println("Très près!");
                                } else if (difference < 25) {
                                        System.out.println("Près, mais pas tant!");
                                } else {
                                        System.out.println("Dans le champ!");
                                }
                        }

                        if (!joueurGagne) {
                                System.out.println("Désolé, vous avez dépassé les 15 tentatives. Le nombre était " + nombreCible + ".");
                        }

                        System.out.println("Voulez-vous jouer une autre partie? (oui/non)");
                        String reponse = scanner.next();
                        if (!reponse.equalsIgnoreCase("oui")) {
                                return;
                        }
        }
}
