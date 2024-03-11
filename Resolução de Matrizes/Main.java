import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        double[][] matrix = {
            {4, 2, -1},
            {2, 5, -3},
            {-1, -3, 9}
        };

        double[][] resultMatrix = {
            {
                10,
                20,
                30
            }
        };

        double[][] chutesIniciais = {
            {
                -100,
                0,
                0
            }
        };


        int metodo = 0;
        do {
            System.out.println("Qual método deseja utilizar?");
            System.out.println("0. Encerrar | 1. Eliminação Gaussiana | 2. Decomposição LU | 3. Decomposição Cholesky | 4. Gauss-Jacobi | 5. Gauss-Seidel3");
            metodo = scanner.nextInt();
            System.out.print("\n");
            
            if (metodo == 1) {
                double[][] copiaMatrix = new double[matrix.length][matrix.length];
                double[][] copiaResultMatrix = new double[1][chutesIniciais[0].length];

                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        copiaMatrix[i][j] = matrix[i][j];
                    }
                }

                for (int i = 0; i < resultMatrix[0].length; i++) {
                    copiaResultMatrix[0][i] = resultMatrix[0][i];
                }

                TriangularSuperior ts = new TriangularSuperior(new Matrix(copiaMatrix), new Matrix(copiaResultMatrix));
                ts.solve();
            }
            if (metodo == 2) {
                DecomposicaoLU decLU = new DecomposicaoLU(new Matrix(matrix), new Matrix(resultMatrix));
                decLU.solve();
            }
            else if (metodo == 3) {
                DecomposicaoCholesky decCH = new DecomposicaoCholesky(new Matrix(matrix), new Matrix(resultMatrix));
                decCH.solve();
            }
            else if (metodo == 4) {
                double[][] copiaChutesIniciais = new double[1][chutesIniciais[0].length];

                for (int i = 0; i < chutesIniciais[0].length; i++) {
                    copiaChutesIniciais[0][i] = chutesIniciais[0][i];
                }

                GaussJacobi gJac = new GaussJacobi(new Matrix(matrix), new Matrix(resultMatrix), new Matrix(copiaChutesIniciais), 0.05);
                gJac.solve();
            }
            else if (metodo == 5) {
                double[][] copiaChutesIniciais = new double[1][chutesIniciais[0].length];

                for (int i = 0; i < chutesIniciais[0].length; i++) {
                    copiaChutesIniciais[0][i] = chutesIniciais[0][i];
                }

                GaussSeidel gSeid = new GaussSeidel(new Matrix(matrix), new Matrix(resultMatrix), new Matrix(copiaChutesIniciais), 0.05);
                gSeid.solve();
            }
        }
        while (metodo != 0);

        scanner.close();

    }
}