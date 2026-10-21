package cegepst.com;

public class Combat {

    private Participant player;
    private Participant enemy;
    private int currentDay;
    private boolean playerTurn;
    private boolean combatOver;
    private boolean playerEscaped;
    private int playerEscapeCounter;
    private int enemyEscapeCounter;

    public Combat(Participant player, Participant enemy, int currentDay) {
        this.player = player;
        this.enemy = enemy;
        this.currentDay = currentDay;
        this.combatOver = false;
        this.playerEscaped = false;
        this.playerEscapeCounter = 0;
        this.enemyEscapeCounter = 0;
        this.playerTurn = whoStarts();
    }

    private boolean whoStarts() {
        int playerRoll = (int)(Math.random() * 100) + player.getLuck();
        int enemyRoll = (int)(Math.random() * 100) + enemy.getLuck();
        return playerRoll >= enemyRoll;
    }

    public Participant start() {
        System.out.println();
        System.out.println("========================================");
        System.out.println(Terminal.Color.RED.colorize("COMBAT START!"));
        System.out.println(player.getFullName() + " VS " + enemy.getFullName());
        System.out.println("========================================");
        System.out.println();

        if (playerTurn) {
            System.out.println(Terminal.Color.GREEN.colorize(player.getFullName()) + " starts first!");
        } else {
            System.out.println(Terminal.Color.RED.colorize(enemy.getFullName()) + " starts first!");
        }

        Terminal.pause(1500);

        while (!combatOver) {
            displayHud();

            if (playerTurn) {
                playerTurn();
            } else {
                enemyTurn();
            }

            if (player.isDead()) {
                combatOver = true;
                System.out.println();
                System.out.println(Terminal.Color.RED.colorize("You have been defeated!"));
                System.out.println(enemy.getFullName() + " says: \"Put that foolish ambition to rest\"");
                return enemy;
            }

            if (enemy.isDead()) {
                combatOver = true;
                System.out.println();
                System.out.println(Terminal.Color.GREEN.colorize("Victory!"));
                System.out.println(enemy.getFullName() + " has been turned into a frog!");
                offerExchange();
                offerFrog();
                return player;
            }

            if (playerEscaped) {
                combatOver = true;
                System.out.println();
                System.out.println(Terminal.Color.YELLOW.colorize("You escaped the combat!"));
                return player;
            }

            playerTurn = !playerTurn;
            Terminal.pause(1000);
        }

        return player;
    }

    private void displayHud() {
        System.out.println();
        System.out.println("----------------------------------------");
        player.hud();
        enemy.hud();
        System.out.println("----------------------------------------");
    }

    private void playerTurn() {
        if (playerEscapeCounter > 0) {
            playerEscapeCounter--;
            System.out.println(Terminal.Color.YELLOW.colorize("You are escaping... " + playerEscapeCounter + " turn(s) left"));
            if (playerEscapeCounter == 0) {
                playerEscaped = true;
            }
            return;
        }

        Integer choice = null;
        do {
            displayMenu();
            choice = Terminal.readInt();
            if (choice == null || choice < 1 || choice > 5) {
                System.out.println("Invalid choice!");
                choice = null;
            }
            if (choice != null && choice == 3 && currentDay == 1) {
                System.out.println("You cannot escape on the first day!");
                choice = null;
            }
            if (choice != null && choice == 2 && player.getBag().getRationCount() == 0) {
                System.out.println("You have no rations!");
                choice = null;
            }
        } while (choice == null);

        if (choice == 1) {
            playerAttack();
        } else if (choice == 2) {
            playerEat();
        } else if (choice == 3) {
            playerEscape();
        } else if (choice == 4) {
            playerDoNothing();
        } else if (choice == 5) {
            player.getBag().display();
            playerTurn();
        }
    }

    private void displayMenu() {
        System.out.println();
        System.out.println("What do you want to do?");
        System.out.println("1 - Attack");
        System.out.println("2 - Eat (" + player.getBag().getRationCount() + " rations)");
        if (currentDay == 1) {
            System.out.println("3 - Escape " + Terminal.Color.RED.colorize("(Not available day 1)"));
        } else {
            System.out.println("3 - Escape");
        }
        System.out.println("4 - Do nothing");
        System.out.println("5 - Inventory");
        System.out.print("Your choice: ");
    }

    private void playerAttack() {
        System.out.println();
        System.out.println(player.getFullName() + " attacks!");

        int roll = (int)(Math.random() * 100) + 1;
        int precisionWithLuck = player.getPrecision() + (player.getLuck() / 4);

        if (roll <= precisionWithLuck) {
            int damage = player.getTotalAttack();
            int luckBonus = (int)(Math.random() * (player.getLuck() / 5));
            damage = damage + luckBonus;

            int actualDamage = damage - enemy.getTotalDefense();
            if (actualDamage < 1) {
                actualDamage = 1;
            }

            System.out.println(Terminal.Color.GREEN.colorize("Hit!") + " " + actualDamage + " damage!");
            enemy.takeDamage(damage);
        } else {
            System.out.println(Terminal.Color.RED.colorize("Missed!"));
        }
    }

    private void playerEat() {
        System.out.println();
        System.out.println("Choose a ration to eat:");
        for (int i = 0; i < player.getBag().getRationCount(); i++) {
            Ration r = player.getBag().getRation(i);
            System.out.println((i + 1) + " - " + r.getDisplay());
        }
        System.out.print("Your choice: ");

        Integer choice = Terminal.readInt();
        if (choice != null && choice >= 1 && choice <= player.getBag().getRationCount()) {
            Ration ration = player.getBag().removeRation(choice - 1);
            int oldHealth = player.getHealth();
            player.heal(ration.getHealing());
            System.out.println(Terminal.Color.YELLOW.colorize("Yum yum que c'est bon!"));
            System.out.println("Health: " + oldHealth + " -> " + player.getHealth());
        } else {
            System.out.println("Invalid choice!");
            playerTurn();
        }
    }

    private void playerEscape() {
        System.out.println();
        int escapeChance = 50 + player.getLuck();
        int roll = (int)(Math.random() * 100) + 1;

        if (roll <= escapeChance) {
            System.out.println(Terminal.Color.YELLOW.colorize("You start running away!"));
            System.out.println("Survive 2 turns to escape...");
            playerEscapeCounter = 2;
        } else {
            System.out.println(Terminal.Color.RED.colorize("Failed to start escaping!"));
        }
    }

    private void playerDoNothing() {
        System.out.println();
        System.out.println(player.getFullName() + " does nothing...");
    }

    private void enemyTurn() {
        if (enemyEscapeCounter > 0) {
            enemyEscapeCounter--;
            System.out.println(Terminal.Color.YELLOW.colorize(enemy.getFullName() + " is escaping..."));
            return;
        }

        int choice = decideEnemyAction();

        if (choice == 1) {
            enemyAttack();
        } else if (choice == 2) {
            enemyEat();
        } else if (choice == 3) {
            enemyEscape();
        } else {
            enemyDoNothing();
        }
    }

    private int decideEnemyAction() {
        double healthPercent = (double) enemy.getHealth() / enemy.getMaxHealth() * 100;
        boolean hasRations = enemy.getBag().getRationCount() > 0;
        boolean canEscape = currentDay > 1;

        if (healthPercent < 25 && hasRations) {
            return 2;
        }

        if (healthPercent < 20 && canEscape) {
            int roll = (int)(Math.random() * 100);
            if (roll < 60) {
                return 3;
            }
        }

        if (healthPercent < 40 && hasRations) {
            int roll = (int)(Math.random() * 100);
            if (roll < 50) {
                return 2;
            }
        }

        if (enemy.getTotalAttack() > player.getTotalDefense() + 5) {
            return 1;
        }

        int roll = (int)(Math.random() * 100);
        if (roll < 70) {
            return 1;
        } else if (roll < 85 && hasRations && healthPercent < 80) {
            return 2;
        } else {
            return 1;
        }
    }

    private void enemyAttack() {
        System.out.println();
        System.out.println(enemy.getFullName() + " attacks!");

        int roll = (int)(Math.random() * 100) + 1;
        int precisionWithLuck = enemy.getPrecision() + (enemy.getLuck() / 4);

        if (roll <= precisionWithLuck) {
            int damage = enemy.getTotalAttack();
            int luckBonus = (int)(Math.random() * (enemy.getLuck() / 5));
            damage = damage + luckBonus;

            int actualDamage = damage - player.getTotalDefense();
            if (actualDamage < 1) {
                actualDamage = 1;
            }

            System.out.println(Terminal.Color.RED.colorize("Hit!") + " " + actualDamage + " damage!");
            player.takeDamage(damage);
        } else {
            System.out.println(Terminal.Color.GREEN.colorize("Missed!"));
        }
    }

    private void enemyEat() {
        System.out.println();
        if (enemy.getBag().getRationCount() > 0) {
            Ration ration = enemy.getBag().removeRation(0);
            int oldHealth = enemy.getHealth();
            enemy.heal(ration.getHealing());
            System.out.println(enemy.getFullName() + " eats " + ration.getName());
            System.out.println("Health: " + oldHealth + " -> " + enemy.getHealth());
        }
    }

    private void enemyEscape() {
        System.out.println();
        System.out.println(enemy.getFullName() + " tries to escape!");
        int escapeChance = 50 + enemy.getLuck();
        int roll = (int)(Math.random() * 100) + 1;

        if (roll <= escapeChance) {
            System.out.println(Terminal.Color.YELLOW.colorize("Enemy starts running away!"));
            enemyEscapeCounter = 2;
        } else {
            System.out.println("Failed to escape!");
        }
    }

    private void enemyDoNothing() {
        System.out.println();
        System.out.println(enemy.getFullName() + " does nothing...");
    }

    private void offerExchange() {
        System.out.println();
        Weapon playerWeapon = player.getBag().getWeapon();
        Weapon enemyWeapon = enemy.getBag().getWeapon();

        if (enemyWeapon != null) {
            String playerWeaponName = "None (0 dmg)";
            if (playerWeapon != null) {
                playerWeaponName = playerWeapon.getDisplay();
            }
            System.out.println("Exchange your weapon " + Terminal.Color.CYAN.colorize(playerWeaponName) +
                    " for enemy weapon " + Terminal.Color.YELLOW.colorize(enemyWeapon.getDisplay()) + "? (Y/N)");
            String answer = Terminal.readString();
            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("O")) {
                player.getBag().setWeapon(enemyWeapon);
                System.out.println("Weapon exchanged!");
            }
        }

        Armor playerArmor = player.getBag().getArmor();
        Armor enemyArmor = enemy.getBag().getArmor();

        if (enemyArmor != null) {
            String playerArmorName = "None (0 def)";
            if (playerArmor != null) {
                playerArmorName = playerArmor.getDisplay();
            }
            System.out.println("Exchange your armor " + Terminal.Color.CYAN.colorize(playerArmorName) +
                    " for enemy armor " + Terminal.Color.YELLOW.colorize(enemyArmor.getDisplay()) + "? (Y/N)");
            String answer = Terminal.readString();
            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("O")) {
                player.getBag().setArmor(enemyArmor);
                System.out.println("Armor exchanged!");
            }
        }
    }

    private void offerFrog() {
        int chance = player.getPrecision() + player.getLuck();
        int roll = (int)(Math.random() * 100) + 1;

        if (roll <= chance) {
            System.out.println();
            System.out.println(Terminal.Color.GREEN.colorize("You caught the frog!"));
            System.out.println("Add it to your rations? (Y/N)");
            String answer = Terminal.readString();
            if (answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("O")) {
                Ration frog = new Ration("Frog", 25);
                player.getBag().addRation(frog);
                System.out.println("Frog added to inventory!");
            }
        }
    }
}
