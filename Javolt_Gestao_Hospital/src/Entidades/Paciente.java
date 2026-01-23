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
        this.especialidadeDesejada = "Clínica Geral";
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

    public void incrementarTempoEspera() {
        tempoEspera++;
    }

    // getter para o gestorTurnos
    // 1=Baixa/Verde, 2=Média/Laranja, 3=Urgente/Vermelho
    public int getNivelUrgenciaNumerico() {
        if (this.nivelUrgencia == null) return 1;

        if (this.nivelUrgencia.equalsIgnoreCase("Urgente") ||
                this.nivelUrgencia.equalsIgnoreCase("Vermelho")) {
            return 3;
        }
        if (this.nivelUrgencia.equalsIgnoreCase("Média") ||
                this.nivelUrgencia.equalsIgnoreCase("Media") ||
                this.nivelUrgencia.equalsIgnoreCase("Laranja")) {
            return 2;
        }
        return 1; // Baixa
    }

    public void atualizarUrgenciaPorNumero(int nivelNumerico) {
        switch (nivelNumerico) {
            case 3: this.nivelUrgencia = "Urgente"; break;
            case 2: this.nivelUrgencia = "Média"; break;
            default: this.nivelUrgencia = "Baixa"; break;
        }
    }

    @Override
    public String toString() {
        return String.format("%-20s %-10s %-20s %-10s Esp: %d",
                nome, nivelUrgencia,
                especialidadeDesejada,
                (emAtendimento ? "Em Atend." : "Espera"),
                tempoEspera);
    }

    // Urgencia passa a numeros no GestaoTurnos
    public void setNivelUrgencia(int nivelNumerico) {
        switch (nivelNumerico) {
            case 3: this.nivelUrgencia = "Urgente"; break;
            case 2: this.nivelUrgencia = "Média"; break;
            default: this.nivelUrgencia = "Baixa"; break;
        }
    }
}