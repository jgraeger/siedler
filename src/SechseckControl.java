/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Reagiert auf Mausklicks in Sechseck
 * @author jbrose
 */
public class SechseckControl implements MouseListener {

    private SechseckModel model;    

    public SechseckControl(SechseckModel model){
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
     
        //Darf der Räuber überhaupt versetzt werden?
         if (Main.gameManager.getPhase() != GameManager.WAITING_FOR_HOTZENPLOTZ_MOVING){
             return;
         }
                
         int[] sx = new int[] {0, SpielfeldView.SECHSECKLAENGE/4, 3*SpielfeldView.SECHSECKLAENGE/4, SpielfeldView.SECHSECKLAENGE, 3*SpielfeldView.SECHSECKLAENGE/4, SpielfeldView.SECHSECKLAENGE/4, 0};
         int[] sy = new int[] {SpielfeldView.SECHSECKHOEHE/2, SpielfeldView.SECHSECKHOEHE, SpielfeldView.SECHSECKHOEHE, SpielfeldView.SECHSECKHOEHE/2, 0, 0, SpielfeldView.SECHSECKHOEHE/2};
                
         Polygon p = new Polygon(sx, sy, 6);
                
         if (p.contains(me.getX(), me.getY())){
            if (model.hatRaeuber()){
                JOptionPane.showMessageDialog(null, "Der Räuber befindet sich bereits hier.", "Land bereits ausgebeutet", JOptionPane.ERROR_MESSAGE);
            } else if (Main.gameManager.getPhase() == GameManager.WAITING_FOR_HOTZENPLOTZ_MOVING){
                if (model.istWasserfeld()){
                      JOptionPane.showMessageDialog(null, "Der Räuber Hotzenplotz kann leider nicht schwimmen.");
                      return;
                }
                
                Main.gameManager.getRaeuberFeld().raueberWegsetzen();
                model.raeuberHinsetzen();
                Main.gameManager.setRaeuberFeld(model);
                Main.gameManager.hotzenplotzMoved();
                
                //Wenn der Räuber versetzt wurde, darf der versetzende Spieler einem Spieler, der
                //an diesem Feld eine Siedlung/Stadt besitzt, einen Rohstoff blind (=per Zufall) stehlen
                SpielerModel[] players = model.getOwningPlayers();
                
                if (players == null || (players.length == 1 && players[0] == Main.gameManager.getCurrentPlayer())) {
                    //Es gibt keine Siedlungen
                    //oder zumindest keine von anderen Spielern
                    return;
                }
              
                
                //TODO Später kann der Spieler evtl. noch selbstständig gewählt werden, bisher per Zufall
                boolean ok = false;
                int playerNr = -1;
                
                while (!ok){
                    playerNr = Main.gameManager.random(players.length);
                    
                    ok = players[playerNr] != Main.gameManager.getCurrentPlayer();
                }
                
                SpielerModel chosenPlayer = players[playerNr];
                
                
                //Prüfen, ob der ausgewählte Spieler überhaupt Rohstoffe hat
                int summeRohstoffe = 0;
                
                for (int i = 0;i < 5;i++){
                    summeRohstoffe += chosenPlayer.getAnzahlRohstoffe(i);
                }
                
                if (summeRohstoffe == 0) {
                    JOptionPane.showMessageDialog(null, "Der ausgewählte Spieler besitzt keine Rohstoffe.\nPech gehabt!", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                //Rohstoff ermitteln, der gestohlen werden soll
                int type = -1;
                ok = false;
                
                while(!ok){
                    type = Main.gameManager.random(5);
                    
                    ok = chosenPlayer.getAnzahlRohstoffe(type) >= 1;
                }
                
                //Den Diebstahl durchführen
                chosenPlayer.remRohstoff(type);
                Main.gameManager.getCurrentPlayer().addRohstoff(type);
                Main.gameManager.log(Main.gameManager.getCurrentPlayer().getName() + " hat " + chosenPlayer.getName() + " ein " + RohstoffView.ROHSTOFFNAMEN[type] + " gestohlen", false);
            }                                                              
        }                                         
    }           

    @Override
    public void mouseEntered(MouseEvent me) {
        //Main.gameManager.log("mouseEntered wird aufgerufen", true);
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //Main.gameManager.log("mouseExited wird aufgerufen", true);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //Main.gameManager.log("mousePressed wird aufgerufen", true);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //Main.gameManager.log("mouseReleased wird aufgerufen", true);
    }
}