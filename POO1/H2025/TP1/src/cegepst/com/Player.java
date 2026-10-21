package cegepst.com;

import java.util.Scanner;

public class Player {
    private String name;
    private char symbol;
    private boolean hasUsedPower;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.hasUsedPower = false;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void usePower(Grid grid) {
        if (!hasUsedPower) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(name + ", choisissez un pouvoir :");
            if (symbol == 'S') {
                System.out.println("1. HIGH SPEED\n2. HEAT VISION");
            } else {
                System.out.println("1. BATARANG\n2. TACTICIAN");
            }
            int choice = scanner.nextInt();
            Power power = null;

            if (symbol == 'S') {
                if (choice == 1) {
                    power = new HighSpeed(this);
                } else {
                    power = new HeatVision(this);
                }
            } else {
                if (choice == 1) {
                    power = new Batarang(this);
                } else {
                    power = new Tactician(this);
                }
            }

            power.activate(grid);
            hasUsedPower = true;
        } else {
            System.out.println("Vous avez déjà utilisé votre pouvoir.");
        }
    }
}
