public class DecomposicaoCholesky {
    private Matrix matrix;
    private Matrix resultMatrix;
    private Matrix L;
    private Matrix U;
    private Matrix Y;
    private Matrix X;

    public DecomposicaoCholesky(Matrix matrix, Matrix resultMatrix) {
        this.matrix = matrix;
        this.resultMatrix = resultMatrix;
        
        L = new Matrix(new double[matrix.getMatrix().length][matrix.getMatrix().length]);
        U = new Matrix(new double[matrix.getMatrix().length][matrix.getMatrix().length]);
    }

    private void calculateDiagonal(int index) {
        double[][] matrix = this.matrix.getMatrix();
        double[][] L = this.L.getMatrix();

        double auxValue = 0;
        for (int i = 0; i < index; i++) {
            auxValue += Math.pow(L[index][i], 2);
        }

        L[index][index] = Math.sqrt(matrix[index][index]-auxValue);
        this.L.setMatrix(L);
    }

    private void calculateL(int index) {
        double[][] matrix = this.matrix.getMatrix();
        double[][] L = this.L.getMatrix();
        
        for (int i = index + 1; i < matrix.length; i++) {
            double auxValue = 0;
            if (index != 0) {
                for (int j = 0; j < index; j++) {
                    auxValue += L[index][j]*L[i][j];
                }
            }

            L[i][index] = (matrix[i][index]-auxValue)/L[index][index];
        }

        this.L.setMatrix(L);
    }

    private void calculateU() {
        double[][] L = this.L.getMatrix();
        double[][] U = new double[L.length][L.length];

        for (int i = 0; i < L.length; i++) {
            for (int j = 0; j <= i; j++) {
                U[j][i] = L[i][j];
            }
        }

        this.U.setMatrix(U);
    }

    private void calculateLU() {
        double[][] matrix = this.matrix.getMatrix();

        for (int i = 0; i < matrix.length - 1; i++) {
            calculateDiagonal(i);
            calculateL(i);
        }
        calculateDiagonal(matrix.length - 1);

        calculateU();
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

            Y[0][i] = (resultMatrix[0][i] - auxValue)/L[i][i];
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

    private boolean verificarSimetria() {
        double[][] matrix = this.matrix.getMatrix();
        double[][] transporta = new double[matrix.length][matrix.length];

        for (int i = 0; i < transporta.length; i++) {
            for (int j = 0; j < transporta.length; j++) {
                if (matrix[j][i] != matrix[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public void solve() {
        if (verificarSimetria()) {
            calculateLU();
    
            // AX = B
            // A = L*U (L * Transporta de L)
            // LUX = B
            // Y = UX
            // LY = B
    
            calculateY();
            calculateX();
    
            System.out.print(toString());
        }
        else {
            System.out.println("Matriz não é simétrica.");
        }
    }

    public String toString() {
        String str = "";

        str += "Matrix:\n";
        str += matrix.toString()+"\n";

        str += "Result Matrix:\n";
        str += resultMatrix.toString()+"\n";

        str += "Lower:\n";
        str += L.toString()+"\n";

        str += "(Lower)':\n";
        str += U.toString()+"\n";

        str += "Y:\n";
        str += Y.toString()+"\n";

        str += "X:\n";
        str += X.toString()+"\n";

        return str;
    }
}