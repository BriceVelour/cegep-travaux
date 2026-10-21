package com.cegepst;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //Les objets disponibles
        String [] objets = {"Armure de Mithril", "Hache de gurre", "Épée longue", "Excalibur"};
        int[] prix = {1500,75,50,95000};
        int[] stock = {3,20,25,1};

        //Demander le nom de l'utilisateur
        System.out.print("Entrez votre nom: ");
        String nom = scanner.nextLine();

        // Afficher la liste des objets disponibles
        System.out.println("Articles disponibles:");
        for (int i = 0; i < objets.length; i++) {
            System.out.println((i + 1) + ". " + objets[i] + " - " + prix[i] + " pièces d'or (Stock: " + stock[i] + ")");
        }

        // Sélectionner un article
        System.out.print("Sélectionnez un article (1-4): ");
        int choix = scanner.nextInt();
        if (choix < 1 || choix > objets.length) {
            System.out.println("Numéro d'article invalide.");
            return;
        }


        // Sélectionner la quantité
        System.out.print("Entrez la quantité: ");
        int quantite = scanner.nextInt();

        // Vérifier la quantité en stock
        if (quantite > stock[choix - 1]) {
            System.out.println("Quantité en stock insuffisante.");
            return;
        } else {
            System.out.println("Article ajouté au panier.");
        }

        // Calculer le prix total
        int prixTotal = prix[choix - 1] * quantite;

        // Appliquer les réductions
        if (quantite > 5) {
            prixTotal *= 0.85; // Réduction de 15%
        }

        if (nom.equalsIgnoreCase("Arthur Pendragon") && objets[choix].equals("Excalibur")) {
            prixTotal = 0; // Excalibur gratuite pour Arthur Pendragon
        }

        // Afficher le prix total
        System.out.println("Prix total: " + prixTotal + " pièces d'or");

        // Afficher la transaction réussie
        System.out.println("Transaction réussie. Merci pour votre achat !");

    }


}
