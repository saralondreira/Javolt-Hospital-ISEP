package Entidades;

public class Medico extends Pessoa {
    private String nome;
    private String especialidade;
    private int horaEntrada;
    private int horaSaida;
    private double valorHora;
    private boolean disponivel = true;
    private double horasTrabalhadas = 0;
    private int horasTrabalhoContinuo = 0;
    private int tempoDescandoRestante = 0;

    public Medico() {super();}

    public Medico(String nome, String especialidade, int horaEntrada, int horaSaida, double valorHora) {
        super(nome);
        this.nome = nome;
        this.especialidade = especialidade;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.valorHora = valorHora;
    }

    // GETTERS
    public String getEspecialidade() { return especialidade; }
    public int getHoraEntrada() { return horaEntrada; }
    public int getHoraSaida() { return horaSaida; }
    public double getValorHora() { return valorHora; }
    public boolean isDisponivel() { return disponivel; }
    public double getHorasTrabalhadas() { return horasTrabalhadas; }
    public int getHorasTrabalhoContinuo() { return horasTrabalhoContinuo; }
    public int getTempoDescandoRestante() { return tempoDescandoRestante; }

    // SETTERS
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public void setHoraEntrada(int horaEntrada) { this.horaEntrada = horaEntrada; }
    public void setHoraSaida(int horaSaida) { this.horaSaida = horaSaida; }
    public void setValorHora(double valorHora) { this.valorHora = valorHora; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
    public void setHorasTrabalhadas(double horasTrabalhadas) { this.horasTrabalhadas = horasTrabalhadas; }
    public void setHorasTrabalhoContinuo(int horas) { this.horasTrabalhoContinuo = horas; }
    public void setTempoDescandoRestante(int tempo) { this.tempoDescandoRestante = tempo; }

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

    public boolean estaEmServico(int unidadeTempo) {
        return disponivel &&
                unidadeTempo >= horaEntrada &&
                unidadeTempo < horaSaida;
    }

    public void definirDescanso (int unidades) {
        this.tempoDescandoRestante = unidades;
        this.disponivel = false;
    }

    public void decrementarDescanso(){
        if (tempoDescandoRestante > 0) {
            tempoDescandoRestante--;
            if(tempoDescandoRestante == 0){
                disponivel = true;
            }
        }
    }

    public boolean estaDeFolga() {
        return tempoDescandoRestante > 0;
    }

    @Override
    public String toString() {
        return "Médico: " + getNome() +
                " | Esp: " + especialidade +
                " | Horário: " + horaEntrada + "h - " + horaSaida + "h" +
                " | Disponível: " + (disponivel ? "Sim" : "Não") +
                " | Horas trabalhadas: " + horasTrabalhadas;
    }
}