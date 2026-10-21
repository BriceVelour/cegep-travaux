package cegepst.com;

import java.io.FileWriter;
import java.io.IOException;

public class GenerateurFichiers {

    public static void main(String[] args) {
        // Liste de prénoms
        String[] prenoms = {
                "Alice", "Bob", "Clara", "David", "Emma",
                "Felix", "Grace", "Hugo", "Isabelle", "Jack",
                "Kim", "Léo", "Maya", "Nathan", "Olivia",
                "Paul", "Quentin", "Rose", "Sarah", "Tom"
        };

        // Liste de noms
        String[] noms = {
                "Anderson", "Brown", "Carter", "Davis", "Evans",
                "Foster", "Garcia", "Harris", "Jones", "Klein",
                "Lewis", "Martin", "Nelson", "Olivier", "Perez",
                "Quinn", "Roberts", "Smith", "Taylor", "Walker"
        };

        // Générer le fichier des prénoms
        try (FileWriter writer = new FileWriter("prenoms.txt")) {
            for (String prenom : prenoms) {
                writer.write(prenom + "\n");
            }
            System.out.println("Fichier 'prenoms.txt' généré avec succès !");
        } catch (IOException e) {
            System.out.println("Erreur lors de la génération du fichier prenoms.txt.");
        }

        // Générer le fichier des noms
        try (FileWriter writer = new FileWriter("noms.txt")) {
            for (String nom : noms) {
                writer.write(nom + "\n");
            }
            System.out.println("Fichier 'noms.txt' généré avec succès !");
        } catch (IOException e) {
            System.out.println("Erreur lors de la génération du fichier noms.txt.");
        }
    }
}
