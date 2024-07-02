import java.util.Scanner;

public class Main {
    static final int BOARD_SIZE = 5;
    static final char EMPTY = '-';
    static final char SHIP = 'S';
    static final char HIT = 'X';
    static final char MISS = 'O';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[][] player1Board = new char[BOARD_SIZE][BOARD_SIZE];
        char[][] player2Board = new char[BOARD_SIZE][BOARD_SIZE];

        System.out.println("Player 1, place your ships:");
        initializeBoard(player1Board);
        displayBoard(player1Board);
        placeShips(player1Board, scanner);

        System.out.println("\nPlayer 2, place your ships:");
        initializeBoard(player2Board);
        displayBoard(player2Board);
        placeShips(player2Board, scanner);

        playGame(player1Board, player2Board, scanner);

        scanner.close();
    }

    static void initializeBoard(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    static void displayBoard(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void placeShips(char[][] board, Scanner scanner) {
        for (int shipSize = 1; shipSize <= 3; shipSize++) {
            System.out.println("Place a ship of size " + shipSize);
            for (int i = 0; i < shipSize; i++) {
                System.out.print("Enter row and column (0-4): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE || board[row][col] != EMPTY) {
                    System.out.println("Invalid position. Try again.");
                    i--;
                } else {
                    board[row][col] = SHIP;
                }
            }
            displayBoard(board);
        }
    }

    static void playGame(char[][] player1Board, char[][] player2Board, Scanner scanner) {
        boolean player1Turn = true;

        while (!gameOver(player1Board) && !gameOver(player2Board)) {
            char[][] currentBoard = player1Turn ? player2Board : player1Board;
            char[][] opponentBoard = player1Turn ? player1Board : player2Board;
            String playerName = player1Turn ? "Player 1" : "Player 2";

            System.out.println("\n" + playerName + ", it's your turn:");
            System.out.println("Your board:");
            displayBoard(currentBoard);
            System.out.println("Opponent's board:");
            displayBoard(opponentBoard);

            System.out.print("Enter row and column to guess: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
                System.out.println("Invalid position. Try again.");
                continue;
            }

            if (opponentBoard[row][col] == EMPTY) {
                System.out.println("Miss!");
                opponentBoard[row][col] = MISS;
            } else if (opponentBoard[row][col] == SHIP) {
                System.out.println("Hit!");
                opponentBoard[row][col] = HIT;
            } else {
                System.out.println("Already guessed this position. Try again.");
                continue;
            }

            player1Turn = !player1Turn;
        }

        if (gameOver(player1Board)) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("Player 1 wins!");
        }
    }

    static boolean gameOver(char[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == SHIP) {
                    return false;
                }
            }
        }
        return true;
    }
}