package com.cegest;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //Numero1

        System.out.print("Saisir un chiffre :");
        int chiffre = scanner.nextInt();

        int x = 0;

        while (x <= chiffre){
            System.out.print(x);
            x++;
        }

        //Numero2

        System.out.print("Entrez un chiffre: ");
        int chiffre1 = scanner.nextInt();

        long factoriel = 1;

        int y = 1;

        while (y <= chiffre) {
            factoriel *= y;
            y++;
        }

        System.out.println("Le factoriel de " + chiffre + " est: " + factoriel);
    }



}


