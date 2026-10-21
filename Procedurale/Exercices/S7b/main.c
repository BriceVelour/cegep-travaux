#include <stdio.h>
#include <stdlib.h>

int main() {
    int *tab_nombre = NULL; // initialiser le tableau à NULL
    int taille = 0; // taille initiale du tableau
    int choix, pos, num;

    do {
        printf("\nMenu:\n");
        printf("1. Ajouter un nombre\n");
        printf("2. Supprimer un nombre\n");
        printf("3. Afficher le tableau\n");
        printf("4. Quitter\n");
        printf("Votre choix : ");
        scanf("%d", &choix);

        switch (choix) {
            case 1:
                printf("Entrez le nombre à ajouter : ");
                scanf("%d", &num);
                // augmenter la taille du tableau
                taille++;
                // allouer plus de mémoire
                tab_nombre = (int *)realloc(tab_nombre, taille * sizeof(int));
                if (tab_nombre == NULL) {
                    printf("Erreur d'allocation de mémoire.\n");
                    return 1;
                }
                // ajouter le nombre au tableau
                tab_nombre[taille - 1] = num;
                break;

            case 2:
                printf("Entrez la position du nombre à supprimer : ");
                scanf("%d", &pos);
                if (pos < 0 || pos >= taille) {
                    printf("Position invalide.\n");
                } else {
                    // décaler les nombres vers la gauche
                    for (int i = pos; i < taille - 1; i++) {
                        tab_nombre[i] = tab_nombre[i + 1];
                    }
                    // diminuer la taille du tableau
                    taille--;
                    // réallouer la mémoire
                    tab_nombre = (int *)realloc(tab_nombre, taille * sizeof(int));
                    if (taille > 0 && tab_nombre == NULL) {
                        printf("Erreur d'allocation de mémoire.\n");
                        return 1;
                    }
                }
                break;

            case 3:
                printf("Contenu du tableau :\n");
                for (int i = 0; i < taille; i++) {
                    printf("%d ", tab_nombre[i]);
                }
                printf("\n");
                break;

            case 4:
                printf("Au revoir !\n");
                break;

            default:
                printf("Choix invalide.\n");
        }
    } while (choix != 4);

    free(tab_nombre); // libérer la mémoire allouée pour le tableau

    return 0;
}
