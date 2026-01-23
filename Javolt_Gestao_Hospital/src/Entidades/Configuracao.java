package Entidades;

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

    // CONSTANTES DO ENUNCIADO
    public static final char SEPARADOR_DEFAULT = ';';
    public static final String CAMINHO_DEFAULT = "Recursos/";
    public static final String PASSWORD_DEFAULT = "javolt";
    public static final int TEMPO_CONSULTA_BAIXA_DEFAULT = 1;
    public static final int TEMPO_CONSULTA_MEDIA_DEFAULT = 2;
    public static final int TEMPO_CONSULTA_URGENTE_DEFAULT = 3;
    public static final int HORAS_TRABALHO_DESCANSO_DEFAULT = 5;
    public static final int UNIDADES_DESCANSO_DEFAULT = 1;
    public static final int TEMPO_BAIXA_PARA_MEDIA_DEFAULT = 3;
    public static final int TEMPO_MEDIA_PARA_URGENTE_DEFAULT = 3;
    public static final int TEMPO_URGENTE_PARA_SAIDA_DEFAULT = 2;

    public Configuracao() {
        this.caminhoFicheiros = CAMINHO_DEFAULT;
        this.separador = SEPARADOR_DEFAULT;
        this.password = PASSWORD_DEFAULT;
        this.tempoConsultaBaixa = TEMPO_CONSULTA_BAIXA_DEFAULT;
        this.tempoConsultaMedia = TEMPO_CONSULTA_MEDIA_DEFAULT;
        this.tempoConsultaUrgente = TEMPO_CONSULTA_URGENTE_DEFAULT;
        this.horasTrabalhoParaDescanso = HORAS_TRABALHO_DESCANSO_DEFAULT;
        this.unidadesDescanso = UNIDADES_DESCANSO_DEFAULT;
        this.tempoBaixaParaMedia = TEMPO_BAIXA_PARA_MEDIA_DEFAULT;
        this.tempoMediaParaUrgente = TEMPO_MEDIA_PARA_URGENTE_DEFAULT;
        this.tempoUrgenteParaSaida = TEMPO_URGENTE_PARA_SAIDA_DEFAULT;
    }

    // GETTERS
    public String getCaminhoFicheiros() { return caminhoFicheiros; }
    public char getSeparador() { return separador; }
    public int getTempoConsultaBaixa() { return tempoConsultaBaixa; }
    public int getTempoConsultaMedia() { return tempoConsultaMedia; }
    public int getTempoConsultaUrgente() { return tempoConsultaUrgente; }
    public int getHorasTrabalhoParaDescanso() { return horasTrabalhoParaDescanso; }
    public int getUnidadesDescanso() { return unidadesDescanso; }
    public int getTempoBaixaParaMedia() { return tempoBaixaParaMedia; }
    public int getTempoMediaParaUrgente() { return tempoMediaParaUrgente; }
    public int getTempoUrgenteParaSaida() { return tempoUrgenteParaSaida; }

    // SETTERS COM VALIDAÇÃO (do colega)
    public void setCaminhoFicheiros(String caminhoFicheiros) {
        if (caminhoFicheiros == null || caminhoFicheiros.trim().isEmpty()) {
            throw new IllegalArgumentException("Caminho não pode ser vazio.");
        }
        this.caminhoFicheiros = caminhoFicheiros;
    }

    public void setSeparador(char separador) {
        this.separador = separador;
    }

    public void setTempoConsultaBaixa(int tempoConsultaBaixa) {
        if (tempoConsultaBaixa <= 0) {
            throw new IllegalArgumentException("Tempo deve ser positivo");
        }
        this.tempoConsultaBaixa = tempoConsultaBaixa;
    }

    public void setTempoConsultaMedia(int tempoConsultaMedia) {
        if (tempoConsultaMedia <= 0) {
            throw new IllegalArgumentException("Tempo deve ser positivo");
        }
        this.tempoConsultaMedia = tempoConsultaMedia;
    }

    public void setTempoConsultaUrgente(int tempoConsultaUrgente) {
        if (tempoConsultaUrgente <= 0) {
            throw new IllegalArgumentException("Tempo deve ser positivo");
        }
        this.tempoConsultaUrgente = tempoConsultaUrgente;
    }

    // PASSWORD (do colega - mais completo)
    public boolean verificarPassword(String tentativa) {
        return this.password != null && this.password.equals(tentativa);
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
                "Média -> Urgente: " + tempoMediaParaUrgente + " UT\n" +
                "Urgente -> Saída: " + tempoUrgenteParaSaida + " UT\n";
    }
}