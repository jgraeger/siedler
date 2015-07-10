/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Zeigt die Rohstoffe und SP eines einzelnen Spielers an
 *@author jgraeger
 */
public class RohstoffView extends JPanel implements Observer {

    private SpielerModel spieler;
    private JLabel[] labels;
    public static final String[] ROHSTOFFNAMEN =  {"Getreide","Schaf","Lehm","Erz","Holz"};
    private JPanel contentPanel;

    public RohstoffView(SpielerModel s) {
        this.setLayout(new BorderLayout());
        this.spieler = s;
        s.addObserver(this);

        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3,2));
        this.add(contentPanel);

        this.labels = new JLabel[6];
        for (int i=0; i<6; i++) {
            this.labels[i] = new JLabel();
        }
        refreshLabels();
        TitledBorder title = BorderFactory.createTitledBorder(spieler.getName());
        title.setTitleColor(spieler.getFarbe());
        this.setBorder(title);
    }

    public void update(Observable o, Object o1) {
        refreshLabels();
    }

    private void refreshLabels()
    {
        contentPanel.removeAll();
        
        for (int x=0 ; x<5 ; x++) {
            this.labels[x].setIcon(ImageManager.getImageIcon("img/sonstige/icon" + ROHSTOFFNAMEN[x] + ".png", new Dimension(30, 30)));
            this.labels[x].setText(Integer.toString(spieler.getAnzahlRohstoffe(x)));
            contentPanel.add(labels[x]);
        }
        
        this.labels[5].setIcon(ImageManager.getImageIcon("img/sonstige/siegpunkt.png", new Dimension(30, 30)));
        this.labels[5].setText(Integer.toString(spieler.getSiegpunkte()));
        contentPanel.add(labels[5]);      
        
        this.repaint();
    }
}
