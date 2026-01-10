import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

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
            } catch (IOException e){
            System.out.println("Erro ao ler ficheiro para contagem: " + e.getMessage());
            return 0;
        }
        return linhas;
    }

    /**
     * Lê um ficheiro de texto e devolve um array de Strings,
     * onde cada posição do array é uma linha do ficheiro.
     */

    public static String[] lerFicheiro(String nomeFicheiro) {

        // 1. Descobrir o tamanho necessário
        int totalLinhas = contarLinhas(nomeFicheiro);

        // Se o ficheiro estiver vazio ou der erro, devolve array vazio
        if (totalLinhas == 0) {
            return new String[0];
        }

        // 2. Criar o array com o tamanho exato
        String[] dados = new String[totalLinhas];

        try {

            // 3. Preparar a leitura
            FileReader fr = new FileReader(nomeFicheiro);
            BufferedReader br = new BufferedReader(fr);

            String linha;
            int i = 0;

            // 4. Preencher o array
            while ((linha = br.readLine()) != null) {
                dados[i] = linha;
                i++;
            }

            br.close();

        }catch (FileNotFoundException e){
            System.out.println("Ficheiro não encontrado: " + nomeFicheiro);
        }catch (IOException e) {
            System.out.println("Erro de leitura: " + e.getMessage());
        }
        return dados;
    }
}
