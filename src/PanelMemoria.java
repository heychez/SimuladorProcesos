
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Roberto
 */
public class PanelMemoria extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        ArrayList<Proceso> procesosEnMemoria = Memoria.getInstance().getProcesosEnMemoria();
        int x = 10, y = 10, m;

        for (int i = 0; i < procesosEnMemoria.size(); i++) {
            Proceso proceso = procesosEnMemoria.get(i);

            int ancho = proceso.getMemoria() / 2;
            //g.drawImage(img.getImage(), disNodo, 35, 170, 50, this);
            g.setColor(Color.gray);
            g.fillRect(x, y, ancho, 60);

            g.setColor(Color.black);
            g.fillRect(x + ancho - 1, y, 1, 60);

            g.setColor(Color.white);
            g.drawString(proceso.getNombre(), x + 10, y + 20);
            
            m = proceso.getMemoria()*100/Memoria.MAX_MEMORIA_K;
            g.drawString(String.valueOf(m) + "%", x + 10, y + 40);

            this.repaint();

            x += ancho;
        }

        int memoriaDisponible = Memoria.getInstance().getMemoriaDisponible();
        g.setColor(Color.lightGray);
        g.fillRect(x, y, memoriaDisponible / 2, 60);
        g.setColor(Color.black);
        g.drawString("DISP", x + 5, y + 20);
        
        m = memoriaDisponible*100/Memoria.MAX_MEMORIA_K;
        g.drawString(String.valueOf(m) + "%", x + 5, y + 40);
        this.repaint();

    }

}
