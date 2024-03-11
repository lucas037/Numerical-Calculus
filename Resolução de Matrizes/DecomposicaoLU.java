public class DecomposicaoLU {
    private Matrix matrix;
    private Matrix resultMatrix;
    private Matrix L;
    private Matrix U;
    private Matrix Y;
    private Matrix X;

    public DecomposicaoLU(Matrix matrix, Matrix resultMatrix) {
        this.matrix = matrix;
        this.resultMatrix = resultMatrix;
        generateL(matrix.getMatrix());
        generateU(matrix.getMatrix());
    }
    

    private void generateL(double[][] originalMatrix) {
        double[][] L = new double[originalMatrix.length][originalMatrix.length];

        for (int i = 0; i < originalMatrix.length; i++) {
            L[i][i] = 1;
        }

        this.L = new Matrix(L);
    }

    private void generateU(double[][] originalMatrix) {
        double[][] U = new double[originalMatrix.length][originalMatrix.length];

        this.U = new Matrix(U);

    }

    private void calculateLine(int line) {
        double[][] matrix = this.matrix.getMatrix();
        double[][] L = this.L.getMatrix();
        double[][] U = this.U.getMatrix();

        for (int i = line; i < matrix.length; i++) {
            double auxValue = 0;
            if (line != 0) {
                for (int j = 0; j < line; j++) {
                    auxValue += (L[line][j]*U[j][i]);
                }
            }

            U[line][i] = matrix[line][i] - auxValue;
            
        }

        this.U.setMatrix(U);
    }

    private void calculateColumn(int column) {
        double[][] matrix = this.matrix.getMatrix();
        double[][] L = this.L.getMatrix();
        double[][] U = this.U.getMatrix();

        for (int i = column + 1; i < matrix.length; i++) {
            double auxValue = 0;
            if (column != 0) {
                for (int j = 0; j < column; j++) {
                    auxValue += L[i][j]*U[j][column];
                }
            }

            double pseudoPiv = U[column][column];
            L[i][column] = (matrix[i][column] - auxValue)/pseudoPiv;
        }

        this.L.setMatrix(L);
    }

    public void decompLU() {
        for (int i = 0; i < matrix.getMatrix().length; i++) {
            calculateLine(i);
            calculateColumn(i);
        }
    }

    private void calculateY() {
        double[][] L = this.L.getMatrix();
        double[][] resultMatrix = this.resultMatrix.getMatrix();
        double[][] Y = new double[1][resultMatrix[0].length];

        for (int i = 0; i < Y[0].length; i++) {
            double auxValue = 0;
            for (int j = 0; j < i; j++) {
                auxValue += Y[0][j]*L[i][j];
            }

            Y[0][i] = resultMatrix[0][i] - auxValue;
        }

        this.Y = new Matrix(Y);
    }

    private void calculateX() {
        double[][] U = this.U.getMatrix();
        double[][] resultMatrix = this.resultMatrix.getMatrix();
        double[][] Y = this.Y.getMatrix();
        double[][] X = new double[1][resultMatrix[0].length];

        for (int i = X[0].length - 1; i >= 0; i--) {
            double auxValue = 0;
            for (int j = X[0].length - 1; j > i; j--) {
                auxValue += U[i][j]*X[0][j];
            }

            
            X[0][i] = (Y[0][i]-auxValue)/U[i][i];

        }

        this.X = new Matrix(X);
    }

    public void solve() {
        // AX = B
        // A = LU
        decompLU(); // encontra LU

        // LY = B
        calculateY();

        // YX = B
        calculateX();
        
        System.out.print(toString());

    }

    public String toString() {
        String str = "";

        str += "Matrix:\n";
        str += matrix.toString()+"\n";

        str += "Result Matrix:\n";
        str += resultMatrix.toString()+"\n";

        str += "Lower:\n";
        str += L.toString()+"\n";

        str += "Upper:\n";
        str += U.toString()+"\n";

        str += "Y:\n";
        str += Y.toString()+"\n";

        str += "X:\n";
        str += X.toString()+"\n";

        return str;
    }
}
