package UI;

import Entidades.Especialidade;
import Servicos.GestaoHospital;

public class SubEspecialidade {
    public void menuEspecialidades(GestaoHospital gestaoHospital) {
        int opcao;
        do {
            InputsAuxiliares.limparTela();
            InputsAuxiliares.imprimirCabecalho("GESTÃO DE ESPECIALIDADES");
            System.out.println("|   1 - Adicionar especialidade                    |");
            System.out.println("|   2 - Listar especialidades                      |");
            System.out.println("|   3 - Atualizar especialidade                    |");
            System.out.println("|   4 - Remover especialidade                      |");
            System.out.println("|   0 - Voltar                                     |");
            InputsAuxiliares.imprimirLinha();

            opcao = InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);

            switch (opcao) {
                case 1 -> adicionarEspecialidade(gestaoHospital);
                case 2 -> {gestaoHospital.listarEspecialidades();InputsAuxiliares.pausar();}
                case 3 -> atualizarEspecialidade(gestaoHospital);
                case 4 -> removerEspecialidade(gestaoHospital);
                case 0 -> System.out.println("<< A voltar...");
                default -> InputsAuxiliares.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void adicionarEspecialidade(GestaoHospital gestaoHospital) {
        InputsAuxiliares.imprimirCabecalho("ADICIONAR ESPECIALIDADE");
        InputsAuxiliares.exibirMsgCancelar();
        try {
            String codigo = InputsAuxiliares.lerTextoComCancelamento("Código da especialidade: ");
            String nome = InputsAuxiliares.lerTextoComCancelamento("Nome da especialidade: ");

            Especialidade e = new Especialidade(codigo, nome);

            if (gestaoHospital.adicionarEspecialidade(e)) {
                InputsAuxiliares.imprimirSucesso("Especialidade adicionada com sucesso!");
            } else {
                InputsAuxiliares.imprimirErro("Erro ao adicionar especialidade (código duplicado).");
            }
            InputsAuxiliares.pausar();
        } catch (InputsAuxiliares.OperacaoCanceladaException e) {
            System.out.println("<< Operação cancelada pelo utilizador.");
            InputsAuxiliares.pausar();
        }
    }

    private void atualizarEspecialidade(GestaoHospital gestaoHospital) {
        InputsAuxiliares.imprimirCabecalho("ATUALIZAR ESPECIALIDADE");
        InputsAuxiliares.exibirMsgCancelar();

        try {
            if (InputsAuxiliares.confirmar("Deseja ver a lista de especialidades existente?")){
                System.out.println();
                gestaoHospital.listarEspecialidades();
                InputsAuxiliares.imprimirLinha();
                System.out.println();
            }

            String codigo = InputsAuxiliares.lerTextoComCancelamento("Código da especialidade a atualizar: ");
            Especialidade existente = gestaoHospital.procurarEspecialidade(codigo);

            if (existente == null) {
                InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
                InputsAuxiliares.pausar();
                return;
            }

            System.out.println("\nEspecialidade encontrada: " + existente.getNome());
            System.out.println("Deixe em branco e prima ENTER para manter valor atual.");

            String novoCodigo = InputsAuxiliares.lerTexto("Novo código [" + existente.getCodigo() + "]: ");
            if (novoCodigo.isEmpty()) {
                novoCodigo = existente.getCodigo();
            }

            String novoNome = InputsAuxiliares.lerTexto("Novo nome [" + existente.getNome() + "]: ");
            if (novoNome.isEmpty()) {
                novoNome = existente.getNome();
            }

            Especialidade atualizada = new Especialidade(novoCodigo, novoNome);

            if (gestaoHospital.atualizarEspecialidade(codigo, atualizada)) {
                InputsAuxiliares.imprimirSucesso("Especialidade atualizada com sucesso!");
            } else {
                InputsAuxiliares.imprimirErro("Erro ao atualizar especialidade.");
            }
            InputsAuxiliares.pausar();
        } catch (InputsAuxiliares.OperacaoCanceladaException e) {
            System.out.println("<< Operacao cancelada pelo utilizador.");
            InputsAuxiliares.pausar();
        }
    }

    private void removerEspecialidade(GestaoHospital gestaoHospital) {
        InputsAuxiliares.imprimirCabecalho("REMOVER ESPECIALIDADE");
        InputsAuxiliares.exibirMsgCancelar();

        try {
            if (InputsAuxiliares.confirmar("Deseja remover especialidades existente?")){
                System.out.println();
                gestaoHospital.listarEspecialidades();
                System.out.println();
            }

            String codigo = InputsAuxiliares.lerTextoComCancelamento("Código da especialidade a remover: ");

            if (!InputsAuxiliares.confirmar("Tem a certeza que deseja remover esta especialidade?")) {
                System.out.println("Operação cancelada.");
                InputsAuxiliares.pausar();
                return;
            }

            if (gestaoHospital.removerEspecialidade(codigo)) {
                InputsAuxiliares.imprimirSucesso("Especialidade removida com sucesso!");
            } else {
                InputsAuxiliares.imprimirErro("Erro ao remover especialidade (não encontrada?).");
            }
            InputsAuxiliares.pausar();
        }catch (InputsAuxiliares.OperacaoCanceladaException e) {
            System.out.println("<< Operacao cancelada pelo utilizador.");
            InputsAuxiliares.pausar();
        }
    }
}