package UI;

import Servicos.GestaoHospital;

public class Menu {
    private GestaoHospital gestaoHospital;
    private SubEspecialidade subEspecialidade;
    private SubMedicos subMedicos;
    private SubSintoma subSintoma;

    public Menu(GestaoHospital gestaoHospital) {
        this.gestaoHospital = gestaoHospital;
        this.subEspecialidade = new SubEspecialidade();
        this.subMedicos = new SubMedicos();
        this.subSintoma = new SubSintoma();
    }

    // ================= MENU PRINCIPAL =================
    public void start() {
        InputsAuxiliares.limparTela();
        InputsAuxiliares.imprimirCabecalho("SISTEMA DE GESTÃO HOSPITALAR JAVOLT");
        System.out.println("|    Bem-vindo ao sistema de urgências      |");
        InputsAuxiliares.imprimirLinha();
        System.out.println("|   Desenvolvido por: Levi, Sara,          |");
        System.out.println("|          Leonardo, Micael                |");
        InputsAuxiliares.imprimirLinha();

        int opcao;
        do {
            opcao = menuPrincipal();

            switch (opcao) {
                case 1 -> menuGestaoHospital();
                case 2 -> menuEstatisticas();
                case 3 -> menuConfiguracoes();
                case 4 -> menuGestaoDados(); // NOVO: Menu de gestão CRUD
                case 0 -> {
                    System.out.println("\n A guardar dados...");
                    gestaoHospital.guardarDados();
                    System.out.println("  Obrigado por utilizar o sistema Javolt Hospital.");
                    System.out.println("   A encerrar...");
                }
                default -> InputsAuxiliares.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private int menuPrincipal() {
        InputsAuxiliares.limparTela();
        InputsAuxiliares.imprimirCabecalho("MENU PRINCIPAL");
        System.out.println("|   1 - Gestão do Hospital                |");
        System.out.println("|   2 - Estatísticas                      |");
        System.out.println("|   3 - Configurações                     |");
        System.out.println("|   4 - Gestão de Dados                   |");
        System.out.println("|   0 - Sair                              |");
        InputsAuxiliares.imprimirLinha();
        return InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);
    }

    // ================= GESTÃO HOSPITAL =================
    private void menuGestaoHospital() {
        int opcao;
        do {
            InputsAuxiliares.limparTela();
            InputsAuxiliares.imprimirCabecalho("GESTÃO DO HOSPITAL");
            System.out.println("1 - Registar paciente (Triagem)");
            System.out.println("2 - Avançar tempo (1 Unidade)");
            System.out.println("3 - Listar médicos");
            System.out.println("4 - Listar pacientes");
            System.out.println("5 - Listar consultas em curso");
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
                case 5 -> {
                    // Aqui poderia ter listarConsultas() se implementado
                    System.out.println("\n Funcionalidade em desenvolvimento...");
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
            System.out.println("2 - Tabela de salários");
            System.out.println("3 - Top 3 especialidades");
            System.out.println("4 - Utentes por sintoma");
            System.out.println("0 - Voltar");
            InputsAuxiliares.imprimirLinha();

            opcao = InputsAuxiliares.lerInt("Opção: ");

            switch (opcao) {
                case 1 -> gestaoHospital.mediaPacientesDia();
                case 2 -> gestaoHospital.tabelaSalarios();
                case 3 -> gestaoHospital.topEspecialidades();
                case 4 -> {
                    // Aqui poderia chamar método específico se implementado
                    System.out.println("\n Funcionalidade em desenvolvimento...");
                }
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
            System.out.println("4 - Ver configuração atual");
            System.out.println("0 - Voltar");
            InputsAuxiliares.imprimirLinha();

            opcao = InputsAuxiliares.lerInt("Opção: ");

            switch (opcao) {
                case 1 -> gestaoHospital.alterarCaminhoFicheiros();
                case 2 -> gestaoHospital.alterarSeparador();
                case 3 -> gestaoHospital.alterarTemposConsulta();
                case 4 -> {
                    System.out.println("\n CONFIGURAÇÃO ATUAL:");
                    // Aqui poderia mostrar configuração se houver método
                    System.out.println(" Método toString() da Configuracao em desenvolvimento...");
                }
                case 0 -> { /* Voltar */ }
                default -> InputsAuxiliares.imprimirErro("Opção inválida.");
            }
            if (opcao != 0) pausa();
        } while (opcao != 0);
    }

    // ================= GESTÃO DE DADOS (CRUD) =================
    private void menuGestaoDados() {
        int opcao;
        do {
            InputsAuxiliares.limparTela();
            InputsAuxiliares.imprimirCabecalho("GESTÃO DE DADOS");
            System.out.println("1 - Gestão de Médicos");
            System.out.println("2 - Gestão de Especialidades");
            System.out.println("3 - Gestão de Sintomas");
            System.out.println("0 - Voltar");
            InputsAuxiliares.imprimirLinha();

            opcao = InputsAuxiliares.lerInt("Opção: ");

            switch (opcao) {
                case 1 -> subMedicos.menuMedicos(gestaoHospital);
                case 2 -> subEspecialidade.menuEspecialidades(gestaoHospital);
                case 3 -> subSintoma.menuSintomas(gestaoHospital);
                case 0 -> { /* Voltar */ }
                default -> InputsAuxiliares.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // ================= MÉTODOS AUXILIARES =================
    private void pausa() {
        InputsAuxiliares.lerString("\n↵ Prima ENTER para continuar...");
    }
}