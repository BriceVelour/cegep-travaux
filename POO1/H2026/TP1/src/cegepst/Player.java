package cegepst;

import java.util.Scanner;

public class Player {
    private char symbol;
    private boolean highSpeedUsed;
    private boolean heatVisionUsed;
    private boolean batarangUsed;
    private boolean tacticianUsed;
    public Player(char symbol) {
        this.symbol = symbol;
        this.highSpeedUsed = false;
        this.heatVisionUsed = false;
        this.batarangUsed = false;
        this.tacticianUsed = false;
    }
    public char getSymbol() {
        return symbol;
    }
    public void playTurn(Scanner scanner, Grid grid) {
        System.out.println("Player " + symbol + "'s turn");
        System.out.println("What do you want to do?");
        System.out.println("1 - Place your symbol");
        if (symbol == 'S') {
            if (!highSpeedUsed) System.out.println("2 - High Speed (play twice in a row)");
            if (!heatVisionUsed) System.out.println("3 - Heat Vision (destroy a B)");
        } else {
            if (!batarangUsed) System.out.println("2 - Batarang (move last S)");
            if (!tacticianUsed) System.out.println("3 - Tactician (swap with a S)");
        }
        System.out.print("Choice (1");
        if (symbol == 'S' && !highSpeedUsed) System.out.print(", 2");
        if (symbol == 'S' && !heatVisionUsed) System.out.print(", 3");
        if (symbol == 'B' && (!batarangUsed || !tacticianUsed)) System.out.print(", 2 or 3");
        System.out.print(") : ");
        int choice = scanner.nextInt();
        if (choice == 1) {
            placeSymbol(scanner, grid);
        } else if (choice == 2 && symbol == 'S' && !highSpeedUsed) {
            highSpeedUsed = true;
            System.out.println("\u001B[32mHigh Speed activated! You play again this turn.\u001B[0m\n");
            playTurn(scanner, grid);
        } else if (choice == 3 && symbol == 'S' && !heatVisionUsed) {
            useHeatVision(scanner, grid);
        } else if (choice == 2 && symbol == 'B' && !batarangUsed) {
            useBatarang(scanner, grid);
        } else if (choice == 3 && symbol == 'B' && !tacticianUsed) {
            useTactician(scanner, grid);
        } else {
            System.out.println("\u001B[31mInvalid choice. Try again.\u001B[0m\n");
            playTurn(scanner, grid);
        }
    }
    private void placeSymbol(Scanner scanner, Grid grid) {
        System.out.print("Row (0-3): ");
        int row = scanner.nextInt();
        System.out.print("Column (0-3): ");
        int col = scanner.nextInt();

        if (grid.place(row, col, symbol)) {
            System.out.println("\u001B[32mSymbol placed!\u001B[0m\n");
        } else {
            System.out.println("\u001B[31mInvalid or already taken position! Try again.\u001B[0m\n");
            placeSymbol(scanner, grid);
        }
    }
    private void useHeatVision(Scanner scanner, Grid grid) {
        System.out.println("HEAT VISION ACTIVATED! Choose position of B to destroy.");
        System.out.print("Row (0-3): ");
        int row = scanner.nextInt();
        System.out.print("Column (0-3): ");
        int col = scanner.nextInt();
        if (row >= 0 && row < 4 && col >= 0 && col < 4) {
            if (grid.getCell(row, col) == 'B') {
                grid.setCell(row, col, ' ');
                heatVisionUsed = true;
                System.out.println("\u001B[1m\u001B[31mB destroyed successfully!\u001B[0m\n");
            } else {
                System.out.println("\u001B[33mNo B at this position. Try again.\u001B[0m\n");
                useHeatVision(scanner, grid);
            }
        } else {
            System.out.println("\u001B[33mInvalid position! Try again.\u001B[0m\n");
            useHeatVision(scanner, grid);
        }
    }
    private void useBatarang(Scanner scanner, Grid grid) {
        int lastRow = grid.getLastSRow();
        int lastCol = grid.getLastSCol();
        if (lastRow == -1) {
            System.out.println("No S has been placed yet. Cannot use Batarang.\n");
            playTurn(scanner, grid);
            return;
        }
        System.out.println("BATARANG LAUNCHED! Choose new position for the last S.");
        System.out.print("Row (0-3): ");
        int newRow = scanner.nextInt();
        System.out.print("Column (0-3): ");
        int newCol = scanner.nextInt();
        if (newRow >= 0 && newRow < 4 && newCol >= 0 && newCol < 4) {
            if (grid.getCell(newRow, newCol) == ' ') {
                grid.setCell(lastRow, lastCol, ' ');
                grid.place(newRow, newCol, 'S');
                batarangUsed = true;
                System.out.println("\u001B[1m\u001B[33mLast S moved successfully!\u001B[0m\n");
            } else {
                System.out.println("\u001B[31mPosition already taken! Try again.\u001B[0m\n");
                useBatarang(scanner, grid);
            }
        } else {
            System.out.println("\u001B[31mInvalid position! Try again.\u001B[0m\n");
            useBatarang(scanner, grid);
        }
    }
    private void useTactician(Scanner scanner, Grid grid) {
        System.out.println("TACTICIAN ACTIVATED! Choose a S to swap with your last placement.");
        System.out.print("Row of S (0-3): ");
        int sRow = scanner.nextInt();
        System.out.print("Column of S (0-3): ");
        int sCol = scanner.nextInt();
        if (sRow >= 0 && sRow < 4 && sCol >= 0 && sCol < 4) {
            if (grid.getCell(sRow, sCol) == 'S') {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (grid.getCell(i, j) == 'B') {
                            grid.setCell(i, j, 'S');
                            grid.setCell(sRow, sCol, 'B');
                            tacticianUsed = true;
                            System.out.println("\u001B[1m\u001B[33mTactical swap successful!\u001B[0m\n");
                            return;
                        }
                    }
                }
                System.out.println("\u001B[33mNo B found to swap with.\u001B[0m\n");
            } else {
                System.out.println("\u001B[31mNo S at this position! Try again.\u001B[0m\n");
                useTactician(scanner, grid);
            }
        } else {
            System.out.println("\u001B[31mInvalid position! Try again.\u001B[0m\n");
            useTactician(scanner, grid);
        }
    }
}