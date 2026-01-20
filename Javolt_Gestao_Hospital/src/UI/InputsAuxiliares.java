package UI;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputsAuxiliares {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    // ================== MÉTODOS BÁSICOS ==================

    /**
     * Lê opção do menu com validação
     */
    public static int lerOpcaoMenu(String msg) {
        System.out.print(msg);
        while (!scanner.hasNextInt()) {
            System.out.println(">> Valor inválido. Insira um número.");
            scanner.next();
            System.out.print(msg);
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

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
                System.out.println(">> ERRO: O campo não pode estar vazio.");
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
                    return Integer.MIN_VALUE; // Valor especial para cancelamento
                }
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(">> ERRO: Insira um número inteiro válido.");
            }
        }
    }

    /**
     * Lê inteiro dentro de intervalo
     */
    public static int lerInteiroIntervalo(String msg, int min, int max) {
        while (true) {
            int valor = lerInteiro(msg);

            if (valor == Integer.MIN_VALUE) {
                return valor; // Cancelado
            }

            if (valor >= min && valor <= max) {
                return valor;
            }

            System.out.println(">> ERRO: O valor deve estar entre " + min + " e " + max + ".");
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
                    return Double.MIN_VALUE; // Valor especial para cancelamento
                }
                continue;
            }

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println(">> ERRO: Insira um número decimal válido.");
            }
        }
    }

    // ================== LEITURA DE CARACTERES ==================

    /**
     * Lê um único caractere
     */
    public static char lerChar(String msg) {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine().trim();

            if (input.length() == 1) {
                return input.charAt(0);
            }

            System.out.println(">> ERRO: Insira apenas um caractere.");
        }
    }

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

            System.out.println(">> ERRO: Separador inválido. Use ; , | ou TAB");
        }
    }

    // ================== LEITURA DE DATAS ==================

    /**
     * Lê data e hora
     */
    public static LocalDateTime lerData(String msg) {
        while (true) {
            System.out.print(msg + " (formato: dd-MM-yyyy HH:mm, 0 para cancelar): ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                return null; // Cancelar
            }

            try {
                return LocalDateTime.parse(input, dateTimeFormatter);
            } catch (DateTimeParseException e) {
                System.out.println(">> ERRO: Formato inválido. Use dia-mes-ano hora:minuto");
            }
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
        for (int i = 0; i < esquerda; i++) System.out.print(" ");
        System.out.print(titulo.toUpperCase());
        for (int i = 0; i < direita; i++) System.out.print(" ");
        System.out.println("|");

        imprimirLinha();
    }

    public static void imprimirLinha() {
        System.out.print("|");
        for (int i = 0; i < 50; i++) System.out.print("-");
        System.out.println("|");
    }

    public static void imprimirTitulo(String titulo) {
        System.out.println("\n--- " + titulo.toUpperCase() + " ---");
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

    // ================== MÉTODOS COMPATIBILIDADE ==================

    /**
     * Método compatível com Menu.java (ler int simples)
     */
    public static int lerInt(String msg) {
        return lerInteiro(msg);
    }

    /**
     * Método compatível com Menu.java (ler string simples)
     */
    public static String lerString(String msg) {
        return lerTexto(msg);
    }

    /**
     * Método compatível para pausa no Menu
     */
    public static void pausa() {
        pausar();
    }
}