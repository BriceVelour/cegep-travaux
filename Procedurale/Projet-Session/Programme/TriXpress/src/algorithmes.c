#include <stdlib.h>
#include "algorithmes.h"

// Tri à bulles
void bubbleSort(TriStatistiques *stats) {
    for (int i = 0; i < stats->taille - 1; i++) {
        for (int j = 0; j < stats->taille - i - 1; j++) {
            stats->nombreComparaisons++;
            if (stats->donnees[j] > stats->donnees[j + 1]) {
                int temp = stats->donnees[j];
                stats->donnees[j] = stats->donnees[j + 1];
                stats->donnees[j + 1] = temp;
            }
        }
    }
}

void selectionSort(TriStatistiques *stats) {
    for (int i = 0; i < stats->taille - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < stats->taille; j++) {
            stats->nombreComparaisons++;
            if (stats->donnees[j] < stats->donnees[minIndex]) {
                minIndex = j;
            }
        }
        if (minIndex != i) {
            int temp = stats->donnees[i];
            stats->donnees[i] = stats->donnees[minIndex];
            stats->donnees[minIndex] = temp;
        }
    }
}


// Partition utilisée par QuickSort
int partition(TriStatistiques *stats, int low, int high) {
    int pivot = stats->donnees[high];
    int i = (low - 1);

    for (int j = low; j < high; j++) {
        stats->nombreComparaisons++;
        if (stats->donnees[j] < pivot) {
            i++;
            int temp = stats->donnees[i];
            stats->donnees[i] = stats->donnees[j];
            stats->donnees[j] = temp;
        }
    }
    int temp = stats->donnees[i + 1];
    stats->donnees[i + 1] = stats->donnees[high];
    stats->donnees[high] = temp;

    return (i + 1);
}

// QuickSort
void quickSortRec(TriStatistiques *stats, int low, int high) {
    if (low < high) {
        int pi = partition(stats, low, high);
        quickSortRec(stats, low, pi - 1);
        quickSortRec(stats, pi + 1, high);
    }
}

void quickSort(TriStatistiques *stats) {
    quickSortRec(stats, 0, stats->taille - 1);
}

// Merge utilisé par MergeSort
void merge(TriStatistiques *stats, int l, int m, int r) {
    int n1 = m - l + 1;
    int n2 = r - m;

    int *L = malloc(n1 * sizeof(int));
    int *R = malloc(n2 * sizeof(int));

    for (int i = 0; i < n1; i++) L[i] = stats->donnees[l + i];
    for (int j = 0; j < n2; j++) R[j] = stats->donnees[m + 1 + j];

    int i = 0, j = 0, k = l;
    while (i < n1 && j < n2) {
        stats->nombreComparaisons++;
        if (L[i] <= R[j]) {
            stats->donnees[k++] = L[i++];
        } else {
            stats->donnees[k++] = R[j++];
        }
    }

    while (i < n1) stats->donnees[k++] = L[i++];
    while (j < n2) stats->donnees[k++] = R[j++];

    free(L);
    free(R);
}

// MergeSort
void mergeSortRec(TriStatistiques *stats, int l, int r) {
    if (l < r) {
        int m = l + (r - l) / 2;
        mergeSortRec(stats, l, m);
        mergeSortRec(stats, m + 1, r);
        merge(stats, l, m, r);
    }
}

void mergeSort(TriStatistiques *stats) {
    mergeSortRec(stats, 0, stats->taille - 1);
}

// Heap utilisé par HeapSort
void heapify(TriStatistiques *stats, int n, int i) {
    int largest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;

    if (left < n) {
        stats->nombreComparaisons++;
        if (stats->donnees[left] > stats->donnees[largest]) largest = left;
    }
    if (right < n) {
        stats->nombreComparaisons++;
        if (stats->donnees[right] > stats->donnees[largest]) largest = right;
    }

    if (largest != i) {
        int temp = stats->donnees[i];
        stats->donnees[i] = stats->donnees[largest];
        stats->donnees[largest] = temp;
        heapify(stats, n, largest);
    }
}

// HeapSort
void heapSort(TriStatistiques *stats) {
    for (int i = stats->taille / 2 - 1; i >= 0; i--) {
        heapify(stats, stats->taille, i);
    }
    for (int i = stats->taille - 1; i > 0; i--) {
        int temp = stats->donnees[0];
        stats->donnees[0] = stats->donnees[i];
        stats->donnees[i] = temp;
        heapify(stats, i, 0);
    }
}
