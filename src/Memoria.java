
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Roberto
 */
public class Memoria {

    public static final int MAX_MEMORIA_K = 1024;

    private static Memoria instance = null;
    private ArrayList<Proceso> procesosEnMemoria = new ArrayList();
    private int memoriaEnUso = 0;

    public static Memoria getInstance() {
        if (instance == null) {
            instance = new Memoria();
        }
        return instance;
    }

    public boolean cargarProceso(Proceso proceso) {
        if (instance.memoriaEnUso + proceso.getMemoria() <= MAX_MEMORIA_K) {
            instance.memoriaEnUso += proceso.getMemoria();
            instance.procesosEnMemoria.add(proceso);
            return true;
        } else {
            return false;
        }
    }

    public void quitarProceso(Proceso proceso) {
        instance.memoriaEnUso -= proceso.getMemoria();
        instance.procesosEnMemoria.remove(proceso);
    }

    public void limpiar() {
        instance.procesosEnMemoria.clear();
        instance.memoriaEnUso = 0;
    }

    public ArrayList<Proceso> getProcesosEnMemoria() {
        return instance.procesosEnMemoria;
    }

    public int getMemoriaEnUso() {
        return instance.memoriaEnUso;
    }

    public int getMemoriaDisponible() {
        return MAX_MEMORIA_K - instance.memoriaEnUso;
    }

}
