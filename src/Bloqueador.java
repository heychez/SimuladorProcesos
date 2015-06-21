
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
public class Bloqueador extends Thread {

    Simulador simulador;

    public Bloqueador(Simulador simulador) {
        this.simulador = simulador;
    }

    @Override
    public void run() {
        try {
            this.sleep(2500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Admision.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Proceso> procesosBloqueados = simulador.getProcesosBloqueados();
        int i = 0;

        while (true) {
            if (procesosBloqueados.size() == 0) {
                continue;
            }
            
            Proceso proceso = procesosBloqueados.get(i++);

            if (proceso.getEstado() == Proceso.ESTADO_BLOQUEADO) {

                try {
                    this.sleep(proceso.gettBloqueado() * simulador.getUnidadDeTiempoMs());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Admision.class.getName()).log(Level.SEVERE, null, ex);
                }
                proceso.desbloqueado();
                simulador.refrescarTablaProcesosBloqueados();
            }

            if (i >= procesosBloqueados.size()) {
                i = 0;
            }

            try {
                this.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Admision.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
