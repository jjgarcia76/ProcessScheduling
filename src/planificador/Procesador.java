/* Procesador.java */

/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Seccion A
 *
 * Procesador del simulador
 * Atiende procesos según la política seleccionada
 * 
 * Para RR:
 *   - Usa quantum
 *   - Consume solo parte del tiempo
 *   - Reencola si el proceso no ha terminado
 */

package planificador;

import scheduler.processing.SimpleProcess;
import scheduler.processing.rr.*;
import scheduler.scheduling.policy.*;

public class Procesador implements Runnable {

    private final Policy politica;
    private boolean ejecutando = true;

    private int atendidos = 0;
    private long tiempoTotalAtencion = 0;

    public Procesador(Policy politica) {
        this.politica = politica;
    }

    @Override
    public void run() {
        iniciar();
    }

    public void iniciar() {
        System.out.println("\n--- Procesador iniciado ---");
        System.out.println("Politica utilizada: " + politica.getNombrePolitica());

        boolean esRR = politica instanceof RRPolicy;

        long quantumMs = 0;
        if (esRR) {
            quantumMs = ((RRPolicy) politica).getQuantumMs();
        }

        while (ejecutando) {

            SimpleProcess proceso = politica.siguienteProceso();

            if (proceso == null) {
                try { Thread.sleep(200); } catch (InterruptedException e) {}
                continue;
            }

            System.out.println("\nAtendiendo proceso: " + proceso);
            System.out.println("Cola actual:");
            politica.imprimirCola();

            long inicio = System.currentTimeMillis();

            // -------------------------
            //   ROUND ROBIN
            // -------------------------
            if (esRR && proceso instanceof RRArithmeticProcess || proceso instanceof RRIOProcess || proceso instanceof RRConditionalProcess || proceso instanceof RRLoopProcess) {
                long remaining = 0;

                if (proceso instanceof RRArithmeticProcess)
                    remaining = ((RRArithmeticProcess) proceso).getRemainingTime();
                if (proceso instanceof RRIOProcess)
                    remaining = ((RRIOProcess) proceso).getRemainingTime();
                if (proceso instanceof RRConditionalProcess)
                    remaining = ((RRConditionalProcess) proceso).getRemainingTime();
                if (proceso instanceof RRLoopProcess)
                    remaining = ((RRLoopProcess) proceso).getRemainingTime();

                long tiempoAConsumir = Math.min(quantumMs, remaining);

                try { Thread.sleep(tiempoAConsumir); } catch (InterruptedException e) {}

                // Consumir quantum
                if (proceso instanceof RRArithmeticProcess)
                    ((RRArithmeticProcess) proceso).consume(quantumMs);
                if (proceso instanceof RRIOProcess)
                    ((RRIOProcess) proceso).consume(quantumMs);
                if (proceso instanceof RRConditionalProcess)
                    ((RRConditionalProcess) proceso).consume(quantumMs);
                if (proceso instanceof RRLoopProcess)
                    ((RRLoopProcess) proceso).consume(quantumMs);

                boolean terminado = false;

                if (proceso instanceof RRArithmeticProcess)
                    terminado = ((RRArithmeticProcess) proceso).isDone();
                if (proceso instanceof RRIOProcess)
                    terminado = ((RRIOProcess) proceso).isDone();
                if (proceso instanceof RRConditionalProcess)
                    terminado = ((RRConditionalProcess) proceso).isDone();
                if (proceso instanceof RRLoopProcess)
                    terminado = ((RRLoopProcess) proceso).isDone();

                if (terminado) {
                    atendidos++;
                    System.out.println(" → Proceso completado");
                } else {
                    // reencolar
                    politica.encolarProceso(proceso);
                    System.out.println(" → Quantum consumido. Reencolado.");
                }

            } else {
                // -------------------------
                //   FCFS, LCFS, PP
                // -------------------------
                try {
                    Thread.sleep(proceso.getServiceTime());
                } catch (InterruptedException e) {}

                atendidos++;
            }

            long fin = System.currentTimeMillis();
            tiempoTotalAtencion += (fin - inicio);
        }

        System.out.println("\n--- Procesador detenido ---");
    }

    public void detener() {
        ejecutando = false;
    }

    public int getProcesosAtendidos() {
        return atendidos;
    }

    public double getTiempoPromedioMs() {
        if (atendidos == 0) return 0;
        return (double) tiempoTotalAtencion / atendidos;
    }
}
