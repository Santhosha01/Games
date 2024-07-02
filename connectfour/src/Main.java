import java.util.Scanner;

public class Main {
    static final char EMPTY = '.';
    static final char PLAYER1 = 'X';
    static final char PLAYER2 = 'O';
    static int rows;
    static int columns;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter game board dimensions (rows columns): ");
        rows = scanner.nextInt();
        columns = scanner.nextInt();

        char[][] board = new char[rows][columns];
        initializeBoard(board);

        boolean gameOver = false;
        char currentPlayer = PLAYER1;

        while (!gameOver) {
            displayBoard(board);
            System.out.println("Player " + currentPlayer + "'s turn");

            int col;
            while (true) {
                System.out.print("Enter column (0-" + (columns - 1) + "): ");
                col = scanner.nextInt();
                if (col < 0 || col >= columns) {
                    System.out.println("Invalid column. Try again.");
                } else if (board[0][col] != EMPTY) {
                    System.out.println("Column is full. Try again.");
                } else {
                    break;
                }
            }

            int row = dropDisc(board, col, currentPlayer);

            if (checkWin(board, row, col, currentPlayer)) {
                displayBoard(board);
                System.out.println("Player " + currentPlayer + " wins!");
                gameOver = true;
            } else if (isBoardFull(board)) {
                displayBoard(board);
                System.out.println("It's a tie!");
                gameOver = true;
            } else {
                currentPlayer = (currentPlayer == PLAYER1) ? PLAYER2 : PLAYER1;
            }
        }
        scanner.close();
    }

    static void initializeBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    static void displayBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static int dropDisc(char[][] board, int col, char player) {
        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i][col] == EMPTY) {
                board[i][col] = player;
                return i;
            }
        }
        return -1;
    }

    static boolean checkWin(char[][] board, int row, int col, char player) {
        return checkDirection(board, row, col, player, 1, 0)  // horizontal
                || checkDirection(board, row, col, player, 0, 1)  // vertical
                || checkDirection(board, row, col, player, 1, 1)  // diagonal \
                || checkDirection(board, row, col, player, 1, -1); // diagonal /
    }

    static boolean checkDirection(char[][] board, int row, int col, char player, int dRow, int dCol) {
        int count = 1;
        count += countDirection(board, row, col, player, dRow, dCol);
        count += countDirection(board, row, col, player, -dRow, -dCol);
        return count >= rows;
    }

    static int countDirection(char[][] board, int row, int col, char player, int dRow, int dCol) {
        int count = 0;
        for (int i = 1; i < rows; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length && board[newRow][newCol] == player) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    static boolean isBoardFull(char[][] board) {
        for (int j = 0; j < board[0].length; j++) {
            if (board[0][j] == EMPTY) {
                return false;
            }
        }
        return true;
    }
}