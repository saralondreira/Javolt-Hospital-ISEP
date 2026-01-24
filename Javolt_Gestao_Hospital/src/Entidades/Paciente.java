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
        this.especialidadeDesejada = null;
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
    public String getNome() {
        return nome;
    }

    public Sintoma[] getSintomas() {
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

    // SETTERS
    public void setEmAtendimento(boolean emAtendimento) {
        this.emAtendimento = emAtendimento;
    }

    public void setNivelUrgencia(String nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }

    public void setEspecialidadeDesejada(String especialidade) {
        this.especialidadeDesejada = especialidade;
    }

    public void setTempoEspera(int tempoEspera) {
        this.tempoEspera = tempoEspera;
    }

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
            case 3:
                this.nivelUrgencia = "Urgente";
                break;
            case 2:
                this.nivelUrgencia = "Média";
                break;
            default:
                this.nivelUrgencia = "Baixa";
                break;
        }
    }

    @Override
    public String toString() {
        return String.format("%-20s %-10s %-20s %-10s Tempo: %d",
                nome, nivelUrgencia,
                especialidadeDesejada,
                (emAtendimento ? "Em Atend." : "Espera"),
                tempoEspera);
    }

    //calcular nivel de urgencia

    /*public String calcularNivelUrgencia() {
        boolean temMedia = false;

        for (int i = 0; i < totalSintomas; i++) {
            String urg = sintomas[i].getNivelUrgencia();

            if (urg.equalsIgnoreCase("Vermelha")) {
                nivelUrgencia = "Urgente";
                return nivelUrgencia;
            }
            if (urg.equalsIgnoreCase("Laranja")) {
                temMedia = true;
            }
        }

        nivelUrgencia = temMedia ? "Média" : "Baixa";
        return nivelUrgencia;
    }*/


    // Urgencia passa a numeros no GestaoTurnos
    public void setNivelUrgencia(int nivelNumerico) {
        switch (nivelNumerico) {
            case 3:
                this.nivelUrgencia = "Urgente";
                break;
            case 2:
                this.nivelUrgencia = "Média";
                break;
            default:
                this.nivelUrgencia = "Baixa";
                break;
        }
    }

    // Calcula urgência e especialidade com base nos sintomas (TRIAGEM)
    public void calcularUrgenciaEEspecialidade() {

        boolean temMedia = false;

        // 1) PRIORIDADE ABSOLUTA: sintomas vermelhos
        for (int i = 0; i < totalSintomas; i++) {
            Sintoma s = sintomas[i];

            if (s.getNivelUrgencia().equalsIgnoreCase("Vermelha")) {
                this.nivelUrgencia = "Urgente";

                // se o sintoma tiver especialidade associada
                if (s.getEspecialidade() != null) {
                    this.especialidadeDesejada = s.getEspecialidade().getNome();
                }

                return; // ignora os restantes sintomas
            }

            if (s.getNivelUrgencia().equalsIgnoreCase("Laranja")) {
                temMedia = true;
            }
        }

        // 2) Definir urgência se não houve vermelhos
        if (temMedia) {
            this.nivelUrgencia = "Média";
        } else {
            this.nivelUrgencia = "Baixa";
        }

        // 3) Calcular especialidade por contagem (arrays apenas)
        String[] especialidades = new String[totalSintomas];
        int[] contagem = new int[totalSintomas];
        int total = 0;

        for (int i = 0; i < totalSintomas; i++) {
            Sintoma s = sintomas[i];
            if (s.getEspecialidade() == null) continue;

            int pos = -1;

            for (int j = 0; j < total; j++) {
                if (especialidades[j].equalsIgnoreCase(s.getEspecialidade().getNome())) {

                    pos = j;
                    break;
                }
            }

            if (pos == -1) {
                especialidades[total] = s.getEspecialidade().getNome();
                contagem[total] = 1;
                total++;
            } else {
                contagem[pos]++;
            }
        }

        int max = 0;
        for (int i = 0; i < total; i++) {
            if (contagem[i] > max) {
                max = contagem[i];
                this.especialidadeDesejada = especialidades[i];
            }
        }
    }
}
