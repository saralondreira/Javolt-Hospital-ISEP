package Ficheiros;

import Entidades.Medico;
import Entidades.Especialidade;
import Entidades.Sintoma;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorFicheiros {
    private String separador;

    public LeitorFicheiros(String separador) {
        this.separador = separador;
    }

    private int contarLinhas(String caminho) {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            while (br.readLine() != null) total++;
        } catch (IOException e) {
            System.out.println("⚠ Erro ao contar linhas: " + e.getMessage());
        }
        return total;
    }

    public Especialidade[] lerEspecialidades(String caminho) {
        int total = contarLinhas(caminho);
        Especialidade[] lista = new Especialidade[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(separador);
                if (partes.length == 2) {
                    lista[i++] = new Especialidade(partes[0], partes[1]);
                }
            }
        } catch (IOException e) {
            System.out.println(" Erro ao ler especialidades: " + e.getMessage());
        }
        return lista;
    }

    public Medico[] lerMedicos(String caminho, Especialidade[] especialidades, int totalEspecialidades) {
        int total = contarLinhas(caminho);
        Medico[] lista = new Medico[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(separador);
                if (partes.length == 5) {
                    lista[i++] = new Medico(
                            partes[0], // nome
                            partes[1], // especialidade
                            Integer.parseInt(partes[2]), // entrada
                            Integer.parseInt(partes[3]), // saida
                            Double.parseDouble(partes[4]) // valor hora
                    );
                }
            }
        } catch (IOException e) {
            System.out.println(" Erro ao ler médicos: " + e.getMessage());
        }
        return lista;
    }

    public Sintoma[] lerSintomas(String caminho, Especialidade[] especialidadesSistema) {
        int total = contarLinhas(caminho);
        Sintoma[] lista = new Sintoma[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(separador);
                if (partes.length >= 2) {
                    String nome = partes[0];
                    String urgencia = partes[1];
                    Especialidade especialidade = null;

                    if (partes.length == 3 && especialidadesSistema != null) {
                        String codEsp = partes[2];
                        for (Especialidade e : especialidadesSistema) {
                            if (e != null && e.getCodigo().equalsIgnoreCase(codEsp)) {
                                especialidade = e;
                                break;
                            }
                        }
                    }

                    lista[i++] = new Sintoma(nome, urgencia, especialidade);
                }
            }
        } catch (IOException e) {
            System.out.println(" Erro ao ler sintomas: " + e.getMessage());
        }
        return lista;
    }
}