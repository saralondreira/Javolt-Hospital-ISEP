package Servicos;

import Entidades.Medico;
import Entidades.Paciente;
import Entidades.Especialidade;
import Entidades.Sintoma;

public class ConsultaEstatistica {

    /* ==============================
       MÉDIA DE UTENTES POR DIA
       ============================== */
    public static double mediaUtentesPorDia(int totalUtentes, int totalDias) {
        if (totalDias == 0) return 0;
        return (double) totalUtentes / totalDias;
    }

    public static void mostrarMediaPacientes(int totalUtentes, int totalDias) {
        double media = mediaUtentesPorDia(totalUtentes, totalDias);
        System.out.println("\n" + "=".repeat(40));
        System.out.println(" MÉDIA DE PACIENTES POR DIA");
        System.out.println("=".repeat(40));
        System.out.println("Total pacientes atendidos: " + totalUtentes);
        System.out.println("Total dias decorridos: " + totalDias);
        System.out.printf("Média diária: %.2f pacientes/dia\n", media);
        System.out.println("=".repeat(40));
    }

    /* ==============================
       SALÁRIO POR MÉDICO
       ============================== */
    public static double calcularSalarioMedico(Medico medico, int dias) {
        // Considera que as horas trabalhadas são acumuladas
        // Salário = valor hora * horas trabalhadas
        return medico.getHorasTrabalhadas() * medico.getValorHora();
    }

    public static void mostrarTabelaSalarios(Medico[] medicos, int totalMedicos, int dias) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(" TABELA DE SALÁRIOS POR MÉDICO");
        System.out.println("=".repeat(50));
        System.out.printf("%-20s %-15s %-10s %-10s %-10s%n",
                "MÉDICO", "ESPECIALIDADE", "HORAS", "VALOR/H", "SALÁRIO");
        System.out.println("-".repeat(50));

        double totalSalarios = 0;

        for (int i = 0; i < totalMedicos; i++) {
            Medico m = medicos[i];
            double salario = calcularSalarioMedico(m, dias);
            totalSalarios += salario;

            System.out.printf("%-20s %-15s %-10.1f %-10.2f € %-10.2f €%n",
                    m.getNome(),
                    m.getEspecialidade(),
                    m.getHorasTrabalhadas(),
                    m.getValorHora(),
                    salario);
        }

        System.out.println("-".repeat(50));
        System.out.printf("%-56s %-10.2f €%n", "TOTAL GERAL:", totalSalarios);
        System.out.printf("%-56s %-10.2f €%n", "Média por médico:",
                (totalMedicos > 0 ? totalSalarios / totalMedicos : 0));
        System.out.println("=".repeat(50));
    }

    /* ==============================
       UTENTES POR SINTOMA
       ============================== */
    public static int contarUtentesComSintoma(String sintomaNome, Paciente[] pacientes, int totalPacientes) {
        int contador = 0;

        for (int i = 0; i < totalPacientes; i++) {
            Paciente p = pacientes[i];
            Sintoma[] sintomasPaciente = p.getSintomas();

            for (int j = 0; j < p.getTotalSintomas(); j++) {
                if (sintomasPaciente[j].getNome().equalsIgnoreCase(sintomaNome)) {
                    contador++;
                    break; // Paciente tem o sintoma, contar apenas uma vez
                }
            }
        }
        return contador;
    }

    public static void mostrarUtentesPorSintoma(Paciente[] pacientes, int totalPacientes,
                                                Sintoma[] sintomas, int totalSintomas) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(" UTENTES POR SINTOMA");
        System.out.println("=".repeat(50));
        System.out.printf("%-25s %-15s %-10s%n",
                "SINTOMA", "NÍVEL", "PACIENTES");
        System.out.println("-".repeat(50));

        for (int i = 0; i < totalSintomas; i++) {
            Sintoma s = sintomas[i];
            int count = contarUtentesComSintoma(s.getNome(), pacientes, totalPacientes);

            // Calcular percentagem
            double percent = totalPacientes > 0 ? (count * 100.0) / totalPacientes : 0;

            System.out.printf("%-25s %-15s %-10d (%.1f%%)%n",
                    s.getNome(),
                    s.getNivelUrgencia(),
                    count,
                    percent);
        }
        System.out.println("=".repeat(50));
    }

    /* ==============================
       TOP 3 ESPECIALIDADES
       ============================== */
    public static void mostrarTopEspecialidades(Especialidade[] especialidades,
                                                int totalEspecialidades,
                                                Paciente[] pacientes,
                                                int totalPacientes) {
        if (totalEspecialidades == 0) {
            System.out.println("\n Nenhuma especialidade registada.");
            return;
        }

        // Array para contagem
        int[] contadores = new int[totalEspecialidades];
        int totalPacientesComEspecialidade = 0;

        // Contar pacientes por especialidade
        for (int i = 0; i < totalPacientes; i++) {
            String espPaciente = pacientes[i].getEspecialidadeDesejada();

            if (espPaciente == null || espPaciente.isEmpty()) continue;

            for (int j = 0; j < totalEspecialidades; j++) {
                if (especialidades[j].getNome().equalsIgnoreCase(espPaciente)) {
                    contadores[j]++;
                    totalPacientesComEspecialidade++;
                    break;
                }
            }
        }

        // Criar arrays para ordenação
        Especialidade[] espOrdenadas = new Especialidade[totalEspecialidades];
        int[] contadoresOrdenados = new int[totalEspecialidades];

        // Copiar arrays
        for (int i = 0; i < totalEspecialidades; i++) {
            espOrdenadas[i] = especialidades[i];
            contadoresOrdenados[i] = contadores[i];
        }

        // Ordenar por contagem (bubble sort - simples)
        for (int i = 0; i < totalEspecialidades - 1; i++) {
            for (int j = 0; j < totalEspecialidades - i - 1; j++) {
                if (contadoresOrdenados[j] < contadoresOrdenados[j + 1]) {
                    // Trocar contadores
                    int tempCont = contadoresOrdenados[j];
                    contadoresOrdenados[j] = contadoresOrdenados[j + 1];
                    contadoresOrdenados[j + 1] = tempCont;

                    // Trocar especialidades
                    Especialidade tempEsp = espOrdenadas[j];
                    espOrdenadas[j] = espOrdenadas[j + 1];
                    espOrdenadas[j + 1] = tempEsp;
                }
            }
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println(" TOP 3 ESPECIALIDADES");
        System.out.println("=".repeat(50));

        int limite = Math.min(3, totalEspecialidades);
        for (int i = 0; i < limite; i++) {
            double percentagem = totalPacientesComEspecialidade > 0 ?
                    (contadoresOrdenados[i] * 100.0) / totalPacientesComEspecialidade : 0;

            System.out.printf("%dº: %-25s -> %d pacientes (%.1f%%)%n",
                    i + 1,
                    espOrdenadas[i].getNome(),
                    contadoresOrdenados[i],
                    percentagem);
        }

        // Mostrar estatísticas adicionais
        System.out.println("-".repeat(50));
        System.out.printf("Total pacientes analisados: %d%n", totalPacientes);
        System.out.printf("Pacientes com especialidade definida: %d%n", totalPacientesComEspecialidade);
        System.out.printf("Pacientes sem especialidade: %d%n",
                totalPacientes - totalPacientesComEspecialidade);
        System.out.println("=".repeat(50));
    }
}