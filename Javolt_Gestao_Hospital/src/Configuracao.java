public class Configuracao {

    private String caminhoFicheiros;
    private char separador;
    private int tempoConsultaBaixa;
    private int tempoConsultaMedia;
    private int tempoConsultaUrgente;
    private int horasTrabalhoParaDescanso;
    private int unidadesDescanso;
    private int tempoBaixaParaMedia;
    private int tempoMediaParaUrgente;
    private int tempoUrgenteParaSaida;
    private String password;

    public static final char SEPARADOR_DEFAULT = ';';
    public static final String CAMINHO_DEFAULT = "dados/";
    public static final String PASSWORD_DEFAULT = "javolt";

    public Configuracao() {
    }

    public Configuracao(String caminhoFicheiros, char separador, int tempoConsultaBaixa, int tempoConsultaMedia, int tempoConsultaUrgente, int horasTrabalhoParaDescanso, int unidadesDescanso, int tempoBaixaParaMedia, int tempoMediaParaUrgente, int tempoUrgenteParaSaida, String password) {
        this.caminhoFicheiros = caminhoFicheiros;
        this.separador = separador;
        this.tempoConsultaBaixa = tempoConsultaBaixa;
        this.tempoConsultaMedia = tempoConsultaMedia;
        this.tempoConsultaUrgente = tempoConsultaUrgente;
        this.horasTrabalhoParaDescanso = horasTrabalhoParaDescanso;
        this.unidadesDescanso = unidadesDescanso;
        this.tempoBaixaParaMedia = tempoBaixaParaMedia;
        this.tempoMediaParaUrgente = tempoMediaParaUrgente;
        this.tempoUrgenteParaSaida = tempoUrgenteParaSaida;
        this.password = password;
    }

    public String getCaminhoFicheiros() {
        return caminhoFicheiros;
    }

    public void setCaminhoFicheiros(String caminhoFicheiros) {
        if (caminhoFicheiros == null || caminhoFicheiros.trim().isEmpty()) {
            throw new IllegalArgumentException("Caminho não pode ser vazio.");
        }
        this.caminhoFicheiros = caminhoFicheiros;
    }

    public char getSeparador() {
        return separador;
    }

    public void setSeparador(char separador) {
        this.separador = separador;
    }

    public int getTempoConsultaBaixa() {
        return tempoConsultaBaixa;
    }

    public void setTempoConsultaBaixa(int tempoConsultaBaixa) {
        if (tempoConsultaBaixa <= 0) {
            throw new RuntimeException("Tempo deve ser positivo");
        }
        this.tempoConsultaBaixa = tempoConsultaBaixa;
    }

    public int getTempoConsultaMedia() {
        return tempoConsultaMedia;
    }

    public void setTempoConsultaMedia(int tempoConsultaMedia) {
        if (tempoConsultaMedia <= 0) {
            throw new RuntimeException("Tempo deve ser positivo");
        }
        this.tempoConsultaMedia = tempoConsultaMedia;
    }

    public int getTempoConsultaUrgente() {
        return tempoConsultaUrgente;
    }

    public void setTempoConsultaUrgente(int tempoConsultaUrgente) {
        if (tempoConsultaUrgente <= 0) {
            throw new RuntimeException("Tempo deve ser positivo");
        }
        this.tempoConsultaUrgente = tempoConsultaUrgente;
    }

    public int getHorasTrabalhoParaDescanso() {
        return horasTrabalhoParaDescanso;
    }

    public void setHorasTrabalhoParaDescanso(int horasTrabalhoParaDescanso) {
        if (horasTrabalhoParaDescanso <= 0) {
            throw new RuntimeException("Horas devem ser positivo");
        }
        this.horasTrabalhoParaDescanso = horasTrabalhoParaDescanso;
    }

    public int getUnidadesDescanso() {
        return unidadesDescanso;
    }

    public void setUnidadesDescanso(int unidadesDescanso) {
        if (unidadesDescanso <= 0) {
            throw new RuntimeException("Horas devem ser positivo");
        }
        this.unidadesDescanso = unidadesDescanso;
    }

    public int getTempoBaixaParaMedia() {
        return tempoBaixaParaMedia;
    }

    public void setTempoBaixaParaMedia(int tempoBaixaParaMedia) {
        if (tempoBaixaParaMedia <= 0) {
            throw new RuntimeException("Tempo deve ser positivo");
        }
        this.tempoBaixaParaMedia = tempoBaixaParaMedia;
    }

    public int getTempoMediaParaUrgente() {
        return tempoMediaParaUrgente;
    }

    public void setTempoMediaParaUrgente(int tempoMediaParaUrgente) {
        if (tempoMediaParaUrgente <= 0) {
            throw new RuntimeException("Tempo deve ser positivo");
        }
        this.tempoMediaParaUrgente = tempoMediaParaUrgente;
    }

    public int getTempoUrgenteParaSaida() {
        return tempoUrgenteParaSaida;
    }

    public void setTempoUrgenteParaSaida(int tempoUrgenteParaSaida) {
        if (tempoUrgenteParaSaida <= 0) {
            throw new RuntimeException("Tempo deve ser positivo");
        }
        this.tempoUrgenteParaSaida = tempoUrgenteParaSaida;
    }

    public boolean verificarPassword(String tentativa) {
        return this.password.equals(tentativa);
    }

    public boolean alterarPassword(String passwordAntiga, String passwordNova) {
        if (verificarPassword(passwordAntiga)) {
            this.password = passwordNova;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Configurações do Sistema:\n" +
                "Caminho dos ficheiros: " + caminhoFicheiros + "\n" +
                "Separador: " + separador + "\n" +
                "Tempo consulta Baixa: " + tempoConsultaBaixa + " UT\n" +
                "Tempo consulta Média: " + tempoConsultaMedia + " UT\n" +
                "Tempo consulta Urgente: " + tempoConsultaUrgente + " UT\n" +
                "Descanso: " + unidadesDescanso + " UT a cada " + horasTrabalhoParaDescanso + " UT trabalho\n" +
                "Baixa -> Média: " + tempoBaixaParaMedia + " UT\n" +
                "Média -> Urgente" + tempoMediaParaUrgente + " UT\n" +
                "Urgente -> Saída" + tempoUrgenteParaSaida + " UT\n";
    }
}


