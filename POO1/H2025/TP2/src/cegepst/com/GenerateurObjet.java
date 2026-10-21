package cegepst.com;

import java.util.Random;

public class GenerateurObjet {

    // Générer une arme aléatoire
    public static Arme genererArmeAleatoire() {
        String[] nomsArmes = {"Couvercle de marmite", "Fan en papier", "Marteau en plastique", "Batarang en mousse", "Épée rouillée", "Sniper de précision"};
        int[] bonusAttaque = {5, 2, 10, 7, 12, 20}; // Bonus d'attaque pour chaque arme
        int[] bonusPrecision = {0, 5, 3, 10, 15, 25}; // Bonus de précision pour chaque arme

        // Sélectionner aléatoirement une arme
        int index = new Random().nextInt(nomsArmes.length);
        return new Arme(nomsArmes[index], bonusAttaque[index], bonusPrecision[index]);
    }

    // Générer une armure aléatoire
    public static Armure genererArmureAleatoire() {
        String[] nomsArmures = {"Cartable", "Veste antiballe", "Ruban à mesurer", "Doudou jaune", "Bouclier en bois"};
        int[] bonusDefense = {5, 12, 3, 2, 8}; // Bonus de défense pour chaque armure

        // Sélectionner aléatoirement une armure
        int index = new Random().nextInt(nomsArmures.length);
        return new Armure(nomsArmures[index], bonusDefense[index]);
    }
}
