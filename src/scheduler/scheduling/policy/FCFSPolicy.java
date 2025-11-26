/* FCFSPolicy.java */

/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Sección: A
 *
 * Descripción:
 * Implementación de la política FIRST COME FIRST SERVED (FCFS).
 * El primer proceso que entra es el primero en ser atendido.
 * Internamente usa una cola FIFO (ConcurrentLinkedQueue).
 */

package scheduler.scheduling.policy;

import java.util.concurrent.ConcurrentLinkedQueue;
import scheduler.processing.SimpleProcess;

public class FCFSPolicy extends Policy {

    // Cola de procesos (FIFO)
    private final ConcurrentLinkedQueue<SimpleProcess> cola = new ConcurrentLinkedQueue<>();

    @Override
    public String getNombrePolitica() {
        return "First Come First Served";
    }

    /**
     * Agrega un proceso al final de la cola.
     */
    @Override
    public void enqueue(SimpleProcess p) {
        cola.add(p);
    }

    /**
     * Saca el siguiente proceso (el primero que llegó).
     * Retorna null si la cola está vacía.
     */
    @Override
    public SimpleProcess dequeue() {
        return cola.poll(); // poll() devuelve null si está vacía
    }

    /**
     * Indica si ya no hay procesos en la cola.
     */
    @Override
    public boolean isEmpty() {
        return cola.isEmpty();
    }

    /**
     * Imprime la cola completa de procesos en orden FIFO.
     */
    @Override
    public void imprimirCola() {
        if (cola.isEmpty()) {
            System.out.println("[cola vacía]");
            return;
        }

        System.out.print("Cola FCFS: ");
        for (SimpleProcess p : cola) {
            System.out.print(p + " <- ");
        }
        System.out.println();
    }

    @Override
    public int getQueueSize() {
        return cola.size();
    }
}