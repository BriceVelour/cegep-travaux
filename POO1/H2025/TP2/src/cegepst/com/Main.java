package cegepst.com;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // Introduction du jeu
        Introduction.lancerIntroduction(scanner);

        // Saisie des informations du joueur
        System.out.println("\nEntrez votre prénom : ");
        String joueurPrenom = scanner.nextLine();
        System.out.println("Entrez votre nom : ");
        String joueurNom = scanner.nextLine();

        // Génération des participants
        List<Participant> participants = ParticipantGenerator.genererParticipants(joueurPrenom, joueurNom);

        // Affichage des participants
        System.out.println("\n" + UIUtils.CYAN + "=== Liste des Participants ===" + UIUtils.RESET);
        for (Participant p : participants) {
            UIUtils.printSeparator(UIUtils.YELLOW);
            p.afficherCaracteristiques();
        }

        // Gestion des cycles de jours
        CycleDesJours cycleDesJours = new CycleDesJours(participants);
        cycleDesJours.gererJours();

        // Message de fin
        System.out.println("\n" + UIUtils.GREEN + "Merci d'avoir joué ! Appuyez sur Entrée pour terminer." + UIUtils.RESET);
        scanner.nextLine();
        scanner.close();
    }
}
