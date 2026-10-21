package com.example.batpasta_débutant;
public class Commande {
    private static int compteur = 1;
    private int id;
    private String typePate;
    private String sauce;
    private String format;
    private boolean breuvage;
    private boolean dessert;
    private String employe;
    private double total;
    public Commande(String typePate, String sauce, String format, boolean breuvage, boolean dessert, String employe) {
        this.id = compteur++;
        this.typePate = typePate;
        this.sauce = sauce;
        this.format = format;
        this.breuvage = breuvage;
        this.dessert = dessert;
        this.employe = employe;
        this.total = calculerTotal();
    }
    private double calculerTotal() {
        double prixPate = Prix.getPrixPate(typePate);
        double prixSauce = Prix.getPrixSauce(sauce);
        double prixFormat = Prix.getPrixFormat(format);
        double total = prixPate + prixSauce + prixFormat;
        if (breuvage) total += Prix.BREUVAGE;
        if (dessert) total += Prix.DESSERT;
        return total * 1.15;
    }
    public int getId() {
        return id;
    }
    public String getTypePate() {
        return typePate;
    }
    public String getSauce() {
        return sauce;
    }
    public String getFormat() {
        return format;
    }
    public boolean hasBreuvage() {
        return breuvage;
    }
    public boolean hasDessert() {
        return dessert;
    }
    public String getEmploye() {
        return employe;
    }
    public double getTotal() {
        return total;
    }
}
