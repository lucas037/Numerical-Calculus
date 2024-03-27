import java.text.DecimalFormat;

public class TrapezoidalRule {
    public static void principal(double a, double b, int numPartitions) {
        double xDelta = (b-a)/numPartitions;
        double sum = 0;

        for (int i = 0, j = 0; i < numPartitions; i++, j++) {
            double pointA = a + i*xDelta;
            double pointB = a + (i+1)*xDelta;

            double sub = pointB - pointA;
            double sumF = Main.funcao(pointA) + Main.funcao(pointB);

            if (i == 0 || j == numPartitions/10) {
                String partition = "("+pointA+", "+pointB+")";
                System.out.println("Partition "+(i)+": "+partition);
                j = 0;
            }

            sum += (sub*sumF)/2;
        }

        DecimalFormat formato = new DecimalFormat("#.##");
        System.out.println("\nIntegral: "+formato.format(sum));
    }
}
