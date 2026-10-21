package cegepst.com;

public class Armor extends Item {

    public Armor(String name, int defense) {
        super(name, defense);
    }

    public int getDefense() {
        return getValue();
    }

    public String getDisplay() {
        return getName() + " (+" + getDefense() + " def)";
    }
}
