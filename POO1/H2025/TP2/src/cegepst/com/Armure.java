package cegepst.com;

public class Armure {
    private String nom;
    private int bonusDefense;

    // Constructeur
    public Armure(String nom, int bonusDefense) {
        this.nom = nom;
        this.bonusDefense = bonusDefense;
    }

    // Getters
    public String getNom() { return nom; }
    public int getBonusDefense() { return bonusDefense; }
}
