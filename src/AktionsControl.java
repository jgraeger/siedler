/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Reagiert auf Eingaben von AktionsPanel, und stößt notfalls im GameManager die notwendigen Änderungen an
 * @author jbrose
 */
public class AktionsControl implements ActionListener {   
    
    public AktionsControl() {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("throwDices")){
            if (Main.optionManager.getDiceAnimation()){
                JOptionPane.showMessageDialog(null, ImageManager.getImageIcon("img/sonstige/wuerfel.gif"), null, JOptionPane.PLAIN_MESSAGE);             
            }
            
            Main.gameManager.wuerfeln();
        } else if (actionCommand.equals("trade")){
            Main.gameManager.handel();
        } else if (actionCommand.equals("buyCard")){
            //Der Spieler kann sich die Karte leisten, da der Knopf sonst nicht klickbar wäre
            Entwicklungskarte karte = Entwicklungskarte.getEntwicklungskarte();
            
            if (karte == null){
                //Es gibt keine Karten mehr
                JOptionPane.showMessageDialog(null, "Es wurden schon alle Karten gezogen!", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JOptionPane.showMessageDialog(null, Main.gameManager.getCurrentPlayer().getName() + " hat eine " + karte.getName() + " erhalten", "Entwicklungskarte", JOptionPane.INFORMATION_MESSAGE);
            
            Main.gameManager.getCurrentPlayer().addEntwicklungskarte(karte);
            Main.gameManager.getCurrentPlayer().karteKaufen();
            String currentPlayer = Main.gameManager.getCurrentPlayer().getName().toString();
            Main.gameManager.log(currentPlayer + " hat eine Entwicklungskarte gekauft",false);
        } else if (actionCommand.equals("playCard")){
            EntwicklungsControl control = new EntwicklungsControl(Main.gameManager.getCurrentPlayer());
            new EntwicklungsView(control,Main.gameManager.getCurrentPlayer());
            Main.gameManager.notifyer();
        } else if (actionCommand.equals("nextTurn")){
            Main.gameManager.naechsteRunde();
        }
    }

}
