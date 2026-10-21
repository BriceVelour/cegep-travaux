package cegepst.com;

public class Participant {
    // Codes ANSI pour les couleurs (pour les messages colorés)
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    // Propriétés principales
    private String prenom;
    private String nom;
    private int vitalite;
    private int attaque;
    private int defense;
    private int precision;
    private int chance;
    private Sac sac; // Chaque participant a un sac d'équipement
    private int eliminations;

    // Constructeur
    public Participant(String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;

        // Activation du cheat "Bruce Wayne"
        if (prenom.equalsIgnoreCase("Bruce") && nom.equalsIgnoreCase("Wayne")) {
            System.out.println(PURPLE + "Cheat activé: Bienvenue, Bruce Wayne ! Vous êtes maintenant invincible!" + RESET);

            this.vitalite = 150; // Vitalité augmentée
            this.attaque = 50; // Attaque puissante
            this.defense = 40; // Défense renforcée
            this.precision = 90; // Précision presque parfaite
            this.chance = 70; // Très chanceux

            // Meilleur équipement
            this.sac = new Sac();
            this.sac.echangeArme(new Arme("Batarang légendaire", 50, 10)); // Arme puissante
            this.sac.echangeArmure(new Armure("Veste antiballe ultime", 40)); // Armure robuste
            this.sac.ajouterRation(10); // 10 rations pour bien débuter
        } else {
            // Caractéristiques normales pour les autres joueurs
            this.vitalite = 100;
            this.attaque = (int) (Math.random() * 5) + 1;
            this.defense = (int) (Math.random() * 13);
            this.precision = (int) (Math.random() * 61) + 20;
            this.chance = (int) (Math.random() * 40) + 1;
            this.sac = new Sac();
        }

        this.eliminations = 0;
    }

    // Getters
    public String getPrenom() { return prenom; }
    public String getNom() { return nom; }
    public int getVitalite() { return vitalite; }
    public int getAttaque() { return attaque + sac.getArme().getBonusAttaque(); } // Inclut le bonus de l'arme
    public int getDefense() { return defense + sac.getArmure().getBonusDefense(); } // Inclut le bonus de l'armure
    public int getPrecision() { return precision + sac.getArme().getBonusPrecision(); }
    public int getChance() { return chance; }
    public int getEliminations() { return eliminations; }
    public Sac getSac() { return sac; }

    // Méthode pour afficher les caractéristiques
    public void afficherCaracteristiques() {
        System.out.println(CYAN + "Prénom : " + prenom + RESET);
        System.out.println(CYAN + "Nom : " + nom + RESET);
        System.out.println(CYAN + "Vitalité : " + vitalite + RESET);
        System.out.println(CYAN + "Attaque : " + getAttaque() + RESET);
        System.out.println(CYAN + "Défense : " + getDefense() + RESET);
        System.out.println(CYAN + "Précision : " + getPrecision() + "%" + RESET);
        System.out.println(CYAN + "Chance : " + chance + RESET);
        sac.afficherContenuSac(); // Affiche le contenu du sac
    }

    // Méthode pour afficher la barre de vie
    public void afficherBarreDeVie() {
        int longueurBarre = 20; // Longueur totale de la barre
        int vitaliteRestante = (int) ((double) vitalite / 100 * longueurBarre); // Portion remplie

        // Couleur selon l'état de la vitalité
        String couleur;
        if (vitalite > 70) {
            couleur = GREEN;
        } else if (vitalite > 30) {
            couleur = YELLOW;
        } else {
            couleur = RED;
        }

        // Construire la barre de vie
        StringBuilder barre = new StringBuilder();
        barre.append(couleur); // Appliquer la couleur
        for (int i = 0; i < vitaliteRestante; i++) {
            barre.append("█");
        }
        for (int i = vitaliteRestante; i < longueurBarre; i++) {
            barre.append("-");
        }
        barre.append(RESET); // Réinitialiser la couleur

        // Afficher la barre
        System.out.println(prenom + " (" + vitalite + " HP) : " + barre.toString());
    }

    // Méthode pour perdre de la vitalité
    public void perdreVitalite(int dommage) {
        int reductionDommage = Math.max(0, dommage - getDefense()); // Inclut le bonus d'armure
        vitalite = Math.max(0, vitalite - reductionDommage);        // Vitalité ne peut pas descendre en dessous de 0
        System.out.println(RED + prenom + " a perdu " + reductionDommage + " points de vitalité. Vitalité actuelle : " + vitalite + RESET);
        afficherBarreDeVie(); // Affiche la barre de vie après les dégâts
    }

    // Méthode pour utiliser une ration
    public void mangerRation() {
        if (sac.getNombreDeRations() > 0) {
            int restauration = sac.utiliserRation();
            vitalite = Math.min(100, vitalite + restauration); // Restaurer la vitalité sans dépasser 100
            System.out.println(GREEN + prenom + " mange une ration et regagne " + restauration + " points de vitalité !" + RESET);
            afficherBarreDeVie(); // Met à jour la barre de vie après la restauration
        } else {
            System.out.println(YELLOW + prenom + " n'a plus de rations !" + RESET);
        }
    }

    // Méthode pour utiliser un objet spécifique
    public void utiliserObjet(String objet) {
        switch (objet) {
            case "Potion de santé" -> {
                vitalite = Math.min(100, vitalite + 20);
                System.out.println(GREEN + prenom + " utilise une Potion de santé et regagne 20 points de vitalité !" + RESET);
                afficherBarreDeVie(); // Mise à jour de la barre après utilisation
            }
            case "Bouclier en bois" -> {
                defense += 5;
                System.out.println(CYAN + prenom + " équipe un Bouclier en bois, augmentant sa défense de 5 !" + RESET);
            }
            case "Épée rouillée" -> {
                attaque += 2;
                System.out.println(CYAN + prenom + " brandit une Épée rouillée, augmentant son attaque de 2 !" + RESET);
            }
            default -> System.out.println(RED + prenom + " tente d'utiliser un objet inconnu, sans succès..." + RESET);
        }
    }

    // Méthode pour échanger une arme
    public void echangerArme(Arme nouvelleArme) {
        System.out.println(YELLOW + prenom + " échange son arme [" + sac.getArme().getNom() + "] contre [" + nouvelleArme.getNom() + "]." + RESET);
        sac.echangeArme(nouvelleArme);
    }

    // Méthode pour ajouter une élimination
    public void ajouterElimination() {
        eliminations++;
        System.out.println(GREEN + prenom + " a maintenant " + eliminations + " élimination(s) !" + RESET);
    }

    // Vérifie si le participant est transformé en lapin
    public boolean estTransformeEnLapin() {
        return vitalite <= 0;
    }
}
