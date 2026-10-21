package cegepst.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BattleRoyale {

    private Participant player;
    private Participant[] enemies;
    private int enemiesAlive;
    private int currentDay;

    private String[] firstNames;
    private String[] lastNames;

    private String[] weaponNames = {"Stick", "Scissors", "Knife", "Baseball Bat", "Sword", "Axe", "Excalibur"};
    private int[] weaponDamage = {1, 2, 3, 4, 5, 6, 10};

    private String[] armorNames = {"T-Shirt", "Leather Jacket", "Chainmail", "Iron Armor", "Bulletproof Vest", "Magic Shield", "Batman Armor"};
    private int[] armorDefense = {1, 2, 4, 6, 8, 10, 12};

    private String[] rationNames = {"Apple", "Bread", "Cheese", "Meat", "Feast", "Potion"};
    private int[] rationHealing = {5, 10, 15, 20, 30, 25};

    public BattleRoyale() {
        enemies = new Participant[41];
        enemiesAlive = 0;
        currentDay = 1;
        loadFirstNames();
        loadLastNames();
    }

    private void loadFirstNames() {
        firstNames = new String[]{
            "John", "Mary", "Peter", "Sophie", "Luke", "Emma", "Thomas", "Julie",
            "Nick", "Camille", "Tony", "Lea", "Max", "Chloe", "Alex", "Sarah",
            "Will", "Laura", "Vince", "Manon", "Seb", "Clara", "Frank", "Alice",
            "Dave", "Julia", "Kevin", "Oceane", "Matt", "Charlotte", "Julian",
            "Pauline", "Oliver", "Marine", "Phil", "Anais", "Chris", "Clem",
            "Larry", "Margot", "Fabien", "Mel"
        };

        try {
            File file = new File("firstnames.txt");
            if (file.exists()) {
                Scanner reader = new Scanner(file);
                int count = 0;
                while (reader.hasNextLine()) {
                    reader.nextLine();
                    count++;
                }
                reader.close();

                firstNames = new String[count];
                reader = new Scanner(file);
                for (int i = 0; i < count; i++) {
                    firstNames[i] = reader.nextLine();
                }
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("firstnames.txt not found, using default names.");
        }
    }

    private void loadLastNames() {
        lastNames = new String[]{
            "Martin", "Bernard", "Dubois", "Thomas", "Robert", "Richard", "Petit",
            "Durand", "Leroy", "Moreau", "Simon", "Laurent", "Lefebvre", "Michel",
            "Garcia", "David", "Bertrand", "Roux", "Vincent", "Fournier", "Morel",
            "Girard", "Andre", "Lefevre", "Mercier", "Dupont", "Lambert", "Bonnet",
            "Francois", "Martinez", "Legrand", "Garnier", "Faure", "Rousseau", "Blanc",
            "Guerin", "Muller", "Henry", "Roussel", "Nicolas", "Perrin", "Morin"
        };

        try {
            File file = new File("lastnames.txt");
            if (file.exists()) {
                Scanner reader = new Scanner(file);
                int count = 0;
                while (reader.hasNextLine()) {
                    reader.nextLine();
                    count++;
                }
                reader.close();

                lastNames = new String[count];
                reader = new Scanner(file);
                for (int i = 0; i < count; i++) {
                    lastNames[i] = reader.nextLine();
                }
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("lastnames.txt not found, using default names.");
        }
    }

    public void start() {
        displayTitle();
        createPlayer();
        createEnemies();

        System.out.println();
        System.out.println("42 participants enter the arena...");
        System.out.println("May the best one win!");
        System.out.println();

        Terminal.pause(2000);

        while (currentDay <= 3 && player.isAlive() && enemiesAlive > 0) {
            playDay();
            if (player.isAlive() && enemiesAlive > 0) {
                currentDay++;
            }
        }

        endGame();
    }

    private void displayTitle() {
        System.out.println("============================================");
        System.out.println(Terminal.Color.RED.colorize("          BATTLE ROYALE"));
        System.out.println("============================================");
        System.out.println();
    }

    private void createPlayer() {
        System.out.println("Create your character");
        System.out.println("---------------------");

        System.out.print("Enter your first name: ");
        String firstName = Terminal.readString();

        System.out.print("Enter your last name: ");
        String lastName = Terminal.readString();

        if (firstName.equalsIgnoreCase("Bruce") && lastName.equalsIgnoreCase("Wayne")) {
            player = new Participant(firstName, lastName, 12, 5, 80, 40);
            player.setIsPlayer(true);

            player.getBag().setWeapon(new Weapon("Excalibur", 10));
            player.getBag().setArmor(new Armor("Batman Armor", 12));

            for (int i = 0; i < 5; i++) {
                player.getBag().addRation(new Ration("Feast", 30));
            }

            System.out.println();
            System.out.println(Terminal.Color.YELLOW.colorize("*** BATMAN MODE ACTIVATED ***"));
            System.out.println("You have maximum stats and equipment!");
        } else {
            int defense = randomBetween(0, 12);
            int attack = randomBetween(1, 5);
            int precision = randomBetween(20, 80);
            int luck = randomBetween(1, 40);

            player = new Participant(firstName, lastName, defense, attack, precision, luck);
            player.setIsPlayer(true);

            equipParticipant(player);
        }

        System.out.println();
        System.out.println("Your character:");
        player.displayStats();
        System.out.println();
        player.getBag().display();
    }

    private void createEnemies() {
        System.out.println();
        System.out.println("Generating enemies...");

        for (int i = 0; i < 41; i++) {
            String firstName = firstNames[randomBetween(0, firstNames.length - 1)];
            String lastName = lastNames[randomBetween(0, lastNames.length - 1)];

            int defense = randomBetween(0, 12);
            int attack = randomBetween(1, 5);
            int precision = randomBetween(20, 80);
            int luck = randomBetween(1, 40);

            enemies[i] = new Participant(firstName, lastName, defense, attack, precision, luck);
            equipParticipant(enemies[i]);
        }

        enemiesAlive = 41;
        System.out.println("41 enemies generated!");
    }

    private void equipParticipant(Participant participant) {
        int weaponIndex = randomWeighted(weaponNames.length);
        Weapon weapon = new Weapon(weaponNames[weaponIndex], weaponDamage[weaponIndex]);
        participant.getBag().setWeapon(weapon);

        int armorIndex = randomWeighted(armorNames.length);
        Armor armor = new Armor(armorNames[armorIndex], armorDefense[armorIndex]);
        participant.getBag().setArmor(armor);

        int nbRations = randomBetween(1, 3);
        for (int i = 0; i < nbRations; i++) {
            int rationIndex = randomBetween(0, rationNames.length - 1);
            Ration ration = new Ration(rationNames[rationIndex], rationHealing[rationIndex]);
            participant.getBag().addRation(ration);
        }

        participant.getBag().addRation(new Ration("Frog", 5));
    }

    private int randomBetween(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    private int randomWeighted(int max) {
        int roll = randomBetween(0, 100);

        if (roll < 30) {
            return randomBetween(0, max / 3);
        } else if (roll < 70) {
            return randomBetween(max / 3, 2 * max / 3);
        } else if (roll < 95) {
            return randomBetween(2 * max / 3, max - 2);
        } else {
            return max - 1;
        }
    }

    private void playDay() {
        System.out.println();
        System.out.println("============================================");
        System.out.println(Terminal.Color.YELLOW.colorize("                 DAY " + currentDay));
        System.out.println("============================================");

        int eliminationPercent;
        if (currentDay == 1) {
            eliminationPercent = 45;
        } else if (currentDay == 2) {
            eliminationPercent = 30;
        } else {
            eliminationPercent = 20;
        }

        randomEliminations(eliminationPercent);

        if (enemiesAlive == 0) {
            return;
        }

        int nbCombats;
        if (currentDay == 3) {
            nbCombats = enemiesAlive;
        } else {
            nbCombats = randomBetween(1, 4);
            if (nbCombats > enemiesAlive) {
                nbCombats = enemiesAlive;
            }
        }

        System.out.println();
        System.out.println("You will face " + nbCombats + " opponent(s) today.");

        for (int i = 0; i < nbCombats; i++) {
            if (!player.isAlive() || enemiesAlive == 0) {
                break;
            }

            System.out.println();
            System.out.println("--- Combat " + (i + 1) + "/" + nbCombats + " ---");

            Participant enemy = pickAliveEnemy();

            Combat combat = new Combat(player, enemy, currentDay);
            Participant winner = combat.start();

            if (winner == player) {
                countAliveEnemies();
            }

            Terminal.pause(1500);
        }

        if (player.isAlive() && enemiesAlive > 0) {
            displaySurvivors();
            System.out.println();
            System.out.println(Terminal.Color.PURPLE.colorize("\"May the odds be in your favor\""));
            System.out.println();
            System.out.print("Press Enter to continue...");
            Terminal.readString();
        }
    }

    private void randomEliminations(int percent) {
        int toEliminate = (enemiesAlive * percent) / 100;

        System.out.println();
        System.out.println("Fights are happening elsewhere in the arena...");
        System.out.println(Terminal.Color.RED.colorize(toEliminate + " participants have been eliminated!"));

        int eliminated = 0;
        while (eliminated < toEliminate) {
            int index = randomBetween(0, 40);

            if (enemies[index] != null && enemies[index].isAlive()) {
                enemies[index].setHealth(0);
                eliminated++;
            }
        }

        countAliveEnemies();
    }

    private void countAliveEnemies() {
        enemiesAlive = 0;
        for (int i = 0; i < 41; i++) {
            if (enemies[i] != null && enemies[i].isAlive()) {
                enemiesAlive++;
            }
        }
    }

    private Participant pickAliveEnemy() {
        int index;
        do {
            index = randomBetween(0, 40);
        } while (enemies[index] == null || !enemies[index].isAlive());

        return enemies[index];
    }

    private void displaySurvivors() {
        System.out.println();
        System.out.println("--- End of Day " + currentDay + " ---");
        System.out.println("You: " + player.getFullName() + " (HP: " + player.getHealth() + ")");
        System.out.println("Enemies remaining: " + Terminal.Color.RED.colorize(String.valueOf(enemiesAlive)));
    }

    private void endGame() {
        System.out.println();
        System.out.println("============================================");
        System.out.println(Terminal.Color.YELLOW.colorize("          GAME OVER"));
        System.out.println("============================================");

        if (!player.isAlive()) {
            System.out.println();
            System.out.println(Terminal.Color.RED.colorize("You have been eliminated..."));
            System.out.println("Better luck next time.");
        } else if (enemiesAlive == 0) {
            System.out.println();
            System.out.println(Terminal.Color.GREEN.colorize("CONGRATULATIONS!"));
            System.out.println(player.getFullName() + " is the winner of the Battle Royale!");
            System.out.println();
            System.out.println("Final stats:");
            player.displayStats();
        }

        System.out.println();
        System.out.println("Thanks for playing!");
    }
}
