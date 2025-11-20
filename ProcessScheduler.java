/*ProcessScheduler.java*/ 

/**
 * Hecho por: Jonathan Garcia
 * Carnet: 25001306
 * Seccion: 25001306
 * Clase Main del Proyecto #2 – Process Scheduling
 * Esta clase interpreta los argumentos enviados desde consola, selecciona
 * la política indicada y ejecuta la simulación hasta que el usuario
 * presione la tecla 'q'. 
 * Todavia no contiene la logica interna de las politicas, generador de procesos o procesador, 
 * solo es el esqueleto principal para arrancar el proyecto.
*/

import java.io.*;  //InputStreamReader y BufferedReader

public class ProcessScheduler {
    public static void main(String[] args) {
        try {
            if (args.length < 6) {
                System.out.println("\nUso:");
                System.out.println("java ProcessScheduler -fcfs rango arith io cond loop");
                System.out.println("java ProcessScheduler -lcfs rango arith io cond loop");
                System.out.println("java ProcessScheduler -pp rango arith io cond loop");
                System.out.println("java ProcessScheduler -rr rango arith io cond loop quantum");
                return;
            }
            //politica
            String politica = args[0];
            boolean esRR = politica.equalsIgnoreCase("-rr");

            if (esRR && args.length < 7) {
                System.out.println("se ha dado un error debido a que para el Round Robin se debe agregar un quantum"); 
                return;
            }
            //rango de ingreso, ejemplo de 1.5-3
            String rango = args[1];
            String[] rparts = rango.split("-");
            double rangoMin = Double.parseDouble(rparts[0]);
            double rangoMax = Double.parseDouble(rparts[1]);

            //tiempos por tipo de proceso
            double timeArith = Double.parseDouble(args[2]);
            double timeIO = Double.parseDouble(args[3]);
            double timeCond = Double.parseDouble(args[4]);
            double timeLoop = Double.parseDouble(args[5]);

            // Quantum (SOLO para RR)
            double quantum = 0;
            if (esRR) {
                quantum = Double.parseDouble(args[6]);
            }

            //Creacion de las politicas
            Object politicaUsada = null; //luego sera FCFS, LCFS, RR o PP
            switch (politica.toLowerCase()) {
                case "-fcfs":
                    System.out.println("Politica escogida: FIRST COME FIRST SERVED");
                    politicaUsada = null; //luego creamos FCFSPolicy
                    break;
                case "-lcfs":
                    System.out.println("Politica escogida: LAST COME FIRST SERVED");
                    politicaUsada = null; //luego creamos el LCFSPolicy
                    break;
                case "-pp":
                    System.out.println("Politica escogida: PRIORITY POLICY");
                    politicaUsada = null; //luego creamos el PPPolicy
                    break;
                case "-rr":
                    System.out.println("Politica escogida: ROUND ROBIN");
                    politicaUsada = null; //luego creamos el RRPolicy
                    break;
                default:
                    System.out.println("Escoge una que este disponible");
                    return;
            }

            System.out.println("\nInicio de la ejecucion");
            System.out.println("Rango de ingreso: " + rangoMin + " - " + rangoMax + " segundos");
            System.out.println("Tiempo en Arith: " + timeArith);
            System.out.println("Tiempo en IO: " + timeIO);
            System.out.println("Tiempo en Cond: " + timeCond);
            System.out.println("Tiempo en Loop: " + timeLoop);
            if (esRR) {
                System.out.println("Quantum: " + quantum);
            }

            /**    
             * AQUI luego crearemos: ProcessGenerator generator = new ProcessGenerator(...); y Processor cpu = new Processor(...);
             * Thread tg = new Thread(generator);
             * Thread tp = new Thread(cpu);
             * tg.start();
             * tp.start();
            */

            System.out.println("\nSe esta ejecutando el programa");
            System.out.println("Presione 'q' + ENTER para detener la ejecucion\n");

            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            String linea = "";

            while (true) {
                linea = buffer.readLine();
                if (linea != null && linea.trim().equalsIgnoreCase("q")) {
                    System.out.println("\nSe esta deteniendo la ejecucion");
                    break;
                }
            }

            //generator.stop(), cpu.stop(), tg.join(), tp.join()
            //resumen final para cuando todo este listo

            System.out.println("\n------------------------------------");
            System.out.println("Termino la ejecucion del programa");
            System.out.println("------------------------------------");
            System.out.println("Procesos atendidos: [pendiente]");
            System.out.println("Procesos en cola: [pendiente]");
            System.out.println("Tiempo promedio: [pendiente]");
            System.out.println("Politica usada: " + politica);

        } catch (Exception exception) {
            System.out.println("Error de ejecucion: ");
            exception.printStackTrace();
        }
    }
}
