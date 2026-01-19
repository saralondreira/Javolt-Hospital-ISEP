import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InputsAuxiliares {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Lê a opção introduzida do menu
     */
    public static int lerOpcaoMenu(String msg) {
        while (true) {
            exibirPrompt(msg);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                exibirErro("Valor inválido. Tente novamente.")
            }
        }
    }

    /**
     * Lê texto simples
     */
    public static String lerTexto(String msg) {
        exibirPrompt(msg);
        return scanner.nextLine().trim();
    }

    /**
     * Lê texto com validação de não vazio
     */
    public static String lerTextoNaoVazio(String msg) {
        while (true) {
            exibirPrompt(msg);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                exibirErro("O campo não pode estar vazio.");
                continue;
            }
            return input;
        }
    }

    /**
     * Lê texto com validação (usa "0" para cancelar).
     *
     * @param msg mensagem a mostrar
     * @return texto ou null se cancelado
     */
    public static String lerTextoComCancelamento(String msg) {
        System.out.println("(Prima 0 para cancelar)");

        while (true) {
            exibirPrompt(msg);
            String input = scanner.nextLine().trim();

            //Cancelamento com "0"
            if (input.equals("0")) {
                lerSimNao("Deseja cancelar a operação?");
            }
            System.out.println("Operação continuada.");
            continue;
        }

        if (input.isEmpty()) {
            exibirErro("O campo não pode estar vazio.");
            continue;
        }

        return input;
    }


/**
 * Lê inteiro com validação (usa "0" para cancelar).
 *
 * @param msg mensagem a mostrar
 * @return inteiro válido
 */
public static String lerInteiro(String msg) {
    while (true) {
        System.out.println(msg);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Valor inválido. Insira um número inteiro.");
        }
    }
}

/**
 * Lê inteiro dentro de um intervalo.
 *
 * @param msg mensagem a mostrar
 * @param min valor mínimo
 * @param max valor máximo
 * @return inteiro no intervalo
 */
public static int lerInteiroIntervalo(String msg, int min, int max) {
    while (true) {
        int valor = lerInteiro(msg);

        if (valor >= min && valor <= max) {
            return valor;
        }
        System.out.println("Erro: O valor deve estar entre " + min + " e " + max + ".");
    }
}

/**
 * Lê inteiro com CANCELAMENTO.
 *
 * @param msg mensagem a mostrar
 * @return inteiro ou Integer.MIN_VALUE se cancelado
 */
public static int lerInteiroComCancelamento(String msg) {
    System.out.println("(Prima 0 para cancelar)");
    while (true) {
        System.out.println(msg);
        String input = scanner.nextLine().trim();

        if (input.equals("0")) {
            System.out.println("Deseja cancelar a operação? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                return Integer.MIN_VALUE;
            }
            System.out.println("Operação continuada.");
            continue;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Valor inválido. Insira um número inteiro");
        }
    }
}

/**
 * Lê double com validação.
 *
 * @param msg mensagem a mostrar
 * @return double válido
 */
public static double lerDouble(String msg) {
    while (true) {
        System.out.println(msg);
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Valor inválido. Insira um número.");
        }
    }
}

/**
 * Lê double com CANCELAMENTO.
 *
 * @param msg mensagem a mostrar
 * @return double ou Double.MIN_VALUE se cancelado
 */
public static double lerDoubleComCancelamento(String msg) {
    System.out.println("(Prima 0 para cancelar)");
    while (true) {
        System.out.println(msg);
        String input = scanner.nextLine().trim();

        if (input.equals("0")) {
            lerSimNao("Deseja cancelar a operação?");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                return Double.MIN_VALUE;
            }
            System.out.println("Operação continuada.");
            continue;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Erro: Valor inválido. Insira um número.");
        }
    }
}

/**
 * Lê um caracter (apenas primeiro caractere).
 *
 * @param msg mensagem a mostrar
 * @return caractere inserido
 */
public static char lerChar(String msg) {
    while (true) {
        System.out.println(msg);
        String input = scanner.nextLine().trim();

        if (input.length() == 1) {
            return input.charAt(0);
        }
        System.out.println("Erro: Insira apenas um caractere.");
    }
}

public static boolean lerSimNao(String msg) {
    while (true) {
        System.out.println(msg + " (S/N): ");
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.equals("S") || input.equals("SIM")) {
            return true;
        }
        if (input.equals("N") || input.equals("NAO") || input.equals("NÃO")) {
            return false;
        }

        System.out.println("Erro: Responda com S (Sim) ou N (Não).");
    }
}

    // ================= CONTAR LINHAS =================
    public class FicheiroUtils {

        protected String separador;

        public FicheiroUtils(String separador) {
            this.separador = separador;
        }

        protected int contarLinhas(String caminho) {
            int total = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
                while (br.readLine() != null) {
                    total++;
                }
            } catch (IOException e) {
                System.out.println("Erro ao contar linhas: " + e.getMessage());
            }

            return total;
        }
    }
}
