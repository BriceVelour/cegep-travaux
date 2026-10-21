package cegepst.com;

public class Bag {

    private Weapon weapon;
    private Armor armor;
    private Ration[] rations;
    private int rationCount;

    public Bag() {
        rations = new Ration[20];
        rationCount = 0;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public int getRationCount() {
        return rationCount;
    }

    public Ration getRation(int index) {
        if (index >= 0 && index < rationCount) {
            return rations[index];
        }
        return null;
    }

    public void addRation(Ration ration) {
        if (rationCount < rations.length) {
            rations[rationCount] = ration;
            rationCount++;
        }
    }

    public Ration removeRation(int index) {
        if (index >= 0 && index < rationCount) {
            Ration removed = rations[index];
            for (int i = index; i < rationCount - 1; i++) {
                rations[i] = rations[i + 1];
            }
            rationCount--;
            return removed;
        }
        return null;
    }

    public void display() {
        System.out.println("=== INVENTORY ===");
        if (weapon != null) {
            System.out.println("Weapon: " + weapon.getDisplay());
        } else {
            System.out.println("Weapon: None");
        }
        if (armor != null) {
            System.out.println("Armor: " + armor.getDisplay());
        } else {
            System.out.println("Armor: None");
        }
        System.out.println("Rations: " + rationCount);
        for (int i = 0; i < rationCount; i++) {
            System.out.println("  " + (i + 1) + ". " + rations[i].getDisplay());
        }
        System.out.println("=================");
    }
}
