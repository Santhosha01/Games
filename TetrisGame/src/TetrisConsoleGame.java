import java.util.Scanner;

public class TetrisConsoleGame {
    static final char EMPTY = '.';
    static final char BLOCK = '#';
    static int score = 0;

    // Define tetrominoes
    static final char[][][] SHAPES = {
            {{'#', '#', '#', '#'}}, // I
            {{'#', '#'}, {'#', '#'}}, // O
            {{'#', '#', '#'}, {' ', '#', ' '}}, // T
            {{'#', '#', ' '}, {' ', '#', '#'}}, // S
            {{' ', '#', '#'}, {'#', '#', ' '}}  // Z
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter game box dimensions (n m): ");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        char[][] box = new char[n][m];
        initializeBox(box);

        while (true) {
            displayBox(box);

            System.out.print("Enter shape index (0-4): ");
            int shapeIndex = scanner.nextInt();

            System.out.print("Enter number of rotations (0-3): ");
            int rotations = scanner.nextInt();

            System.out.print("Enter starting column: ");
            int col = scanner.nextInt();

            char[][] shape = getRotatedShape(SHAPES[shapeIndex], rotations);

            if (!dropShape(box, shape, col)) {
                System.out.println("Game Over!");
                break;
            }

            clearFullRows(box);
            System.out.println("Score: " + score);
        }

        scanner.close();
    }

    static void initializeBox(char[][] box) {
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[i].length; j++) {
                box[i][j] = EMPTY;
            }
        }
    }

    static void displayBox(char[][] box) {
        for (char[] row : box) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    static char[][] getRotatedShape(char[][] shape, int rotations) {
        char[][] result = shape;
        for (int i = 0; i < rotations; i++) {
            result = rotate90Degrees(result);
        }
        return result;
    }

    static char[][] rotate90Degrees(char[][] shape) {
        int rows = shape.length;
        int cols = shape[0].length;
        char[][] rotated = new char[cols][rows];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotated[c][rows - 1 - r] = shape[r][c];
            }
        }

        return rotated;
    }

    static boolean dropShape(char[][] box, char[][] shape, int startCol) {
        int n = box.length;
        int m = box[0].length;
        int shapeHeight = shape.length;
        int shapeWidth = shape[0].length;

        // Adjust start column if out of bounds
        if (startCol < 0) startCol = 0;
        if (startCol + shapeWidth > m) startCol = m - shapeWidth;

        // Start from the bottom and move upwards to find the lowest position
        for (int row = n - shapeHeight; row >= 0; row--) {
            if (canPlace(box, shape, row, startCol)) {
                placeShape(box, shape, row, startCol);
                return true;
            }
        }
        return false;
    }

    static boolean canPlace(char[][] box, char[][] shape, int startRow, int startCol) {
        int n = box.length;
        int m = box[0].length;
        int shapeHeight = shape.length;
        int shapeWidth = shape[0].length;

        for (int i = 0; i < shapeHeight; i++) {
            for (int j = 0; j < shapeWidth; j++) {
                if (shape[i][j] == BLOCK) {
                    int newRow = startRow + i;
                    int newCol = startCol + j;
                    if (newRow >= n || newCol < 0 || newCol >= m || box[newRow][newCol] == BLOCK) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static void placeShape(char[][] box, char[][] shape, int startRow, int startCol) {
        int shapeHeight = shape.length;
        int shapeWidth = shape[0].length;

        for (int i = 0; i < shapeHeight; i++) {
            for (int j = 0; j < shapeWidth; j++) {
                if (shape[i][j] == BLOCK) {
                    box[startRow + i][startCol + j] = BLOCK;
                }
            }
        }
    }

    static void clearFullRows(char[][] box) {
        int n = box.length;
        int m = box[0].length;
        int fullRows = 0;

        for (int i = 0; i < n; i++) {
            boolean isFull = true;
            for (int j = 0; j < m; j++) {
                if (box[i][j] == EMPTY) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                removeRow(box, i);
                fullRows++;
            }
        }

        if (fullRows > 0) {
            score += 10 * Math.pow(2, fullRows - 1);
        }
    }

    static void removeRow(char[][] box, int rowToRemove) {
        int n = box.length;
        int m = box[0].length;

        for (int i = rowToRemove; i > 0; i--) {
            for (int j = 0; j < m; j++) {
                box[i][j] = box[i - 1][j];
            }
        }

        for (int j = 0; j < m; j++) {
            box[0][j] = EMPTY;
        }
    }
}