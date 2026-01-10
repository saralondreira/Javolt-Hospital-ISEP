/**
 * Representa um Médico
 * @author Grupo 1 - Javolt (Levi, Sara, Leonardo, Micael)
 * @version 1
 * @since 2025-12-17
 */
public class Medico {
    private String nome; // Nome do Mécico
    private String especialidade; // Especialidade, pode ser alterado para uma classe Especialidade depois
    private int horaEntrada; // Hora de Entrada do Médico
    private int horaSaida; // Hora de Saída do Médico
    private double valorHora; // Valor hora

    /**
     * Costrutor Vazio
     */
    public Medico() {
    }

    /**
     *
     * @param nome Dado Nome do Médico
     * @param especialidade Dado Especialidade
     * @param horaEntrada Dado Hora de Entrada
     * @param horaSaida Dado Hora de Saída
     * @param valorHora Dado Valor hora
     */
    public Medico(String nome, String especialidade, int horaEntrada, int horaSaida, double valorHora) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.valorHora = valorHora;
    }

    /**
     * Obtém o Nome do Médico
     * @return Retorna o Nome do Médico
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define um Novo Nome
     * @param nome Atribui um Novo Nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a Especialidade
     * @return Retorna a Especialidade
     */
    public String getEspecialidade() {
        return especialidade;
    }

    /**
     * Define a Especialidade
     * @param especialidade Atribui a Especialidade
     */
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    /**
     * Obtém a Hora de Entrada
     * @return Retorna a Hora de Entrada
     */
    public int getHoraEntrada() {
        return horaEntrada;
    }

    /**
     * Define a Hora de Entrada
     * @param horaEntrada Atribui a Hora de Entrada
     */
    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    /**
     * Obtém a Hora de Saída
      * @return Retorna a Hora de Saída
     */
    public int getHoraSaida() {
        return horaSaida;
    }

    /**
     * Define a Hora de Saída
     * @param horaSaida Atribui a Hora de Saída
     */
    public void setHoraSaida(int horaSaida) {
        this.horaSaida = horaSaida;
    }

    /**
     * Obtém o Valor Hora
     * @return Retorna o Valor Hora
     */
    public double getValorHora() {
        return valorHora;
    }

    /**
     * Define o Valor Hora
     * @param valorHora Atribui o Valor Hora
     */
    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }

    /**
     * Metodo para Mostrar a Informação do Médico
     * @return
     */
    @Override
    public String toString() {
        return "Médico: " + nome + " | Esp: " + especialidade +
                " | Horário: " + horaEntrada + "h - " + horaSaida + "h";
    }
}
