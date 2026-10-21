package com.cegepst;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Numéro1
        System.out.println("Nombres de 0 à 10");
        int nombre; scanner.nextLine();

        for (int i = 0; i <= 10; i++) {
            System.out.println(i);
        }
        //Numéro2
        System.out.print("Entrez un chiffre: ");
        int chiffre = scanner.nextInt();

        for (int i = 0; i <= chiffre; i++) {
            System.out.println(i);
            }
    }
}
