package UI;

import Entidades.Especialidade;
import Entidades.Sintoma;
import Servicos.GestaoHospital;

public class SubSintoma {
    public void menuSintomas(GestaoHospital gestaoHospital) {
        int opcao;
        do {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("   GESTÃO DE SINTOMAS");
            System.out.println("=".repeat(40));
            System.out.println("1 - Adicionar sintoma");
            System.out.println("2 - Listar sintomas");
            System.out.println("3 - Atualizar sintoma");
            System.out.println("4 - Remover sintoma");
            System.out.println("0 - Voltar");
            System.out.println("-".repeat(40));

            opcao = InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);

            switch (opcao) {
                case 1 -> adicionarSintoma(gestaoHospital);
                case 2 -> gestaoHospital.listarSintomas();
                case 3 -> atualizarSintoma(gestaoHospital);
                case 4 -> removerSintoma(gestaoHospital);
                case 0 -> System.out.println("← A voltar...");
                default -> System.out.println("Opção inválida.");
            }

            if (opcao != 0) InputsAuxiliares.pausar();

        } while (opcao != 0);
    }

    private void adicionarSintoma(GestaoHospital gestaoHospital) {
        System.out.println("\n ADICIONAR NOVO SINTOMA");
        System.out.println("-".repeat(30));

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do sintoma: ");

        System.out.println("\nNíveis de urgência:");
        System.out.println("1 - Verde (Baixa)");
        System.out.println("2 - Laranja (Média)");
        System.out.println("3 - Vermelho (Urgente)");

        int urgenciaOpcao = InputsAuxiliares.lerInteiroIntervalo("Nível de urgência (1-3): ", 1, 3);
        String urgenciaTexto = switch (urgenciaOpcao) {
            case 1 -> "Verde";
            case 2 -> "Laranja";
            case 3 -> "Vermelho";
            default -> "Verde";
        };

        System.out.println("\nEspecialidade associada (ENTER se não existir):");
        String codigoEsp = InputsAuxiliares.lerTexto("Código da especialidade: ");

        Especialidade esp = null;
        if (codigoEsp != null && !codigoEsp.isEmpty()) {
            esp = gestaoHospital.procurarEspecialidade(codigoEsp);
            if (esp == null) {
                InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
                return;
            }
        }

        Sintoma s = new Sintoma(nome, urgenciaTexto, esp);

        if (gestaoHospital.adicionarSintoma(s)) {
            InputsAuxiliares.imprimirSucesso("Sintoma adicionado com sucesso!");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao adicionar sintoma (nome duplicado ou capacidade máxima?).");
        }
    }

    private void atualizarSintoma(GestaoHospital gestaoHospital) {
        System.out.println("\n  ATUALIZAR SINTOMA");
        System.out.println("-".repeat(30));

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do sintoma a atualizar: ");
        Sintoma existente = gestaoHospital.procurarSintoma(nome);

        if (existente == null) {
            InputsAuxiliares.imprimirErro("Sintoma não encontrado.");
            return;
        }

        System.out.println("\nSintoma encontrado: " + existente.getNome());
        System.out.println("Deixe em branco para manter valor atual.");

        String novoNome = InputsAuxiliares.lerTexto("Novo nome [" + existente.getNome() + "]: ");
        if (novoNome.isEmpty()) novoNome = existente.getNome();

        System.out.println("\nNíveis de urgência:");
        System.out.println("1 - Verde (Baixa) [" + (existente.getNivelUrgencia().equals("Verde") ? "✓" : " ") + "]");
        System.out.println("2 - Laranja (Média) [" + (existente.getNivelUrgencia().equals("Laranja") ? "✓" : " ") + "]");
        System.out.println("3 - Vermelho (Urgente) [" + (existente.getNivelUrgencia().equals("Vermelho") ? "✓" : " ") + "]");

        String inputUrgencia = InputsAuxiliares.lerTexto("Novo nível (1-3, ENTER para manter): ");
        String urgenciaTexto;

        if (inputUrgencia.isEmpty()) {
            urgenciaTexto = existente.getNivelUrgencia();
        } else {
            int urgenciaOpcao = Integer.parseInt(inputUrgencia);
            urgenciaTexto = switch (urgenciaOpcao) {
                case 1 -> "Verde";
                case 2 -> "Laranja";
                case 3 -> "Vermelho";
                default -> existente.getNivelUrgencia();
            };
        }

        System.out.println("\nEspecialidade associada:");
        String espAtual = existente.getEspecialidade() != null ?
                existente.getEspecialidade().getNome() : "Nenhuma";
        System.out.println("Atual: " + espAtual);

        String codigoEsp = InputsAuxiliares.lerTexto("Novo código especialidade (ENTER para manter, 'none' para remover): ");

        Especialidade esp;
        if (codigoEsp.isEmpty()) {
            esp = existente.getEspecialidade();
        } else if (codigoEsp.equalsIgnoreCase("none")) {
            esp = null;
        } else {
            esp = gestaoHospital.procurarEspecialidade(codigoEsp);
            if (esp == null) {
                InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
                return;
            }
        }

        Sintoma atualizado = new Sintoma(novoNome, urgenciaTexto, esp);

        if (gestaoHospital.atualizarSintoma(nome, atualizado)) {
            InputsAuxiliares.imprimirSucesso("Sintoma atualizado com sucesso!");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao atualizar sintoma.");
        }
    }

    private void removerSintoma(GestaoHospital gestaoHospital) {
        System.out.println("\n  REMOVER SINTOMA");
        System.out.println("-".repeat(30));

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do sintoma a remover: ");

        if (!InputsAuxiliares.confirmar("Tem a certeza que deseja remover o sintoma '" + nome + "'?")) {
            System.out.println("Operação cancelada.");
            return;
        }

        if (gestaoHospital.removerSintoma(nome)) {
            InputsAuxiliares.imprimirSucesso("Sintoma removido com sucesso!");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao remover sintoma (não encontrado?).");
        }
    }
}