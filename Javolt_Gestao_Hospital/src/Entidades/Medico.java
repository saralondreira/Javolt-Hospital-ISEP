package Entidades;

public class Medico {
    private String nome;
    private String especialidade;
    private int horaEntrada;
    private int horaSaida;
    private double valorHora;
    private boolean disponivel = true;
    private double horasTrabalhadas = 0;
    private int horasTrabalhoContinuo = 0; // Para controlo de descanso

    public Medico() {}

    public Medico(String nome, String especialidade, int horaEntrada, int horaSaida, double valorHora) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.valorHora = valorHora;
    }

    // GETTERS
    public String getNome() { return nome; }
    public String getEspecialidade() { return especialidade; }
    public int getHoraEntrada() { return horaEntrada; }
    public int getHoraSaida() { return horaSaida; }
    public double getValorHora() { return valorHora; }
    public boolean isDisponivel() { return disponivel; }
    public double getHorasTrabalhadas() { return horasTrabalhadas; }
    public int getHorasTrabalhoContinuo() { return horasTrabalhoContinuo; }

    // SETTERS
    public void setNome(String nome) { this.nome = nome; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public void setHoraEntrada(int horaEntrada) { this.horaEntrada = horaEntrada; }
    public void setHoraSaida(int horaSaida) { this.horaSaida = horaSaida; }
    public void setValorHora(double valorHora) { this.valorHora = valorHora; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
    public void setHorasTrabalhadas(double horasTrabalhadas) { this.horasTrabalhadas = horasTrabalhadas; }
    public void setHorasTrabalhoContinuo(int horas) { this.horasTrabalhoContinuo = horas; }

    // MÉTODOS DE NEGÓCIO
    public void adicionarHorasTrabalhadas(double horas) {
        this.horasTrabalhadas += horas;
        this.horasTrabalhoContinuo += horas;
    }

    public void resetarHorasContinuo() {
        this.horasTrabalhoContinuo = 0;
    }

    public boolean precisaDescanso(int limiteHoras) {
        return horasTrabalhoContinuo >= limiteHoras;
    }

    // Verifica se o médico está em serviço na unidade de tempo atual
    public boolean estaEmServico(int unidadeTempo) {
        return disponivel &&
                unidadeTempo >= horaEntrada &&
                unidadeTempo < horaSaida;
    }

    @Override
    public String toString() {
        return "Médico: " + nome +
                " | Esp: " + especialidade +
                " | Horário: " + horaEntrada + "h - " + horaSaida + "h" +
                " | Disponível: " + (disponivel ? "Sim" : "Não") +
                " | Horas trabalhadas: " + horasTrabalhadas;
    }
}