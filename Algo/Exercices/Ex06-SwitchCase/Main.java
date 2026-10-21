package com.cegepst;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Numéro 2
        System.out.print("Entrez une lettre: ");
        char lettre = scanner.next().charAt(0);
        if ("AEIOUaeiou".indexOf(lettre) != -1) {
            System.out.println("La lettre est une voyelle.");
        } else {
            System.out.println("La lettre est une consonne.");
        }

        // Numéro 3
        System.out.print("Entrez le premier nombre: ");
        double nombre1 = scanner.nextDouble();
        System.out.print("Entrez le deuxième nombre: ");
        double nombre2 = scanner.nextDouble();
        System.out.print("Entrez un opérateur (+, -, /, *, %): ");
        char operateur = scanner.next().charAt(0);
        double resultat = 0;
        switch (operateur) {
            case '+':
                resultat = nombre1 + nombre2;
                break;
            case '-':
                resultat = nombre1 - nombre2;
                break;
            case '/':
                resultat = nombre1 / nombre2;
                break;
            case '*':
                resultat = nombre1 * nombre2;
                break;
            case '%':
                resultat = nombre1 % nombre2;
                break;
            default:
                System.out.println("Opérateur non valide.");
                return;
        }
        System.out.printf("Le résultat : %.2f %c %.2f = %.2f%n", nombre1, operateur, nombre2, resultat);

        // Numéro 4
        System.out.print("Entrez le type de billet (Parterre, Section 1, Section 2, VIP): ");
        String typeBillet = scanner.next();
        System.out.print("Entrez l'âge du client: ");
        int age = scanner.nextInt();
        double prixBillet = 0;
        switch (typeBillet) {
            case "Parterre":
                prixBillet = 50;
                break;
            case "Section 1":
                prixBillet = 65;
                break;
            case "Section 2":
                prixBillet = 80;
                break;
            case "VIP":
                prixBillet = 150;
                break;
            default:
                System.out.println("Type de billet non valide.");
                return;
        }
        double rabais = 0;
        if (age <= 12) {
            rabais = 0.25;
        } else if (age >= 65) {
            rabais = 0.15;
        }
        double prixFinal = prixBillet * (1 - rabais);
        if (!typeBillet.equals("VIP")) {
            prixFinal += 5; // frais de vente
        }
        prixFinal *= 1.15; // taxes de 15%
        System.out.printf("Le prix total du billet est : %.2f$%n", prixFinal);
    }
}

