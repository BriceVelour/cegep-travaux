package com.cegepst;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> notes = new ArrayList<>();
        String continuer;

        do {
            System.out.print("Entrez le nom de l'étudiant: ");
            String nom = scanner.nextLine();

            System.out.print("Entrez la note du premier travail (sur 100): ");
            double note1 = scanner.nextDouble() * 0.15;

            System.out.print("Entrez la note du deuxième travail (sur 100): ");
            double note2 = scanner.nextDouble() * 0.20;

            System.out.print("Entrez la note de l'examen (sur 100): ");
            double note3 = scanner.nextDouble() * 0.30;

            System.out.print("Entrez la note du projet final (sur 100): ");
            double note4 = scanner.nextDouble() * 0.35;

            double resultatPondere = note1 + note2 + note3 + note4;
            notes.add(resultatPondere);

            System.out.printf("Résultat pondéré de %s: %.2f%%\n", nom, resultatPondere);

            if (resultatPondere < 50) {
                System.out.println("Échec");
            } else if (resultatPondere >= 55 && resultatPondere <= 60) {
                System.out.println("Examen de reprise");
            }

            System.out.print("Voulez-vous continuer à saisir d'autres étudiants? (Oui/Non): ");
            scanner.nextLine();
            continuer = scanner.nextLine();
        } while (continuer.equalsIgnoreCase("Oui"));

        double somme = 0;
        for (double note : notes) {
            somme += note;
        }
        double moyenneGroupe = somme / notes.size();
        System.out.printf("Moyenne du groupe: %.2f%%\n", moyenneGroupe);

        scanner.close();
    }
}
