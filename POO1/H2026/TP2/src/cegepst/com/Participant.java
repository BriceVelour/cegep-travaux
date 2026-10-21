package cegepst.com;

public class Participant {

    private String firstName;
    private String lastName;
    private int health;
    private int maxHealth;
    private int defense;
    private int attack;
    private int precision;
    private int luck;
    private Bag bag;
    private boolean isPlayer;
    private int escapeCounter;

    public Participant(String firstName, String lastName, int defense, int attack, int precision, int luck) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.health = 100;
        this.maxHealth = 100;
        this.defense = defense;
        this.attack = attack;
        this.precision = precision;
        this.luck = luck;
        this.bag = new Bag();
        this.isPlayer = false;
        this.escapeCounter = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
        if (this.health < 0) {
            this.health = 0;
        }
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDefense() {
        return defense;
    }

    public int getTotalDefense() {
        int total = defense;
        if (bag.getArmor() != null) {
            total = total + bag.getArmor().getDefense();
        }
        return total;
    }

    public int getAttack() {
        return attack;
    }

    public int getTotalAttack() {
        int total = attack;
        if (bag.getWeapon() != null) {
            total = total + bag.getWeapon().getDamage();
        }
        return total;
    }

    public int getPrecision() {
        return precision;
    }

    public int getLuck() {
        return luck;
    }

    public Bag getBag() {
        return bag;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setIsPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getEscapeCounter() {
        return escapeCounter;
    }

    public void setEscapeCounter(int counter) {
        this.escapeCounter = counter;
    }

    public void takeDamage(int damage) {
        int actualDamage = damage - getTotalDefense();
        if (actualDamage < 1) {
            actualDamage = 1;
        }
        health = health - actualDamage;
        if (health < 0) {
            health = 0;
        }
    }

    public void heal(int amount) {
        health = health + amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public void displayStats() {
        System.out.println("=== " + Terminal.Color.YELLOW.colorize(getFullName()) + " ===");
        System.out.print("Health: ");
        displayHealthBar();
        System.out.println("Defense: " + Terminal.Color.BLUE.colorize(String.valueOf(getTotalDefense())));
        System.out.println("Attack: " + Terminal.Color.RED.colorize(String.valueOf(getTotalAttack())));
        System.out.println("Precision: " + Terminal.Color.GREEN.colorize(String.valueOf(precision) + "%"));
        System.out.println("Luck: " + Terminal.Color.PURPLE.colorize(String.valueOf(luck) + "%"));
    }

    public void displayHealthBar() {
        System.out.print("[");
        int bars = health / 10;
        for (int i = 0; i < bars; i++) {
            System.out.print(Terminal.Color.RED.colorize("*"));
        }
        for (int i = bars; i < 10; i++) {
            System.out.print(Terminal.Color.BLACK.colorize("*"));
        }
        System.out.println("] " + health + "/" + maxHealth);
    }

    public void hud() {
        System.out.print(getFullName() + " [");
        int bars = health / 10;
        for (int i = 0; i < bars; i++) {
            System.out.print(Terminal.Color.RED.colorize("*"));
        }
        for (int i = bars; i < 10; i++) {
            System.out.print(Terminal.Color.BLACK.colorize("-"));
        }
        System.out.println("] " + health + " HP");
    }
}
