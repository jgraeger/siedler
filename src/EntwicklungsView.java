/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *  Bietet die Möglichkeit, Entwicklungskarten auszuspielen
 * @author Johannes
 */
public class EntwicklungsView extends JFrame {
    
    JLabel ritterKarten;
    JLabel siegpunktKarten;
    JLabel monopolKarten;
    
    public EntwicklungsView(EntwicklungsControl control,SpielerModel s) {

        //Get the count of Cards on the Stack of the currentPlayer
        ArrayList<Entwicklungskarte> currentPlayerCards = s.getEntwicklungskarten();
        int knightCardsCount = 0;
        int victoryPointsCardsCount = 0;
        int monopolCardsCount = 0;

        for (Entwicklungskarte k : currentPlayerCards)
        {
            switch (k.getTyp())
            {
                case Entwicklungskarte.RITTER: knightCardsCount++;
                    break;
                case Entwicklungskarte.SIEGPUNKT: victoryPointsCardsCount++;
                    break;
                case Entwicklungskarte.MONOPOL: monopolCardsCount++;
                    break;
            }
        }

        this.setTitle("Entwicklungskarten");
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(650,450));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new GridLayout(1, 3));
        this.add(pnlContent);
        
        JPanel pnlRitter = new JPanel();
        pnlRitter.setLayout(new BorderLayout());
        pnlContent.add(pnlRitter);

        ritterKarten = new JLabel();
        ritterKarten.setText("Auf der Hand: " + knightCardsCount);
        ritterKarten.setHorizontalAlignment(SwingConstants.CENTER);
        pnlRitter.add(ritterKarten,BorderLayout.NORTH);
        
        JLabel ritterBild = new JLabel();
        ritterBild.setIcon(ImageManager.getImageIcon("img/sonstige/ritterkarte.png"));
        pnlRitter.add(ritterBild,BorderLayout.CENTER);
        
        JButton spieleRitter = new JButton();
        spieleRitter.setText("Spielen!");
        spieleRitter.setActionCommand("Ritter");
        spieleRitter.addActionListener(control);
        if (knightCardsCount < 1)
            spieleRitter.setEnabled(false);
        pnlRitter.add(spieleRitter,BorderLayout.SOUTH);
        
        JPanel pnlSiegpunkt = new JPanel();
        pnlSiegpunkt.setLayout(new BorderLayout());
        pnlContent.add(pnlSiegpunkt);
        
        siegpunktKarten = new JLabel();
        siegpunktKarten.setText("Auf der Hand: " + victoryPointsCardsCount);
        siegpunktKarten.setHorizontalAlignment(SwingConstants.CENTER);
        pnlSiegpunkt.add(siegpunktKarten, BorderLayout.NORTH);
        
        JLabel siegpunktBild = new JLabel();
        siegpunktBild.setIcon(ImageManager.getImageIcon("img/sonstige/monument.png"));
        pnlSiegpunkt.add(siegpunktBild,BorderLayout.CENTER);
        
        JButton spieleSiegpunkt = new JButton();
        spieleSiegpunkt.setText("Spielen!");
        spieleSiegpunkt.setActionCommand("Siegpunkt");
        spieleSiegpunkt.addActionListener(control);
        if (victoryPointsCardsCount < 1)
            spieleSiegpunkt.setEnabled(false);
        pnlSiegpunkt.add(spieleSiegpunkt,BorderLayout.SOUTH);
        
        JPanel pnlMonopol = new JPanel();
        pnlMonopol.setLayout(new BorderLayout());
        pnlContent.add(pnlMonopol);
        
        monopolKarten = new JLabel();
        monopolKarten.setText("Auf der Hand: " + monopolCardsCount);
        monopolKarten.setHorizontalAlignment(SwingConstants.CENTER);
        pnlMonopol.add(monopolKarten,BorderLayout.NORTH);
        
        JLabel monopolBild = new JLabel();
        monopolBild.setIcon(ImageManager.getImageIcon("img/sonstige/monopol.png"));
        pnlMonopol.add(monopolBild,BorderLayout.CENTER);
        
        JButton spieleMonopol = new JButton();
        spieleMonopol.setText("Spielen!");
        spieleMonopol.setActionCommand("Monopol");
        spieleMonopol.addActionListener(control);
        if (monopolCardsCount < 1)
            spieleMonopol.setEnabled(false);
        pnlMonopol.add(spieleMonopol,BorderLayout.SOUTH);
        
        JButton close = new JButton();
        close.setText("Fenster schließen");
        close.setActionCommand("Exit");
        close.addActionListener(control);
        this.add(close,BorderLayout.SOUTH);

        this.setVisible(true);
    }

}
