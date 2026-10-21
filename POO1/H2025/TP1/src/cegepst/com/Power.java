package cegepst.com;

public abstract class Power {
    protected Player player;

    public Power(Player player) {
        this.player = player;
    }

    public abstract void activate(Grid grid);
}
