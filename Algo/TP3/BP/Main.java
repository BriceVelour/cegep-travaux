package com.cegepst;

import java.io.*;
import java.util.*;

public class Main {
    static final String FILENAME = "mots.txt";
    static final int MAX_TRIES = 6;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choix;
        do {
            menu();
            choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne
            switch (choix) {
                case 1:
                    nouvellePartie();
                    break;
                case 2:
                    ajouterMot();
                    break;
                case 3:
                    supprimerMot();
                    break;
                case 4:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix non valide.");
            }
        } while (choix != 4);
    }

    static void menu() {
        System.out.println("1. Nouvelle partie");
        System.out.println("2. Ajouter un mot");
        System.out.println("3. Supprimer un mot");
        System.out.println("4. Quitter");
    }

    static void nouvellePartie() {
        List<String> mots = lireMotsDepuisFichier();
        if (mots.isEmpty()) {
            System.out.println("Pas de mots disponibles.");
            return;
        }
        Random rand = new Random();
        String motSecret = mots.get(rand.nextInt(mots.size()));
        char[] etatMot = new char[motSecret.length()];
        Arrays.fill(etatMot, '_');
        int tentatives = MAX_TRIES;

        Scanner scanner = new Scanner(System.in);
        while (tentatives > 0 && !String.valueOf(etatMot).equals(motSecret)) {
            System.out.println("Mot recherché : " + String.valueOf(etatMot));
            System.out.print("Saisir une lettre ou le mot entier : ");
            String saisie = scanner.nextLine();

            if (saisie.length() == motSecret.length()) {
                if (saisie.equals(motSecret)) {
                    System.out.println("Bravo, vous avez gagné !");
                    return;
                } else {
                    tentatives--;
                    System.out.println("Mauvaise réponse. Il vous reste " + tentatives + " tentatives.");
                }
            } else if (saisie.length() == 1) {
                char lettre = saisie.charAt(0);
                if (motSecret.indexOf(lettre) >= 0) {
                    for (int i = 0; i < motSecret.length(); i++) {
                        if (motSecret.charAt(i) == lettre) {
                            etatMot[i] = lettre;
                        }
                    }
                    System.out.println("Bonne lettre ! Il vous reste " + tentatives + " tentatives.");
                } else {
                    tentatives--;
                    System.out.println("Mauvaise lettre. Il vous reste " + tentatives + " tentatives.");
                }
            }
        }

        if (tentatives == 0) {
            System.out.println("Vous avez perdu. Le mot était : " + motSecret);
        } else {
            System.out.println("Félicitations ! Vous avez trouvé le mot : " + motSecret);
        }
    }

    static void ajouterMot() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Saisir un nouveau mot : ");
        String nouveauMot = scanner.nextLine();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(nouveauMot);
            writer.newLine();
            System.out.println("Mot ajouté avec succès.");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ajout du mot.");
        }
    }

    static void supprimerMot() {
        List<String> mots = lireMotsDepuisFichier();
        if (mots.isEmpty()) {
            System.out.println("Pas de mots disponibles.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < mots.size(); i++) {
            System.out.println((i + 1) + ". " + mots.get(i));
        }
        System.out.print("Sélectionner un numéro de mot à supprimer : ");
        int selection = scanner.nextInt();
        if (selection > 0 && selection <= mots.size()) {
            mots.remove(selection - 1);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
                for (String mot : mots) {
                    writer.write(mot);
                    writer.newLine();
                }
                System.out.println("Mot supprimé avec succès.");
            } catch (IOException e) {
                System.out.println("Erreur lors de la suppression du mot.");
            }
        } else {
            System.out.println("Numéro invalide.");
        }
    }

    static List<String> lireMotsDepuisFichier() {
        List<String> mots = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                mots.add(ligne);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier.");
        }
        return mots;
    }
}
