package UI;

import Entidades.Especialidade;
import Entidades.Medico;
import Servicos.GestaoHospital;

public class SubMedicos {
    public void menuMedicos(GestaoHospital gestaoHospital) {
        int opcao;
        do {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("     GESTÃO DE MÉDICOS");
            System.out.println("=".repeat(40));
            System.out.println("1 - Adicionar médico");
            System.out.println("2 - Listar médicos");
            System.out.println("3 - Atualizar médico");
            System.out.println("4 - Remover médico");
            System.out.println("0 - Voltar");
            System.out.println("-".repeat(40));

            opcao = InputsAuxiliares.lerInteiroIntervalo("Opção: ", 0, 4);

            switch (opcao) {
                case 1 -> adicionarMedico(gestaoHospital);
                case 2 -> gestaoHospital.listarMedicos();
                case 3 -> atualizarMedico(gestaoHospital);
                case 4 -> removerMedico(gestaoHospital);
                case 0 -> System.out.println("← A voltar...");
                default -> System.out.println("Opção inválida.");
            }

            if (opcao != 0) InputsAuxiliares.pausar();

        } while (opcao != 0);
    }

    private void adicionarMedico(GestaoHospital gestaoHospital) {
        System.out.println("\n ADICIONAR NOVO MÉDICO");
        System.out.println("-".repeat(30));

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do médico: ");

        // Listar especialidades disponíveis
        System.out.println("\nEspecialidades disponíveis:");
        // Aqui poderia listar especialidades

        String codigoEsp = InputsAuxiliares.lerTextoNaoVazio("Código da especialidade: ");
        Especialidade esp = gestaoHospital.procurarEspecialidade(codigoEsp);

        if (esp == null) {
            InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
            return;
        }

        int horaEntrada = InputsAuxiliares.lerInteiroIntervalo("Hora de entrada (0-23): ", 0, 23);
        int horaSaida = InputsAuxiliares.lerInteiroIntervalo("Hora de saída (0-23): ", 0, 23);

        if (horaSaida <= horaEntrada) {
            InputsAuxiliares.imprimirErro("Hora de saída deve ser após hora de entrada.");
            return;
        }

        double valorHora = InputsAuxiliares.lerDouble("Valor hora (€): ");
        if (valorHora <= 0) {
            InputsAuxiliares.imprimirErro("Valor hora deve ser positivo.");
            return;
        }

        Medico medico = new Medico(nome, esp.getNome(), horaEntrada, horaSaida, valorHora);

        if (gestaoHospital.adicionarMedico(medico)) {
            InputsAuxiliares.imprimirSucesso("Médico adicionado com sucesso!");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao adicionar médico (capacidade máxima?).");
        }
    }

    private void atualizarMedico(GestaoHospital gestaoHospital) {
        System.out.println("\n  ATUALIZAR MÉDICO");
        System.out.println("-".repeat(30));

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do médico a atualizar: ");
        Medico existente = gestaoHospital.procurarMedicoPorNome(nome);

        if (existente == null) {
            InputsAuxiliares.imprimirErro("Médico não encontrado.");
            return;
        }

        System.out.println("\nMédico encontrado: " + existente.getNome());
        System.out.println("Deixe em branco para manter valor atual.");

        String novoNome = InputsAuxiliares.lerTexto("Novo nome [" + existente.getNome() + "]: ");
        if (novoNome.isEmpty()) novoNome = existente.getNome();

        String codigoEsp = InputsAuxiliares.lerTexto(
                "Novo código especialidade [" + existente.getEspecialidade() + "]: ");

        Especialidade esp;
        if (codigoEsp.isEmpty()) {
            esp = gestaoHospital.procurarEspecialidade(existente.getEspecialidade());
        } else {
            esp = gestaoHospital.procurarEspecialidade(codigoEsp);
        }

        if (esp == null) {
            InputsAuxiliares.imprimirErro("Especialidade não encontrada.");
            return;
        }

        String inputEntrada = InputsAuxiliares.lerTexto(
                "Nova hora entrada [" + existente.getHoraEntrada() + "]: ");
        int horaEntrada = inputEntrada.isEmpty() ? existente.getHoraEntrada() : Integer.parseInt(inputEntrada);

        String inputSaida = InputsAuxiliares.lerTexto(
                "Nova hora saída [" + existente.getHoraSaida() + "]: ");
        int horaSaida = inputSaida.isEmpty() ? existente.getHoraSaida() : Integer.parseInt(inputSaida);

        String inputValor = InputsAuxiliares.lerTexto(
                "Novo valor hora [" + existente.getValorHora() + "]: ");
        double valorHora = inputValor.isEmpty() ? existente.getValorHora() : Double.parseDouble(inputValor);

        Medico atualizado = new Medico(novoNome, esp.getNome(), horaEntrada, horaSaida, valorHora);

        if (gestaoHospital.atualizarMedico(nome, atualizado)) {
            InputsAuxiliares.imprimirSucesso("Médico atualizado com sucesso!");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao atualizar médico.");
        }
    }

    private void removerMedico(GestaoHospital gestaoHospital) {
        System.out.println("\n  REMOVER MÉDICO");
        System.out.println("-".repeat(30));

        String nome = InputsAuxiliares.lerTextoNaoVazio("Nome do médico a remover: ");

        if (!InputsAuxiliares.confirmar("Tem a certeza que deseja remover o médico '" + nome + "'?")) {
            System.out.println("Operação cancelada.");
            return;
        }

        if (gestaoHospital.removerMedico(nome)) {
            InputsAuxiliares.imprimirSucesso("Médico removido com sucesso!");
        } else {
            InputsAuxiliares.imprimirErro("Erro ao remover médico (não encontrado?).");
        }
    }
}