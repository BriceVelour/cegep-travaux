package cegepst;

public class Grid {
    private char[][] cells;
    private int lastSRow = -1;
    private int lastSCol = -1;
    public Grid() {
        cells = new char[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cells[i][j] = ' ';
            }
        }
    }
    public void display() {
        System.out.println("    0   1   2   3");
        for (int i = 0; i < 4; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < 4; j++) {
                char cell = cells[i][j];
                if (cell == 'B') {
                    System.out.print("\u001B[31m " + cell + " \u001B[0m");
                } else if (cell == 'S') {
                    System.out.print("\u001B[34m " + cell + " \u001B[0m");
                } else {
                    System.out.print("   ");
                }
                if (j < 3) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < 3) {
                System.out.println("   ---+---+---+---");
            }
        }
        System.out.println();
    }
    public boolean place(int row, int col, char symbol) {
        if (row >= 0 && row < 4 && col >= 0 && col < 4 && cells[row][col] == ' ') {
            cells[row][col] = symbol;
            if (symbol == 'S') {
                lastSRow = row;
                lastSCol = col;
            }
            return true;
        }
        return false;
    }
    public char getCell(int row, int col) {
        if (row >= 0 && row < 4 && col >= 0 && col < 4) {
            return cells[row][col];
        }
        return ' ';
    }
    public void setCell(int row, int col, char value) {
        if (row >= 0 && row < 4 && col >= 0 && col < 4) {
            cells[row][col] = value;
        }
    }
    public int getLastSRow() {
        return lastSRow;
    }
    public int getLastSCol() {
        return lastSCol;
    }
    public boolean checkVictory(char symbol) {
        for (int i = 0; i < 4; i++) {
            if (cells[i][0] == symbol && cells[i][1] == symbol &&
                    cells[i][2] == symbol && cells[i][3] == symbol) {
                return true;
            }
        }
        for (int j = 0; j < 4; j++) {
            if (cells[0][j] == symbol && cells[1][j] == symbol &&
                    cells[2][j] == symbol && cells[3][j] == symbol) {
                return true;
            }
        }
        if (cells[0][0] == symbol && cells[1][1] == symbol &&
                cells[2][2] == symbol && cells[3][3] == symbol) {
            return true;
        }
        if (cells[0][3] == symbol && cells[1][2] == symbol &&
                cells[2][1] == symbol && cells[3][0] == symbol) {
            return true;
        }
        return false;
    }
    public boolean isFull() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cells[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}