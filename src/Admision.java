
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Roberto
 */
public class Admision extends Thread {

    Memoria memoria = Memoria.getInstance();
    Simulador simulador;

    public Admision(Simulador simulador) {
        this.simulador = simulador;
    }

    @Override
    public void run() {
        ArrayList<Proceso> procesosNuevos = simulador.getProcesosNuevos();
        int i = 0;

        while (true) {
            Proceso proceso = procesosNuevos.get(i++);

            if (proceso.getEstado() == Proceso.ESTADO_NUEVO) {
                if (memoria.cargarProceso(proceso.copia())) {
                    simulador.getLabelDatosMemoria().setText("<html><br>" + memoria.getMemoriaEnUso() + " k<br>" + memoria.getMemoriaDisponible() + " k<br>" + Memoria.MAX_MEMORIA_K + " k</html>");

                    proceso.listo();
                    simulador.refrescarTablaProcesosNuevos();

                    simulador.getColaDeProcesos().add(proceso.copia());
                    simulador.refrescarTablaColaProcesos();
                }
            }

            if (i >= procesosNuevos.size()) {
                i = 0;
            }
            
            try {
                this.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Admision.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
