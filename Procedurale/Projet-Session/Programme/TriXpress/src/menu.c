#include <stdio.h>
#include "menu.h"
#include "utils.h"  // pour afficherTexteDefilant

void afficherMenuAlgorithmes() {
    afficherTexteDefilant("+-------------------------------------------------------------+");
    afficherTexteDefilant("|                 SELECTIONNER UN ALGORITHME                  |");
    afficherTexteDefilant("+-------------------------------------------------------------+");
    printf("| %-15s -> %-39s |\n", "BULLE",     "Tri simple par echanges successifs");
    printf("| %-15s -> %-39s |\n", "SELECTION", "Tri par selection des minimas");
    printf("| %-15s -> %-39s |\n", "RAPIDE",    "Tri rapide (QuickSort)");
    printf("| %-15s -> %-39s |\n", "FUSION",    "Tri par fusion (MergeSort)");
    printf("| %-15s -> %-39s |\n", "TAS",       "Tri par tas (HeapSort)");
    afficherTexteDefilant("+-------------------------------------------------------------+");
}

void afficherTableauScores() {
    printf("+------------------+------------------------+---------------+\n");
    printf("| Algorithme       | Etapes de comparaison  | Temps moyen   |\n");
    printf("+------------------+------------------------+---------------+\n");
    printf("| Tri Bulle        | ~5000                  | 0.2s          |\n");
    printf("| Tri Selection    | ~3000                  | 0.15s         |\n");
    printf("| Tri Rapide       | ~1000                  | 0.05s         |\n");
    printf("| Tri Fusion       | ~1200                  | 0.04s         |\n");
    printf("| Tri Par Tas      | ~1500                  | 0.06s         |\n");
    printf("+------------------+------------------------+---------------+\n");
}

void afficherBanniere() {
    printf("==============================================================\n");
    printf("|                          TriXpress                         |\n");
    printf("|              DOMINEZ VOS DONNEES AVEC ELEGANCE             |\n");
    printf("==============================================================\n");
}

void afficherMenuStylise() {
    printf("\n");
    afficherTexteDefilant("+------------------------------------------------------------+");
    afficherTexteDefilant("|                        MENU PRINCIPAL                      |");
    afficherTexteDefilant("+------------------------------------------------------------+");
    printf("| %-15s -> %-39s |\n", "CHARGER",     "Charger une base de donnees");
    printf("| %-15s -> %-39s |\n", "ALGORITHME",  "Choisir un algorithme de tri");
    printf("| %-15s -> %-39s |\n", "TRI",         "Executer le tri selectionne");
    printf("| %-15s -> %-39s |\n", "STATISTIQUES","Voir les performances");
    printf("| %-15s -> %-39s |\n", "SAUVEGARDER", "Sauvegarder les resultats");
    printf("| %-15s -> %-39s |\n", "QUITTER",     "Quitter TriXpress");
    afficherTexteDefilant("+------------------------------------------------------------+");
}

void afficherAideComplete() {
    afficherTexteDefilant("+------------------ AIDE DE TRIXPRESS -----------------------+");
    printf("| %-12s -> %s\n", "CHARGER",     "Charger une base de donnees (.csv)");
    printf("| %-12s -> %s\n", "ALGORITHME", "Choisir un algorithme de tri");
    printf("| %-12s -> %s\n", "TRI",         "Executer le tri selectionne");
    printf("| %-12s -> %s\n", "STATISTIQUES","Afficher les performances du tri");
    printf("| %-12s -> %s\n", "SAUVEGARDER", "Sauvegarder les donnees triees");
    printf("| %-12s -> %s\n", "HELP",        "Afficher ce menu d'aide");
    printf("| %-12s -> %s\n", "QUITTER",     "Quitter l'application proprement");
    afficherTexteDefilant("+------------------------------------------------------------+");
    printf("\n[INFO] Entrez les commandes en lettres majuscules pour un fonctionnement optimal.\n");
}
