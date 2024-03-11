import java.text.DecimalFormat;

public class Matrix {
    double[][] matrix;
    double[] matrixB;

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return this.matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        String str = "";
        for (int i = 0; i < matrix.length; i++) {
            str +="["+df.format(matrix[i][0]);
            for (int j = 1; j < matrix[i].length; j++) {
                str += ", "+df.format(matrix[i][j]);
            }
            str += "]\n";
        }
        return str;
    }
}
