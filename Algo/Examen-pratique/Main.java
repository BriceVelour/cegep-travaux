package com.cegepst;

import java.util.Scanner;

    public class Main {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            double totalparents = 0;
            double totalenfants = 0;

            while (true) {
                System.out.print("Entrez le code de dépense (0 = la fin des dépenses ),(1 = les parents),(2 = les enfants) :");
                int code = scanner.nextInt();
                if (code == 0) {
                    break;
                }
                System.out.print("Entrez le montant de la dépense : ");
                double montant = scanner.nextDouble();
                if (code == 1) {
                    totalparents += montant;
                } else if (code == 2) {
                    totalenfants += montant;
                }
            }

            double totalfamille = totalparents + totalenfants;
            double pourcentageparents = (totalparents / totalfamille) * 100;
            double pourcentageenfants = (totalenfants / totalfamille) * 100;

            System.out.printf("Dépenses des parents : %.2f$ (%.1f%%)\n", totalparents, pourcentageparents);
            System.out.printf("Dépenses des enfants : %.2f$ (%.1f%%)\n", totalenfants, pourcentageenfants);
            System.out.printf("Total des dépenses : %.2f$ (100.0%%)\n", totalfamille);

            if (totalparents > totalenfants) {
                System.out.println("Les parents dépensent le plus en vacances.");
            } else {
                System.out.println("Les enfants dépensent le plus en vacances.");
            }
        }
    }

