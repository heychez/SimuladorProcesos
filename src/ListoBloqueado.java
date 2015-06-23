
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PREGRADO
 */
public class ListoBloqueado extends Thread {

    Simulador simulador;

    public ListoBloqueado(Simulador simulador) {
        this.simulador = simulador;
    }

    @Override
    public void run() {
        try {
            this.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(NuevoListo.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Proceso> procesosBloqueados = simulador.getProcesosBloqueados();

        while (true) {
            for (int i = 0; i < procesosBloqueados.size(); i++) {
                Proceso proceso = procesosBloqueados.get(i);

                synchronized (proceso) {
                    if (proceso.getEstado() == Proceso.ESTADO_BLOQUEADO) {
                        try {
                            this.sleep(proceso.gettBloqueado() * simulador.getUnidadDeTiempoMs());
                        } catch (InterruptedException ex) {
                            Logger.getLogger(NuevoListo.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        proceso.desbloqueado();
                        simulador.refrescarTablaProcesosBloqueados();
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
