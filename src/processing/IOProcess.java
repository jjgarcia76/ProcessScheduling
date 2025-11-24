/* SimpleProcess.java */
/**
 ** Hecho por: Santiago
 ** Carnet: 25000328
 ** Seccion: A
 PSDT: Literalmente solo es asignarle un tipo de servicio llamado IO
 
 
 **/


package processing;


public class IOProcess extends SimpleProcess {
    public IOProcess(int id, long serviceTime) {
        super(id, serviceTime, "IO");
    }
}