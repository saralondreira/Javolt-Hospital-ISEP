package Serviços;

import Entidades.*;
import Ficheiros.*;
import UI.InputsAuxiliares;
import java.io.File;

public class GestaoHospital {

    // ================== ATRIBUTOS ==================
    private Medico[] medicos;
    private Paciente[] pacientes;
    private Consulta[] consultas;
    private Sintoma[] sintomas;
    private Especialidade[] especialidades;

    private Entidades.Configuracao configuracao;

    private LeitorFicheiros leitor;
    private GestorFicheiros gestor;

    private int totalMedicos;
    private int totalPacientes;
    private int totalConsultas;
    private int totalSintomas;
    private int totalEspecialidades;

    private int totalPacientesAtendidos = 0;
    private int diasDecorridos = 1;
    private int unidadeTempoAtual = 1;
    private final int UNIDADES_POR_DIA = 24;

    // ================== CONSTRUTOR ==================
    public GestaoHospital() {
        medicos = new Medico[100];
        pacientes = new Paciente[200];
        consultas = new Consulta[100];
        sintomas = new Sintoma[50];
        especialidades = new Especialidade[20];

        totalMedicos = 0;
        totalPacientes = 0;
        totalConsultas = 0;
        totalSintomas = 0;
        totalEspecialidades = 0;

        configuracao = new Entidades.Configuracao();

        // Inicializar leitor e gestor de ficheiros
        leitor = new LeitorFicheiros(String.valueOf(configuracao.getSeparador()));
        gestor = new GestorFicheiros(String.valueOf(configuracao.getSeparador()));

        carregarDadosIniciais();
        gestor.escreverLog("logs.txt", "Sistema iniciado com sucesso");
    }

    // ================== CARREGAMENTO DE DADOS ==================
    private void carregarDadosIniciais() {
        try {
            String caminho = configuracao.getCaminhoFicheiros();
            File dir = new File(caminho);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Especialidade[] espLidas = leitor.lerEspecialidades(caminho + "especialidades.txt");
            if (espLidas != null) {
                for (int i = 0; i < espLidas.length; i++) {
                    if (espLidas[i] != null && totalEspecialidades < especialidades.length)
                        especialidades[totalEspecialidades++] = espLidas[i];
                }
            }

            Medico[] medLidos = leitor.lerMedicos(caminho + "medicos.txt");
            if (medLidos != null) {
                for (int i = 0; i < medLidos.length; i++) {
                    if (medLidos[i] != null && totalMedicos < medicos.length)
                        medicos[totalMedicos++] = medLidos[i];
                }
            }

            Sintoma[] sintLidos = leitor.lerSintomas(caminho + "sintomas.txt", especialidades);
            if (sintLidos != null) {
                for (int i = 0; i < sintLidos.length; i++) {
                    if (sintLidos[i] != null && totalSintomas < sintomas.length)
                        sintomas[totalSintomas++] = sintLidos[i];
                }
            }
            InputsAuxiliares.imprimirSucesso("Dados carregados com sucesso!");

        } catch (Exception e) {
            InputsAuxiliares.imprimirErro("Erro ao carregar dados: " + e.getMessage());
        }
    }

    // ================== CONFIGURAÇÕES ==================
    public void alterarCaminhoFicheiros() {
        InputsAuxiliares.imprimirCabecalho("ALTERAR CAMINHO DOS FICHEIROS");
        String novoCaminho = InputsAuxiliares.lerTextoNaoVazio("Novo caminho: ");
        if (!novoCaminho.endsWith("/") && !novoCaminho.endsWith("\\")) novoCaminho += "/";

        if (configuracao.verificarPassword(InputsAuxiliares.lerTexto("Password: "))) {
            configuracao.setCaminhoFicheiros(novoCaminho);
            totalEspecialidades = 0;
            totalMedicos = 0;
            totalSintomas = 0;

            leitor = new LeitorFicheiros(String.valueOf(configuracao.getSeparador()));
            carregarDadosIniciais();
            InputsAuxiliares.imprimirSucesso("Caminho alterado!");
        } else {
            InputsAuxiliares.imprimirErro("Password incorreta!");
        }
    }

    public void alterarSeparador() {
        InputsAuxiliares.imprimirCabecalho("ALTERAR SEPARADOR");
        char novoSeparador = InputsAuxiliares.lerSeparador("Novo separador");

        if (configuracao.verificarPassword(InputsAuxiliares.lerTexto("Password: "))) {
            configuracao.setSeparador(novoSeparador);
            leitor = new LeitorFicheiros(String.valueOf(novoSeparador));
            gestor = new GestorFicheiros(String.valueOf(novoSeparador));
            InputsAuxiliares.imprimirSucesso("Separador alterado!");
        } else {
            InputsAuxiliares.imprimirErro("Password incorreta!");
        }
    }

    public void alterarTemposConsulta() {
        InputsAuxiliares.imprimirCabecalho("ALTERAR TEMPOS DE CONSULTA");
        if (!configuracao.verificarPassword(InputsAuxiliares.lerTexto("Password: "))) {
            InputsAuxiliares.imprimirErro("Password incorreta!");
            return;
        }

        int tempoBaixa = InputsAuxiliares.lerInteiro("Tempo Baixa (" + configuracao.getTempoConsultaBaixa() + "): ");
        if (tempoBaixa > 0) configuracao.setTempoConsultaBaixa(tempoBaixa);

        int tempoMedia = InputsAuxiliares.lerInteiro("Tempo Média (" + configuracao.getTempoConsultaMedia() + "): ");
        if (tempoMedia > 0) configuracao.setTempoConsultaMedia(tempoMedia);

        int tempoUrgente = InputsAuxiliares.lerInteiro("Tempo Urgente (" + configuracao.getTempoConsultaUrgente() + "): ");
        if (tempoUrgente > 0) configuracao.setTempoConsultaUrgente(tempoUrgente);

        InputsAuxiliares.imprimirSucesso("Tempos atualizados!");
    }

    // ================== ESTATÍSTICAS ==================

    public void mediaPacientesDia() {
        Consulta_estatistica.mostrarMediaPacientes(totalPacientesAtendidos, diasDecorridos);
    }

    public void tabelaSalarios() {
        Consulta_estatistica.mostrarTabelaSalarios(medicos, totalMedicos, diasDecorridos);
    }

    public void topEspecialidades() {
        Consulta_estatistica.mostrarTopEspecialidades(especialidades, totalEspecialidades, pacientes, totalPacientes);
    }

    // ================== GESTÃO DE MÉDICOS ==================
    public void listarMedicos() {
        InputsAuxiliares.imprimirCabecalho("LISTA DE MÉDICOS");
        for (int i = 0; i < totalMedicos; i++) {
            System.out.println(medicos[i].toString());
        }
    }

    public Medico procurarMedicoPorEspecialidade(String especialidade) {
        for (int i = 0; i < totalMedicos; i++) {
            if (medicos[i].getEspecialidade().equalsIgnoreCase(especialidade) && medicos[i].isDisponivel()) {
                return medicos[i];
            }
        }
        return null;
    }

    // ================== GESTÃO DE PACIENTES E TRIAGEM ==================
    public boolean adicionarPaciente(Paciente p) {
        if (totalPacientes >= pacientes.length) return false;
        pacientes[totalPacientes++] = p;
        return true;
    }

    public void listarPacientes() {
        InputsAuxiliares.imprimirCabecalho("LISTA DE PACIENTES");
        System.out.printf("%-20s %-10s %-20s %-10s%n", "NOME", "URGÊNCIA", "ESPECIALIDADE", "ESPERA");
        for (int i = 0; i < totalPacientes; i++) {
            System.out.println(pacientes[i].toString());
        }
    }

    /**
     * Lógica de Triagem Completa
     */
    public void registarPaciente() {
        InputsAuxiliares.imprimirCabecalho("TRIAGEM DE PACIENTE");

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do paciente: ");
        // Criar paciente com limite de 5 sintomas
        Paciente p = new Paciente(nome, 5);

        // --- Adicionar Sintomas ---
        boolean adicionarMais = true;
        while (adicionarMais && p.getTotalSintomas() < 5) {
            System.out.println("\n--- Lista de Sintomas ---");
            for (int i = 0; i < totalSintomas; i++) {
                System.out.printf("%d. %s (%s)%n", (i + 1), sintomas[i].getNome(), sintomas[i].getNivelUrgencia());
            }

            int escolha = InputsAuxiliares.lerInteiroIntervalo("Escolha o sintoma (0 para terminar): ", 0, totalSintomas);

            if (escolha == 0) {
                adicionarMais = false;
            } else {
                Sintoma selecionado = sintomas[escolha - 1];

                // Verificar se já tem o sintoma (loop manual)
                boolean jaTem = false;
                Sintoma[] sintsPaciente = p.getSintomas();
                for(int k=0; k < p.getTotalSintomas(); k++) {
                    if (sintsPaciente[k].getNome().equals(selecionado.getNome())) {
                        jaTem = true;
                        break;
                    }
                }

                if (!jaTem) {
                    p.adicionarSintoma(selecionado);
                    System.out.println("Sintoma adicionado: " + selecionado.getNome());
                } else {
                    System.out.println("O paciente já tem esse sintoma.");
                }

                if (p.getTotalSintomas() >= 5) {
                    System.out.println("Limite de sintomas atingido.");
                    adicionarMais = false;
                }
            }
        }

        if (p.getTotalSintomas() == 0) {
            InputsAuxiliares.imprimirErro("Paciente tem de ter pelo menos um sintoma.");
            return;
        }

        // --- Calcular Urgência e Especialidade ---
        calcularUrgenciaEEspecialidade(p);

        // --- Registar ---
        if (adicionarPaciente(p)) {
            InputsAuxiliares.imprimirSucesso("Paciente registado na fila!");
            System.out.println("Urgência Atribuída: " + p.getNivelUrgencia());
            System.out.println("Especialidade Encaminhada: " + p.getEspecialidadeDesejada());
        } else {
            InputsAuxiliares.imprimirErro("Hospital cheio!");
        }
    }

    private void calcularUrgenciaEEspecialidade(Paciente p) {
        String maiorUrgencia = "Verde"; // Começa no mais baixo
        Sintoma[] sintomasPaciente = p.getSintomas();

        for (int i = 0; i < p.getTotalSintomas(); i++) {
            String urgSintoma = sintomasPaciente[i].getNivelUrgencia(); // Verde, Laranja, Vermelha

            if (urgSintoma.equalsIgnoreCase("Vermelha")) {
                maiorUrgencia = "Vermelha";
                break; // Máxima prioridade encontrada
            } else if (urgSintoma.equalsIgnoreCase("Laranja") && !maiorUrgencia.equals("Vermelha")) {
                maiorUrgencia = "Laranja";
            }
        }

        String nivelSistema = "Baixa";
        switch (maiorUrgencia) {
            case "Vermelha": nivelSistema = "Urgente"; break;
            case "Laranja": nivelSistema = "Média"; break;
            default: nivelSistema = "Baixa"; break;
        }
        p.setNivelUrgencia(nivelSistema);

        // 2. Determinar Especialidade
        String especialidadeFinal = "Clínica Geral";

        for (int i = 0; i < p.getTotalSintomas(); i++) {
            if (sintomasPaciente[i].getNivelUrgencia().equalsIgnoreCase(maiorUrgencia)) {
                if (sintomasPaciente[i].getEspecialidade() != null) {
                    especialidadeFinal = sintomasPaciente[i].getEspecialidade().getNome();
                    break;
                }
            }
        }
        p.setEspecialidadeDesejada(especialidadeFinal);
    }

    // ================== GESTÃO DE CONSULTAS E TEMPO ==================
    public boolean criarConsulta(Medico medico, Paciente paciente, int tempoConsulta) {
        if (totalConsultas >= consultas.length) return false;

        consultas[totalConsultas++] = new Consulta(medico, paciente, tempoConsulta);
        medico.setDisponivel(false);
        paciente.setEmAtendimento(true);
        gestor.escreverLog("logs.txt", "Consulta iniciada: " + paciente.getNome() + " -> Dr. " + medico.getNome());
        return true;
    }

    public void avancarTempo() {
        InputsAuxiliares.imprimirCabecalho("AVANÇAR TEMPO");

        unidadeTempoAtual++;
        if (unidadeTempoAtual > UNIDADES_POR_DIA) {
            unidadeTempoAtual = 1;
            diasDecorridos++;
            System.out.println(">>> NOVO DIA: " + diasDecorridos + " <<<");
        }

        System.out.println("Hora atual: " + unidadeTempoAtual + " UT");

        for (int i = 0; i < totalConsultas; i++) {
            if (consultas[i] != null) {
                consultas[i].avancarTempo();
                if (consultas[i].terminou()) {
                    terminarConsulta(i);
                    i--; // Ajustar índice
                }
            }
        }

        for (int i = 0; i < totalPacientes; i++) {
            if (pacientes[i] != null && !pacientes[i].isEmAtendimento()) {
                pacientes[i].incrementarTempoEspera();
                verificarAgravamento(pacientes[i]);
            }
        }

        alocarPacientesAutomaticamente();
    }

    private void terminarConsulta(int index) {
        Consulta c = consultas[index];
        c.getMedico().setDisponivel(true);
        c.getPaciente().setEmAtendimento(false);

        // Remover consulta do array (shift left manual)
        for(int i = index; i < totalConsultas - 1; i++){
            consultas[i] = consultas[i+1];
        }
        consultas[--totalConsultas] = null;
        totalPacientesAtendidos++;
        System.out.println(">>> Consulta terminada: " + c.getPaciente().getNome());
    }

    private void verificarAgravamento(Paciente p) {
        int espera = p.getTempoEspera();
        String urg = p.getNivelUrgencia();

        if (urg.equals("Baixa") && espera >= configuracao.getTempoBaixaParaMedia()) {
            p.setNivelUrgencia("Média");
            System.out.println("ALERT: " + p.getNome() + " agravou para MÉDIA.");
        } else if (urg.equals("Média") && espera >= configuracao.getTempoMediaParaUrgente()) {
            p.setNivelUrgencia("Urgente");
            System.out.println("ALERT: " + p.getNome() + " agravou para URGENTE.");
        }
    }

    private void alocarPacientesAutomaticamente() {
        for (int i = 0; i < totalPacientes; i++) {
            Paciente p = pacientes[i];
            if (!p.isEmAtendimento() && p.getEspecialidadeDesejada() != null) {

                Medico m = procurarMedicoPorEspecialidade(p.getEspecialidadeDesejada());
                if (m != null && m.isDisponivel()) {
                    // Define tempo com base na urgência
                    int tempo = 0;
                    switch (p.getNivelUrgencia()) {
                        case "Baixa": tempo = configuracao.getTempoConsultaBaixa(); break;
                        case "Média": tempo = configuracao.getTempoConsultaMedia(); break;
                        case "Urgente": tempo = configuracao.getTempoConsultaUrgente(); break;
                        default: tempo = 1; break;
                    }

                    criarConsulta(m, p, tempo);
                    System.out.println(">>> Alocado: " + p.getNome() + " ao Dr. " + m.getNome());
                }
            }
        }
    }

    // ================== PERSISTÊNCIA (GUARDAR AO SAIR) ==================
    public void guardarDados() {
        try {
            String caminho = configuracao.getCaminhoFicheiros();

            // 1. Guardar Médicos (Sobrescreve o ficheiro atual)
            java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(caminho + "medicos.txt"));
            for (int i = 0; i < totalMedicos; i++) {
                Medico m = medicos[i];
                // Formato: Nome;Especialidade;Entrada;Saida;Valor
                String linha = String.format("%s;%s;%d;%d;%.2f",
                        m.getNome(), m.getEspecialidade(), m.getHoraEntrada(), m.getHoraSaida(), m.getValorHora());
                bw.write(linha.replace(",", ".")); // Garantir ponto decimal
                bw.newLine();
            }
            bw.close();

            // 2. Guardar Pacientes (Para histórico ou estado atual)
            bw = new java.io.BufferedWriter(new java.io.FileWriter(caminho + "pacientes.txt"));
            for (int i = 0; i < totalPacientes; i++) {
                Paciente p = pacientes[i];
                // Exemplo simples de guardado: Nome;Urgencia;Especialidade
                bw.write(p.getNome() + ";" + p.getNivelUrgencia() + ";" + p.getEspecialidadeDesejada());
                bw.newLine();
            }
            bw.close();

            gestor.escreverLog("logs.txt", "Dados guardados com sucesso ao encerrar.");
            InputsAuxiliares.imprimirSucesso("Dados guardados em " + caminho);

        } catch (java.io.IOException e) {
            InputsAuxiliares.imprimirErro("Erro ao guardar dados: " + e.getMessage());
        }
    }
}