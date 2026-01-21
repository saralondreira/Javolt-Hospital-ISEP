package UI;

import Entidades.Especialidade;
import Servicos.GestaoHospital;

public class SubEspecialidade {
    public void menuEspecialidades(GestaoHospital gestaoHospital) {
        int opcao;
        do {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("   🩺 GESTÃO DE ESPECIALIDADES");
            System.out.println("=".repeat(40));
            System.out.println("1 - Adicionar especialidade");
            System.out.println("2 - Listar especialidades");
            System.out.println("3 - Atualizar especialidade");
            System.out.println("4 - Remover especialidade");
            System.out.println("0 - Voltar");
            System.out.println("-".repeat(40));

            opcao = InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);

            switch (opcao) {
                case 1 -> adicionarEspecialidade(gestaoHospital);
                case 2 -> gestaoHospital.listarEspecialidades();
                case 3 -> atualizarEspecialidade(gestaoHospital);
                case 4 -> removerEspecialidade(gestaoHospital);
                case 0 -> System.out.println("← A voltar...");
                default -> System.out.println("Opção inválida.");
            }

            if (opcao != 0) InputsAuxiliares.pausar();

        } while (opcao != 0);
    }

    private void adicionarEspecialidade(GestaoHospital gestaoHospital) {
        System.out.println("\n➕ ADICIONAR NOVA ESPECIALIDADE");
        System.out.println("-".repeat(30));

        String codigo = InputsAuxiliares.lerTextoNaoVazio("Código da especialidade: ");
        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome da especialidade: ");

        Especialidade e = new Especialidade(codigo, nome);

        if (gestaoHospital.adicionarEspecialidade(e)) {
            InputsAuxiliares.imprimirSucesso("Especialidade adicionada com sucesso!");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao adicionar especialidade (código duplicado ou capacidade máxima?).");
        }
    }

    private void atualizarEspecialidade(GestaoHospital gestaoHospital) {
        System.out.println("\n✏️  ATUALIZAR ESPECIALIDADE");
        System.out.println("-".repeat(30));

        String codigo = InputsAuxiliares.lerTextoNaoVazio("Código da especialidade a atualizar: ");
        Especialidade existente = gestaoHospital.procurarEspecialidade(codigo);

        if (existente == null) {
            InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
            return;
        }

        System.out.println("\nEspecialidade encontrada: " + existente.getNome());
        System.out.println("Deixe em branco para manter valor atual.");

        String novoCodigo = InputsAuxiliares.lerTexto("Novo código [" + existente.getCodigo() + "]: ");
        if (novoCodigo.isEmpty()) novoCodigo = existente.getCodigo();

        String novoNome = InputsAuxiliares.lerTexto("Novo nome [" + existente.getNome() + "]: ");
        if (novoNome.isEmpty()) novoNome = existente.getNome();

        Especialidade atualizada = new Especialidade(novoCodigo, novoNome);

        if (gestaoHospital.atualizarEspecialidade(codigo, atualizada)) {
            InputsAuxiliares.imprimirSucesso("Especialidade atualizada com sucesso!");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao atualizar especialidade.");
        }
    }

    private void removerEspecialidade(GestaoHospital gestaoHospital) {
        System.out.println("\n🗑️  REMOVER ESPECIALIDADE");
        System.out.println("-".repeat(30));

        String codigo = InputsAuxiliares.lerTextoNaoVazio("Código da especialidade a remover: ");

        if (!InputsAuxiliares.confirmar("Tem a certeza que deseja remover esta especialidade?")) {
            System.out.println("Operação cancelada.");
            return;
        }

        if (gestaoHospital.removerEspecialidade(codigo)) {
            InputsAuxiliares.imprimirSucesso("Especialidade removida com sucesso!");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao remover especialidade (não encontrada?).");
        }
    }
}