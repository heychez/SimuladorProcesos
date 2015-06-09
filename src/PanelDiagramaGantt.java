
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
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
public class PanelDiagramaGantt extends JPanel {

    ArrayList<Proceso> procesosDelDiagrama = new ArrayList();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 10, y = 5;

        for (int i = 0; i < procesosDelDiagrama.size(); i++) {
            Proceso proceso = procesosDelDiagrama.get(i);
            int ancho = 20 + proceso.getQuantumUtilizado() * 10;
            
            if (x + 10 + ancho >= this.getWidth()) {
                this.setSize(x + ancho + 100, this.getHeight());
            }

            g.setColor(Color.black);
            g.drawString(String.valueOf(proceso.getQuantumUtilizado()), x + ancho / 2 - 5, y + 20);

            g.setColor(Color.gray);
            g.fillRect(x, y + 25, ancho, 30);

            g.setColor(Color.black);
            g.fillRect(x + ancho - 1, y + 25, 1, 30);

            g.setColor(Color.white);
            g.drawString(proceso.getNombre(), x + ancho / 2 - 10, y + 45);

            this.repaint();

            x += ancho;
        }
    }

    public void agregarProceso(Proceso proceso) {
        procesosDelDiagrama.add(proceso);
        this.repaint();
    }

}
