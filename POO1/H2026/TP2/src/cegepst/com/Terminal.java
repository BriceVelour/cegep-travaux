package cegepst.com;

import java.util.Scanner;

public class Terminal {

    public static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Error");
        }
    }

    public static Integer readInt() {
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        try {
            int menuIndex = Integer.valueOf(choice);
            return menuIndex;
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
            return null;
        }
    }

    public static String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static enum Color {
        BLACK((char)27 + "[30m"),
        RED((char)27 + "[31m"),
        GREEN((char)27 + "[32m"),
        YELLOW((char)27 + "[33m"),
        BLUE((char)27 + "[34m"),
        PURPLE((char)27 + "[35m"),
        CYAN((char)27 + "[36m"),
        WHITE((char)27 + "[37m"),
        PINK((char)27 + "[38;2;255;182;193m");

        private String charCode;

        Color(String charCode) {
            this.charCode = charCode;
        }

        public String colorize(String message) {
            return charCode + message + (char)27 + "[0m";
        }
    }
}
