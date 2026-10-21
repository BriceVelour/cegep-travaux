#include <stdio.h>
#include <stdlib.h>

int main() {
    int n;

    // Demande à l'utilisateur de saisir un entier n
    printf("Saisir un entier n : ");
    scanf("%d", &n);

    // Alloue dynamiquement un tableau d'entiers de la taille de n
    int *tableau = (int *)malloc(n * sizeof(int));

    // Vérifie si l'allocation de mémoire a échoué
    if (tableau == NULL) {
        printf("Erreur d'allocation de mémoire.\n");
        return 1;
    }

    // Demande à l'utilisateur de saisir n entiers
    for (int i = 0; i < n; i++) {
        printf("Saisir l'entier %d : ", i + 1);
        scanf("%d", &tableau[i]);
    }

    // Affiche les entiers stockés dans le tableau
    printf("Les entiers saisis sont : ");
    for (int i = 0; i < n; i++) {
        printf("%d ", tableau[i]);
    }
    printf("\n");

    // Libère la mémoire allouée
    free(tableau);

    return 0;
}
