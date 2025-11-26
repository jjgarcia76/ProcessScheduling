/* PriorityPolicy.java */
/**
 * Hecho por: Jonathan Garcia
 * Seccion: A
 *
 * Descripción:
 * Política PRIORITY POLICY (PP).
 * - Usa una PriorityQueue interna.
 * - La prioridad se toma de la interfaz Prioritizable (1 = más alta, 3 = más baja).
 * - Si un proceso no implementa Prioritizable, se asume prioridad media (2).
 */

package scheduler.scheduling.policy;

import java.util.PriorityQueue;
import java.util.Comparator;

import scheduler.processing.SimpleProcess;
import scheduler.processing.Prioritizable;

public class PriorityPolicy extends Policy {

    // Cola de prioridad: menor número de prioridad sale primero
    private final PriorityQueue<SimpleProcess> cola;

    public PriorityPolicy() {
        this.cola = new PriorityQueue<>(
            new Comparator<SimpleProcess>() {
                @Override
                public int compare(SimpleProcess a, SimpleProcess b) {
                    int pa = (a instanceof Prioritizable) ? ((Prioritizable)a).getPriority() : 2;
                    int pb = (b instanceof Prioritizable) ? ((Prioritizable)b).getPriority() : 2;
                    // prioridad 1 < 2 < 3
                    return Integer.compare(pa, pb);
                }
            }
        );
    }

    @Override
    public String getNombrePolitica() {
        return "Priority Policy";
    }

    @Override
    public void enqueue(SimpleProcess p) {
        cola.add(p);
    }

    @Override
    public SimpleProcess dequeue() {
        return cola.poll();   // null si está vacía
    }

    @Override
    public boolean isEmpty() {
        return cola.isEmpty();
    }

    /**
     * Imprime el contenido de la cola de prioridad.
     */
    @Override
    public void imprimirCola() {
        if (cola.isEmpty()) {
            System.out.println("[cola de prioridad vacía]");
            return;
        }

        System.out.print("Cola PP: ");
        for (SimpleProcess p : cola) {
            if (p instanceof Prioritizable) {
                int pr = ((Prioritizable)p).getPriority();
                System.out.print(p + " (prio=" + pr + ") <- ");
            } else {
                System.out.print(p + " <- ");
            }
        }
        System.out.println();
    }

    @Override
    public int getQueueSize() {
        return cola.size();
    }
}
