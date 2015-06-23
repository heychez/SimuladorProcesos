
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
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
public class NuevoListo extends Thread {

    Memoria memoria;
    Simulador simulador;

    public NuevoListo(Simulador simulador) {
        this.simulador = simulador;
        this.memoria = Memoria.getInstance();
    }

    @Override
    public void run() {
        ArrayList<Proceso> procesosNuevos = simulador.getProcesosNuevos();
        PriorityQueue<Proceso> procesosListos = simulador.getProcesosListos();

        while (true) {
            
            for (int i = 0; i < procesosNuevos.size(); i++) {
                Proceso proceso = procesosNuevos.get(i);
                
                if (proceso.getEstado() == Proceso.ESTADO_LISTO_SUSPENDIDO) {
                    if (memoria.cargarProceso(proceso.copia())) {
                        simulador.refrescarLabelMemoria();

                        synchronized (procesosNuevos) {
                            proceso.listo();
                            simulador.refrescarTablaProcesosListosSuspendidos();
                            simulador.refrescarTablaProcesosNuevos();
                        }

                        synchronized (procesosListos) {
                            procesosListos.add(proceso.copia());
                            simulador.refrescarTablaProcesosListos();
                        }

                        try {
                            this.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(NuevoListo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

            for (int i = 0; i < procesosNuevos.size(); i++) {
                Proceso proceso = procesosNuevos.get(i);

                if (proceso.getEstado() == Proceso.ESTADO_NUEVO) {
                    if (memoria.cargarProceso(proceso.copia())) {
                        simulador.refrescarLabelMemoria();

                        synchronized (procesosNuevos) {
                            proceso.listo();
                            simulador.refrescarTablaProcesosNuevos();
                        }

                        synchronized (procesosListos) {
                            procesosListos.add(proceso.copia());
                            simulador.refrescarTablaProcesosListos();
                        }
                    } else { //suspender el proceso
                        synchronized (procesosNuevos) {
                            proceso.suspenderListo();
                            simulador.refrescarTablaProcesosListosSuspendidos();
                            simulador.refrescarTablaProcesosNuevos();
                        }
                    }

                    try {
                        this.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(NuevoListo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            try {
                this.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(NuevoListo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
