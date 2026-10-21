package cegepst.com;

import java.util.Random;
import java.util.Scanner;

public class Combat {
    private Participant joueur;
    private Participant adversaire;
    private int tour;
    private int jourActuel; // Jour actuel dans le cycle de 3 jours
    private int toursEvades = 0; // Nombre de tours restants lors d'une évasion
    private boolean enEvasion = false; // Indique si le joueur est en évasion

    // Codes ANSI pour les couleurs
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    // Constructeur
    public Combat(Participant joueur, Participant adversaire) {
        this.joueur = joueur;
        this.adversaire = adversaire;
        this.tour = 1;
        this.jourActuel = 1; // Début de la partie au jour 1
    }

    // Méthode principale pour démarrer le combat
    public void commencerCombat() throws InterruptedException {
        afficherIntroCombat();

        // Combat limité à 10 tours
        while (tour <= 10 && joueur.getVitalite() > 0 && adversaire.getVitalite() > 0) {
            System.out.println(CYAN + "\n=== TOUR " + tour + " ===" + RESET);

            // Ajout d'événements aléatoires dynamiques
            if (Math.random() < 0.15) { // 15% de chance qu'un événement spécial se déclenche
                declencherEvenement();
            }

            if (tour % 2 == 1) { // Tour du joueur
                menuActions();
            } else { // Tour de l'adversaire
                adversairePrendDecision();
            }

            tour++;
            Thread.sleep(300); // Pause rapide pour garder le combat fluide et dynamique
        }

        // Résultat final après les tours de combat
        afficherResultat();

        // Logique post-combat si le joueur gagne
        if (joueur.getVitalite() > 0 && adversaire.getVitalite() <= 0) {
            choixApresCombat(); // Échange des armes et armures
            System.out.println(GREEN + adversaire.getPrenom() + " est transformé(e) en lapin!" + RESET);
            recupererLapinCommeRation(); // Tente de récupérer le lapin transformé comme ration
        }

        // Passage au jour suivant
        jourActuel++;
        if (jourActuel > 3) { // Condition pour terminer le jeu après 3 jours
            System.out.println(RED + "La partie est terminée! Trop de survivants restent après le troisième jour." + RESET);
            return; // Fin de la partie
        }
        System.out.println(CYAN + "=== JOUR " + jourActuel + " ===" + RESET);
    }

    // Introduction au combat
    private void afficherIntroCombat() {
        System.out.println(PURPLE + "\n=== DÉBUT DU COMBAT ===" + RESET);
        System.out.println(CYAN + joueur.getPrenom() + " (" + joueur.getVitalite() + " HP) armé(e) de " +
                joueur.getSac().getArme().getNom() + " VS " +
                adversaire.getPrenom() + " (" + adversaire.getVitalite() + " HP) armé(e) de " +
                adversaire.getSac().getArme().getNom() + RESET);
        System.out.println(PURPLE + "==========================" + RESET);
    }

    // Actions disponibles pour le joueur
    private void menuActions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nActions disponibles :");
        System.out.println("1. Attaquer");
        System.out.println("2. Manger une ration");
        System.out.println("3. S’évader");
        System.out.println("4. Ne rien faire");
        System.out.println("0. Inventaire"); // Nouvelle option pour afficher l'inventaire
        System.out.print("Votre choix : ");

        int choix = scanner.nextInt();

        if (enEvasion) {
            gererEvasion(); // Si le joueur est en évasion, on gère cet état
            return;
        }

        switch (choix) {
            case 1 -> effectuerAttaque(joueur, adversaire);
            case 2 -> joueur.mangerRation();
            case 3 -> tenterEvasion();
            case 4 -> System.out.println(YELLOW + joueur.getPrenom() + " décide de ne rien faire et perd son tour..." + RESET);
            case 0 -> afficherInventaire(); // Appel de la méthode pour afficher l'inventaire
            default -> System.out.println(RED + "Action invalide, vous perdez un tour !" + RESET);
        }
    }

    // Décision de l'adversaire
    private void adversairePrendDecision() {
        if (adversaire.getVitalite() < 20 && Math.random() * 100 < adversaire.getChance()) {
            tenterEvasionAdversaire();
        } else {
            effectuerAttaque(adversaire, joueur);
        }
    }

    private void afficherInventaire() {
        System.out.println(CYAN + "\n=== INVENTAIRE ===" + RESET);
        System.out.println("Arme : " + joueur.getSac().getArme().getNom() +
                " (Bonus d'attaque : " + joueur.getSac().getArme().getBonusAttaque() + ")");
        System.out.println("Armure : " + joueur.getSac().getArmure().getNom() +
                " (Bonus de défense : " + joueur.getSac().getArmure().getBonusDefense() + ")");
        System.out.println("Nombre de rations restantes : " + joueur.getSac().getNombreDeRations());
        System.out.println(PURPLE + "===================" + RESET);
    }

    // Événements spéciaux pendant le combat
    private void declencherEvenement() {
        String[] evenements = {
                "Une grenade explose soudainement entre les deux combattants!",
                "Un éclair divin frappe, boostant temporairement la puissance d’un participant!",
                "Une mystérieuse potion tombe du ciel... Qui sera assez rapide pour l'attraper?"
        };

        System.out.println(PURPLE + "\n=== ÉVÉNEMENT SURPRISE ===" + RESET);
        int index = new Random().nextInt(evenements.length);
        System.out.println(YELLOW + evenements[index] + RESET);

        switch (index) {
            case 0 -> { // Explosion de grenade
                int dommage = (int) (Math.random() * 20) + 10; // Dommages entre 10 et 30
                joueur.perdreVitalite(dommage);
                adversaire.perdreVitalite(dommage);
                System.out.println(RED + "Les deux combattants perdent " + dommage + " HP dans l'explosion !" + RESET);
            }
            case 1 -> { // Bénédiction divine
                if (Math.random() < 0.5) { // 50% pour le joueur
                    joueur.mangerRation(); // Gain temporaire pour le joueur
                    System.out.println(GREEN + joueur.getPrenom() + " reçoit une bénédiction divine!" + RESET);
                } else {
                    adversaire.mangerRation();
                    System.out.println(GREEN + adversaire.getPrenom() + " reçoit une bénédiction divine!" + RESET);
                }
            }
            case 2 -> { // Apparition de potion
                if (Math.random() < 0.5) { // 50% pour le joueur
                    joueur.mangerRation();
                    System.out.println(GREEN + joueur.getPrenom() + " attrape une potion miraculeuse et récupère des HP!" + RESET);
                } else {
                    adversaire.mangerRation();
                    System.out.println(GREEN + adversaire.getPrenom() + " boit la potion magique et récupère des HP!" + RESET);
                }
            }
        }
    }

    // Gestion de l'évasion
    private void tenterEvasion() {
        if (isDernierJour()) {
            System.out.println(RED + "Impossible de vous évader pendant la dernière journée !" + RESET);
            return;
        }

        System.out.println(PURPLE + joueur.getPrenom() + " tente de s'échapper..." + RESET);
        enEvasion = true;
        toursEvades = 2; // L'évasion dure 2 tours
    }

    private void gererEvasion() {
        if (enEvasion) {
            toursEvades--;
            if (toursEvades <= 0) {
                System.out.println(GREEN + joueur.getPrenom() + " a réussi à s'échapper du combat !" + RESET);
                enEvasion = false;
            } else {
                System.out.println(YELLOW + "Vous êtes encore caché... " + toursEvades + " tour(s) restant(s)." + RESET);
            }
        }
    }

    private boolean isDernierJour() {
        return jourActuel == 3; // Vérifie si c'est le dernier jour
    }

    // Evasion de l'adversaire
    private void tenterEvasionAdversaire() {
        System.out.println(RED + adversaire.getPrenom() + " tremble de peur et tente de s'enfuir!" + RESET);

        if (Math.random() * 100 < adversaire.getChance()) {
            System.out.println(GREEN + adversaire.getPrenom() + " réussit à s'enfuir en laissant toutes ses affaires derrière!" + RESET);
            joueur.getSac().echangeArme(adversaire.getSac().getArme());
            joueur.getSac().echangeArmure(adversaire.getSac().getArmure());
            adversaire.perdreVitalite(adversaire.getVitalite()); // Perd tout sans devenir lapin
        } else {
            System.out.println(YELLOW + adversaire.getPrenom() + " échoue à fuir et reste à votre merci!" + RESET);
        }
    }

    // Attaque classique
    private void effectuerAttaque(Participant attaquant, Participant defenseur) {
        System.out.println(CYAN + attaquant.getPrenom() + " attaque avec " + attaquant.getSac().getArme().getNom() + RESET);
        if (Math.random() * 100 < attaquant.getPrecision()) {
            int dommage = Math.max(1, attaquant.getAttaque() - defenseur.getDefense());
            if (Math.random() < 0.5) { // Coup critique
                dommage *= 2;
                System.out.println(RED + "COUP CRITIQUE! " + attaquant.getPrenom() + " inflige un coup dévastateur!" + RESET);
            }
            defenseur.perdreVitalite(dommage);
            System.out.println(GREEN + attaquant.getPrenom() + " inflige " + dommage + " dégâts !" + RESET);
        } else {
            System.out.println(YELLOW + defenseur.getPrenom() + " esquive l'attaque !" + RESET);
        }
    }

    // Résultat final
    private void afficherResultat() {
        if (joueur.getVitalite() > adversaire.getVitalite()) {
            System.out.println(GREEN + "Bravo ! Vous avez vaincu " + adversaire.getPrenom() + "!" + RESET);
        } else {
            System.out.println(RED + "Vous avez été vaincu par " + adversaire.getPrenom() + "..." + RESET);
        }
    }
    // Logiciel pour échange après combat
    private void choixApresCombat() {
        Scanner scanner = new Scanner(System.in);

        // Comparatif pour l'arme
        System.out.println("Échanger votre arme [" + joueur.getSac().getArme().getNom() + " (+" +
                joueur.getSac().getArme().getBonusAttaque() + " attaque)] contre [" +
                adversaire.getSac().getArme().getNom() + " (+" +
                adversaire.getSac().getArme().getBonusAttaque() + " attaque)] ? (O/N)");
        if (scanner.nextLine().equalsIgnoreCase("O")) {
            joueur.getSac().echangeArme(adversaire.getSac().getArme());
        }

        // Comparatif pour l'armure
        System.out.println("Échanger votre armure [" + joueur.getSac().getArmure().getNom() + " (+" +
                joueur.getSac().getArmure().getBonusDefense() + " défense)] contre [" +
                adversaire.getSac().getArmure().getNom() + " (+" +
                adversaire.getSac().getArmure().getBonusDefense() + " défense)] ? (O/N)");
        if (scanner.nextLine().equalsIgnoreCase("O")) {
            joueur.getSac().echangeArmure(adversaire.getSac().getArmure());
        }
    }

    // Récupération du lapin transformé en ration
    private void recupererLapinCommeRation() {
        if (Math.random() * 100 < joueur.getChance() + joueur.getPrecision() / 2) { // Chance augmentée par la précision
            System.out.println(GREEN + "Vous récupérez un lapin transformé! Il sera ajouté comme ration spéciale." + RESET);
            joueur.getSac().ajouterRation(1); // Ajout de la ration dans le sac
        } else {
            System.out.println(YELLOW + "Le lapin s'échappe avant que vous puissiez le récupérer!" + RESET);
        }
    }
}