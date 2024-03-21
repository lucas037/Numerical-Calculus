import java.text.DecimalFormat;

class RiemannSum {
    public static void principal(double a, double b) {
        if (a > b) {
            double aux = a;
            a = b;
            b = aux;
        }

        int numPartitions = 10;
        double xDelta = (b-a)/numPartitions;

        double sum = 0;

        for (int i = 0, j = 0; i < numPartitions; i++, j++) {
            double pointA = a + i*xDelta;
            double pointB = a + (i+1)*xDelta;
            double height = Main.funcao(pointA);

            if (i == 0 || j == numPartitions/10) {
                String partition = "("+pointA+", "+pointB+")";
                System.out.println("Partition "+(i)+": "+partition+" / "+height);
                j = 0;
            }

            sum += xDelta*height;
        }

        DecimalFormat formato = new DecimalFormat("#.##");
        System.out.println("\nIntegral: "+formato.format(sum));
    }
}