/* GeneradorProcesos.java */

/** 
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Sección: A
 * Descripción:
 * En esta clase se generan procesos aleatorios en intervalos aleatorios.
 * Cada proceso tiene:
 *  - id incremental
 *  - tipo aleatorio (A, IO, C, L)
 *  - tiempo correspondiente según el tipo.
 * El generador introduce los procesos en la cola manejada por la política.
*/

package planificador;

import java.util.*;
import scheduler.politicas.Politica;
import scheduler.procesamiento.*;

public class GeneradorProcesos implements Runnable {

    private Politica politica;
    private double minTiempo;
    private double maxTiempo;
    private int idContador = 1;
    private boolean activo = true;
    private Random random = new Random();

    private double tiempoArit;
    private double tiempoIO;
    private double tiempoCond;
    private double tiempoLoop;

    /**
     * Constructor
     * @param politica  Politica usada para encolar nuevos procesos
     * @param rango     Ejemplo: 1.5-3
     * @param arith     Tiempo de proceso aritmetico
     * @param io        Tiempo de proceso I/O
     * @param cond      Tiempo de proceso condicional
     * @param loop      Tiempo de proceso iterativo
     */
    public GeneradorProcesos(
        Politica politica,
        String rango,
        double arith,
        double io,
        double cond,
        double loop
    ) {
        this.politica = politica;

        String[] partes = rango.split("-");
        this.minTiempo = Double.parseDouble(partes[0]);
        this.maxTiempo = Double.parseDouble(partes[1]);

        this.tiempoArit = arith;
        this.tiempoIO = io;
        this.tiempoCond = cond;
        this.tiempoLoop = loop;
    }

    /**
     * Genera un tipo aleatorio:
     * 0 = Aritmetico
     * 1 = IO
     * 2 = Condicional
     * 3 = Iterativo
     */
    private int tipoAleatorio() {
        return random.nextInt(4);
    }

    /**
     * Genera un tiempo aleatorio entre minTiempo y maxTiempo
     */
    private double tiempoAleatorio() {
        return minTiempo + (maxTiempo - minTiempo) * random.nextDouble();
    }

    /**
     * Hilo generador de procesos
     * Crea un proceso y lo envía a la politica para que lo agregue
     */
    @Override
    public void run() {
        System.out.println("\n--- Generador de procesos activo ---");
        while (activo) {
            int tipo = tipoAleatorio();
            ProcesoSimple nuevo = null;
            switch (tipo) {
                case 0: 
                    nuevo = new ProcesoAritmetico(idContador++, tiempoArit); 
                    break;
                case 1: 
                    nuevo = new ProcesoIO(idContador++, tiempoIO); 
                    break;
                case 2: 
                    nuevo = new ProcesoCondicional(idContador++, tiempoCond); 
                    break;
                case 3: 
                    nuevo = new ProcesoIterativo(idContador++, tiempoLoop); 
                    break;
            }
            politica.encolarProceso(nuevo);
            try {
                Thread.sleep((long) (tiempoAleatorio() * 1000));
            } catch (InterruptedException e) {}
        }
        
    }

    /**
     * Detiene la generacion de los procesos
     */
    public void detener() {
        activo = false;
    }
}
