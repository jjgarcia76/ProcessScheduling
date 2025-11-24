/* Procesador.java */

/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Sección: A
 * Descripción:
 * En esta clase representa al procesador del simulador, 
 * es el que se encarga de atender procesos uno por uno, dependiendo
 * de la política seleccionada (FCFS, LCFS, RR, PP).
 * El procesador NO ejecuta procesos reales solo simula
 * el tiempo de ejecucion usando Thread.sleep().
 * Para Round Robin debe trabajar con quanta.
*/

package planificador;

import scheduler.politicas.Politica;
import scheduler.procesamiento.ProcesoSimple;

public class Procesador{

    private Politica politica;
    private boolean ejecutando;
    private int atendidos;

    /**
     * Constructor del procesador
     * @param politica política de calendarización a utilizar.
     */
    public Procesador(Politica politica){
        this.politica = politica;
        this.ejecutando = true;
        this.atendidos = 0;
    }

    /**
     * Metodo que inicia el ciclo principal del procesador.
     * Atiende un proceso a la vez según la política.
     * Se detiene únicamente cuando se presiona 'q' en la aplicación principal.
     */
        public void iniciar(){
        System.out.println("\n--- Procesador iniciado ---");
        System.out.println("Politica utilizada: " + politica.getNombrePolitica());
        while (ejecutando) {
            ProcesoSimple proceso = politica.siguienteProceso();
            if (proceso != null) {
                System.out.println("\nAtendiendo proceso: " + proceso);
                try{
                    Thread.sleep((long) (proceso.getTiempoRestante() * 1000));
                }catch (InterruptedException e) {}

                politica.procesoTerminado(proceso);
                atendidos++;
            }else{
                try{
                    Thread.sleep(200);
                }catch (Exception e) {}

            }


        }
    }

    /**
     * Detiene el procesador (llamado desde ProcessScheduler cuando se presiona q)
     */
    public void detener() {
        this.ejecutando = false;
    }

    /**
     * Devuelve la cantidad total de procesos atendidos
     */
    public int getProcesosAtendidos() {
        return atendidos;
    }
}
