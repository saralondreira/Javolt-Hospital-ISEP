package Entidades;

public class Paciente {
    private String nome;
    private Sintoma[] sintomas;
    private int totalSintomas;
    private String nivelUrgencia;
    private String especialidadeDesejada;
    private int tempoEspera;
    private boolean emAtendimento;

    public Paciente(String nome, int maxSintomas) {
        this.nome = nome;
        this.sintomas = new Sintoma[maxSintomas];
        this.totalSintomas = 0;
        this.tempoEspera = 0;
        this.emAtendimento = false;
        this.nivelUrgencia = "Baixa";
    }

    public void adicionarSintoma(Sintoma s) {
        if (totalSintomas < sintomas.length) {
            sintomas[totalSintomas++] = s;
        }
    }

    public boolean temSintoma(String nomeSintoma) {
        for (int i = 0; i < totalSintomas; i++) {
            if (sintomas[i].getNome().equalsIgnoreCase(nomeSintoma)) {
                return true;
            }
        }
        return false;
    }

    // GETTERS
    public String getNome() { return nome; }
    public Sintoma[] getSintomas() { return sintomas; }
    public int getTotalSintomas() { return totalSintomas; }
    public String getNivelUrgencia() { return nivelUrgencia; }
    public String getEspecialidadeDesejada() { return especialidadeDesejada; }
    public int getTempoEspera() { return tempoEspera; }
    public boolean isEmAtendimento() { return emAtendimento; }

    // SETTERS
    public void setEmAtendimento(boolean emAtendimento) { this.emAtendimento = emAtendimento; }
    public void setNivelUrgencia(String nivelUrgencia) { this.nivelUrgencia = nivelUrgencia; }
    public void setEspecialidadeDesejada(String especialidade) { this.especialidadeDesejada = especialidade; }
    public void setTempoEspera(int tempoEspera) { this.tempoEspera = tempoEspera; }

    public void incrementarTempoEspera() { tempoEspera++; }

    @Override
    public String toString() {
        return String.format("%-20s %-10s %-20s %-10s %d sintomas",
                nome, nivelUrgencia,
                (especialidadeDesejada != null ? especialidadeDesejada : "N/D"),
                (emAtendimento ? "Em atend." : "Em espera"), totalSintomas);
    }
}