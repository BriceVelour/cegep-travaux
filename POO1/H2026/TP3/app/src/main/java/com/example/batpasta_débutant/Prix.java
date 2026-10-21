package com.example.batpasta_débutant;

import java.util.ArrayList;
import java.util.HashMap;

public class Prix {
    private static final HashMap<String, Double> pates = new HashMap<>();
    private static final HashMap<String, Double> sauces = new HashMap<>();
    private static final HashMap<String, Double> formats = new HashMap<>();

    public static final double BREUVAGE = 2.30;
    public static final double DESSERT = 2.30;

    static {
        pates.put("Spaghetti", 3.00);
        pates.put("Penne", 2.80);
        pates.put("Fettucine", 3.20);
        pates.put("Gnocchi", 3.00);
        pates.put("Rigatoni", 4.50);
        pates.put("Linguine", 3.00);
        pates.put("Macaroni", 2.50);
        pates.put("Fusilli", 4.00);

        sauces.put("Carbonara", 4.30);
        sauces.put("Pesto", 3.90);
        sauces.put("Pesto tomate", 4.00);
        sauces.put("Rosee", 3.90);
        sauces.put("Vodka", 6.90);
        sauces.put("Poulet cajun", 5.80);
        sauces.put("Bolonaise", 5.50);
        sauces.put("Trois from.", 4.30);

        formats.put("Petit", 2.00);
        formats.put("Moyen", 3.50);
        formats.put("Grand", 4.15);
        formats.put("Tres grand", 5.70);
    }
        public static ArrayList<String> getPates() {
            return new ArrayList<>(pates.keySet());
        }
        public static ArrayList<String> getSauces() {
            return new ArrayList<>(sauces.keySet());
        }
        public static ArrayList<String> getFormats() {
            return new ArrayList<>(formats.keySet());
        }
        public static double getPrixPate(String nom) {
            return pates.getOrDefault(nom, 0.0);
        }
        public static double getPrixSauce(String nom) {
            return sauces.getOrDefault(nom, 0.0);
        }
        public static double getPrixFormat(String nom) {
            return formats.getOrDefault(nom, 0.0);
        }
    }
