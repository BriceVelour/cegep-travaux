package cegepst.com;

public class UIUtils {
    // Codes ANSI pour les couleurs (pour une meilleure lisibilité)
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    public static void printSeparator(String color) {
        System.out.println(color + "========================================" + RESET);
    }

    public static void printAvecAnimation(String texte, int vitesse) throws InterruptedException {
        for (char c : texte.toCharArray()) {
            System.out.print(c);
            Thread.sleep(vitesse);
        }
        System.out.println();
    }
}
