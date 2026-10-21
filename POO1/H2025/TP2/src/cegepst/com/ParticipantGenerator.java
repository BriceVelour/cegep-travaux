package cegepst.com;

import java.util.ArrayList;
import java.util.List;

public class ParticipantGenerator {
    // Générer une liste de participants
    public static List<Participant> genererParticipants(String joueurPrenom, String joueurNom) {
        List<Participant> participants = new ArrayList<>();

        // Charger les fichiers de prénoms et noms
        List<String> prenoms = Utils.lireFichier("prenoms.txt");
        List<String> noms = Utils.lireFichier("noms.txt");

        // Vérifier si les fichiers sont vides
        if (prenoms.isEmpty() || noms.isEmpty()) {
            System.out.println(UIUtils.RED + "Erreur : fichiers de prénoms ou noms vides." + UIUtils.RESET);
            return participants; // Retourne une liste vide si erreur
        }

        // Ajouter le joueur humain
        Participant joueur = new Participant(joueurPrenom, joueurNom);
        participants.add(joueur);

        // Générer 41 participants aléatoires
        for (int i = 0; i < 41; i++) {
            String prenomAleatoire = prenoms.get((int) (Math.random() * prenoms.size()));
            String nomAleatoire = noms.get((int) (Math.random() * noms.size()));
            Participant participant = new Participant(prenomAleatoire, nomAleatoire);
            participants.add(participant);
        }

        return participants; // Retourne la liste complète des participants
    }
}
