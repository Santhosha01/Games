import java.util.Scanner;

public class Main {
    static final char EMPTY = '-';
    static final char PLAYER_X = 'X';
    static final char PLAYER_O = 'O';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] board = new char[3][3];
        initializeBoard(board);

        char currentPlayer = PLAYER_X;
        boolean gameOver = false;

        while (!gameOver) {
            displayBoard(board);
            System.out.println("Player " + currentPlayer + "'s turn");
            System.out.print("Enter row and column (0-2): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (!isValidMove(board, row, col)) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            board[row][col] = currentPlayer;

            if (isWinner(board, currentPlayer)) {
                displayBoard(board);
                System.out.println("Player " + currentPlayer + " wins!");
                gameOver = true;
            } else if (isBoardFull(board)) {
                displayBoard(board);
                System.out.println("It's a tie!");
                gameOver = true;
            }

            currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
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

    static boolean isValidMove(char[][] board, int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == EMPTY;
    }

    static boolean isWinner(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true; // horizontal
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true; // vertical
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true; // diagonal \
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true; // diagonal /
        }
        return false;
    }

    static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}