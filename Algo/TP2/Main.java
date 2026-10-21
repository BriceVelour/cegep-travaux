package com.cegepst;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        //ATTAQUE ZEBRA !
        // Définir mon Pokémon: Zebra et ses attaques
        String[] nomsAttaques = {"Griffe", "Flammèche", "Draco-Souffle", "Feu d’enfer"};
        int[] degatsAttaques = {10, 20, 35, 80};
        int[] coutPPAttaques = {0, 10, 20, 40};
        // Statistiques initiales de Zebra
        int PVZebra = 100;  // Points de Vie
        int PPZebra = 60;   // Points de Pouvoir
        int victoires = 0;   // Compteur de victoires
        // Générer un ennemi avec des statistiques aléatoires
        int PVEnnemi; // Points de Vie de l'ennemi (entre 50 et 100)
        int degatsEnnemi; // Dégâts de l'ennemi (entre 5 et 20)
        boolean combat;
        // Afficher les options d'action
        int choix;
        // Choisir une attaque
        int attaqueChoisie;
        // Calculer les chances de raté et de coup critique
        boolean rate; // 20% de chances de raté
        boolean critique; // 5% de chances de coup critique
        // Calculer les dégâts infligés
        int degats;
        // Tenter de fuir le combat
        boolean fuiteReussie; // 30% de chances de fuite réussie
        // Tour de l'ennemi
        int degatsEnnemiAttaque;
        boolean rateEnnemi; // 20% de chances de raté pour l'ennemi
        boolean critiqueEnnemi; // 5% de chances de coup critique pour l'ennemi
        // Vérifier si Zebra est vaincu
        String recommencer;
        while (true) {
            // Générer un ennemi avec des statistiques aléatoires
            PVEnnemi = random.nextInt(51) + 50; // Points de Vie de l'ennemi (entre 50 et 100)
            degatsEnnemi = random.nextInt(16) + 5; // Dégâts de l'ennemi (entre 5 et 20)
            combat = true;// Debut du combat
            //LE COMBAT COMMENCE!
            while (combat) {
                // Afficher les options d'action
                System.out.println("Que voulez-vous faire ?");
                System.out.println("1. Attaquer");
                System.out.println("2. Fuir");
                System.out.println("3. Ne rien faire");
                choix = scanner.nextInt();
                switch (choix) {
                    case 1:
                        // Choisir une attaque
                        System.out.println("Choisissez une attaque:");
                        for (int i = 0; i < nomsAttaques.length; i++) {
                            System.out.println((i + 1) + ". " + nomsAttaques[i] + " (Dégâts: " + degatsAttaques[i] + ", Coût PP: " + coutPPAttaques[i] + ")");
                        }
                        attaqueChoisie = scanner.nextInt() - 1;
                        // Valider le choix de l'attaque
                        if (attaqueChoisie >= 0 && attaqueChoisie < nomsAttaques.length) {
                            // Vérifier si Zebra a assez de PP
                            if (PPZebra >= coutPPAttaques[attaqueChoisie]) {
                                PPZebra -= coutPPAttaques[attaqueChoisie]; // Réduire les PP de Zebra
                                // Calculer les chances de raté et de coup critique
                                rate = random.nextDouble() < 0.2; // 20% de chances de raté
                                critique = random.nextDouble() < 0.05; // 5% de chances de coup critique
                                // Calculer les dégâts infligés
                                if (rate) {
                                    degats = 0;
                                } else {
                                    if (critique) {
                                        degats = degatsAttaques[attaqueChoisie] * 2;
                                    } else {
                                        degats = degatsAttaques[attaqueChoisie];
                                    }
                                }
                                PVEnnemi -= degats; // Réduire les PV de l'ennemi
                                System.out.println("Votre attaque a infligé " + degats + " dégâts.");
                            } else {
                                System.out.println("Pas assez de PP.");
                            }
                        } else {
                            System.out.println("Choix invalide.");
                        }
                        break;
                    case 2:
                        // Tenter de fuir le combat
                        fuiteReussie = random.nextDouble() < 0.3; // 30% de chances de fuite réussie
                        if (fuiteReussie) {
                            System.out.println("Vous avez réussi à fuir !");
                            combat = false; // Terminer le combat
                        } else {
                            System.out.println("Fuite échouée.");
                        }
                        break;
                    case 3:
                        // Ne rien faire
                        System.out.println("Vous n'avez rien fait.");
                        break;
                    default:
                        System.out.println("Choix invalide.");
                        break;
                }
                // Vérifier si l'ennemi est vaincu
                if (PVEnnemi <= 0) {
                    System.out.println("Vous avez vaincu l'ennemi !");
                    victoires++;
                    combat = false; // Terminer le combat
                } else {
                    // Tour de l'ennemi
                    degatsEnnemiAttaque = degatsEnnemi;
                    rateEnnemi = random.nextDouble() < 0.2; // 20% de chances de raté pour l'ennemi
                    critiqueEnnemi = random.nextDouble() < 0.05; // 5% de chances de coup critique pour l'ennemi
                    if (rateEnnemi) {
                        degatsEnnemiAttaque = 0;
                    } else {
                        if (critiqueEnnemi) {
                            degatsEnnemiAttaque = degatsEnnemi * 2;
                        } else {
                            degatsEnnemiAttaque = degatsEnnemi;
                        }
                    }
                    PVZebra -= degatsEnnemiAttaque; // Réduire les PV de Zebra
                    System.out.println("L'ennemi vous a infligé " + degatsEnnemiAttaque + " dégâts.");
                    // Vérifier si Zebra est vaincu
                    if (PVZebra <= 0) {
                        System.out.println("Vous avez été vaincu !");
                        System.out.println("Nombre de victoires : " + victoires);
                        System.out.println("Voulez-vous recommencer ? (oui/non)");
                        recommencer = scanner.next();
                        if (recommencer.equalsIgnoreCase("non")) {
                            System.exit(0); // Quitter le jeu
                        } else {
                            // Réinitialiser les statistiques de Zebra
                            PVZebra = 100;
                            PPZebra = 60;
                            victoires = 0;
                            break;
                        }
                    }
                }
                // Pause de rafraîchissement après 5 victoires
                if (victoires > 0 && victoires % 5 == 0) {
                    PVZebra = 100;
                    PPZebra = 60;
                    System.out.println("Pause de rafraîchissement ! Vos PV et PP sont restaurés.");
                }
            }
        }
    }
}
