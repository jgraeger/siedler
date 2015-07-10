/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Reagiert auf die möglichen Benutzerinteraktionen und führt nach Berechtigungsprüfung Transaktionen durch
 * @author Johannes
 */
public class HandelControl implements ActionListener {

    private HandelModel model;
    private GameManager gameManager;

    public HandelControl(HandelModel model,GameManager gm) {
        this.model = model;
        this.gameManager = gm;
    }

    public void actionPerformed(ActionEvent e) {
        HandelView view = (HandelView) SwingUtilities.getRoot((Component) e.getSource());
        int value = 0;

        SpielerModel traded = Main.gameManager.getPlayerByName((String) view.getCbPlayer().getSelectedItem());

        if (traded != null) {


            if (e.getActionCommand().equals("submit")) {

                //Überprüfen, ob überhaupt genügend Rohstoffe vorhanden sind
                for (int i = 0; i < 5; i++) {
                    if (model.getTradingPlayer().getAnzahlRohstoffe(i) < ((Integer) (view.getTradingPlayerRessources().get(i).getValue())).intValue()) {
                        JOptionPane.showMessageDialog(null, model.getTradingPlayer().getName() + " besitzt nicht genug " + RohstoffView.ROHSTOFFNAMEN[i] + ".", "Fehler", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (traded.getAnzahlRohstoffe(i) < ((Integer) (view.getTradedPlayerRessources().get(i).getValue())).intValue()) {
                        JOptionPane.showMessageDialog(null, traded.getName() + " besitzt nicht genug " + RohstoffView.ROHSTOFFNAMEN[i] + ".", "Fehler", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                model.setTradedPlayer(traded);

                int eingabe = JOptionPane.showConfirmDialog(null, "Ist " + traded.getName() + " einverstanden?", "Einverständniserklärung", JOptionPane.YES_NO_CANCEL_OPTION);
                if (eingabe == JOptionPane.YES_OPTION) {
                    for (int x = 1; x < 3; x++) {
                        for (int i = 0; i < 5; i++) {
                            if (x == 1) {
                                value = ((Number) view.getTradingPlayerRessources().get(i).getValue()).intValue();
                            } else if (x == 2) {
                                value = ((Number) view.getTradedPlayerRessources().get(i).getValue()).intValue();
                            }
                            model.performTransaction(value, i, x);
                        }
                    }
                    view.dispose();
                }
            }
        } else {
            //Es soll mit der Bank gehandelt werden


            //Überprüfen, ob überhaupt genügend Rohstoffe vorhanden sind
            for (int i = 0; i < 5; i++) {
                if (model.getTradingPlayer().getAnzahlRohstoffe(i) < ((Integer) (view.getTradingPlayerRessources().get(i).getValue())).intValue()) {
                    JOptionPane.showMessageDialog(null, "Sie besitzten nicht genug " + RohstoffView.ROHSTOFFNAMEN[i] + ".", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }


            int nrOfAllowedGoods = 0;
            int nrOfRequestedGoods = 0;

            //Anzahl der geforderten Güter
            for (int i = 0; i < 5; i++) {
                nrOfRequestedGoods += ((Integer) (view.getTradedPlayerRessources().get(i).getValue())).intValue();
            }

            for (int i = 0; i < 5; i++) {
                if (model.getTradingPlayer().hatHandelsbonus(i + 7)) {
                    nrOfAllowedGoods += ((Integer) (view.getTradingPlayerRessources().get(i).getValue())).intValue() / 2;
                } else if (model.getTradingPlayer().hatHandelsbonus(SechseckModel.HAFEN_ALLGEMEIN)) {
                    nrOfAllowedGoods += ((Integer) (view.getTradingPlayerRessources().get(i).getValue())).intValue() / 3;
                } else {
                    nrOfAllowedGoods += ((Integer) (view.getTradingPlayerRessources().get(i).getValue())).intValue() / 4;
                }
            }


            if (nrOfRequestedGoods > nrOfAllowedGoods || nrOfAllowedGoods == 0) {
                JOptionPane.showMessageDialog(null, "Die Bank hat ihr Angebot abgelehnt.", "Handel abgelehnt", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Die Bank hat ihr Angebot akzeptiert.", "Handel angenommen", JOptionPane.INFORMATION_MESSAGE);

            for (int i = 0;i < 5;i++){
                model.getTradingPlayer().addRohstoffe(i, ((Integer) (view.getTradedPlayerRessources().get(i).getValue())).intValue());
                model.getTradingPlayer().remRohstoffe(i, ((Integer) (view.getTradingPlayerRessources().get(i).getValue())).intValue());
            }
            
            view.dispose();
        }
        model.getTradedPlayer().notifyObservers();
        model.getTradingPlayer().notifyObservers();
        gameManager.notifyObservers();
    }
}