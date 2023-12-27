public class GaussJordanElimination {

    public static double[] solve(double[][] A, double[] b) {
        int n = A.length;

        // Augment matrix A with vector b
        double[][] augmentedMatrix = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, augmentedMatrix[i], 0, n);
            augmentedMatrix[i][n] = b[i];
        }

        // Gauss-Jordan elimination
        for (int i = 0; i < n; i++) {
            // Find the pivot row
            int pivotRow = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(augmentedMatrix[j][i]) > Math.abs(augmentedMatrix[pivotRow][i])) {
                    pivotRow = j;
                }
            }

            // Swap rows
            double[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[pivotRow];
            augmentedMatrix[pivotRow] = temp;

            // Divide the pivot row by its pivot element
            double pivot = augmentedMatrix[i][i];
            for (int j = i; j < n + 1; j++) {
                augmentedMatrix[i][j] /= pivot;
            }

            // Eliminate elements above and below the diagonal
            for (int j = 0; j < n; j++) {
                if (j == i) continue;
                double factor = augmentedMatrix[j][i];
                for (int k = i; k < n + 1; k++) {
                    augmentedMatrix[j][k] -= factor * augmentedMatrix[i][k];
                }
            }
        }

        // Extract the solution vector from the last column of the augmented matrix
        double[] x = new double[n];
        for (int i = 0; i < n; i++) {
            x[i] = augmentedMatrix[i][n];
        }

        return x;
    }

    public static void main(String[] args) {
        double[][] A = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1},
                {11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                {11, -10, 9, -8, 7, -6, 5, -4, 3, -2, 1}
        };

        // Print the matrix A
        System.out.println("Matrix A:");
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                System.out.printf("%.1f\t", A[i][j]);
            }
            System.out.println();
        }

        double[] b = {2047, 3, 12, 48, 384, 1536, 5, 50, 1952, 4083, 459};

        // Print the vector b
        System.out.println("Vector b:");
        for (int i = 0; i < b.length; i++) {
            System.out.printf("%.1f\t", b[i]);
        }
        System.out.println();

        double[] x = solve(A, b);

        // Print the solution as integers
        System.out.println("The solution:");
        for (int i = 0; i < x.length; i++) {
            System.out.printf("x%d = %d%n", i + 1, (int)Math.round(x[i]));
        }
    }
}
