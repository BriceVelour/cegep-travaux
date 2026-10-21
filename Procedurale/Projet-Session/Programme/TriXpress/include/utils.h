#ifndef UTILS_H
#define UTILS_H

#include "structures.h"  // pour TriStatistiques

// Conversion et affichage stylisé
void convertirEnMajuscules(char *texte);
void afficherTexteDefilant(const char *texte);
void afficherBarreDeProgression(void);
void afficherChargement(void);

// Chargement et validation des données
int validerDonnees(const char *ligne);
int chargerBaseDeDonnees(const char *nomFichier, int **tableau, int *taille);

// Sauvegarde des résultats dans un fichier CSV
int sauvegarderResultats(const char *cheminComplet, TriStatistiques *stats, const char *nomAlgo);

#endif
