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

    public static final char SEPARADOR_DEFAULT = ';';
    public static final String CAMINHO_DEFAULT = "dados/";
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

    // Getters
    public String getCaminhoFicheiros() { return caminhoFicheiros; }
    public char getSeparador() { return separador; }
    public int getTempoConsultaBaixa() { return tempoConsultaBaixa; }
    public int getTempoConsultaMedia() { return tempoConsultaMedia; }
    public int getTempoConsultaUrgente() { return tempoConsultaUrgente; }
    public int getTempoBaixaParaMedia() { return tempoBaixaParaMedia; }
    public int getTempoMediaParaUrgente() { return tempoMediaParaUrgente; }

    // Setters
    public void setCaminhoFicheiros(String caminho) { this.caminhoFicheiros = caminho; }
    public void setSeparador(char separador) { this.separador = separador; }
    public void setTempoConsultaBaixa(int t) { this.tempoConsultaBaixa = t; }
    public void setTempoConsultaMedia(int t) { this.tempoConsultaMedia = t; }
    public void setTempoConsultaUrgente(int t) { this.tempoConsultaUrgente = t; }

    // Password
    public boolean verificarPassword(String tentativa) {
        return this.password != null && this.password.equals(tentativa);
    }
}