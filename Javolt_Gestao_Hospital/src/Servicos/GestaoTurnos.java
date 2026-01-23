package Servicos;

import Entidades.Consulta;
import Entidades.Medico;
import Entidades.Paciente;

public class GestaoTurnos {

    private GestaoHospital gestaoHospital;

    // Variáveis de Controlo de Tempo
    private int unidadeTempoAtual;
    private int diasDecorridos;

    // Array para gerir as consultas
    private Consulta[] consultasAtivas;
    private final int MAX_CONSULTAS_SIMULTANEAS = 100;

    public GestaoTurnos(GestaoHospital gh) {
        this.gestaoHospital = gh;
        this.unidadeTempoAtual = 1;
        this.diasDecorridos = 1;
        this.consultasAtivas = new Consulta[MAX_CONSULTAS_SIMULTANEAS];
    }

    public int getUnidadeTempoAtual() {
        return unidadeTempoAtual;
    }

    public int getDiasDecorridos() {
        return this.diasDecorridos;
    }

    public void avancarUnidadeTempo() {
        System.out.println("\n--------------------------------------------------");
        System.out.println(" RELOGIO AVANCOU: " + unidadeTempoAtual + "h -> " + (unidadeTempoAtual + 1) + "h (Dia " + diasDecorridos + ")");
        System.out.println("--------------------------------------------------");

        // 1. Avançar o relógio
        unidadeTempoAtual++;
        if (unidadeTempoAtual > 24) {
            unidadeTempoAtual = 1;
            diasDecorridos++;
            System.out.println("UM NOVO DIA COMECOU! (Dia " + diasDecorridos + ")");
        }

        // 2. Processar consultas
        processarConsultasAtivas();

        // 3. Verificar cansaço
        verificarDescansoMedicos();

        // 4. Atualizar fila de espera
        atualizarFilaDeEspera();

        // 5. Atribuir pacientes
        atribuirPacientesAutomaticamente();
    }

    private void processarConsultasAtivas() {
        for (int i = 0; i < consultasAtivas.length; i++) {
            if (consultasAtivas[i] != null) {
                Consulta c = consultasAtivas[i];
                Medico m = c.getMedico();

                c.avancarTempo();
                m.adicionarHorasTrabalhadas(1.0);



                if (c.terminou()) {
                    System.out.println("CONSULTA TERMINADA: Dr. " + m.getNome() + " terminou com " + c.getPaciente().getNome());

                    gestaoHospital.adicionarAoHistorico(c);

                    c.getPaciente().setEmAtendimento(false);
                    m.setDisponivel(true);
                    consultasAtivas[i] = null;
                }
            }
        }
    }

    private void verificarDescansoMedicos() {
        Medico[] medicos = gestaoHospital.getListaMedicos();

        for (Medico m : medicos) {
            if (m == null) continue;

            if (m.isDisponivel()) {
                if (m.getHorasTrabalhoContinuo() >= 5) {
                    System.out.println("PAUSA OBRIGATORIA: Dr. " + m.getNome() + " trabalhou 5h seguidas.");
                    m.resetarHorasContinuo();
                }
            }
        }
    }

    private void atualizarFilaDeEspera() {
        Paciente[] fila = gestaoHospital.getFilaEspera();

        for (int i = 0; i < fila.length; i++) {
            if (fila[i] == null) continue;

            Paciente p = fila[i];
            p.incrementarTempoEspera();

            int espera = p.getTempoEspera();

            int nivel = p.getNivelUrgenciaNumerico();

            // Regras de Escala
            if (nivel == 1 && espera >= 3) {
                p.setNivelUrgencia(2);
                p.setTempoEspera(0);
                System.out.println("ESCALADA: " + p.getNome() + " subiu para prioridade MEDIA.");
            }
            else if (nivel == 2 && espera >= 3) {
                p.setNivelUrgencia(3);
                p.setTempoEspera(0);
                System.out.println("ESCALADA: " + p.getNome() + " subiu para prioridade URGENTE.");
            }
            else if (nivel == 3 && espera >= 2) {
                System.out.println("SAIDA POR TEMPO EXCESSIVO: " + p.getNome() + " abandonou a urgencia.");

                gestaoHospital.removerPacienteDaFila(i);
                i--;
            }
        }
    }

    private void atribuirPacientesAutomaticamente() {
        Medico[] medicos = gestaoHospital.getListaMedicos();
        Paciente[] fila = gestaoHospital.getFilaEspera();

        for (Medico m : medicos) {
            if (m == null) continue;

            if (!m.isDisponivel()) continue;

            boolean turnoValido = unidadeTempoAtual >= m.getHoraEntrada() && unidadeTempoAtual < m.getHoraSaida();
            if (!turnoValido) continue;

            // comentado so para testes nada defenitivo
            if (m.getHorasTrabalhoContinuo() == 0 && m.getHorasTrabalhadas() > 0) {
                continue;
            }

            int indexPaciente = encontrarMelhorPaciente(fila, m.getEspecialidade());

            if (indexPaciente != -1) {
                Paciente p = fila[indexPaciente];

                // calcular a duraçao
                int duracao = p.getNivelUrgenciaNumerico();
                if (duracao < 1) duracao = 1;

                iniciarConsulta(m, p, duracao);
                gestaoHospital.removerPacienteDaFila(indexPaciente);
            }
        }
    }

    private int encontrarMelhorPaciente(Paciente[] fila, String especialidadeMedico) {
        int indexMelhor = -1;
        int maiorUrgencia = -1;

        for (int i = 0; i < fila.length; i++) {
            if (fila[i] == null) continue;

            String espPac = fila[i].getEspecialidadeDesejada();
            if (espPac == null) continue;

            // remover acentos
            String esp = java.text.Normalizer.normalize(espPac, java.text.Normalizer.Form.NFD);
            esp = esp.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            esp = esp.toLowerCase().trim();

            String codPaciente = "";

            // ordem correta (evita ortopedia -> pedi)
            if (esp.contains("orto")) codPaciente = "ORTO";
            else if (esp.contains("pediatr")) codPaciente = "PEDI";
            else if (esp.contains("card")) codPaciente = "CARD";
            else if (esp.contains("clinica")) codPaciente = especialidadeMedico; // Clínica Geral = qualquer médico disponivel

            if (codPaciente.equalsIgnoreCase(especialidadeMedico)) {
                int urg = fila[i].getNivelUrgenciaNumerico();
                if (urg > maiorUrgencia) {
                    maiorUrgencia = urg;
                    indexMelhor = i;
                }
            }
        }
        return indexMelhor;
    }









    private void iniciarConsulta(Medico m, Paciente p, int duracao) {
        Consulta novaConsulta = new Consulta(m, p, duracao);

        boolean adicionado = false;
        for (int i = 0; i < consultasAtivas.length; i++) {
            if (consultasAtivas[i] == null) {
                consultasAtivas[i] = novaConsulta;
                m.setDisponivel(false);
                p.setEmAtendimento(true);
                System.out.println("ATRIBUICAO: Dr. " + m.getNome() + " -> " + p.getNome() + " (Duracao: " + duracao + "h)");
                adicionado = true;
                return;
            }
        }

        if (!adicionado) {
            System.out.println("ERRO CRITICO: Limite de consultas simultaneas atingido!");
        }
    }

    //tentativa de encontrar onde pode estar o erro PS:remover depois
    private String norm(String s) {
        if (s == null) return "";
        s = java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
        s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return s.trim().toLowerCase();
    }

}