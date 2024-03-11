import java.text.DecimalFormat;

public class GaussSeidel {
    private Matrix matrix;
    private Matrix resultMatrix;
    private Matrix chute;
    private Matrix chuteAnterior;
    private int iteracoes;
    private double e;

    public GaussSeidel(Matrix matrix, Matrix resultMatrix, Matrix chuteInicial, double e) {
        this.matrix = matrix;
        this.resultMatrix = resultMatrix;
        this.chute = chuteInicial;
        this.chuteAnterior = new Matrix(new double[1][chuteInicial.getMatrix()[0].length]);
        this.e = e;
        iteracoes = 0;
    }

    private double modulo(double a) {
        if (a < 0) {
            return -a;
        }
        return a;
    }

    private boolean testeDiagonalDominante() {
        double[][] matrix = this.matrix.getMatrix();

        for (int i = 0; i < matrix.length; i++) {
            double diagonalPrincipal = modulo(matrix[i][i]);
            double soma = 0;

            for (int j = 0; j < matrix[i].length; j++) {
                if (i != j) {
                    soma += modulo(matrix[i][j]);
                }
            }

            if (diagonalPrincipal < soma) {
                return false;
            }

        }

        return true;
    }

    public void calcularIteracao() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        double[][] matrix = this.matrix.getMatrix();
        double[][] resultMatrix = this.resultMatrix.getMatrix();
        double[][] chute = this.chute.getMatrix();
        double[][] chuteAnterior = this.chuteAnterior.getMatrix();
        iteracoes++;

        for (int i = 0; i < chute[0].length; i++) {
            chuteAnterior[0][i] = chute[0][i];
        }

        System.out.println("\nIteração "+iteracoes+":");

        double[] result = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("X"+i+" = (");
            System.out.print(resultMatrix[0][i]);

            double calcValue = resultMatrix[0][i];
            for (int j = 0; j < matrix.length; j++) {
                if (i != j) {
                    System.out.print(" - "+df.format(matrix[i][j])+"*"+df.format(chute[0][j]));
                    calcValue -= matrix[i][j]*chute[0][j];
                }
            }
            System.out.print(")/"+matrix[i][i]);

            result[i] = calcValue/matrix[i][i];
            System.out.println("   = "+df.format(result[i]));

            chute[0][i] = result[i];
        }

        this.chute.setMatrix(chute);
        this.chuteAnterior.setMatrix(chuteAnterior);
        
    }

    private double calcularDParada() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);

        double[][] chute = this.chute.getMatrix();
        double[][] chuteAnterior = this.chuteAnterior.getMatrix();

        // Calculo Norma da Diferença
        double dMax = modulo(chute[0][0]-chuteAnterior[0][0]);
        for (int i = 1; i < chute[0].length; i++) {
            if (modulo(chute[0][i]-chuteAnterior[0][i]) > dMax) {
                dMax = modulo(chute[0][i]-chuteAnterior[0][i]);
            }
        }

        // Calcular Norma dos Novos "Chutes"
        double dNovo = modulo(chute[0][0]);
        for (int i = 1; i < chute[0].length; i++) {
            if (modulo(chute[0][i]) > dMax) {
                dNovo = modulo(chute[0][i]);
            }
        }

        System.out.println("dParada : "+df.format(dMax/dNovo));

        return dMax/dNovo;

    }

    private boolean verificarParada() {
        return calcularDParada() > this.e;
    }

    public void solve() {
        if (testeDiagonalDominante()) {
            System.out.println("A matriz tem diagonal dominante.");
    
            do {
            calcularIteracao();
            }
            while (verificarParada());
        }
        else {
            System.out.println("A matriz não tem diagonal dominante.");
        }
    }
}