import java.util.Scanner;

public class InputsAuxiliares {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Lê a opção introduzida do menu
     * @param msg Mensagem a mostrar
     * @return Opção escolhida
     */
    public static int lerOpcaoMenu(String msg) {
        System.out.println(msg);
        while (!scanner.hasNextLine()) {
            System.out.println("Valor inválido. Tente novamente.");
            scanner.next(); //Usado para limpar o input inválido do utilizador
            System.out.println(msg);
        }

        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    /**
     * Lê texto do utilizador.
     * @param msg mensagem a mostrar
     * @return texto inserido
     */
    public static String lerTexto(String msg){
        System.out.println(msg);
        return  scanner.nextLine().trim();
    }

    /**
     * Lê texto com validação de não vazio.
     * @param msg mensagem a mostrar
     * @return texto não vazio
     */
    public static String lerTextoNaoVazio(String msg){
        while (true) {
            System.out.println(msg);
            String input = scanner.nextLine().trim();

            if(input.isEmpty()){
                System.out.println("Erro: O campo não pode estar vazio.");
                continue;
            }
            return input;
        }
    }

    /**
     * Lê texto com validação (usa "0" para cancelar).
     * @param msg mensagem a mostrar
     * @return texto ou null se cancelado
     */
    public static String lerTexto(String msg){
        System.out.println("(Prima 0 para cancelar)");

        while (true) {
            System.out.println(msg);
            String input = scanner.nextLine().trim();

            //Cancelamento com "0"
            if(input.equals("0")){
                System.out.println("Deseja cancelar a operação? (S/N): ");
                String confirmação = scanner.nextLine();

                if (confirmação.equalsIgnoreCase("S")) {
                    return null;
                }
                System.out.println("Operação continuada.");
            }

            if (input.isEmpty()) {
                System.out.println("Erro: O campo não pode estar vazio.");
                continue;
            }

            return input;
        }
    }

    /**
     * Lê inteiro com validação (usa "0" para cancelar).
     * @param msg mensagem a mostrar
     * @return inteiro válido
     */
    public static String lerInteiro(String msg){
        while (true) {
            System.out.println(msg);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e){
                System.out.println("Erro: Valor inválido. Insira um número inteiro.");
            }
        }
    }

    

}
