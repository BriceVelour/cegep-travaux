#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include <sys/stat.h>
#include <errno.h>
#include "utils.h"
#include "structures.h"
#include <ctype.h>


void convertirEnMajuscules(char *texte) {
    for (int i = 0; texte[i] != '\0'; i++) {
        texte[i] = toupper(texte[i]);
    }
}

void afficherTexteDefilant(const char *texte) {
    for (int i = 0; texte[i] != '\0'; i++) {
        printf("%c", texte[i]);
        fflush(stdout);
        usleep(25000);
    }
    printf("\n");
}

void afficherBarreDeProgression() {
    printf("[");
    int largeur = 30;
    int progression = 0;
    while (progression < largeur) {
        int pas = rand() % 3 + 1;
        for (int i = 0; i < pas && progression < largeur; i++, progression++) {
            printf("#");
            fflush(stdout);
            usleep(100000);
        }
    }
    printf("] 100%%\n");
}

void afficherChargement() {
    printf("[INFO] Chargement en cours");
    for (int i = 0; i < 3; i++) {
        printf(".");
        fflush(stdout);
        sleep(1);
    }
    printf("\n");
}

int validerDonnees(const char *ligne) {
    const char *ptr = ligne;
    while (*ptr) {
        if (*ptr == ' ' || *ptr == '\t') {
            ptr++;
            continue;
        }
        if ((*ptr < '0' || *ptr > '9') && *ptr != ',' && *ptr != '\n') {
            printf("Ligne invalide : %s\n", ligne);
            return 0;
        }
        ptr++;
    }
    return 1;
}

int chargerBaseDeDonnees(const char *nomFichier, int **tableau, int *taille) {
    FILE *fichier = fopen(nomFichier, "r");
    if (fichier == NULL) {
        perror("Erreur");
        return -1;
    }

    int count = 0;
    char ligne[256];
    while (fgets(ligne, sizeof(ligne), fichier)) {
        if (validerDonnees(ligne)) {
            char *token = strtok(ligne, ",");
            while (token != NULL) {
                count++;
                token = strtok(NULL, ",");
            }
        } else {
            printf("Erreur : Donnees invalides dans le fichier.\n");
            fclose(fichier);
            return -1;
        }
    }

    *tableau = (int *)malloc(count * sizeof(int));
    if (*tableau == NULL) {
        printf("Erreur : Allocation de memoire echouee.\n");
        fclose(fichier);
        return -1;
    }

    rewind(fichier);
    *taille = 0;
    while (fgets(ligne, sizeof(ligne), fichier)) {
        if (validerDonnees(ligne)) {
            char *token = strtok(ligne, ",");
            while (token != NULL) {
                (*tableau)[(*taille)++] = atoi(token);
                token = strtok(NULL, ",");
            }
        }
    }

    fclose(fichier);
    return 0;
}

int sauvegarderResultats(const char *cheminComplet, TriStatistiques *stats, const char *nomAlgo) {
    struct stat st = {0};
    if (stat("saves", &st) == -1) {
        if (mkdir("saves") != 0) {
            perror("[ERREUR] Impossible de créer le dossier 'saves'");
            return -1;
        }
    }

    FILE *fichier = fopen(cheminComplet, "w");
    if (fichier == NULL) {
        perror("[ERREUR] Impossible de créer le fichier de sauvegarde");
        return -1;
    }

    time_t now = time(NULL);
    struct tm *t = localtime(&now);
    char dateHeure[100];
    strftime(dateHeure, sizeof(dateHeure), "%Y-%m-%d %H:%M:%S", t);

    fprintf(fichier, "Algorithme,%s\n", nomAlgo);
    fprintf(fichier, "Comparaisons,%d\n", stats->nombreComparaisons);
    fprintf(fichier, "Temps,%.4f s\n", stats->temps);
    fprintf(fichier, "Date,%s\n", dateHeure);
    fprintf(fichier, "DonneesTriees,");

    for (int i = 0; i < stats->taille; i++) {
        fprintf(fichier, "%d", stats->donnees[i]);
        if (i < stats->taille - 1) fprintf(fichier, ",");
    }
    fprintf(fichier, "\n");

    fclose(fichier);
    return 0;
}
