package Servicos;

import Entidades.*;
import Ficheiros.GestorFicheiros;
import Ficheiros.LeitorFicheiros;
import UI.InputsAuxiliares;

import java.io.File;

public class GestaoHospital {
    // ================== ATRIBUTOS ==================
    private Medico[] medicos;
    private Paciente[] pacientes;
    private Paciente[] historicoPacientes;
    private GestaoTurnos gestorDeTurnos;
    private Consulta[] consultas;
    private Sintoma[] sintomas;
    private Especialidade[] especialidades;

    private Configuracao configuracao;
    private LeitorFicheiros leitor;
    private GestorFicheiros gestor;

    private int totalMedicos;
    private int totalPacientes;
    private int totalConsultas;
    private int totalSintomas;
    private int totalEspecialidades;
    private int totalHistorico;

    private int totalPacientesAtendidos = 0;

    // ================== CONSTRUTOR ==================
    public GestaoHospital() {
        medicos = new Medico[100];
        pacientes = new Paciente[200];
        historicoPacientes = new Paciente[1000];
        consultas = new Consulta[100];
        sintomas = new Sintoma[100];
        especialidades = new Especialidade[20];

        totalMedicos = 0;
        totalPacientes = 0;
        totalConsultas = 0;
        totalSintomas = 0;
        totalEspecialidades = 0;
        totalHistorico = 0;

        configuracao = new Configuracao();

        leitor = new LeitorFicheiros(String.valueOf(configuracao.getSeparador()));
        gestor = new GestorFicheiros(String.valueOf(configuracao.getSeparador()));
        this.gestorDeTurnos = new GestaoTurnos(this);
        carregarDadosIniciais();
        gestor.escreverLog("logs.txt", "Sistema iniciado - Dia " + +gestorDeTurnos.getDiasDecorridos());
    }

    // ================== CARREGAMENTO DE DADOS ==================
    private void carregarDadosIniciais() {
        try {
            String caminho = configuracao.getCaminhoFicheiros();
            File dir = new File(caminho);
            if (!dir.exists()) dir.mkdirs();

            // Carregar especialidades
            Especialidade[] espLidas = leitor.lerEspecialidades(caminho + "especialidades.txt");
            if (espLidas != null) {
                for (Especialidade e : espLidas) {
                    if (e != null && totalEspecialidades < especialidades.length)
                        especialidades[totalEspecialidades++] = e;
                }
            }

            // Carregar médicos
            Medico[] medLidos = leitor.lerMedicos(caminho + "medicos.txt", this.especialidades);
            if (medLidos != null) {
                for (Medico m : medLidos) {
                    if (m != null && totalMedicos < medicos.length)
                        medicos[totalMedicos++] = m;
                }
            }

            // Carregar sintomas
            Sintoma[] sintLidos = leitor.lerSintomas(caminho + "sintomas.txt", especialidades);
            if (sintLidos != null) {
                for (Sintoma s : sintLidos) {
                    if (s != null && totalSintomas < sintomas.length)
                        sintomas[totalSintomas++] = s;
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
        if (!novoCaminho.endsWith("/") && !novoCaminho.endsWith("\\"))
            novoCaminho += "/";

        if (configuracao.verificarPassword(InputsAuxiliares.lerTexto("Password: "))) {
            configuracao.setCaminhoFicheiros(novoCaminho);

            // Recarregar dados do novo caminho
            totalEspecialidades = 0;
            totalMedicos = 0;
            totalSintomas = 0;

            leitor = new LeitorFicheiros(String.valueOf(configuracao.getSeparador()));
            carregarDadosIniciais();

            InputsAuxiliares.imprimirSucesso("Caminho alterado e dados recarregados!");
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
        InputsAuxiliares.exibirMsgCancelar();

        if (!configuracao.verificarPassword(InputsAuxiliares.lerTexto("Password: "))) {
            InputsAuxiliares.imprimirErro("Password incorreta!");
            return;
        }

        System.out.println("(Deixe em branco para manter o valor atual)");

        try {
            String inputBaixa = InputsAuxiliares.lerTexto(
                    "Tempo Baixa [" + configuracao.getTempoConsultaBaixa() + "]: ");
            if (inputBaixa.trim().equals("0")) {
                if(InputsAuxiliares.confirmar("Deseja cancelar a operação? (S/N): ")){
                    return;
                }
            } else if (!inputBaixa.isEmpty()) {
                try {
                    int tempo = Integer.parseInt(inputBaixa);
                    if (tempo > 0) {
                        configuracao.setTempoConsultaBaixa(tempo);
                    } else {
                        InputsAuxiliares.imprimirErro("O tempo deve ser positivo. Valor mantido.");
                    }
                } catch (NumberFormatException e) {
                    InputsAuxiliares.imprimirErro("Valor inválido!");
                }
            }

            String inputMedia = InputsAuxiliares.lerTexto(
                    "Tempo Média [" + configuracao.getTempoConsultaMedia() + "]: ");

            if (inputMedia.trim().equals("0")) {
                if (InputsAuxiliares.confirmar("Deseja cancelar a operação? (S/N): ")){
                    return;
                }
            } else if (!inputMedia.isEmpty()) {
                try {
                    int tempo = Integer.parseInt(inputMedia);
                    if (tempo > 0) {
                        configuracao.setTempoConsultaMedia(tempo);
                    } else {
                        InputsAuxiliares.imprimirErro("O tempo deve ser positivo. Valor mantido.");
                    }
                } catch (NumberFormatException e) {
                    InputsAuxiliares.imprimirErro("Valor inválido!");
                }
            }

            String inputUrgente = InputsAuxiliares.lerTexto(
                    "Tempo Urgente [" + configuracao.getTempoConsultaUrgente() + "]: ");
            if (inputUrgente.trim().equals("0")) {
                if (InputsAuxiliares.confirmar("Deseja cancelar a operação? (S/N): ")){
                    return;
                }
            } else if (!inputUrgente.isEmpty()) {
                try {
                    int tempo = Integer.parseInt(inputUrgente);
                    if (tempo > 0) {
                        configuracao.setTempoConsultaUrgente(tempo);
                    } else{
                        InputsAuxiliares.imprimirErro("O tempo deve ser positivo. Valor mantido.");
                    }
                } catch (NumberFormatException e) {
                    InputsAuxiliares.imprimirErro("Valor inválido!");
                }
            }

            InputsAuxiliares.imprimirSucesso("Configuração de tempos finalizada!");
        } catch (Exception e) {
            System.out.println("<< Operação cancelada pelo utilizador.");
        }
    }
    // ================== ESTATÍSTICAS ==================
    public void mediaPacientesDia() {
        ConsultaEstatistica.mostrarMediaPacientes(totalPacientesAtendidos, gestorDeTurnos.getDiasDecorridos());
    }

    public void tabelaSalarios() {
        ConsultaEstatistica.mostrarTabelaSalarios(medicos, totalMedicos, gestorDeTurnos.getDiasDecorridos());
    }

    public void topEspecialidades() {
        ConsultaEstatistica.mostrarTopEspecialidades(especialidades, totalEspecialidades,
                historicoPacientes, totalHistorico);
    }

    public void listarUtentesPorSintoma() {
        ConsultaEstatistica.mostrarUtentesPorSintoma(historicoPacientes, totalHistorico, sintomas, totalSintomas);
    }

    // ================== GESTÃO DE MÉDICOS ==================
    public void listarMedicos() {
        InputsAuxiliares.imprimirCabecalho("LISTA DE MÉDICOS");

        // 1. Obter a hora atual do Gestor de Turnos
        int horaAtual = 0;
        if (gestorDeTurnos != null) {
            horaAtual = gestorDeTurnos.getUnidadeTempoAtual();
            System.out.println("Hora atual da simulação: " + horaAtual + "h");
        }

        System.out.printf("%-20s %-15s %-15s %-15s %-10s%n",
                "NOME", "ESPECIALIDADE", "HORÁRIO", "ESTADO ATUAL", "HORAS TRAB.");
        System.out.println("-".repeat(80));

        for (int i = 0; i < totalMedicos; i++) {
            Medico m = medicos[i];

            String horario = m.getHoraEntrada() + "h - " + m.getHoraSaida() + "h";
            String estadoStr;

            // 2. Lógica para definir o estado visual
            boolean dentroDoHorario = horaAtual >= m.getHoraEntrada() && horaAtual < m.getHoraSaida();

            if (!dentroDoHorario) {
                estadoStr = "Fora de Turno"; // Está fora do horário
            } else if (m.estaDeFolga()) { // <-- Se já implementaste o descanso que te mandei antes
                estadoStr = "Em Descanso";
            } else if (!m.isDisponivel()) {
                estadoStr = "Ocupado"; // Está numa consulta
            } else {
                estadoStr = "Disponível"; // Está no turno E livre
            }

            System.out.printf("%-20s %-15s %-15s %-15s %.1f%n",
                    m.getNome(),
                    m.getEspecialidade(),
                    horario,
                    estadoStr,
                    m.getHorasTrabalhadas()); // ADICIONA ISTO// Mostra o estado calculado
        }
        System.out.println("=".repeat(80));
    }


    public Medico procurarMedicoPorEspecialidade(String especialidade) {
        for (int i = 0; i < totalMedicos; i++) {
            Medico m = medicos[i];
            if (m.getEspecialidade().equalsIgnoreCase(especialidade) &&
                    m.isDisponivel() &&
                    m.estaEmServico(gestorDeTurnos.getUnidadeTempoAtual())) {
                return m;
            }
        }
        return null;
    }

    public boolean adicionarMedico(Medico medico) {
        if (totalMedicos >= medicos.length) return false;
        medicos[totalMedicos++] = medico;
        gestor.escreverLog("logs.txt", "Médico adicionado: " + medico.getNome());
        return true;
    }

    public Medico procurarMedicoPorNome(String nome) {
        for (int i = 0; i < totalMedicos; i++) {
            if (medicos[i].getNome().equalsIgnoreCase(nome)) {
                return medicos[i];
            }
        }
        return null;
    }

    public boolean atualizarMedico(String nome, Medico atualizado) {
        for (int i = 0; i < totalMedicos; i++) {
            if (medicos[i].getNome().equalsIgnoreCase(nome)) {
                medicos[i] = atualizado;
                gestor.escreverLog("logs.txt", "Médico atualizado: " + nome);
                return true;
            }
        }
        return false;
    }

    public boolean removerMedico(String nome) {
        for (int i = 0; i < totalMedicos; i++) {
            if (medicos[i].getNome().equalsIgnoreCase(nome)) {
                // Shift left manual
                for (int j = i; j < totalMedicos - 1; j++) {
                    medicos[j] = medicos[j + 1];
                }
                medicos[--totalMedicos] = null;
                gestor.escreverLog("logs.txt", "Médico removido: " + nome);
                return true;
            }
        }
        return false;
    }

    // ================== GESTÃO DE PACIENTES E TRIAGEM ==================
    // CÓDIGO CORRIGIDO
    public boolean adicionarPaciente(Paciente p) {
        // 1. Adicionar à Fila de Espera (Pacientes atuais)
        if (totalPacientes >= pacientes.length) {
            return false;
        }
        pacientes[totalPacientes++] = p;

        // 2. Adicionar ao Histórico (Para estatísticas)
        if (totalHistorico < historicoPacientes.length) {
            historicoPacientes[totalHistorico++] = p; // Usa e incrementa totalHistorico
        }

        return true;
    }

    public void listarPacientes() {
        InputsAuxiliares.imprimirCabecalho("LISTA DE PACIENTES");
        System.out.printf("%-20s %-10s %-20s %-15s %-10s%n",
                "NOME", "URGÊNCIA", "ESPECIALIDADE", "ESTADO", "ESPERA (UT)");

        for (int i = 0; i < totalPacientes; i++) {
            Paciente p = pacientes[i];
            System.out.printf("%-20s %-10s %-20s %-15s %d UT%n",
                    p.getNome(),
                    p.getNivelUrgencia(),
                    (p.getEspecialidadeDesejada() != null ? p.getEspecialidadeDesejada() : "N/D"),
                    (p.isEmAtendimento() ? "Em Atendimento" : "Sala de Espera"),
                    p.getTempoEspera());
        }
    }

    /**
     * TRIAGEM COMPLETA - Com pesquisa de sintomas
     */
    public void registarPaciente() {
        InputsAuxiliares.imprimirCabecalho("TRIAGEM DE PACIENTE");

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do paciente: ");
        Paciente p = new Paciente(nome, 5);

        // 1. SELECIONAR SINTOMAS COM PESQUISA
        boolean adicionarMais = true;

        while (adicionarMais && p.getTotalSintomas() < 5) {
            System.out.println("\n--- ADICIONAR SINTOMA (" + (p.getTotalSintomas() + 1) + "/5) ---");
            System.out.println("Escreva parte do nome para pesquisar (ex: 'cabeca', 'dor')");
            System.out.println("Ou prima ENTER sem escrever nada para ver a lista completa.");

            String termo = InputsAuxiliares.lerTexto("Pesquisa: ").trim().toLowerCase();

            // Array auxiliar para mapear a escolha do utilizador para o índice real do array 'sintomas'
            int[] indicesEncontrados = new int[totalSintomas];
            int qtdEncontrados = 0;

            System.out.println("\n--- RESULTADOS DA PESQUISA ---");
            for (int i = 0; i < totalSintomas; i++) {
                // Se termo vazio mostra tudo, se não, filtra pelo nome
                if (termo.isEmpty() || sintomas[i].getNome().toLowerCase().contains(termo)) {
                    // Guarda o índice original deste sintoma
                    indicesEncontrados[qtdEncontrados] = i;
                    qtdEncontrados++;

                    // Mostra a opção (ex: 1. Dor de cabeça)
                    System.out.printf("%d. %s%n", qtdEncontrados, sintomas[i]);
                }
            }

            if (qtdEncontrados == 0) {
                System.out.println(">> Nenhum sintoma encontrado.");
                if (!InputsAuxiliares.confirmar("Deseja tentar nova pesquisa? (N para terminar seleção)")) {
                    adicionarMais = false;
                }
                continue; // Volta ao início para nova pesquisa
            }

            System.out.println("0. Cancelar / Nova Pesquisa");
            System.out.println("-1. Terminar Seleção de Sintomas e Finalizar");

            int escolha = InputsAuxiliares.lerInteiroIntervalo(
                    "Escolha o sintoma (número): ", -1, qtdEncontrados);

            if (escolha == 0) {
                continue; // Volta a pesquisar
            } else if (escolha == -1) {
                adicionarMais = false; // Sai do loop
            } else {
                // Recupera o sintoma original usando o índice mapeado
                int indiceReal = indicesEncontrados[escolha - 1];
                Sintoma selecionado = sintomas[indiceReal];

                // Verificar se já tem o sintoma
                boolean jaTem = p.temSintoma(selecionado.getNome());

                if (!jaTem) {
                    p.adicionarSintoma(selecionado);
                    System.out.println("✓ Sintoma adicionado: " + selecionado.getNome());
                } else {
                    System.out.println(">> O paciente já tem esse sintoma registado.");
                }

                if (p.getTotalSintomas() >= 5) {
                    System.out.println(">> Limite de 5 sintomas atingido.");
                    adicionarMais = false;
                } else {
                    // Pergunta rápida se quer continuar, para agilizar
                    if (!InputsAuxiliares.confirmar("Adicionar outro sintoma?")) {
                        adicionarMais = false;
                    }
                }
            }
        }

        if (p.getTotalSintomas() == 0) {
            InputsAuxiliares.imprimirErro("Paciente tem de ter pelo menos um sintoma para ser registado.");
            return;
        }

        // 2. CALCULAR URGÊNCIA E ESPECIALIDADE (conforme enunciado)
        p.calcularUrgenciaEEspecialidade();

        // 3. REGISTAR PACIENTE
        if (adicionarPaciente(p)) {
            InputsAuxiliares.imprimirSucesso("PACIENTE REGISTADO COM SUCESSO!");
            System.out.println(" Resumo da Triagem:");
            System.out.println("  Nome: " + p.getNome());
            System.out.println("  Nível de Urgência: " + p.getNivelUrgencia());
            System.out.println("  Especialidade Encaminhada: " +
                    (p.getEspecialidadeDesejada() != null ? p.getEspecialidadeDesejada() : "Não definida"));
            System.out.println("  Sintomas: " + p.getTotalSintomas());

            gestor.escreverLog("logs.txt", "Paciente registado: " + p.getNome() +
                    " - Urgência: " + p.getNivelUrgencia());
        } else {
            InputsAuxiliares.imprimirErro("ERRO: Capacidade máxima de pacientes atingida!");
        }
    }


    // ================== GESTÃO DE CONSULTAS E TEMPO ==================
    public boolean criarConsulta(Medico medico, Paciente paciente, int tempoConsulta) {
        if (totalConsultas >= consultas.length) return false;

        consultas[totalConsultas++] = new Consulta(medico, paciente, tempoConsulta);
        medico.setDisponivel(false);
        paciente.setEmAtendimento(true);

        // Registrar horas trabalhadas
        medico.adicionarHorasTrabalhadas(1);

        gestor.escreverLog("logs.txt",
                "Consulta iniciada: " + paciente.getNome() + " -> Dr. " + medico.getNome() +
                        " (" + tempoConsulta + " UT)");

        return true;
    }

    /**
     * AVANÇAR TEMPO - conforme enunciado (24 UT por dia)
     */
    public void avancarTempo() {
        // Classe GestaoTurnos faz o avançar o tempo
        // tem a lógica de simulação, relógio, descanso e assim.
        if (this.gestorDeTurnos != null) {
            this.gestorDeTurnos.avancarUnidadeTempo();
        } else {
            System.out.println("ERRO: Gestor de Turnos não inicializado.");
        }
    }

    // ================== GESTÃO DE ESPECIALIDADES ==================
    public void listarEspecialidades() {
        InputsAuxiliares.imprimirCabecalho("LISTA DE ESPECIALIDADES");
        for (int i = 0; i < totalEspecialidades; i++) {
            System.out.println((i + 1) + ". " + especialidades[i]);
        }
    }

    public Especialidade procurarEspecialidade(String codigo) {
        for (int i = 0; i < totalEspecialidades; i++) {
            if (especialidades[i].getCodigo().equalsIgnoreCase(codigo)) {
                return especialidades[i];
            }
        }
        return null;
    }

    public boolean adicionarEspecialidade(Especialidade e) {
        if (totalEspecialidades >= especialidades.length) return false;

        // Verificar duplicados
        for (int i = 0; i < totalEspecialidades; i++) {
            if (especialidades[i].getCodigo().equalsIgnoreCase(e.getCodigo())) {
                return false;
            }
        }

        especialidades[totalEspecialidades++] = e;
        gestor.escreverLog("logs.txt", "Especialidade adicionada: " + e.getNome());
        return true;
    }

    public boolean atualizarEspecialidade(String codigo, Especialidade atualizada) {
        for (int i = 0; i < totalEspecialidades; i++) {
            if (especialidades[i].getCodigo().equalsIgnoreCase(codigo)) {
                especialidades[i] = atualizada;
                gestor.escreverLog("logs.txt", "Especialidade atualizada: " + codigo);
                return true;
            }
        }
        return false;
    }

    public boolean removerEspecialidade(String codigo) {
        for (int i = 0; i < totalEspecialidades; i++) {
            if (especialidades[i].getCodigo().equalsIgnoreCase(codigo)) {
                for (int j = i; j < totalEspecialidades - 1; j++) {
                    especialidades[j] = especialidades[j + 1];
                }
                especialidades[--totalEspecialidades] = null;
                gestor.escreverLog("logs.txt", "Especialidade removida: " + codigo);
                return true;
            }
        }
        return false;
    }

    public void listarConsultasEmCurso() {
        if (gestorDeTurnos != null) {
            gestorDeTurnos.mostrarConsultasAtivas();
        } else {
            System.out.println("ERRO: Gestor de turnos não inicializado.");
        }
    }

    // ================== GESTÃO DE SINTOMAS ==================
    public void listarSintomas() {
        InputsAuxiliares.imprimirCabecalho("LISTA DE SINTOMAS");
        for (int i = 0; i < totalSintomas; i++) {
            System.out.println((i + 1) + ". " + sintomas[i]);
        }
    }

    public Sintoma procurarSintoma(String nome) {
        for (int i = 0; i < totalSintomas; i++) {
            if (sintomas[i].getNome().equalsIgnoreCase(nome)) {
                return sintomas[i];
            }
        }
        return null;
    }

    public boolean adicionarSintoma(Sintoma s) {
        if (totalSintomas >= sintomas.length) return false;

        // Verificar duplicados
        for (int i = 0; i < totalSintomas; i++) {
            if (sintomas[i].getNome().equalsIgnoreCase(s.getNome())) {
                return false;
            }
        }

        sintomas[totalSintomas++] = s;
        gestor.escreverLog("logs.txt", "Sintoma adicionado: " + s.getNome());
        return true;
    }

    public boolean atualizarSintoma(String nome, Sintoma atualizado) {
        for (int i = 0; i < totalSintomas; i++) {
            if (sintomas[i].getNome().equalsIgnoreCase(nome)) {
                sintomas[i] = atualizado;
                gestor.escreverLog("logs.txt", "Sintoma atualizado: " + nome);
                return true;
            }
        }
        return false;
    }

    public boolean removerSintoma(String nome) {
        for (int i = 0; i < totalSintomas; i++) {
            if (sintomas[i].getNome().equalsIgnoreCase(nome)) {
                for (int j = i; j < totalSintomas - 1; j++) {
                    sintomas[j] = sintomas[j + 1];
                }
                sintomas[--totalSintomas] = null;
                gestor.escreverLog("logs.txt", "Sintoma removido: " + nome);
                return true;
            }
        }
        return false;
    }

    // ================== PERSISTÊNCIA ==================
    public void guardarDados() {
        try {
            String caminho = configuracao.getCaminhoFicheiros();

            // Guardar Médicos
            java.io.BufferedWriter bw = new java.io.BufferedWriter(
                    new java.io.FileWriter(caminho + "medicos.txt"));
            for (int i = 0; i < totalMedicos; i++) {
                Medico m = medicos[i];
                String linha = String.format("%s;%s;%d;%d;%.2f",
                        m.getNome(), m.getEspecialidade(),
                        m.getHoraEntrada(), m.getHoraSaida(), m.getValorHora());
                bw.write(linha.replace(",", "."));
                bw.newLine();
            }
            bw.close();

            // Guardar Pacientes (histórico)
            bw = new java.io.BufferedWriter(
                    new java.io.FileWriter(caminho + "pacientes_historico.txt", true)); // Append
            for (int i = 0; i < totalPacientes; i++) {
                Paciente p = pacientes[i];
                bw.write(p.getNome() + ";" + p.getNivelUrgencia() + ";" +
                        p.getEspecialidadeDesejada() + ";" + p.getTempoEspera());
                bw.newLine();
            }
            bw.close();

            // Guardar configuração atual
            bw = new java.io.BufferedWriter(
                    new java.io.FileWriter(caminho + "configuracao.txt"));
            bw.write(configuracao.toString());
            bw.close();

            gestor.escreverLog("logs.txt",
                    "Dados guardados com sucesso ao encerrar (Dia " + gestorDeTurnos.getDiasDecorridos() + ")");

        } catch (java.io.IOException e) {
            InputsAuxiliares.imprimirErro("Erro ao guardar dados: " + e.getMessage());
        }
    }

    // ver medicos
    public Medico[] getListaMedicos() {
        return this.medicos;
    }

    public Paciente[] getFilaEspera() {
        return this.pacientes;
    }

    // remover pacientes
    public void removerPacienteDaFila(int index) {
        if (index >= 0 && index < totalPacientes) {
            for (int i = index; i < totalPacientes - 1; i++) {
                pacientes[i] = pacientes[i + 1];
            }
            pacientes[--totalPacientes] = null;
        }
    }

    // guuuardar historico no gestorTurnos
    public void adicionarAoHistorico(Consulta c) {
        totalPacientesAtendidos++;
        if (gestor != null) {
            gestor.escreverLog("logs.txt", "Histórico: Consulta terminada - " + c.getPaciente().getNome());
        }
    }

    public String getConfiguracaoTexto() {
        return configuracao.toString();
    }
}