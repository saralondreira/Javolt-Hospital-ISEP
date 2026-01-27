package Entidades;

    public abstract class Pessoa {

        // Variável de instância privada (Encapsulamento)
        private String nome;

        // Construtor vazio
        public Pessoa() {
        }

        // Construtor com parâmetros
        public Pessoa(String nome) {
            this.nome = nome;
        }

        // Métodos de acesso (Getters e Setters)
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        // Método toString genérico
        @Override
        public String toString() {
            return "Nome: " + nome;
        }
    }

