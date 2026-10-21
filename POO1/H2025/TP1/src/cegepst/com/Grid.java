package cegepst.com;

public class Grid {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private char[][] board;

    public Grid() {
        board = new char[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void display() {
        System.out.println("  1 2 3 4");
        for (int i = 0; i < 4; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 'S') {
                    System.out.print(RED + board[i][j] + RESET + " ");
                } else if (board[i][j] == 'B') {
                    System.out.print(BLUE + board[i][j] + RESET + " ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean updateCell(int row, int col, char symbol) {
        if (board[row][col] == '-') {
            board[row][col] = symbol;
            return true;
        }
        return false;
    }

    public char getSymbol(int row, int col) {
        return board[row][col];
    }

    public boolean checkVictory(char symbol) {
        for (int i = 0; i < 4; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2], board[i][3], symbol) ||
                    checkRowCol(board[0][i], board[1][i], board[2][i], board[3][i], symbol)) {
                return true;
            }
        }
        return (checkRowCol(board[0][0], board[1][1], board[2][2], board[3][3], symbol) ||
                checkRowCol(board[0][3], board[1][2], board[2][1], board[3][0], symbol));
    }

    private boolean checkRowCol(char c1, char c2, char c3, char c4, char symbol) {
        return (c1 == symbol && c2 == symbol && c3 == symbol && c4 == symbol);
    }

    public boolean isFull() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public void removeSymbol(int row, int col) {
        board[row][col] = '-';
    }

    public void relocateSymbol(int oldRow, int oldCol, int newRow, int newCol, char symbol) {
        board[oldRow][oldCol] = '-';
        board[newRow][newCol] = symbol;
    }
}
