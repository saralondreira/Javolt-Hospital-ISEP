import java.util.Scanner;

public class Menu {

    public static Scanner sc = new Scanner(System.in);

    public Menu(String[] args) {
        int opcao;

        do {
            mostrarMenuPrincipal();
            opcao = sc.nextInt();
            sc.nextLine(); // limar buffer

            switch (opcao) {
                case 1:
                    menuGestaoDados();
                    break;
                case 2:
                    menuFuncionamentoHospital();
                    break;
                case 3:
                    menuEstatisticas();
                    break;
                case 4:
                    menuConfiguracoes();
                    break;
                case 0:
                    System.out.println("A sair do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            }while (opcao !=0);
        }

        private static void mostrarMenuPrincipal(){
            System.out.println("\n=== SISTEMA DE GESTÃO DO HOSPITAL ===");
            System.out.println("1 - Gestão de Dados");
            System.out.println("2 - Funcionamento do Hospital");
            System.out.println("3 - Estatísticas e Logs");
            System.out.println("4 - Configurações");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
        }
    private static void menuGestaoDados() {
        int opcao;
        do {
            System.out.println("\n--- Gestão de Dados ---");
            System.out.println("1 - Gerir Médicos");
            System.out.println("2 - Gerir Especialidades");
            System.out.println("3 - Gerir Sintomas");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Gestão de Médicos (em desenvolvimento)");
                    break;
                case 2:
                    System.out.println("Gestão de Especialidades (em desenvolvimento)");
                    break;
                case 3:
                    System.out.println("Gestão de Sintomas (em desenvolvimento)");
                    break;
            }
        }
        while (opcao != 0);
    }
    private static void menuFuncionamentoHospital() {
        int opcao ;
        do {
            System.out.println("\n--- Funcionamento do Hospital ---");
            System.out.println("1 - Registar Utente");
            System.out.println("2 - Realizar Triagem");
            System.out.println("3 - Encaminhar Utentes");
            System.out.println("4 - Avançar Dia");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    System.out.println("Registo de utente");
                    break;
                case 2:
                    System.out.println("Triagem");
                    break;
                case 3:
                    System.out.println("Encaminhamento");
                    break;
                case 4:
                    System.out.println("Avançar dia");
                    break;
            }
        }
        while (opcao !=0);
    }

    private static void menuEstatisticas() {
        int opcao;
        do {
            System.out.println("\n--- Estatísticas ---");
            System.out.println("1 - Média de Utentes por Dia");
            System.out.println("2 - Salários por Médico");
            System.out.println("3 - Top 3 Especialidades");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Média de utentes");
                    break;
                case 2:
                    System.out.println("Salários médicos");
                    break;
                case 3:
                    System.out.println("Top especialidades");
                    break;
            }
        }
        while (opcao != 0);
    }
    private static void menuConfiguracoes() {
        int opcao;
        do {
            System.out.println("\n--- Configurações ---");
            System.out.println("1 - Alterar Caminho de Ficheiros");
            System.out.println("2 - Alterar Separador");
            System.out.println("3 - Tempos de Consulta");
            System.out.println("4 - Alterar Password");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Alterar caminho");
                    break;
                case 2:
                    System.out.println("Alterar separador");
                    break;
                case 3:
                    System.out.println("Tempos de consulta");
                    break;
                case 4:
                    System.out.println("Alterar password");
                    break;
            }
        }
        while (opcao != 0);
    }
}