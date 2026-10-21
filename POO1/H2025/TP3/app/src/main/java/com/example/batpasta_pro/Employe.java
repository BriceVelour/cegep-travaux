package com.example.batpasta_pro;
public class Employe {
    private String numeroEmploye;
    private String motDePasse;
    public Employe(String numeroEmploye, String motDePasse) {
        this.numeroEmploye = numeroEmploye;
        this.motDePasse = motDePasse;
    }
    public String getNumeroEmploye() {
        return numeroEmploye;
    }
    public String getMotDePasse() {
        return motDePasse;
    }
}
