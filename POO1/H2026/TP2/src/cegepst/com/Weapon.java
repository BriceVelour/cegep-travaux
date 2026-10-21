package cegepst.com;

public class Weapon extends Item {

    public Weapon(String name, int damage) {
        super(name, damage);
    }

    public int getDamage() {
        return getValue();
    }

    public String getDisplay() {
        return getName() + " (+" + getDamage() + " dmg)";
    }
}
