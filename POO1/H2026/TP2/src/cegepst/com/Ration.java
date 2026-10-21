package cegepst.com;

public class Ration extends Item {

    public Ration(String name, int healing) {
        super(name, healing);
    }

    public int getHealing() {
        return getValue();
    }

    public String getDisplay() {
        return getName() + " (+" + getHealing() + " hp)";
    }
}
