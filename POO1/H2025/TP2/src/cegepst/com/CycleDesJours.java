package cegepst.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CycleDesJours {
    // Codes ANSI pour les couleurs (pour une meilleure lisibilité)
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    private List<Participant> participants; // Liste des participants restants
    private double[] probabilitesElimination = {0.45, 0.30, 0.20}; // Probabilités d'élimination pour chaque jour
    private Scanner scanner;

    // Constructeur
    public CycleDesJours(List<Participant> participants) {
        this.participants = participants;
        this.scanner = new Scanner(System.in);
    }

    // Gestion des jours, incluant combats, événements et interactions
    public void gererJours() throws InterruptedException {
        int jour = 1;

        for (double prob : probabilitesElimination) {
            System.out.println(RED + "\n=== JOUR " + jour + " ===" + RESET);

            // Narration immersive
            afficherNarration(jour);

            // Éliminations avec suspense
            eliminerParticipants(prob);

            // Vérification si le joueur humain est éliminé
            if (joueurEstElimine()) {
                System.out.println(RED + "Vous avez été éliminé. Fin de la partie." + RESET);
                afficherClassementFinal();
                return; // Fin immédiate
            }

            // Combat aléatoire entre deux participants
            if (participants.size() > 1) {
                organiserCombat();
            }

            // Vérification après combat
            if (joueurEstElimine()) {
                System.out.println(RED + "Vous avez été éliminé pendant le combat. Fin de la partie." + RESET);
                afficherClassementFinal();
                return; // Fin immédiate
            }

            // Événement aléatoire
            declencherEvenementAleatoire();

            // Interaction entre les jours (sauf dernier jour)
            if (jour < probabilitesElimination.length && !joueurEstElimine()) {
                interactionEntreJours();
            }

            jour++; // Passage au jour suivant
        }

        // Victoire si le joueur survit aux trois jours
        System.out.println(GREEN + "Félicitations ! Vous avez survécu aux trois jours et gagné la partie !" + RESET);
        afficherClassementFinal();
    }

    // Vérifie si le joueur humain est éliminé
    private boolean joueurEstElimine() {
        return !participants.contains(participants.get(0)); // Suppose que le joueur est toujours en position 0
    }

    // Organise un combat aléatoire
    private void organiserCombat() throws InterruptedException {
        System.out.println(YELLOW + "\nUn combat va avoir lieu entre deux participants !" + RESET);

        Participant p1 = participants.get((int) (Math.random() * participants.size()));
        Participant p2 = participants.get((int) (Math.random() * participants.size()));

        while (p1.equals(p2)) { // Assure que les participants sont différents
            p2 = participants.get((int) (Math.random() * participants.size()));
        }

        Combat combat = new Combat(p1, p2);
        combat.commencerCombat();

        // Retirer les participants éliminés
        if (p1.getVitalite() <= 0) {
            participants.remove(p1);
            System.out.println(RED + p1.getPrenom() + " " + p1.getNom() + " a été éliminé après le combat." + RESET);
        }
        if (p2.getVitalite() <= 0) {
            participants.remove(p2);
            System.out.println(RED + p2.getPrenom() + " " + p2.getNom() + " a été éliminé après le combat." + RESET);
        }
    }

    // Affiche une narration immersive
    private void afficherNarration(int jour) throws InterruptedException {
        switch (jour) {
            case 1 -> System.out.println(PURPLE + "Le brouillard s'étend sur l'île... Qui survivra aujourd'hui ?" + RESET);
            case 2 -> System.out.println(PURPLE + "Le danger s'intensifie. Les alliances se brisent !" + RESET);
            case 3 -> System.out.println(PURPLE + "Le final approche. Chaque décision compte !" + RESET);
        }
        Thread.sleep(2000);
    }

    // Élimine des participants selon la probabilité
    private void eliminerParticipants(double prob) throws InterruptedException {
        List<Participant> elimines = new ArrayList<>();
        for (Participant p : participants) {
            if (Math.random() < prob) {
                elimines.add(p);
            }
        }
        participants.removeAll(elimines);

        System.out.println(RED + "=== Éliminations du jour ===" + RESET);
        for (Participant elimine : elimines) {
            System.out.println(RED + elimine.getPrenom() + " " + elimine.getNom() + " a été éliminé !" + RESET);
            Thread.sleep(1000);
        }
        System.out.println(GREEN + "Participants restants : " + participants.size() + RESET);
        Thread.sleep(1000);
    }

    // Déclenche un événement aléatoire
    private void declencherEvenementAleatoire() throws InterruptedException {
        System.out.println(YELLOW + "\nUn événement imprévu se produit..." + RESET);
        int evenement = (int) (Math.random() * 3);
        switch (evenement) {
            case 0 -> System.out.println(RED + "Une tempête sauvage frappe l'île!" + RESET);
            case 1 -> System.out.println(CYAN + "Un coffre mystérieux apparaît. Qui aura la chance de le trouver?" + RESET);
            case 2 -> System.out.println(GREEN + "Le soleil brille. Tout le monde regagne 10 points de vitalité!" + RESET);
        }
        Thread.sleep(2000);
    }

    // Interaction entre les jours
    private void interactionEntreJours() throws InterruptedException {
        System.out.println(CYAN + "\nEntre les jours, vous avez le choix :" + RESET);
        System.out.println("1. Explorer pour trouver des objets.");
        System.out.println("2. Se reposer pour regagner de la vitalité.");
        System.out.println("3. Observer les adversaires.");
        System.out.print("Que décidez-vous ? ");
        int choix = scanner.nextInt();

        switch (choix) {
            case 1 -> System.out.println(CYAN + "Vous explorez l'île et trouvez un objet utile !" + RESET);
            case 2 -> {
                System.out.println(GREEN + "Vous vous reposez et regagnez 15 points de vitalité." + RESET);
                participants.get(0).perdreVitalite(-15); // Restaure 15 points
            }
            case 3 -> System.out.println(YELLOW + "Vous analysez vos adversaires et planifiez votre prochain mouvement." + RESET);
            default -> System.out.println(RED + "Choix invalide. Vous perdez du temps !" + RESET);
        }
        Thread.sleep(2000);
    }

    // Affiche le classement final
    private void afficherClassementFinal() {
        System.out.println(GREEN + "\n=== Classement Final ===" + RESET);
        participants.sort((p1, p2) -> Integer.compare(p2.getEliminations(), p1.getEliminations()));
        for (Participant p : participants) {
            System.out.println(p.getPrenom() + " " + p.getNom() + " : " + p.getEliminations() + " éliminations");
        }
    }
}
