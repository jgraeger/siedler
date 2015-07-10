/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
/**
 * Die Menuleiste, über die Einstellungen vorgenommen werden können
 * @author jbrose
 */
public class OptionView extends JMenuBar {

    public OptionView(OptionControl oc) {
        JMenuBar menuBar = new JMenuBar();        

                     
        JMenu mnExtras = new JMenu("Extras");               
       
        JMenuItem mntmPreise = new JMenuItem("Baukosten");
        mntmPreise.setActionCommand("Preise");
        mntmPreise.addActionListener(oc);
        mnExtras.add(mntmPreise);
        
        JMenuItem mntmCredits = new JMenuItem("Credits");
        mntmCredits.setActionCommand("Credits");
        mntmCredits.addActionListener(oc);
        mnExtras.add(mntmCredits);
        
        
        JMenu mnEinstellungen = new JMenu("Einstellungen");

        JCheckBoxMenuItem mntmDiceAnimation = new JCheckBoxMenuItem("Würfelanimation");
        mntmDiceAnimation.setActionCommand("diceAnimation");
        mntmDiceAnimation.addActionListener(oc);        
        mnEinstellungen.add(mntmDiceAnimation);

        JMenuItem mntmPlayerCount = new JMenuItem("Spieleranzahl");
        mntmPlayerCount.setActionCommand("playerCount");
        mntmPlayerCount.addActionListener(oc);
        mnEinstellungen.add(mntmPlayerCount);

        JMenuItem mntmSiegpunkte = new JMenuItem("Siegpunkte");
        mntmSiegpunkte.setActionCommand("siegpunkte");
        mntmSiegpunkte.addActionListener(oc);
        mnEinstellungen.add(mntmSiegpunkte);
        
        JMenuItem mntmDiagonale = new JMenuItem("Diagonale");
        mntmDiagonale.setActionCommand("diagonale");
        mntmDiagonale.addActionListener(oc);
        mnEinstellungen.add(mntmDiagonale);
        
        
        JMenu mnSpiel = new JMenu("Spiel");
        
        JMenuItem mntmStart = new JMenuItem("Spiel starten");
        mntmStart.setActionCommand("startGame");
        mntmStart.addActionListener(oc);
        mnSpiel.add(mntmStart);
        
        JMenuItem mntmReset = new JMenuItem("Zurücksetzen");
        mntmReset.setActionCommand("reset");
        mntmReset.addActionListener(oc);
        mnSpiel.add(mntmReset);

        JMenuItem mntmSpeichern = new JMenuItem("Speichern");
        mntmSpeichern.addActionListener(oc);
        mnSpiel.add(mntmSpeichern);

        JMenuItem mntmLaden = new JMenuItem("Laden");
        mntmLaden.setActionCommand("Laden");
        mntmLaden.addActionListener(oc);
        mnSpiel.add(mntmLaden);

        JMenuItem mntmBeenden = new JMenuItem("Beenden");
        mntmBeenden.setActionCommand("Beenden");
        mntmBeenden.addActionListener(oc);
        mnSpiel.add(mntmBeenden);

        menuBar.add(mnSpiel);
        menuBar.add(mnEinstellungen);
        menuBar.add(mnExtras);       
        this.add(menuBar);
    }   
}