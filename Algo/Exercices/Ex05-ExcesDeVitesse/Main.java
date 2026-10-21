package com.cegepst;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Type de route (municipale ou autoroute) : ");
        String typederoute = scanner.nextLine();

        System.out.print("Limite de vitesse permise (km/h) : ");
        int limitedevitesse = scanner.nextInt();

        System.out.print("Vitesse du contrevenant (km/h) : ");
        int vitesseducontrevenant = scanner.nextInt();

        int excesdevitesse = limitedevitesse - vitesseducontrevenant;

        int amendeinitiaux = 25;

        if (excesdevitesse > 0) {
            if (typederoute.equalsIgnoreCase("municipale")) {
                amendeinitiaux += excesdevitesse * 15;
            } else if (typederoute.equalsIgnoreCase("autoroute") || excesdevitesse > 20) {
                amendeinitiaux += excesdevitesse * 20; // le "+=" c'est comme a=a+b
            }
        }

        if (amendeinitiaux < 100) {
            if (limitedevitesse < 50 && excesdevitesse < 5) {
                amendeinitiaux = 25;
            } else {
                amendeinitiaux = 100;
            }
        }

        System.out.println("L'amende à payer est de : " + amendeinitiaux + " $");

        scanner.close();

    }
}
