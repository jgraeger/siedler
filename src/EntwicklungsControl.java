/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Reagiert auf Aktionen in EntwicklungView
 *
 * @author Johannes
 */
public class EntwicklungsControl implements ActionListener {

    private SpielerModel spieler;

    public EntwicklungsControl(SpielerModel model) {
        spieler = model;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        EntwicklungsView view = (EntwicklungsView) SwingUtilities.getRoot((Component) e.getSource());
        ArrayList<Entwicklungskarte> karten = spieler.getEntwicklungskarten();

        if (command.equals("Exit")) {
            view.dispose();
        } else if (command.equals("Ritter")) {
            boolean success = false;
            for (Entwicklungskarte karte : karten) {
                if (karte.getTyp() == Entwicklungskarte.RITTER && karte.istGespielt() == false) {
                    karte.spielen();
                    Main.gameManager.setPhase(GameManager.WAITING_FOR_HOTZENPLOTZ_MOVING);
                    Main.gameManager.notifyer();
                    success = true;
                    break;
                }
            }
            if (!success) {
                JOptionPane.showMessageDialog(null, "Du hast keine entsprechende Entwicklungskarte auf der Hand", "Fehler", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Eine Ritterkarte wurde ausgespielt", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                view.dispose();
            }
        } else if (command.equals("Siegpunkt")) {
            boolean success = false;
            for (Entwicklungskarte karte : karten) {
                if (karte.getTyp() == Entwicklungskarte.SIEGPUNKT && karte.istGespielt() == false) {
                    karte.spielen();
                    spieler.addSiegpunkt();
                    String currentPlayer = Main.gameManager.getCurrentPlayer().getName().toString();
                    Main.gameManager.log(currentPlayer + " hat ein Monument gebaut",true);
                    spieler.notifyer();
                    Main.gameManager.notifyer();
                    success = true;
                    break;
                }
            }
            if (!success) {
                JOptionPane.showMessageDialog(null, "Du hast keine entsprechende Entwicklungskarte auf der Hand", "Fehler", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Du hast einen Siegpunkt erhalten", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                view.dispose();
            }
        } else if (command.equals("Monopol")) {
            boolean success = false;
            for (Entwicklungskarte karte : karten) {
                if (karte.getTyp() == Entwicklungskarte.MONOPOL && karte.istGespielt() == false) {
                    JPanel pnlRessource = new JPanel();

                    JLabel label = new JLabel();
                    label.setText("Bitte wählen sie einen Rohstoff:");
                    pnlRessource.add(label);

                    String[] res = {"Getreide", "Wolle", "Lehm", "Erz", "Holz"};
                    JComboBox rohstoff = new JComboBox(res);
                    rohstoff.setSelectedIndex(1);
                    pnlRessource.add(rohstoff);
                    JOptionPane.showMessageDialog(null, pnlRessource);

                    int selectedRes = rohstoff.getSelectedIndex();
                    ArrayList<SpielerModel> players = Main.gameManager.getPlayers();
                    int abgezogen = 0;
                    for (SpielerModel player : players) {
                        if (player != spieler) {
                            int anzahl = player.getAnzahlRohstoffe(selectedRes);
                            player.remRohstoffe(selectedRes, anzahl);
                            abgezogen = abgezogen + anzahl;
                        }
                    }
                    spieler.addRohstoffe(selectedRes, abgezogen);
                    JOptionPane.showMessageDialog(null, "Du hast Rohstoffe erhalten", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                    view.dispose();
                    success = true;
                }
            }
            if (!success) {
                JOptionPane.showMessageDialog(null, "Du hast keine entsprechende Entwicklungskarte auf der Hand", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
