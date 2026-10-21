package com.example.batpasta_débutant;
import java.util.ArrayList;
import java.util.List;
public class EmployeRepository {
    private static final List<Employe> employes = new ArrayList<>();
    static {
        employes.add(new Employe("1234567", "test123"));
        employes.add(new Employe("7654321", "admin"));
    }
    public static Employe authentifier(String numero, String motDePasse) {
        for (Employe e : employes) {
            if (e.getNumeroEmploye().equals(numero) && e.getMotDePasse().equals(motDePasse)) {
                return e;
            }
        }
        return null;
    }
}
