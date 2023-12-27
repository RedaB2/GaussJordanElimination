import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MostPreciousPath {

    public static void main(String[] args) {
        // Define the input 2D array representing the room
        int[][] room = {
                {84, 99, 68, 75, 98, 44, 33, 96},
                {93, 53, 24, 46, 86, 1, 41, 10},
                {7, 30, 51, 65, 27, 94, 97, 83},
                {12, 67, 88, 22, 64, 47, 70, 56},
                {15, 92, 71, 13, 48, 77, 11, 91},
                {63, 16, 4, 31, 25, 17, 59, 32},
                {74, 40, 37, 78, 23, 14, 5, 79},
                {21, 95, 20, 82, 66, 52, 89, 35}
        };

        // Call the findMostPreciousPath method with the room as input
        findMostPreciousPath(room);
    }

    public static void findMostPreciousPath(int[][] room) {
        // Get the number of rows and columns in the room
        int rows = room.length;
        int cols = room[0].length;

        // Initialize a 2D array for dynamic programming (dp)
        int[][] dp = new int[rows][cols];

        // Initialize the first row of dp table with the first row of the room
        dp[0] = Arrays.copyOf(room[0], cols);

        // Fill the rest of the dp table using dynamic programming
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Calculate the possible gem counts from adjacent cells in the previous row
                int left = j > 0 ? dp[i - 1][j - 1] : 0;
                int up = dp[i - 1][j];
                int right = j < cols - 1 ? dp[i - 1][j + 1] : 0;

                // Store the maximum gem count for the current cell
                dp[i][j] = room[i][j] + Math.max(Math.max(left, up), right);
            }
        }

        // Find the starting square with the maximum gem count and the total number of gems collected
        int maxGems = -1;
        int startSquare = -1;
        for (int j = 0; j < cols; j++) {
            if (dp[rows - 1][j] > maxGems) {
                maxGems = dp[rows - 1][j];
                startSquare = j;
            }
        }

        // Trace back the path using the dp table
        int[] path = new int[rows];
        int currentSquare = startSquare;
        // Initialize a HashSet to store visited cells for redundancy/inconsistency check
        Set<String> visitedCells = new HashSet<>();

        for (int i = rows - 1; i >= 0; i--) {
            // Store the current cell in the path
            path[i] = currentSquare;
            // Generate a unique key for the current cell (row and column)
            String cellKey = i + "_" + currentSquare;
            // Check if the current cell has already been visited
            if (visitedCells.contains(cellKey)) {
                // If the cell has already been visited, print a message and exit the method
                System.out.println("Inconsistency detected: duplicate cell (" + (i + 1) + "," + (currentSquare + 1) + ")");
                return;
            }
            // Add the current cell to the visitedCells set
            visitedCells.add(cellKey);

            // If there is a previous row, determine which adjacent cell from the previous row led to the current cell
            if (i > 0) {
                int left = currentSquare > 0 ? dp[i - 1][currentSquare - 1] : Integer.MIN_VALUE;
                int up = dp[i - 1][currentSquare];
                int right = currentSquare < cols - 1 ? dp[i - 1][currentSquare + 1] : Integer.MIN_VALUE;

                // Choose the adjacent cell with the maximum gem count and update the currentSquare accordingly
                if (up >= left && up >= right) {
                    // Do nothing, stay in the same column
                } else if (left >= up && left >= right) {
                    currentSquare--;
                } else {
                    currentSquare++;
                }
            }
        }

        // Print the output: starting square, path, total gems collected, and vault number containing the Arkenstone
        System.out.println("Bilbo's starting square: " + (startSquare + 1));
        System.out.print("Bilbo's path: ");
        for (int i = 0; i < rows; i++) {
            System.out.print("(" + (i + 1) + "," + (path[i] + 1) + ")");
            if (i < rows - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
        System.out.println("Total number of gems collected: " + maxGems);
        System.out.println("Vault number containing the Arkenstone: " + (path[rows - 1] + 1));
    }
}