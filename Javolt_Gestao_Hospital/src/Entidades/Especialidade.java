package Entidades;

public class Especialidade {
    private String codigo;
    private String nome;

    public Especialidade(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }

    @Override
    public String toString() { return codigo + " - " + nome; }
}