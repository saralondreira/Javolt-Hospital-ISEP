import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GerirFicheiro {

    /**
     * Método auxiliar para contar quantas linhas tem o ficheiro.
     * Necessário para definir o tamanho do vetor, já que não podemos usar ArrayList.
     */

    public static int contarLinhas(String nomeFicheiro) {
        int linhas = 0;
        try {
            FileReader fr = new FileReader(nomeFicheiro);
            BufferedReader br = new BufferedReader(fr);

            while (br.readLine() != null) {
                linhas++;
            }
            br.close();
        } catch (IOException e) {
            return 0;
        }
        return linhas;
    }


    /**
     * Lê o ficheiro e devolve uma MATRIZ (Tabela), onde:
     * [i] = linha
     * [j] = coluna (dado separado por ;)
     */

    public static String[][] lerFicheiro(String nomeFicheiro) {

        // 1. Descobrir o tamanho necessário
        int totalLinhas = contarLinhas(nomeFicheiro);

        // Se o ficheiro estiver vazio ou der erro, devolve array vazio
        if (totalLinhas == 0) {
            return new String[0][0];
        }

        // Criamos uma matriz: Linhas vs Colunas
        String[][] tabelaDados = new String[totalLinhas][];

        try {

            // 3. Preparar a leitura
            FileReader fr = new FileReader(nomeFicheiro);
            BufferedReader br = new BufferedReader(fr);

            String linha;
            int i = 0;

            // 4. Preencher o array
            while ((linha = br.readLine()) != null) {
                // AQUI fazemos logo o split
                // Guardamos o array resultante na posição i da matriz
                tabelaDados[i] = linha.split(";");
                i++;
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Erro de leitura: " + e.getMessage());
        }
        return tabelaDados;
    }
}
