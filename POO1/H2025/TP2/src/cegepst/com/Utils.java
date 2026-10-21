package cegepst.com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    // Lire un fichier texte et retourner une liste des lignes
    public static List<String> lireFichier(String cheminFichier) {
        List<String> lignes = new ArrayList<>();

        // Ouvrir le fichier avec un BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;

            // Lire chaque ligne et l'ajouter à la liste
            while ((ligne = br.readLine()) != null) {
                lignes.add(ligne.trim()); // Supprime les espaces inutiles
            }
        } catch (IOException e) {
            // Afficher une erreur si la lecture échoue
            System.out.println("Erreur lors de la lecture du fichier : " + cheminFichier);
        }

        // Retourner la liste des lignes lues
        return lignes;
    }
}
