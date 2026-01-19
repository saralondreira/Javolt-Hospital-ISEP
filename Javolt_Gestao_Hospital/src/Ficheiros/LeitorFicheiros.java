package Ficheiros;

import Entidades.Medico;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorFicheiros extends InputsAuxiliares.FicheiroUtils {

    public LeitorFicheiros(String separador) {
        super(separador);
    }
    // ================= ESPECIALIDADES =================
    public Especialidade[] lerEspecialidades(String caminho){
        int total = contarLinhas(caminho);
        Especialidade[] lista = new Especialidade[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

            String linha;
            int i = 0;

            while ((linha = br.readLine=()) != null){
                String [] partes = linha.split(separador);

                String codigo = partes[0];
                String nome = partes[1];

                lista[i] = new Especialidade(codigo, nome);
                i++;
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler especialidades: " + e.getMessage());
        }

        return lista;
    }
    // ================= MÉDICOS =================
    public Medico[] lerMedicos(String caminho) {

        int total = contarLinhas(caminho);
        Medico[] lista = new Medico[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(separador);

                String nome = partes[0];
                String codEspecialidade = partes[1];
                int horaEntrada = Integer.parseInt(partes[2]);
                int horaSaida = Integer.parseInt(partes[3]);
                double valorHora = Double.parseDouble(partes[4]);

                lista[i] = new Medico(
                        nome,
                        codEspecialidade,
                        horaEntrada,
                        horaSaida,
                        valorHora
                );
                i++;
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler médicos: " + e.getMessage());
        }

        return lista;
    }

    // ================= SINTOMAS =================
    public Sintoma[] lerSintomas(String caminho) {

        int total = contarLinhas(caminho);
        Sintoma[] lista = new Sintoma[total];

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

            String linha;
            int i = 0;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(separador);

                String nome = partes[0];
                String urgencia = partes[1];

                String[] especialidades = new String[partes.length - 2];
                for (int j = 2; j < partes.length; j++) {
                    especialidades[j - 2] = partes[j];
                }

                lista[i] = new Sintoma(nome, urgencia, especialidades);
                i++;
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler sintomas: " + e.getMessage());
        }

        return lista;
    }
}


