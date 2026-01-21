package Entidades;

public class Consulta {
    private Medico medico;
    private Paciente paciente;
    private int tempoRestante;
    private int tempoTotal;

    public Consulta(Medico medico, Paciente paciente, int tempoConsulta) {
        this.medico = medico;
        this.paciente = paciente;
        this.tempoRestante = tempoConsulta;
        this.tempoTotal = tempoConsulta;
    }

    public void avancarTempo() {
        if (tempoRestante > 0) tempoRestante--;
    }

    public boolean terminou() { return tempoRestante <= 0; }

    public Medico getMedico() { return medico; }
    public Paciente getPaciente() { return paciente; }
    public int getTempoRestante() { return tempoRestante; }
    public int getTempoTotal() { return tempoTotal; }

    @Override
    public String toString() {
        return "Consulta{" +
                "Paciente=" + paciente.getNome() +
                ", Médico=" + medico.getNome() +
                ", Tempo restante=" + tempoRestante + "/" + tempoTotal + '}';
    }
}