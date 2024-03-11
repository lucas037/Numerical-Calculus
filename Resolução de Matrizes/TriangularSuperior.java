public class TriangularSuperior {
    private Matrix matrix;
    private Matrix resultMatrix;
    private Matrix X;

    public TriangularSuperior(Matrix matrix, Matrix resultMatrix) {
        this.matrix = matrix;
        this.resultMatrix = resultMatrix;
        this.X = new Matrix(new double[1][matrix.getMatrix().length]);
    }

    private void swapLines(int a, int b) {
        double[][] matrix = this.matrix.getMatrix();
        double[][] resultMatrix = this.resultMatrix.getMatrix();

        double auxValue = resultMatrix[0][a];
        resultMatrix[0][a] = resultMatrix[0][b];
        resultMatrix[0][b] = auxValue;

        for (int i = 0; i < matrix.length; i++) {
            auxValue = matrix[a][i];
            matrix[a][i] = matrix[b][i];
            matrix[b][i] = auxValue;
        }

        this.matrix.setMatrix(matrix);
        this.resultMatrix.setMatrix(resultMatrix);
    }

    private void multiplyLine(int a, double factor) {
        double[][] matrix = this.matrix.getMatrix();
        double[][] resultMatrix = this.resultMatrix.getMatrix();

        resultMatrix[0][a] *= factor;

        for (int i = 0; i < matrix.length; i++) {
            matrix[a][i] *= factor;
        }

        this.matrix.setMatrix(matrix);
        this.resultMatrix.setMatrix(resultMatrix);
    }

    private void sumChangeLines(int a, int b) {
        double[][] matrix = this.matrix.getMatrix();
        double[][] resultMatrix = this.resultMatrix.getMatrix();

        resultMatrix[0][a] = resultMatrix[0][a] + resultMatrix[0][b];

        for (int i = 0; i < matrix.length; i++) {
            matrix[a][i] = matrix[a][i] + matrix[b][i];
        }

        this.matrix.setMatrix(matrix);
        this.resultMatrix.setMatrix(resultMatrix);
    }

    public void organize(int a) {
        double[][] matrix = this.matrix.getMatrix();

        for (int i = a; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length && matrix[i][i] == 0; j++) {
                if (matrix[j][i] != 0) {
                    swapLines(i, j);
                }
            }
        }

        int lastI = matrix.length - 1;
        int lastJ = matrix[lastI].length - 1;

        for (int i = lastI - 1; i >= a  && matrix[lastI][lastJ] == 0; i--) {
            if (matrix[lastI][i] != 0 && matrix[i][lastJ] != 0) {
                swapLines(lastI, i);
            }
        }

        // teste final
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][i] == 0) {
                System.out.println("Não foi possível organizar a matriz.");
                System.out.println(toString());
                System.out.println("ve aqui");
                System.exit(0);
            }
        }

        this.matrix.setMatrix(matrix);
    }

    public void scaleColumnMatrix(int b) {
        double[][] matrix = this.matrix.getMatrix();

        double pivo = matrix[b][b];

        System.out.println("===== Escalonando... =====");
        System.out.println(toString());

        for (int i = b + 1; i < matrix.length; i++) {
            double valor = matrix[i][b];

            if (valor != 0) {
                double fator = -pivo/valor;
                multiplyLine(i, fator);
            }
        }

        // soma as linhas modificadas, zerando os números abaixo do pivô
        for (int i = b + 1; i < matrix.length; i++) {
            if (matrix[i][b] != 0) {
                sumChangeLines(i, b);
            }
        }
    }

    private void scaleMatrix() {
        double[][] matrix = this.matrix.getMatrix();

        for (int i = 0; i < matrix.length; i++) {
            organize(i);
            scaleColumnMatrix(i);
        }
    }   

    private boolean checkLinearCombinationLines(int a, int b) {
        double[][] matrix = this.matrix.getMatrix();

        double[] lineA = matrix[a];
        double[] lineB = matrix[b];

        for (int i = 0; i < lineA.length; i++) {
            if ((lineA[0] != lineB[0]) && lineA[0]*lineB[0] == 0) {
                return false;
            }
        }

        boolean factorExist = false;
        double factor = 0;
        for (int i = 0; i < lineA.length; i++) {
            if (lineB[i] != 0) {
                if (!factorExist) {
                    factorExist = true;
                    factor = lineA[i]/lineB[i];
                }
                else if (factor != lineA[i]/lineB[i]) {
                    return false;
                }
            }
            else if (factorExist) {
                return false;
            }
        }

        return true;
    }    

    private boolean checkLinearCombinationColumn(int a, int b) {
        double[][] matrix = this.matrix.getMatrix();

        double[] columnA = new double[matrix.length];
        double[] columnB = new double[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            columnA[i] = matrix[i][a];
        }
        for (int i = 0; i < matrix.length; i++) {
            columnB[i] = matrix[i][b];
        }

        for (int i = 0; i < columnA.length; i++) {
            if ((columnA[0] != columnB[0]) && columnA[0]*columnB[0] == 0) {
                return false;
            }
        }

        boolean factorExist = false;
        double factor = 0;
        for (int i = 0; i < columnA.length; i++) {
            if (columnB[i] != 0) {
                if (!factorExist) {
                    factorExist = true;
                    factor = columnA[i]/columnB[i];
                }
                else if (factor != columnA[i]/columnB[i]) {
                    return false;
                }
            }
            else if (factorExist) {
                return false;
            }
        }

        return true;
    }
    
    private boolean check() {
        double[][] matrix = this.matrix.getMatrix();

        // Chega se há pelo menos uma linha zerada
        boolean linhaZerada = true;
        for (int i = 0; i < matrix.length; i++) {
            linhaZerada = true;

            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    linhaZerada = false;
                }
            }

            if (linhaZerada) {
                System.out.println("Linha "+i+" zerada.");
                return true;
            }
        }

        // Chega se há pelo menos uma coluna zerada
        boolean colunaZerada = true;
        for (int i = 0; i < matrix.length; i++) {
            colunaZerada = true;

            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[j][i] != 0) {
                    colunaZerada = false;
                }
            }

            if (colunaZerada) {
                System.out.println("Coluna "+i+" zerada.");
                return true;
            }
        }

        // Chega se há pelo menos uma linha que é combinação linear de outra
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                if (checkLinearCombinationLines(i, j)) {
                    System.out.println("Linhas "+i+" e "+j+" são combinação linear.");
                    return true;
                }
            }
        }

        // Chega se há pelo menos uma coluna que é combinação linear de outra
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                if (checkLinearCombinationColumn(i, j)) {
                    System.out.println("Colunas "+i+" e "+j+" são combinação linear.");
                    return true;
                }
            }
        }

        return false;
    }

    private void findX() {
        double[][] matrix = this.matrix.getMatrix();
        double[][] resultMatrix = this.resultMatrix.getMatrix();
        double[][] X = this.X.getMatrix();

        System.out.println("===== Calculato Resultado Final =====");

        // calcula valores de X
        for (int i = matrix.length - 1; i >= 0; i--) {
            double auxCalc = 0;

            for (int j = matrix.length - 1; j > i; j--) {
                auxCalc += X[0][j]*matrix[i][j];
            }
            X[0][i] = (resultMatrix[0][i] - auxCalc)/matrix[i][i];
            System.out.println("x"+i+" = "+X[0][i]);
        }
    } 

    public void solve() {
        System.out.println(toString());

        // checa se não há nenhum problema que impossibilita resolução da matriz
        if (!check()) {
            System.out.println("\n");
            scaleMatrix();
            findX();
            System.out.println("\n"+toString());
        }
    }

    public String toString() {
        String str = "";

        str += "Matrix:\n";
        str += matrix.toString()+"\n";

        str += "Result Matrix:\n";
        str += resultMatrix.toString()+"\n";

        str += "X:\n";
        str += X.toString();

        return str;
    }

    
}
