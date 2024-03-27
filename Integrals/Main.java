import java.util.Scanner;

class Main {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        int[] variation = getVariation(2, 0);

        int choose = 0;
        do {
            System.out.println("0. Parar | 1. Soma de Riemann | 2. Regra do Trapézio");
            choose = scanner.nextInt();

            int repeticoes;
            switch (choose) {
                case 1:
                    System.out.println("Quantas repetições deseja utilizar? Recomenda-se mais do que 100, no mínimo.");
                    System.out.print("Digite o valor: ");
                    repeticoes = scanner.nextInt();

                    if (repeticoes < 1) {
                        repeticoes = 1;
                    }

                    RiemannSum.principal(variation[0], variation[1], repeticoes);
                    break;
                case 2:
                    System.out.print("Digite o número de repetições (menor do que 2 para regra do trapézio simples): ");
                    repeticoes = scanner.nextInt();

                    if (repeticoes < 1) {
                        repeticoes = 1;
                    }

                    TrapezoidalRule.principal(variation[0], variation[1], repeticoes);
                    break;
            }

            System.out.println("");
        }
        while (choose != 0);

        scanner.close();
        
    }

    public static double funcao(double x) {
        return Math.sqrt(x);
    }

    public static int[] getVariation(int a, int b) {
        if (a > b) {
            int aux = a;
            a = b;
            b = aux;
        }

        int[] vetor = {a, b};
        return vetor;
    }
}