import java.util.*;

public class SnakeGame {
    public static final char SNAKE_BODY = 'S';
    public static final char FOOD = 'F';
    public static final char SNAKE_HEAD='H';
    static Scanner sc=new Scanner(System.in);
    private char[][] board;
    private ArrayList<int[]> snake;
    static int points;// ArrayList to store snake's body segments
    private int[] foodPosition; // Array to store food position

    public SnakeGame() {
        getboardSize();
        snake = new ArrayList<>();
        initializeSnake();
        placeFood();
    }

    private void getboardSize() {
        System.out.println("Enter the row of the board");
        int row = sc.nextInt();
        System.out.println("Enter the column of the board");
        int column = sc.nextInt();
        board = new char[row][column];
    }

    private void initializeSnake() {
        int[] initialPosition = {0, 0};
        snake.add(initialPosition);
        board[0][0] = SNAKE_HEAD;
    }

    private void placeFood() {
        System.out.println("Enter the food row in the matrix");
        int frow=sc.nextInt();
        System.out.println("Enter the food column in the matrix");
        int fcol=sc.nextInt();
        foodPosition = new int[]{frow, fcol};
        board[frow][fcol] = FOOD;
    }

    public void run() {
        int directionX;
        int directionY;

        while (points != ((board.length * board[0].length) - 1)) {
            printBoard();
            System.out.println("1.left 2.right 3.up 4.down");
            // Get user input
            int move = sc.nextInt();

            // Update direction
            switch (move) {
                case 1:
                    directionX = 0;
                    directionY = -1;
                    break;
                case 2:
                    directionX = 0;
                    directionY = 1;
                    break;
                case 3:
                    directionX = -1;
                    directionY = 0;
                    break;
                case 4:
                    directionX = 1;
                    directionY = 0;
                    break;
                default:
                    System.out.println("Game over!");
                    return;
            }

            // Move the snake
            int[] head = snake.get(0);
            int[] newHead = {head[0] + directionX, head[1] + directionY};

            // Check for collision
            if (newHead[0] < 0 || newHead[0] >= board.length || newHead[1] < 0 || newHead[1] >= board[0].length
                    || board[newHead[0]][newHead[1]] == SNAKE_BODY) {
                System.out.println("Game over!");
                return;
            }

            // Check if the snake eats the food
            if (Arrays.equals(newHead, foodPosition)) {
                snake.add(0, newHead);
                points++;
                board[newHead[0]][newHead[1]] = SNAKE_BODY;
                printBoard();
                placeFood();
            } else {
                int[] tail = snake.remove(snake.size() - 1);
                board[tail[0]][tail[1]] = '\u0000';
                snake.add(0, newHead);
                board[newHead[0]][newHead[1]] = SNAKE_BODY;
            }
        }
    }
        public static void main (String[]args){
            SnakeGame game = new SnakeGame();
            game.run();
        }
        private void printBoard() {
            System.out.println("points: " + points);

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

}
