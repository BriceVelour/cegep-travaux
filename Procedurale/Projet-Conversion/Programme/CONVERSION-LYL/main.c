#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

// Constantes pour les fichiers
#define FICHIER_SOURCE "db1_2025_v1.csv"
#define FICHIER_DESTINATION "bdprod.csv"
#define MAX_PATIENTS 17702 // Limite maximale de patients

// Structure pour representer un patient
typedef struct {
    int patient_id;
    char lastname[50];
    char firstname[50];
    char sex[10];
    char birthday[15];
    char email[50];
    char road[50];
    char appart[10];
    char town[50];
    char province[20];
    char country[20];
    char postal_code[15];
    char ramq_date_file_no[20];
    char language[20];
    char creation_date[15];
    char norecall[10];
    char job[50];
    int actif; // 1 = actif, 0 = inactif
} Patient;


// Tableau pour stocker les patients
Patient patients[MAX_PATIENTS];
int nombrePatients = 0;

// Fonction pour afficher la date et l'heure actuelles
void afficherDateHeure() {
    time_t t = time(NULL);
    struct tm *tm_info = localtime(&t);
    char buffer[26];
    strftime(buffer, 26, "%Y-%m-%d %H:%M:%S", tm_info);
    printf("\nDate et heure actuelles : %s\n", buffer);
}


// Fonction pour afficher le menu principal modernisé
void afficherMenu() {
    afficherDateHeure(); // Afficher la date et l'heure

    printf("\n**************************************\n");
    printf("          MENU PRINCIPAL         \n");
    printf("**************************************\n");
    printf("    1. Importer les donnees           \n");
    printf("    2. Afficher les patients          \n");
    printf("    3. Exporter les donnees           \n");
    printf("    4. Rechercher un patient          \n");
    printf("    5. Ajouter un patient             \n");
    printf("    6. Desactiver un patient          \n");
    printf("    7. Charger un fichier existant    \n");
    printf("    8. Quitter le programme           \n");
    printf("**************************************\n");
    printf("Que souhaitez-vous faire? Votre choix:");
}

// Fonction pour importer les donnees depuis un fichier CSV
void importerDonnees() {
    FILE *fichier = fopen(FICHIER_SOURCE, "r");
    if (!fichier) {
        printf("Erreur : impossible d'ouvrir le fichier source.\n");
        return;
    }

    char ligne[1024];
    int lignesIgnorees = 0; // Compteur pour les lignes invalides
    nombrePatients = 0;

    printf("\n===== DEBUT DE L'IMPORTATION =====\n");

    clock_t debut = clock(); // Commence le chronometrage

    while (fgets(ligne, sizeof(ligne), fichier)) {
        Patient p;

        // Lire toutes les colonnes du fichier CSV
        int champsLus = sscanf(ligne, "%d;%49[^;];%49[^;];%9[^;];%14[^;];%49[^;];%49[^;];%9[^;];%49[^;];%19[^;];%19[^;];%14[^;];%19[^;];%14[^;];%9[^;];%49[^\n]",
                               &p.patient_id, p.lastname, p.firstname, p.sex, p.birthday, p.email, p.road, p.appart,
                               p.town, p.province, p.country, p.postal_code, p.ramq_date_file_no, p.language,
                               p.creation_date, p.norecall, p.job);

        // Validation des colonnes obligatoires
        if (champsLus < 16 || strlen(p.lastname) == 0 || strlen(p.firstname) == 0) {
            lignesIgnorees++;
            // Ignore les lignes où les champs essentiels sont manquants
        }

        // Validation spécifique pour le champ Recall (norecall)
        if (strcmp(p.norecall, "FAUX") != 0) {
            strcpy(p.norecall, "FAUX"); // Corrige automatiquement la valeur
        }

        // Ajouter le statut "actif" par défaut
        p.actif = 1;

        // Ajouter le patient au tableau
        patients[nombrePatients++] = p;

        if (nombrePatients >= MAX_PATIENTS) {
            printf("\nCapacite maximale atteint. Importation terminee.\n");
            break;
        }
    }

    clock_t fin = clock(); // Fin du chronometrage
    double tempsExecution = (double)(fin - debut) / CLOCKS_PER_SEC;

    fclose(fichier);

    // Afficher le resume apres l'importation
    printf("\n===== IMPORTATION TERMINEE =====\n");
    printf("| %-15s | %-15s | %-15s   |\n", "Patients importes", "Lignes ignorees", "Temps (sec)");
    printf("| %-15d   | %-15d | %-15.2f   |\n", nombrePatients, lignesIgnorees, tempsExecution);

    // Afficher un echantillon de 3 patients
    if (nombrePatients > 0) {
        printf("\n===== APERCU DES DONNEES =====\n");
        printf("| %-10s | %-15s | %-15s | %-9s | %-12s | %-20s |\n", "ID", "Nom", "Prenom", "Sexe", "Date Naiss.", "RAMQ Date/File");
        printf("| %-10s | %-15s | %-15s | %-9s | %-12s | %-20s |\n", "==========", "===============", "===============", "=========", "============", "====================");
        for (int i = 0; i < 3 && i < nombrePatients; i++) {
            Patient p = patients[i];
            printf("| %-10d | %-15s | %-15s | %-9s | %-12s | %-20s |\n",
                   p.patient_id, p.lastname, p.firstname, p.sex, p.birthday, p.ramq_date_file_no);
        }
    } else {
        printf("\nAucun patient valide n'a ete importe.\n");
    }
    printf("\n================================\n");
}


// Fonction pour désactiver un patient
void desactiverPatient() {
    if (nombrePatients == 0) {
        printf("\nAucun patient en memoire. Importez des donnees d'abord.\n");
        return;
    }

    int rechercheID;
    printf("\nEntrez le nodossier du patient a desactiver : ");
    scanf("%d", &rechercheID);

    // Parcourir le tableau pour trouver le patient
    for (int i = 0; i < nombrePatients; i++) {
        if (patients[i].patient_id == rechercheID) {
            if (patients[i].actif == 0) {
                printf("\nCe patient est deja desactive.\n");
                return;
            }

            // Désactiver le patient
            patients[i].actif = 0;
            printf("\nPatient avec nodossier %d desactive avec succes.\n", rechercheID);
            return;
        }
    }

    printf("\nPatient avec nodossier %d introuvable.\n", rechercheID);
}

// Fonction pour charger un fichier CSV existant et remplacer les données en mémoire
void chargerFichierExistant() {
    FILE *fichier = fopen(FICHIER_DESTINATION, "r");
    if (!fichier) {
        printf("\nErreur : impossible d'ouvrir le fichier %s. Assurez-vous qu'il existe.\n", FICHIER_DESTINATION);
        return;
    }

    char ligne[1024];
    int lignesIgnorees = 0; // Compteur pour les lignes invalides
    nombrePatients = 0; // Réinitialiser la mémoire

    printf("\n===== CHARGEMENT DU FICHIER EXISTANT =====\n");

    while (fgets(ligne, sizeof(ligne), fichier)) {
        Patient p;

        // Lire toutes les colonnes du fichier CSV
        int champsLus = sscanf(ligne, "%d;%49[^;];%49[^;];%9[^;];%14[^;];%49[^;];%49[^;];%9[^;];%49[^;];%19[^;];%19[^;];%14[^;];%19[^;];%14[^;];%9[^;];%49[^\n]",
                               &p.patient_id, p.lastname, p.firstname, p.sex, p.birthday, p.email, p.road, p.appart,
                               p.town, p.province, p.country, p.postal_code, p.ramq_date_file_no, p.language,
                               p.creation_date, p.norecall, p.job);

        // Validation des champs obligatoires
        if (champsLus < 16 || strlen(p.lastname) == 0 || strlen(p.firstname) == 0) {
            lignesIgnorees++;
            continue;
        }

        // Ajouter le statut "actif" par défaut lors du chargement
        p.actif = 1;

        // Ajouter le patient au tableau
        patients[nombrePatients++] = p;

        if (nombrePatients >= MAX_PATIENTS) {
            printf("\nCapacite maximale atteinte. Chargement partiel effectue.\n");
            break;
        }
    }

    fclose(fichier);

    // Résumé après le chargement
    printf("\n===== CHARGEMENT TERMINE =====\n");
    printf("| %-15s | %-15s |\n", "Patients charges", "Lignes ignorees");
    printf("| %-15d  | %-15d |\n", nombrePatients, lignesIgnorees);
    printf("=========================================\n");
}

// Fonction pour demander les colonnes à afficher (4 colonnes max, sans emojis)
void choisirColonnes(int colonnes[4]) {
    printf("\n===========================================\n");
    printf("           Affichage des patients !         \n");
    printf("===========================================\n");
    printf("Voici les colonnes disponibles :\n\n");

    printf("  1. ID\n");
    printf("  2. Nom\n");
    printf("  3. Prenom\n");
    printf("  4. Sexe\n");
    printf("  5. Date creation\n");
    printf("  6. ...\n");
    printf("  7. Naissance\n");
    printf("  8. Langue\n");
    printf("  9. Email\n");
    printf(" 10. Adresse\n");
    printf(" 11. ...\n");
    printf(" 12. Ville\n");
    printf(" 13. RAMQ\n");
    printf(" 14. Province\n");
    printf(" 15. Code postal\n");
    printf(" 16. Recall\n");
    printf(" 17. ProfessionX\n\n");

    printf("Choisissez **4 colonnes** a afficher (par exemple : 1 2 3 4).\n");
    printf("Entrez vos choix :\n");

    for (int i = 0; i < 4; i++) {
        printf("  Choix %d : ", i + 1);
        scanf("%d", &colonnes[i]);

        // Confirmation dynamique du choix
        printf("    Colonne %d enregistree.\n", colonnes[i]);
    }

    printf("\nSuper ! Les colonnes selectionnees sont : ");
    for (int i = 0; i < 4; i++) {
        if (i > 0) printf(", ");
        switch (colonnes[i]) {
            case 1: printf("ID"); break;
            case 2: printf("Nom"); break;
            case 3: printf("Prenom"); break;
            case 4: printf("Sexe"); break;
            case 5: printf("Date creation"); break;
            case 6: printf("..."); break;
            case 7: printf("Naissance"); break;
            case 8: printf("Langue"); break;
            case 9: printf("Email"); break;
            case 10: printf("Adresse"); break;
            case 11: printf("..."); break;
            case 12: printf("Villel"); break;
            case 13: printf("RAMQ"); break;
            case 14: printf("Province"); break;
            case 15: printf("Code postal"); break;
            case 16: printf("Recall"); break;
            case 17: printf("ProfessionX"); break;
            default: printf("N/A"); break;
        }
    }
    printf(".\n");
    printf("===========================================\n\n");
}

// Fonction pour rechercher un patient par son nodossier (ID)
void rechercherPatient() {
    if (nombrePatients == 0) {
        printf("\nAucun patient en memoire. Importez des donnees d'abord.\n");
        return;
    }

    int rechercheID;
    printf("\nEntrez le nodossier du patient a rechercher : ");
    scanf("%d", &rechercheID);

    // Parcourir le tableau pour trouver le patient
    for (int i = 0; i < nombrePatients; i++) {
        if (patients[i].patient_id == rechercheID) {
            printf("\nPatient trouve ! Voici ses informations :\n");
            printf("ID         : %d\n", patients[i].patient_id);
            printf("Nom        : %s\n", patients[i].lastname);
            printf("Prenom     : %s\n", patients[i].firstname);
            printf("Sexe       : %s\n", patients[i].sex);
            printf("Naissance  : %s\n", patients[i].birthday);
            printf("Email      : %s\n", patients[i].email);
            printf("Adresse    : %s\n", patients[i].road);
            printf("Ville      : %s\n", patients[i].town);
            printf("RAMQ       : %s\n", patients[i].ramq_date_file_no);
            printf("Langue     : %s\n", patients[i].language);
            printf("Job        : %s\n", patients[i].job);
            return;
        }
    }

    printf("\nPatient avec nodossier %d introuvable.\n", rechercheID);
}

// Fonction pour afficher les patients en fonction de 4 colonnes choisies
void afficherPatients() {
    if (nombrePatients == 0) {
        printf("\nAucun patient en memoire. Importez des donnees d'abord.\n");
        return;
    }

    int colonnes[4];
    choisirColonnes(colonnes); // Demander les colonnes à afficher

    // Afficher l'en-tête
    printf("\n===== LISTE DES PATIENTS ACTIFS =====\n");
    for (int i = 0; i < 4; i++) {
        switch (colonnes[i]) {
            case 1: printf("| %-10s ", "ID"); break;
            case 2: printf("| %-20s ", "Nom"); break;
            case 3: printf("| %-15s ", "Prenom"); break;
            case 4: printf("| %-7s ", "Sexe"); break;
            case 5: printf("| %-15s ", "Date creation"); break;
            case 6: printf("| %-25s ", "..."); break;
            case 7: printf("| %-10s ", "Naissance"); break;
            case 8: printf("| %-10s ", "Langue"); break;
            case 9: printf("| %-40s ", "Email"); break;
            case 10: printf("| %-40s ", "Adresse"); break;
            case 11: printf("| %-15s ", "..."); break;
            case 12: printf("| %-15s ", "Ville"); break;
            case 13: printf("| %-20s ", "RAMQ"); break;
            case 14: printf("| %-15s ", "Province"); break;
            case 15: printf("| %-15s ", "Code postal"); break;
            case 16: printf("| %-10s ", "Recall"); break;
            case 17: printf("| %-20s ", "ProfessionX"); break;
            default: printf("| %-10s ", "N/A"); break;
        }
    }
    printf("|\n");

    // Afficher une ligne de séparation
    for (int i = 0; i < 4; i++) {
        switch (colonnes[i]) {
            case 1: printf("| %-10s ", "=========="); break;
            case 2: printf("| %-20s ", "===================="); break;
            case 3: printf("| %-15s ", "==============="); break;
            case 4: printf("| %-7s ", "======="); break;
            case 5: printf("| %-15s ", "==============="); break;
            case 6: printf("| %-25s ", "========================="); break;
            case 7: printf("| %-10s ", "=========="); break;
            case 8: printf("| %-10s ", "=========="); break;
            case 9: printf("| %-40s ", "======================================="); break;
            case 10: printf("| %-40s ", "======================================="); break;
            case 11: printf("| %-15s ", "==============="); break;
            case 12: printf("| %-15s ", "==============="); break;
            case 13: printf("| %-20s ", "===================="); break;
            case 14: printf("| %-15s ", "==============="); break;
            case 15: printf("| %-15s ", "==============="); break;
            case 16: printf("| %-10s ", "=========="); break;
            case 17: printf("| %-20s ", "===================="); break;
            default: printf("| %-10s ", "=========="); break;
        }
    }
    printf("|\n");

    // Afficher les données des patients actifs
    for (int i = 0; i < nombrePatients; i++) {
        Patient p = patients[i];

        // Filtrer uniquement les patients actifs
        if (p.actif == 1) {
            for (int j = 0; j < 4; j++) {
                switch (colonnes[j]) {
                    case 1: printf("| %-10d ", p.patient_id); break;
                    case 2: printf("| %-20s ", p.lastname); break;
                    case 3: printf("| %-15s ", p.firstname); break;
                    case 4: printf("| %-7s ", p.sex); break;
                    case 5: printf("| %-15s ", p.birthday); break;
                    case 6: printf("| %-25s ", p.email); break;
                    case 7: printf("| %-10s ", p.road); break;
                    case 8: printf("| %-10s ", p.appart); break;
                    case 9: printf("| %-40s ", p.town); break;
                    case 10: printf("| %-40s ", p.province); break;
                    case 11: printf("| %-15s ", p.country); break;
                    case 12: printf("| %-15s ", p.postal_code); break;
                    case 13: printf("| %-20s ", p.ramq_date_file_no); break;
                    case 14: printf("| %-15s ", p.language); break;
                    case 15: printf("| %-15s ", p.creation_date); break;
                    case 16: printf("| %-10s ", p.norecall); break;
                    case 17: printf("| %-20s ", p.job); break;
                    default: printf("| %-10s ", "N/A"); break;
                }
            }
            printf("|\n");
        }
    }

    // Ligne de clôture
    printf("============================================================\n");
}

void corrigerRecall() {
    for (int i = 0; i < nombrePatients; i++) {
        if (strcmp(patients[i].norecall, "FAUX") != 0) {
            printf("Correction du champ Recall pour le patient ID %d : %s\n", patients[i].patient_id, patients[i].norecall);
            strcpy(patients[i].norecall, "FAUX");
        }
    }
    printf("\nToutes les corrections ont ete effectuees.\n");
}

// Fonction pour ajouter un patient manuellement
void ajouterPatient() {
    // Vérifier s'il y a un espace libre pour réutiliser un patient désactivé
    for (int i = 0; i < nombrePatients; i++) {
        if (patients[i].actif == 0) { // Trouver un patient inactif à réutiliser
            printf("\nUn patient desactive a ete trouve. Il sera remplace.\n");

            // Saisie des nouvelles données pour le patient
            Patient *nouveauPatient = &patients[i];
            printf("Entrez l'ID du patient (nodossier) : ");
            scanf("%d", &nouveauPatient->patient_id);

            printf("Entrez le nom : ");
            scanf(" %49[^\n]", nouveauPatient->lastname);

            printf("Entrez le prenom : ");
            scanf(" %49[^\n]", nouveauPatient->firstname);

            printf("Entrez le sexe (Homme/Femme) : ");
            scanf(" %9[^\n]", nouveauPatient->sex);

            printf("Entrez la date de naissance (AAAA-MM-JJ) : ");
            scanf(" %14[^\n]", nouveauPatient->birthday);

            printf("Entrez l'email : ");
            scanf(" %49[^\n]", nouveauPatient->email);

            printf("Entrez l'adresse : ");
            scanf(" %49[^\n]", nouveauPatient->road);

            printf("Entrez la ville : ");
            scanf(" %49[^\n]", nouveauPatient->town);

            printf("Entrez le RAMQ : ");
            scanf(" %19[^\n]", nouveauPatient->ramq_date_file_no);

            printf("Entrez la langue : ");
            scanf(" %19[^\n]", nouveauPatient->language);

            printf("Entrez la profession : ");
            scanf(" %49[^\n]", nouveauPatient->job);

            // Réactiver le patient
            nouveauPatient->actif = 1;

            printf("\n Patient ajoute avec succes !\n");
            return; // Sortir une fois le patient ajouté
        }
    }

    // Vérifier si la mémoire est pleine
    if (nombrePatients >= MAX_PATIENTS) {
        printf("\nLa memoire est pleine. Impossible d'ajouter un nouveau patient.\n");
        return;
    }

    // Ajouter un nouveau patient si la mémoire n'est pas pleine
    Patient *nouveauPatient = &patients[nombrePatients++];
    printf("Entrez l'ID du patient (nodossier) : ");
    scanf("%d", &nouveauPatient->patient_id);

    printf("Entrez le nom : ");
    scanf(" %49[^\n]", nouveauPatient->lastname);

    printf("Entrez le prenom : ");
    scanf(" %49[^\n]", nouveauPatient->firstname);

    printf("Entrez le sexe (Homme/Femme) : ");
    scanf(" %9[^\n]", nouveauPatient->sex);

    printf("Entrez la date de naissance (AAAA-MM-JJ) : ");
    scanf(" %14[^\n]", nouveauPatient->birthday);

    printf("Entrez l'email : ");
    scanf(" %49[^\n]", nouveauPatient->email);

    printf("Entrez l'adresse : ");
    scanf(" %49[^\n]", nouveauPatient->road);

    printf("Entrez la ville : ");
    scanf(" %49[^\n]", nouveauPatient->town);

    printf("Entrez le RAMQ : ");
    scanf(" %19[^\n]", nouveauPatient->ramq_date_file_no);

    printf("Entrez la langue : ");
    scanf(" %19[^\n]", nouveauPatient->language);

    printf("Entrez la profession : ");
    scanf(" %49[^\n]", nouveauPatient->job);

    // Ajouter le statut actif
    nouveauPatient->actif = 1;

    printf("\n Patient ajoute avec succes !\n");
}

// Fonction pour exporter les donnees dans un fichier CSV
void exporterDonnees() {
    if (nombrePatients == 0) {
        printf("Aucun patient a exporter. Importez des donnees d'abord.\n");
        return;
    }

    FILE *fichier = fopen(FICHIER_DESTINATION, "w");
    if (!fichier) {
        printf("Erreur : impossible de creer le fichier destination.\n");
        return;
    }

    fprintf(fichier, "patient_id;lastname;firstname;sex;birthday;email;road;appart;town;province;country;postal_code;ramq_date_file_no;language;creation_date;norecall;job\n");

    for (int i = 0; i < nombrePatients; i++) {
        Patient p = patients[i];
        fprintf(fichier, "%d;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                p.patient_id, p.lastname, p.firstname, p.sex, p.birthday, p.email, p.road, p.appart, p.town,
                p.province, p.country, p.postal_code, p.ramq_date_file_no, p.language, p.creation_date, p.norecall, p.job);
    }

    fclose(fichier);
    printf("Exportation terminee avec succes.\n");
}

int confirmerQuitter() {
    char confirmation;
    printf("\nVoulez-vous vraiment quitter le programme ? (O/N) : ");
    scanf(" %c", &confirmation); // Lire un seul caractère

    if (confirmation == 'O' || confirmation == 'o') {
        printf("\nFermeture du programme...\n");
        return 1; // Confirme la fermeture
    }

    printf("\nRetour au menu principal.\n");
    return 0; // Annule la fermeture
}

// Fonction principale
int main() {
    int choix;

    do {
        afficherMenu(); // Affiche le menu modernisé
        scanf("%d", &choix);

        switch (choix) {
            case 1:
                importerDonnees(); // Importation des donnees
                break;
            case 2:
                afficherPatients(); // Afficher les patients
                break;
            case 3:
                exporterDonnees(); // Exporter les donnees
                break;
            case 4:
                rechercherPatient(); // Rechercher un patient
                break;
            case 5:
                ajouterPatient(); // Ajouter un patient
                break;
            case 6:
                desactiverPatient(); // Désactiver un patient
                break;
            case 7:
                chargerFichierExistant(); // Charger un fichier existant
                break;
            case 8:
                // Confirmation avant de quitter
                if (confirmerQuitter()) {
                    return 0; // Quitter le programme
                }
                break;
            default:
                printf("Choix invalide. Veuillez reessayer.\n");
                break;
        }
    } while (choix != 8);

    return 0;
}
