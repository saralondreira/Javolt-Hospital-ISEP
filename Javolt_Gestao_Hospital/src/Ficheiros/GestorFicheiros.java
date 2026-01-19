package Ficheiros;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GestorFicheiros {

    private String separador;

    public GestorFicheiros(String separador) {
        this.separador = separador;
    }

    // ================== CONTAR LINHAS ==================
    public void escreverLog(String caminho, String texto) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho, true))) {
            bw.write(texto);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao escrever log: " + e.getMessage());
        }
    }
}