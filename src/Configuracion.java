/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Roberto
 */
public class Configuracion {

    public static final int QUANTUM = 5;//2;
    public static int timer = -1;
    private static int nroDeProcesos = 0;

    public void reset() {
        nroDeProcesos = 0;
    }

    public static String nuevoProceso(String nombre) {
        nroDeProcesos++;
        if (nombre == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("P");

            if (nroDeProcesos < 10) {
                sb.append("0");
            }
            sb.append(nroDeProcesos);

            return sb.toString();
        } else {
            return nombre;
        }
    }

}
