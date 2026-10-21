#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <time.h>
#include <ctype.h>
#include "menu.h"
#include "utils.h"
#include "structures.h"
#include "algorithmes.h"

int main() {
    char commande[30];
    int algoChoisi = 0;
    TriStatistiques stats = {NULL, 0, 0, 0.0};

    srand(time(NULL));
    afficherBanniere();

    do {
        afficherMenuStylise();
        printf("Entrez une commande : ");
        scanf("%s", commande);
        convertirEnMajuscules(commande);

        if (strcmp(commande, "CHARGER") == 0) {
            const char *fichiersDisponibles[] = {
                "base_principale.csv",
                "tri_sportif.csv",
                "classement_notes.csv"
            };
            int nombreFichiers = 3;
            int tentative = 0;

            do {
                afficherTexteDefilant("Voici les fichiers disponibles :");
                for (int i = 0; i < nombreFichiers; i++) {
                    printf("- %s\n", fichiersDisponibles[i]);
                }

                printf("Entrez le nom exact du fichier que vous voulez charger : ");
                char nomFichier[100];
                scanf("%s", nomFichier);

                int fichierTrouve = 0;
                for (int i = 0; i < nombreFichiers; i++) {
                    if (strcasecmp(nomFichier, fichiersDisponibles[i]) == 0) {
                        fichierTrouve = 1;
                        break;
                    }
                }

                if (!fichierTrouve) {
                    afficherTexteDefilant("[ERREUR] Fichier non reconnu.");
                    printf("Voulez-vous reessayer ? (o/n) : ");
                    char reessayer;
                    scanf(" %c", &reessayer);
                    if (reessayer == 'n' || reessayer == 'N') break;
                    tentative = 1;
                } else {
                    afficherChargement();
                    char cheminComplet[150];
                    sprintf(cheminComplet, "data/%s", nomFichier);

                    if (chargerBaseDeDonnees(cheminComplet, &stats.donnees, &stats.taille) == 0) {
                        printf("[SUCCES] %d elements charges depuis %s.\n", stats.taille, cheminComplet);
                        afficherTexteDefilant("Base de donnees chargee avec succes !");
                    } else {
                        afficherTexteDefilant("[ERREUR] Impossible de charger les donnees !");
                    }
                    tentative = 0;
                }
            } while (tentative);

        } else if (strcmp(commande, "ALGORITHME") == 0) {
            char choixAlgo[20];
            int tentative = 0;

            do {
                afficherTexteDefilant("+------------------------------------------------------------+");
                afficherTexteDefilant("|                 SELECTIONNER UN ALGORITHME                 |");
                afficherTexteDefilant("+------------------------------------------------------------+");
                printf("| %-15s -> %-39s |\n", "BULLE",     "Tri simple par echanges successifs");
                printf("| %-15s -> %-39s |\n", "SELECTION", "Tri par selection des minimas");
                printf("| %-15s -> %-39s |\n", "RAPIDE",    "Tri rapide (QuickSort)");
                printf("| %-15s -> %-39s |\n", "FUSION",    "Tri par fusion (MergeSort)");
                printf("| %-15s -> %-39s |\n", "TAS",       "Tri par tas (HeapSort)");
                afficherTexteDefilant("+------------------------------------------------------------+");

                printf("\nEntrez votre choix : ");
                scanf("%s", choixAlgo);
                convertirEnMajuscules(choixAlgo);

                if (strcmp(choixAlgo, "BULLE") == 0) algoChoisi = 1;
                else if (strcmp(choixAlgo, "SELECTION") == 0) algoChoisi = 2;
                else if (strcmp(choixAlgo, "RAPIDE") == 0) algoChoisi = 3;
                else if (strcmp(choixAlgo, "FUSION") == 0) algoChoisi = 4;
                else if (strcmp(choixAlgo, "TAS") == 0) algoChoisi = 5;
                else {
                    afficherTexteDefilant("[ERREUR] Algorithme non reconnu.");
                    printf("Voulez-vous reessayer ? (o/n) : ");
                    char reessayer;
                    scanf(" %c", &reessayer);
                    if (reessayer == 'n' || reessayer == 'N') {
                        algoChoisi = 0;
                        break;
                    }
                    tentative = 1;
                    continue;
                }

                if (algoChoisi != 0) {
                    afficherTexteDefilant("[INFO] Selection de l'algorithme...");
                    afficherBarreDeProgression();
                    afficherTexteDefilant("[SUCCES] Algorithme selectionne avec succes !");

                    printf("\n==========================================================================\n");
                    switch (algoChoisi) {
                        case 1: printf("| Vous avez choisi : Tri Bulle (Simple mais lent pour grandes listes) |\n"); break;
                        case 2: printf("| Vous avez choisi : Tri Selection (Recherche du minimum a chaque passe) |\n"); break;
                        case 3: printf("| Vous avez choisi : Tri Rapide (Ultra rapide pour grandes quantites) |\n"); break;
                        case 4: printf("| Vous avez choisi : Tri Fusion (TrEs performant et stable) |\n"); break;
                        case 5: printf("| Vous avez choisi : Tri Par Tas (Rapide pour gros volumes) |\n"); break;
                        default: printf("| Algorithme inconnu |\n"); break;
                    }
                    printf("==========================================================================\n");
                    tentative = 0;
                }
            } while (tentative);

        } else if (strcmp(commande, "TRI") == 0) {
            if (stats.donnees == NULL || stats.taille == 0) {
                afficherTexteDefilant("[ERREUR] Aucune donnee chargee. Veuillez d'abord charger une base !");
            } else if (algoChoisi < 1 || algoChoisi > 5) {
                afficherTexteDefilant("[ERREUR] Vous devez d'abord selectionner un algorithme valide.");
            } else {
                afficherTexteDefilant("Execution de l'algorithme choisi...");
                afficherBarreDeProgression();

                stats.nombreComparaisons = 0;
                clock_t debut = clock();

                printf("\n+---------------------+\n");
                printf("| Donnees avant tri   |\n");
                printf("+---------------------+\n");
                for (int i = 0; i < stats.taille; i++) printf("%d ", stats.donnees[i]);
                printf("\n\n");

                if (algoChoisi == 1) bubbleSort(&stats);
                else if (algoChoisi == 2) selectionSort(&stats);
                else if (algoChoisi == 3) quickSort(&stats);
                else if (algoChoisi == 4) mergeSort(&stats);
                else if (algoChoisi == 5) heapSort(&stats);

                clock_t fin = clock();
                stats.temps = (double)(fin - debut) / CLOCKS_PER_SEC;

                afficherTexteDefilant("[SUCCES] Tri effectue avec succes !");

                printf("\n+---------------------+\n");
                printf("| Donnees triees      |\n");
                printf("+---------------------+\n");
                for (int i = 0; i < stats.taille; i++) printf("%d ", stats.donnees[i]);
                printf("\n\n");

                afficherTexteDefilant("+------------------------------------------------------------+");
                afficherTexteDefilant("|                    Resume du tri effectue                  |");
                afficherTexteDefilant("+------------------------------------------------------------+");

                char *nomAlgo;
                switch (algoChoisi) {
                    case 1: nomAlgo = "Tri Bulle"; break;
                    case 2: nomAlgo = "Tri Selection"; break;
                    case 3: nomAlgo = "Tri Rapide"; break;
                    case 4: nomAlgo = "Tri Fusion"; break;
                    case 5: nomAlgo = "Tri Par Tas"; break;
                    default: nomAlgo = "Inconnu"; break;
                }

                printf("| Algorithme utilise : %-35s   |\n", nomAlgo);
                printf("| Nombre d'analyses entre les valeurs : %-10d           |\n", stats.nombreComparaisons);
                printf("| (Chaque analyse compare 2 valeurs pour les ordonner)       |\n");
                printf("| Temps d'execution : %-10.4f secondes                    |\n", stats.temps);
                afficherTexteDefilant("+------------------------------------------------------------+\n");
            }

        } else if (strcmp(commande, "STATISTIQUES") == 0) {
            if (stats.donnees == NULL || stats.taille == 0) {
                afficherTexteDefilant("[ERREUR] Aucune base de donnees chargee !");
            } else if (stats.nombreComparaisons == 0) {
                afficherTexteDefilant("[INFO] Vous devez effectuer un tri avant d'analyser les statistiques !");
            } else {
                afficherTexteDefilant("[INFO] Analyse des performances en cours...");
                afficherBarreDeProgression();

                printf("\n");
                afficherTexteDefilant("+------------------------------------------------------------+");
                afficherTexteDefilant("|                 Statistiques de TriXpress                  |");
                afficherTexteDefilant("+------------------------------------------------------------+");

                char *nomAlgo;
                switch (algoChoisi) {
                    case 1: nomAlgo = "Tri Bulle"; break;
                    case 2: nomAlgo = "Tri Selection"; break;
                    case 3: nomAlgo = "Tri Rapide"; break;
                    case 4: nomAlgo = "Tri Fusion"; break;
                    case 5: nomAlgo = "Tri Par Tas"; break;
                    default: nomAlgo = "Inconnu"; break;
                }

                printf("| Algorithme utilise            : %-26s |\n", nomAlgo);
                printf("| Nombre d'analyses effectuees  : %-10d                 |\n", stats.nombreComparaisons);
                printf("| Temps d'execution             : %-10.4f secondes        |\n", stats.temps);
                afficherTexteDefilant("+------------------------------------------------------------+");

                printf("\n");
                if (stats.temps > 1.0) {
                    afficherTexteDefilant("[ASTUCE] Le tri semble lent. Essayez QuickSort ou HeapSort pour un meilleur temps !");
                } else if (stats.nombreComparaisons > 10000) {
                    afficherTexteDefilant("[ASTUCE] Beaucoup de comparaisons ! Un tri fusion pourrait optimiser cela.");
                } else {
                    afficherTexteDefilant("[INFO] Excellent choix d'algorithme pour vos donnees !");
                }

                printf("\n");
                afficherTexteDefilant("+---------------- Graphe Visuel de Performance --------------+");

                int echelleComparaison = stats.nombreComparaisons / 10;
                if (echelleComparaison < 1 && stats.nombreComparaisons > 0) echelleComparaison = 1;

                printf("| Comparaisons : ");
                for (int i = 0; i < echelleComparaison && i < 50; i++) printf("#");
                printf(" (%d)\n", stats.nombreComparaisons);

                int echelleTemps = (int)(stats.temps * 1000);
                if (echelleTemps < 1 && stats.temps > 0) echelleTemps = 1;

                printf("| Temps (en ms) : ");
                for (int i = 0; i < echelleTemps && i < 50; i++) printf("#");
                printf(" (%.4f s)\n", stats.temps);

                afficherTexteDefilant("+------------------------------------------------------------+");
            }

        } else if (strcmp(commande, "SAUVEGARDER") == 0) {
            if (stats.donnees == NULL || stats.taille == 0) {
                afficherTexteDefilant("[ERREUR] Aucune donnees a sauvegarder !");
            } else {
                afficherTexteDefilant("[INFO] Sauvegarde en cours...");
                afficherBarreDeProgression();

                char nomFichier[150];
                printf("Entrez le nom du fichier pour la sauvegarde (laisser vide pour un nom automatique) : ");
                char saisie[100];
                fgets(saisie, sizeof(saisie), stdin); // pour vider stdin
                fgets(saisie, sizeof(saisie), stdin);
                saisie[strcspn(saisie, "\n")] = '\0';

                if (strlen(saisie) == 0) {
                    time_t now = time(NULL);
                    struct tm *t = localtime(&now);
                    strftime(nomFichier, sizeof(nomFichier), "resultats_tri_%Y%m%d_%H%M%S.csv", t);
                } else {
                    strncpy(nomFichier, saisie, sizeof(nomFichier));
                }

                const char *nomAlgo;
                switch (algoChoisi) {
                    case 1: nomAlgo = "Tri Bulle"; break;
                    case 2: nomAlgo = "Tri Selection"; break;
                    case 3: nomAlgo = "Tri Rapide"; break;
                    case 4: nomAlgo = "Tri Fusion"; break;
                    case 5: nomAlgo = "Tri Par Tas"; break;
                    default: nomAlgo = "Inconnu"; break;
                }

                char cheminComplet[200];
                snprintf(cheminComplet, sizeof(cheminComplet), "saves/%s", nomFichier);

                if (sauvegarderResultats(cheminComplet, &stats, nomAlgo) == 0) {
                    printf("[SUCCES] Resultats sauvegardes dans : %s\n", cheminComplet);
                    printf("Date de sauvegarde : ");
                    time_t now = time(NULL);
                    struct tm *t = localtime(&now);
                    char date[100];
                    strftime(date, sizeof(date), "%Y-%m-%d %H:%M:%S", t);
                    printf("%s\n", date);
                } else {
                    afficherTexteDefilant("[ERREUR] Impossible de sauvegarder les resultats !");
                }
            }

        } else if (strcmp(commande, "HELP") == 0) {
            afficherAideComplete();

        } else if (strcmp(commande, "QUITTER") == 0) {
            afficherTexteDefilant("\n[INFO] Fermeture de TriXpress...");
            afficherBarreDeProgression();

            printf("\n+------------------------------------------------------------+\n");
            printf("|                  Merci d'avoir utilise TriXpress           |\n");
            printf("|             Que vos donnees soient toujours triees         |\n");
            printf("|                     avec style et rigueur !                |\n");
            printf("+------------------------------------------------------------+\n");
            printf("\n");
            afficherTexteDefilant("A bientot pour une nouvelle session de tri");
            break;

        } else {
            afficherTexteDefilant("[ERREUR] Commande non reconnue. Tapez 'HELP' pour voir les options.");
        }

    } while (1);

    free(stats.donnees);
    return 0;
}

