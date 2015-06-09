
import java.util.ArrayList;
import java.util.PriorityQueue;
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
public class Despachador extends Thread {

    Memoria memoria = Memoria.getInstance();
    Simulador simulador;

    public Despachador(Simulador simulador) {
        this.simulador = simulador;
    }

    @Override
    public void run() {
        try {
            this.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Admision.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        PriorityQueue<Proceso> colaDeProcesos = simulador.getColaDeProcesos();
        ArrayList<Proceso> procesosFinalizados = simulador.getProcesosFinalizados();

        while (true) {
            Proceso proceso = colaDeProcesos.peek();
            //System.out.println(colaDeProcesos.size());
            if (proceso != null) {
                System.out.println(proceso.getNombre() + ", " + proceso.getNombreEstado());
                if (proceso.getEstado() == Proceso.ESTADO_LISTO) {
                    int q = Planificador.QUANTUM;
                    if (proceso.gettEjecucionFaltante() < Planificador.QUANTUM) {
                        q = proceso.gettEjecucionFaltante();
                    }

                    proceso.ejecutar(q);
                    simulador.refrescarTablaColaProcesos();
                    simulador.getPanelDiagramaGrantt().agregarProceso(proceso.copia());
                    System.out.println("ejecutando.. " + q);
                    try {
                        this.sleep(q * simulador.getUnidadDeTiempoMs());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Admision.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (proceso.gettEjecucionFaltante() == 0) { // el proceso ha finalizado
                        proceso.finalizar();
                        colaDeProcesos.remove(proceso);
                        procesosFinalizados.add(proceso);
                        memoria.quitarProceso(proceso);
                        simulador.getLabelDatosMemoria().setText("<html><br>" + memoria.getMemoriaEnUso() + " k<br>" + memoria.getMemoriaDisponible() + " k<br>" + Memoria.MAX_MEMORIA_K + " k</html>");

                        simulador.refrescarTablaProcesosFinalizados();
                    } else { // el proceso ejecutado vuelve a la cola de los listos
                        colaDeProcesos.remove(proceso);

                        proceso.interrumpir(); // el proceso pasa de ejecucion a listo
                        colaDeProcesos.add(proceso);
                    }
                    simulador.refrescarTablaColaProcesos();
                }
            }
        }
    }

}
