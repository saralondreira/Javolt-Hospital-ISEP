package Servicos;

import Entidades.Consulta;
import Entidades.Medico;
import Entidades.Paciente;
import UI.InputsAuxiliares;

public class GestaoTurnos {

    private GestaoHospital gestaoHospital;

    private int unidadeTempoAtual;
    private int diasDecorridos;

    private Consulta[] consultasAtivas;
    private static final int MAX_CONSULTAS_SIMULTANEAS = 100;

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
        for (Medico m : gestaoHospital.getListaMedicos()){
            if (m != null && m.estaDeFolga()){
                m.decrementarDescanso();
            }
        }
        InputsAuxiliares.imprimirLinha();
        System.out.println(" RELOGIO AVANCOU: " + unidadeTempoAtual + "h -> " + (unidadeTempoAtual + 1) + "h (Dia " + diasDecorridos + ")");
        InputsAuxiliares.imprimirLinha();

        unidadeTempoAtual++;
        if (unidadeTempoAtual > 24) {
            unidadeTempoAtual = 1;
            diasDecorridos++;
            System.out.println("UM NOVO DIA COMECOU! (Dia " + diasDecorridos + ")");
        }

        processarConsultasAtivas();

        verificarDescansoMedicos();

        atualizarFilaDeEspera();

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

        int limiteHoras = gestaoHospital.getConfiguracao().getHorasTrabalhoParaDescanso();
        int tempoDescanso = gestaoHospital.getConfiguracao().getUnidadesDescanso();

        for (Medico m : medicos) {
            if (m == null) continue;

            if (m.isDisponivel()) {
                if (m.precisaDescanso(limiteHoras)) {
                    System.out.println("PAUSA OBRIGATORIA: Dr. " + m.getNome() + " trabalhou 5h seguidas.");
                    m.resetarHorasContinuo();
                    m.definirDescanso(tempoDescanso);
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

            if (nivel == 1 && espera >= 3) {
                p.setNivelUrgencia(2);
                p.setTempoEspera(0);
                System.out.println("ESCALADA: " + p.getNome() + " subiu para prioridade MEDIA.");
            } else if (nivel == 2 && espera >= 3) {
                p.setNivelUrgencia(3);
                p.setTempoEspera(0);
                System.out.println("ESCALADA: " + p.getNome() + " subiu para prioridade URGENTE.");
            } else if (nivel == 3 && espera >= 2) {
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

            if (!m.isDisponivel() || m.estaDeFolga()) continue;

            boolean turnoValido = unidadeTempoAtual >= m.getHoraEntrada() && unidadeTempoAtual < m.getHoraSaida();
            if (!turnoValido) continue;

            int indexPaciente = encontrarMelhorPaciente(fila, m.getEspecialidade());

            if (indexPaciente != -1) {
                Paciente p = fila[indexPaciente];

                int duracao = p.getNivelUrgenciaNumerico();
                if (duracao < 1) duracao = 1;

                iniciarConsulta(m, p, duracao);
                gestaoHospital.removerPacienteDaFila(indexPaciente);
            }
        }
    }

    private int encontrarMelhorPaciente(Paciente[] fila, String especialidadeMedico) {
        String codigoMedico = especialidadeMedico.trim();

        for (int i = 0; i < fila.length; i++) {
            Paciente p = fila[i];
            if (p == null || p.isEmAtendimento()) continue;
            if (p.getEspecialidadeDesejada() == null) continue;

            String espPaciente = p.getEspecialidadeDesejada().trim();

            boolean match = espPaciente.equalsIgnoreCase(codigoMedico) ||
                    espPaciente.equalsIgnoreCase("Clinica Geral") ||
                    espPaciente.equalsIgnoreCase("Clínica Geral");

            if (match && p.getNivelUrgencia().equalsIgnoreCase("Urgente")) {
                return i;
            }
        }

        for (int i = 0; i < fila.length; i++) {
            Paciente p = fila[i];
            if (p == null || p.isEmAtendimento()) continue;
            if (p.getEspecialidadeDesejada() == null) continue;

            String espPaciente = p.getEspecialidadeDesejada().trim();

            boolean match = espPaciente.equalsIgnoreCase(codigoMedico) ||
                    espPaciente.equalsIgnoreCase("Clinica Geral") ||
                    espPaciente.equalsIgnoreCase("Clínica Geral");

            if (match && p.getNivelUrgencia().equalsIgnoreCase("Média")) {
                return i;
            }
        }

        for (int i = 0; i < fila.length; i++) {
            Paciente p = fila[i];
            if (p == null || p.isEmAtendimento()) continue;
            if (p.getEspecialidadeDesejada() == null) continue;

            String espPaciente = p.getEspecialidadeDesejada().trim();

            boolean match = espPaciente.equalsIgnoreCase(codigoMedico) ||
                    espPaciente.equalsIgnoreCase("Clinica Geral") ||
                    espPaciente.equalsIgnoreCase("Clínica Geral");

            if (match && p.getNivelUrgencia().equalsIgnoreCase("Baixa")) {
                return i;
            }
        }

        return -1;
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

    public void mostrarConsultasAtivas() {
        System.out.println("\n--- CONSULTAS A DECORRER (Tempo atual: " + unidadeTempoAtual + "h) ---");
        boolean haConsultas = false;

        for (Consulta c : consultasAtivas) {
            if (c != null) {
                haConsultas = true;
                int tempoPassado = c.getTempoTotal() - c.getTempoRestante();
                System.out.printf("Dr. %-15s -> Paciente: %-15s | Restam: %d UTs | Progresso: [%d/%d]%n",
                        c.getMedico().getNome(),
                        c.getPaciente().getNome(),
                        c.getTempoRestante(),
                        tempoPassado,
                        c.getTempoTotal());
            }
        }

        if (!haConsultas) {
            System.out.println(">> Não existem consultas a decorrer neste momento.");
        }
        InputsAuxiliares.imprimirLinha();
    }
}