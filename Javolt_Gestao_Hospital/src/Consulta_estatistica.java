import Entidades.Medico;
import Entidades.Paciente;

public class Consulta_estatistica {

    /* ==============================
       MÉDIA DE UTENTES POR DIA
       ============================== */
    public static double mediaUtentesPorDia(int totalUtentes, int totalDias) {
        if (totalDias == 0) return 0;
        return (double) totalUtentes / totalDias;
    }

    /* ==============================
       SALÁRIO POR MÉDICO
       ============================== */
    public static double calcularSalarioMedico(Medico medico) {
        return medico.getHorasTrabalhadas() * medico.getValorHora();
    }

    public static void mostrarSalarios(Medico[] medicos, int totalMedicos) {
        System.out.println("\n--- Salários por Médico ---");
        for (int i = 0; i < totalMedicos; i++) {
            double salario = calcularSalarioMedico(medicos[i]);
            System.out.println(medicos[i].getNome() + " -> " + salario + " €");
        }
    }

    /* ==============================
       UTENTES POR SINTOMA
       ============================== */
    public static int contarSintoma(String sintoma,
                                    Paciente[] pacientes,
                                    int totalPacientes) {

        int contador = 0;

        for (int i = 0; i < totalPacientes; i++) {
            String[] sintomas = pacientes[i].getSintomas();
            int totalSintomas = pacientes[i].getTotalSintomas();

            for (int j = 0; j < totalSintomas; j++) {
                if (sintomas[j].equalsIgnoreCase(sintoma)) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /* ==============================
       TOP 3 ESPECIALIDADES
       ============================== */
    public static void top3Especialidades(Especialidade[] especialidades,
                                          int totalEspecialidades,
                                          Paciente[] pacientes,
                                          int totalPacientes) {

        int[] contadores = new int[totalEspecialidades];

        // Contar pacientes por especialidade
        for (int i = 0; i < totalPacientes; i++) {
            String espPaciente = pacientes[i].getEspecialidadeAtribuida();

            if (espPaciente == null) continue;

            for (int j = 0; j < totalEspecialidades; j++) {
                if (especialidades[j].getNome().equals(espPaciente)) {
                    contadores[j]++;
                }
            }
        }

        // Ordenar (bubble sort)
        for (int i = 0; i < totalEspecialidades - 1; i++) {
            for (int j = 0; j < totalEspecialidades - i - 1; j++) {
                if (contadores[j] < contadores[j + 1]) {
                    int temp = contadores[j];
                    contadores[j] = contadores[j + 1];
                    contadores[j + 1] = temp;

                    Especialidade eTemp = especialidades[j];
                    especialidades[j] = especialidades[j + 1];
                    especialidades[j + 1] = eTemp;
                }
            }
        }

        System.out.println("\n--- Top 3 Especialidades ---");
        for (int i = 0; i < 3 && i < totalEspecialidades; i++) {
            double percentagem = (totalPacientes == 0)
                    ? 0
                    : (contadores[i] * 100.0) / totalPacientes;

            System.out.println(
                    especialidades[i].getNome() + " -> " +
                            String.format("%.2f", percentagem) + "%"
            );
        }
    }
}