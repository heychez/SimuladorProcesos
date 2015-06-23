
import java.util.ArrayList;
import java.util.Iterator;
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
public class ListoEjecucion extends Thread {

    Memoria memoria;
    Simulador simulador;

    public ListoEjecucion(Simulador simulador) {
        this.simulador = simulador;
        memoria = Memoria.getInstance();
    }

    @Override
    public void run() {
        try {
            this.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(NuevoListo.class.getName()).log(Level.SEVERE, null, ex);
        }

        PriorityQueue<Proceso> procesosListos = simulador.getProcesosListos();
        ArrayList<Proceso> procesosFinalizados = simulador.getProcesosFinalizados();

        while (true) {
            Iterator<Proceso> it = procesosListos.iterator();
            int noListos = 0;

            while (it.hasNext()) {
                Proceso proceso = it.next();

                if (proceso.getEstado() == Proceso.ESTADO_LISTO) {
                    int q = Configuracion.QUANTUM;
                    synchronized (proceso) {
                        if (proceso.gettFaltante() < Configuracion.QUANTUM) {
                            q = proceso.gettFaltante();
                        }

                        proceso.ejecutar(q);
                        simulador.refrescarTablaProcesoEjecucion(proceso);
                        simulador.refrescarTablaProcesosListos();
                    }

                    System.out.println("E> " + proceso.getNombre() + " q:" + q);
                    Configuracion.timer = 1;
                    try {
                        this.sleep(q * simulador.getUnidadDeTiempoMs());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(NuevoListo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    simulador.getPanelDiagramaGrantt().agregarProceso(proceso.copia());
                    Configuracion.timer = -1;

                    if (proceso.gettFaltante() == 0) { // el proceso ha finalizado
                        synchronized (proceso) {
                            proceso.finalizar();
                            //colaDeProcesos.remove(proceso);
                            procesosFinalizados.add(proceso);
                            simulador.refrescarTablaProcesosFinalizados();

                            memoria.quitarProceso(proceso);
                            simulador.refrescarLabelMemoria();
                        }
                    } else { // el proceso ejecutado vuelve a la cola de los listos

                        synchronized (procesosListos) {
                            procesosListos.remove(proceso);
                        }

                        synchronized (proceso) {
                            if (proceso.getEstado() != Proceso.ESTADO_BLOQUEADO) {
                                proceso.interrumpir(); // el proceso pasa de ejecucion a listo
                            }
                        }

                        synchronized (procesosListos) {
                            procesosListos.add(proceso);
                        }
                    }

                    simulador.refrescarTablaProcesosListos();
                    break;

                } else {
                    noListos++;
                }
            }

            if (noListos >= procesosListos.size()) {
                simulador.refrescarTablaProcesoEjecucion(null);
            }
            try {
                this.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(NuevoListo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
