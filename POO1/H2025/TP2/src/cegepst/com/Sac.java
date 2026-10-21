package cegepst.com;

public class Sac {
    private Arme arme;
    private Armure armure;
    private int nombreDeRations;

    // Initialisation du sac avec des objets aléatoires
    public Sac() {
        this.arme = GenerateurObjet.genererArmeAleatoire(); // Génère une arme aléatoire
        this.armure = GenerateurObjet.genererArmureAleatoire(); // Génère une armure aléatoire
        this.nombreDeRations = (int) (Math.random() * 5) + 1; // Entre 1 et 5 rations
    }

    // Obtenir l'arme du sac
    public Arme getArme() { return arme; }

    // Obtenir l'armure du sac
    public Armure getArmure() { return armure; }

    // Obtenir le nombre de rations
    public int getNombreDeRations() { return nombreDeRations; }

    // Afficher le contenu du sac
    public void afficherContenuSac() {
        System.out.println("- Arme : " + arme.getNom() + " (+" + arme.getBonusAttaque() + " Attaque, +" +
                arme.getBonusPrecision() + " Précision)");
        System.out.println("- Armure : " + armure.getNom() + " (+" + armure.getBonusDefense() + " Défense)");
        System.out.println("- Nombre de rations : " + nombreDeRations);
    }

    // Utiliser une ration pour restaurer de la vitalité
    public int utiliserRation() {
        if (nombreDeRations > 0) {
            nombreDeRations--;
            return 15; // Chaque ration restaure 15 points de vitalité
        }
        System.out.println("Aucune ration restante !");
        return 0;
    }

    // Ajouter des rations au sac
    public void ajouterRation(int quantité) {
        nombreDeRations += quantité;
    }

    // Échanger l'arme actuelle avec une nouvelle arme
    public void echangeArme(Arme nouvelleArme) {
        System.out.println("Échange de l'arme [" + arme.getNom() + "] contre [" + nouvelleArme.getNom() + "].");
        this.arme = nouvelleArme;
    }

    // Échanger l'armure actuelle avec une nouvelle armure
    public void echangeArmure(Armure nouvelleArmure) {
        System.out.println("Échange de l'armure [" + armure.getNom() + "] contre [" + nouvelleArmure.getNom() + "].");
        this.armure = nouvelleArmure;
    }
}
