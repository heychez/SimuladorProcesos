
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Roberto
 */
public class Proceso {

    public static final int MIN_PRIORIDAD = 1;
    public static final int MAX_PRIORIDAD = 10;
    public static final int MIN_MEMORIA = 100;//50;//100;
    public static final int MAX_MEMORIA = 300;//600;//100;//600;
    public static final int MIN_T_EJECUCION = 3;
    public static final int MAX_T_EJECUCION = 15;
    public static final int MIN_T_BLOQUEADO = 1;//1;
    public static final int MAX_T_BLOQUEADO = 3;//3;
    public static final int ESTADO_NUEVO = 0;
    public static final int ESTADO_LISTO = 1;
    public static final int ESTADO_EJECUCION = 2;
    public static final int ESTADO_BLOQUEADO = 3;
    public static final int ESTADO_LISTO_SUSPENDIDO = 4;
    public static final int ESTADO_BLOQUEADO_SUSPENDIDO = 5;
    public static final int ESTADO_FINALIZADO = 6;

    private String nombre;
    private int prioridad;
    private int memoria;
    private int tEjecucion;
    private int estado;

    private int tFaltante;
    private int tBloqueado;
    private int quantumUtilizado;

    public boolean enMemoria = false;

    public Proceso() {
        this(null);
    }

    public Proceso(String nombre) {
        this.nombre = Configuracion.nuevoProceso(nombre);
        this.prioridad = new Random().nextInt((MAX_PRIORIDAD - MIN_PRIORIDAD) + 1) + MIN_PRIORIDAD;
        this.memoria = new Random().nextInt((MAX_MEMORIA - MIN_MEMORIA) + 1) + MIN_MEMORIA;
        this.tEjecucion = new Random().nextInt((MAX_T_EJECUCION - MIN_T_EJECUCION) + 1) + MIN_T_EJECUCION;
        this.estado = ESTADO_NUEVO;
        this.tFaltante = tEjecucion;
        this.quantumUtilizado = 0;
    }

    public Proceso(String nombre, int prioridad, int memoria, int tEjecucion, int estado, int tEjecucionFaltante, int quantumUtilizado) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.memoria = memoria;
        this.tEjecucion = tEjecucion;
        this.estado = estado;
        this.tFaltante = tEjecucionFaltante;
        this.quantumUtilizado = quantumUtilizado;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Proceso) {
            Proceso p = (Proceso) o;
            return nombre.equals(p.getNombre()) && tEjecucion == p.gettEjecucion() && memoria == p.getMemoria() && prioridad == p.getPrioridad();
        }
        return false;
    }

    public Proceso copia() {
        return new Proceso(nombre, prioridad, memoria, tEjecucion, estado, tFaltante, quantumUtilizado);
    }

    public synchronized void listo() {
        estado = ESTADO_LISTO;
    }
    
    public synchronized void suspenderListo() {
        estado = ESTADO_LISTO_SUSPENDIDO;
    }

    public synchronized void ejecutar(int quantum) {
        estado = ESTADO_EJECUCION;
        tFaltante -= quantum;
        quantumUtilizado = quantum;
    }

    public synchronized void bloqueado() {
        estado = ESTADO_BLOQUEADO;
        tBloqueado = new Random().nextInt((MAX_T_BLOQUEADO - MIN_T_BLOQUEADO) + 1) + MIN_T_BLOQUEADO;
    }

    public synchronized void desbloqueado() {
        estado = ESTADO_LISTO;
    }

    public synchronized void interrumpir() {
        estado = ESTADO_LISTO;
    }

    public synchronized void finalizar() {
        estado = ESTADO_FINALIZADO;
    }

    public String getNombreEstado() {
        switch (estado) {
            case ESTADO_NUEVO:
                return "NUEVO";
            case ESTADO_LISTO:
                return "LISTO";
            case ESTADO_LISTO_SUSPENDIDO:
                return "LISTO_SUSPENDIDO";
            case ESTADO_EJECUCION:
                return "EJECUCION";
            case ESTADO_BLOQUEADO:
                return "BLOQUEADO";
            case ESTADO_BLOQUEADO_SUSPENDIDO:
                return "BLOQUEADO_SUSPENDIDO";
            case ESTADO_FINALIZADO:
                return "FINALIZADO";
        }
        return "";
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getMemoria() {
        return memoria;
    }

    public int gettEjecucion() {
        return tEjecucion;
    }

    public int getEstado() {
        return estado;
    }

    public int gettFaltante() {
        return tFaltante;
    }

    public int gettBloqueado() {
        return tBloqueado;
    }

    public int getQuantumUtilizado() {
        return quantumUtilizado;
    }

    public synchronized void setEstado(int estado) {
        this.estado = estado;
    }

}
