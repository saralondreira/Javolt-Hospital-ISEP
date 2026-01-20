package Entidades;

public class Paciente {
    private String nome;
    private Sintoma[] sintomas; // Array de objetos Sintoma
    private int totalSintomas;  // Contador
    private String nivelUrgencia;
    private String especialidadeDesejada;
    private int tempoEspera;
    private boolean emAtendimento;

    // Construtor
    public Paciente(String nome, int maxSintomas) {
        this.nome = nome;
        this.sintomas = new Sintoma[maxSintomas]; // Array fixo (sem ArrayList)
        this.totalSintomas = 0;
        this.tempoEspera = 0;
        this.emAtendimento = false;
        this.nivelUrgencia = "Baixa"; // Valor por defeito, será alterado na triagem
    }

    // ================== GESTÃO DE SINTOMAS ==================

    /**
     * Adiciona um objeto Sintoma ao paciente
     */
    public void adicionarSintoma(Sintoma s) {
        if (totalSintomas < sintomas.length) {
            sintomas[totalSintomas++] = s;
        } else {
            System.out.println("Erro: Limite de sintomas atingido para este paciente.");
        }
    }

    /**
     * Verifica se o paciente tem um sintoma pelo nome
     */
    public boolean temSintoma(String nomeSintoma) {
        for (int i = 0; i < totalSintomas; i++) {
            if (sintomas[i].getNome().equalsIgnoreCase(nomeSintoma)) {
                return true;
            }
        }
        return false;
    }

    // ================== GETTERS ==================

    public String getNome() {
        return nome;
    }

    public Sintoma[] getSintomas() { // Corrigido: Retorna Sintoma[], não String[]
        return sintomas;
    }

    public int getTotalSintomas() {
        return totalSintomas;
    }

    public String getNivelUrgencia() {
        return nivelUrgencia;
    }

    public String getEspecialidadeDesejada() {
        return especialidadeDesejada;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public boolean isEmAtendimento() {
        return emAtendimento;
    }

    // ================== SETTERS ==================

    public void setEmAtendimento(boolean emAtendimento) {
        this.emAtendimento = emAtendimento;
    }

    public void setNivelUrgencia(String nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }

    public void setEspecialidadeDesejada(String especialidade) { // Faltava este setter
        this.especialidadeDesejada = especialidade;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

    public void incrementarTempoEspera() {
        tempoEspera++;
    }

    @Override
    public String toString() {
        return nome + " | Urgência: " + nivelUrgencia + " | Esp: " +
                (especialidadeDesejada != null ? especialidadeDesejada : "N/D") +
                " | Sintomas: " + totalSintomas;
    }
}