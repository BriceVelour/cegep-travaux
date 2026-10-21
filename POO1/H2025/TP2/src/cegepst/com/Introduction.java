package cegepst.com;

import java.util.Scanner;

public class Introduction {

    // Lancer l'introduction du jeu
    public static void lancerIntroduction(Scanner scanner) throws InterruptedException {
        // Affichage du séparateur
        UIUtils.printSeparator(UIUtils.YELLOW);

        // Titre principal
        System.out.println(UIUtils.RED + "        BIENVENUE DANS BATTLE ROYALE" + UIUtils.RESET);

        // Affichage d'un autre séparateur
        UIUtils.printSeparator(UIUtils.YELLOW);

        // Messages d'introduction avec animation
        UIUtils.printAvecAnimation(UIUtils.PURPLE + "Préparez-vous à trois jours d'intense survie et combats !" + UIUtils.RESET, 50);
        UIUtils.printAvecAnimation(UIUtils.CYAN + "Votre objectif est simple : ne pas devenir un lapin." + UIUtils.RESET, 50);
        UIUtils.printAvecAnimation(UIUtils.GREEN + "Appuyez sur Entrée pour commencer..." + UIUtils.RESET, 50);

        // Attente de l'entrée utilisateur
        scanner.nextLine();
    }
}
