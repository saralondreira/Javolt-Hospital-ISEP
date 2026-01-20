package UI;

import Serviços.GestaoHospital;

public class Menu {

    private GestaoHospital gestaoHospital;

    public Menu(GestaoHospital gestaoHospital) {
        this.gestaoHospital = gestaoHospital;
    }

    // ================= MENU PRINCIPAL =================

    public void start() {
        InputsAuxiliares.limparTela();
        InputsAuxiliares.imprimirCabecalho("SISTEMA DE GESTÃO HOSPITALAR");
        System.out.println("|    Bem-vindo ao sistema Javolt Hospital    |");
        InputsAuxiliares.imprimirLinha();

        int opcao;

        do {
            // Mostrar menu e ler opção
            opcao = menuPrincipal();

            switch (opcao) {
                case 1 -> menuGestaoHospital();
                case 2 -> menuEstatisticas();
                case 3 -> menuConfiguracoes();
                case 0 -> {
                    System.out.println("\nObrigado por utilizar o sistema Javolt.");
                    System.out.println("A encerrar...");
                }
                default -> InputsAuxiliares.imprimirErro("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private int menuPrincipal() {
        InputsAuxiliares.limparTela(); // Limpar antes de mostrar o menu
        InputsAuxiliares.imprimirCabecalho("MENU PRINCIPAL");
        System.out.println("|   1 - Gestão do Hospital                |");
        System.out.println("|   2 - Estatísticas                      |");
        System.out.println("|   3 - Configurações                     |");
        System.out.println("|   0 - Sair                              |");
        InputsAuxiliares.imprimirLinha();
        return InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 3);
    }

    // ================= GESTÃO HOSPITAL =================

    private void menuGestaoHospital() {
        int opcao;

        do {
            InputsAuxiliares.limparTela();
            InputsAuxiliares.imprimirCabecalho("GESTÃO DO HOSPITAL");
            System.out.println("1 - Registar paciente (Triagem)");
            System.out.println("2 - Avançar tempo (Processar filas)");
            System.out.println("3 - Listar médicos");
            System.out.println("4 - Listar pacientes");
            System.out.println("0 - Voltar");
            InputsAuxiliares.imprimirLinha();

            opcao = InputsAuxiliares.lerInt("Opção: ");

            switch (opcao) {
                case 1 -> {
                    gestaoHospital.registarPaciente();
                    pausa();
                }
                case 2 -> {
                    gestaoHospital.avancarTempo();
                    pausa();
                }
                case 3 -> {
                    gestaoHospital.listarMedicos();
                    pausa();
                }
                case 4 -> {
                    gestaoHospital.listarPacientes();
                    pausa();
                }
                case 0 -> { /* Voltar */ }
                default -> {
                    InputsAuxiliares.imprimirErro("Opção inválida.");
                    pausa();
                }
            }
        } while (opcao != 0);
    }

    // ================= ESTATÍSTICAS =================

    private void menuEstatisticas() {
        int opcao;

        do {
            InputsAuxiliares.limparTela();
            InputsAuxiliares.imprimirCabecalho("ESTATÍSTICAS");
            System.out.println("1 - Média de pacientes por dia");
            System.out.println("2 - Salários por médico");
            System.out.println("3 - Top especialidades");
            System.out.println("0 - Voltar");
            InputsAuxiliares.imprimirLinha();

            opcao = InputsAuxiliares.lerInt("Opção: ");

            switch (opcao) {
                case 1 -> gestaoHospital.mediaPacientesDia();
                case 2 -> gestaoHospital.tabelaSalarios();
                case 3 -> gestaoHospital.topEspecialidades();
                case 0 -> { /* Voltar */ }
                default -> InputsAuxiliares.imprimirErro("Opção inválida.");
            }
            if (opcao != 0) pausa();

        } while (opcao != 0);
    }

    // ================= CONFIGURAÇÕES =================

    private void menuConfiguracoes() {
        int opcao;

        do {
            InputsAuxiliares.limparTela();
            InputsAuxiliares.imprimirCabecalho("CONFIGURAÇÕES");
            System.out.println("1 - Alterar caminho ficheiros");
            System.out.println("2 - Alterar separador");
            System.out.println("3 - Alterar tempos de consulta");
            System.out.println("0 - Voltar");
            InputsAuxiliares.imprimirLinha();

            opcao = InputsAuxiliares.lerInt("Opção: ");

            switch (opcao) {
                case 1 -> gestaoHospital.alterarCaminhoFicheiros();
                case 2 -> gestaoHospital.alterarSeparador();
                case 3 -> gestaoHospital.alterarTemposConsulta();
                case 0 -> { /* Voltar */ }
                default -> InputsAuxiliares.imprimirErro("Opção inválida.");
            }
            if (opcao != 0) pausa();

        } while (opcao != 0);
    }

    // ================= MÉTODOS AUXILIARES =================

    private void pausa() {
        InputsAuxiliares.lerString("\nPrima ENTER para continuar...");
    }
}