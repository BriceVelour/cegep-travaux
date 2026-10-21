package cegepst.com;

public class Arme {
    private String nom;
    private int bonusAttaque;
    private int bonusPrecision;

    // Constructeur
    public Arme(String nom, int bonusAttaque, int bonusPrecision) {
        this.nom = nom;
        this.bonusAttaque = bonusAttaque;
        this.bonusPrecision = bonusPrecision;
    }

    // Getters
    public String getNom() { return nom; }
    public int getBonusAttaque() { return bonusAttaque; }
    public int getBonusPrecision() { return bonusPrecision; }
}
