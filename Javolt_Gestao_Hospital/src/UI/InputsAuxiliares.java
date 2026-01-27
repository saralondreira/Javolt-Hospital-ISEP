package UI;

import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class InputsAuxiliares {
    private static final Scanner scanner = new Scanner(System.in);

    // ================== MÉTODOS BÁSICOS ==================

    /**
     * Lê texto simples
     */
    public static String lerTexto(String msg) {
        System.out.print(msg);
        return scanner.nextLine().trim();
    }

    /**
     * Lê texto não vazio
     */
    public static String lerTextoNaoVazio(String msg) {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                imprimirErro("O campo não pode estar vazio.");
                continue;
            }
            return input;
        }
    }

    /**
     * Lê texto (String) com validação de vazio e cancelamento.
     */
    public static String lerTextoComCancelamento(String msg) throws OperacaoCanceladaException {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine();

            if (input.trim().equals("0")) {
                System.out.print("\n>> Deseja cancelar a operação? (S/N): ");
                String confirmacao = scanner.nextLine();
                if (confirmacao.equalsIgnoreCase("S")) {
                    throw new OperacaoCanceladaException();
                }
                imprimirAviso("Operação retomada. Continue a inserir dados.");
                continue;
            }

            if (input.trim().isEmpty()) {
                imprimirErro("O campo não pode estar vazio.");
                continue;
            }

            return input;
        }
    }

    // ================== LEITURA DE NÚMEROS ==================

    /**
     * Lê inteiro com cancelamento
     */
    public static int lerInteiro(String msg) {
        while (true) {
            System.out.print(msg + " (0 para cancelar): ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                if (confirmar("Deseja cancelar a operação?")) {
                    return Integer.MIN_VALUE;
                }
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                imprimirErro("Insira um número inteiro válido.");
            }
        }
    }

    /**
     * Lê número inteiro com cancelamento.
     */
    public static int lerInteiroComCancelamento(String msg) throws OperacaoCanceladaException {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine();

            if (input.trim().equals("0")) {
                System.out.print("\n>> Deseja cancelar a operação? (S/N): ");
                if (scanner.nextLine().equalsIgnoreCase("S")) {
                    throw new OperacaoCanceladaException();
                }
                imprimirAviso("Operação retomada. Continue a inserir dados.");
                continue;
            }

            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                imprimirErro("Valor inválido. Insira um número inteiro.");
            }
        }
    }

    /**
     * Lê inteiro dentro de intervalo
     */
    public static int lerInteiroIntervalo(String msg, int min, int max) {
        while (true) {
            try {
                System.out.print(msg);
                int v = Integer.parseInt(scanner.nextLine());
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {
            }
            imprimirErro("Valor inválido.");
        }
    }

    /**
     * Lê número decimal com cancelamento
     */
    public static double lerDouble(String msg) {
        while (true) {
            System.out.print(msg + " (0 para cancelar): ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                if (confirmar("Deseja cancelar a operação?")) {
                    return Double.MIN_VALUE;
                }
                continue;
            }

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                imprimirErro("Insira um número decimal válido.");
            }
        }
    }


    /**
     * Lê número decimal (Double) com cancelamento.
     * Necessário para Salários/Valor Hora.
     */
    public static double lerDoubleComCancelamento(String msg) throws OperacaoCanceladaException {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine();

            if (input.trim().equals("0")) {
                System.out.print("\n>> Deseja cancelar a operação? (S/N): ");
                if (scanner.nextLine().equalsIgnoreCase("S")) {
                    throw new OperacaoCanceladaException();
                }
                imprimirAviso("Operação retomada. Continue a inserir dados.");
                continue;
            }

            try {
                return Double.parseDouble(input.trim().replace(",", "."));
            } catch (NumberFormatException e) {
                imprimirErro("Valor inválido. Insira um número decimal (ex: 10.5).");
            }
        }
    }


    // ================== LEITURA DE CARACTERES ==================


    /**
     * Lê caractere separador (ex: ; , | TAB)
     */
    public static char lerSeparador(String msg) {
        System.out.println("Separadores disponíveis:");
        System.out.println(";  - Ponto e vírgula");
        System.out.println(",  - Vírgula");
        System.out.println("|  - Pipe");
        System.out.println("\\t - Tabulação (TAB)");

        while (true) {
            System.out.print(msg + ": ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("\\t") || input.equalsIgnoreCase("TAB")) {
                return '\t';
            }

            if (input.length() == 1) {
                char separador = input.charAt(0);
                if (separador == ';' || separador == ',' || separador == '|') {
                    return separador;
                }
            }

            imprimirErro("Separador inválido. Use ; , | ou TAB");
        }
    }

    // ================== CONFIRMAÇÕES ==================

    /**
     * Confirmação Sim/Não
     */
    public static boolean confirmar(String pergunta) {
        while (true) {
            System.out.print(pergunta + " (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();

            if (resposta.equals("S") || resposta.equals("SIM")) {
                return true;
            }

            if (resposta.equals("N") || resposta.equals("NAO") || resposta.equals("NÃO")) {
                return false;
            }

            System.out.println(">> Responda com S (Sim) ou N (Não)");
        }
    }

    /**
     * Versão antiga mantida para compatibilidade
     */
    public static boolean lerSimNao(String msg) {
        return confirmar(msg);
    }

    // ================== FORMATAÇÃO E EXIBIÇÃO ==================

    public static void imprimirCabecalho(String titulo) {
        int tamanhoFixo = 50;

        System.out.println();
        imprimirLinha();

        int espacos = tamanhoFixo - titulo.length() - 2;
        int esquerda = espacos / 2;
        int direita = espacos - esquerda;

        System.out.print("|");
        for (int i = 0; i <= esquerda; i++) System.out.print(" ");
        System.out.print(titulo.toUpperCase());
        for (int i = 0; i <= direita; i++) System.out.print(" ");
        System.out.println("|");

        imprimirLinha();
    }

    public static void imprimirLinha() {
        System.out.print("|");
        for (int i = 0; i < 50; i++) System.out.print("-");
        System.out.println("|");
    }


    public static void exibirMsgCancelar() {
        System.out.println("(Prima 0 em qualquer momento para cancelar)");
    }

    public static void imprimirErro(String mensagem) {
        System.out.println(">> ERRO: " + mensagem);
    }

    public static void imprimirAviso(String mensagem) {
        System.out.println(">> " + mensagem);
    }

    public static void imprimirSucesso(String mensagem) {
        System.out.println(">> ✓ " + mensagem);
    }

    public static void limparTela() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static void pausar() {
        System.out.print("\nPrima Enter para continuar...");
        scanner.nextLine();
    }

    public static class OperacaoCanceladaException extends Exception {
        public OperacaoCanceladaException() {
            super("Operação cancelada pelo utilizador.");
        }
    }
}