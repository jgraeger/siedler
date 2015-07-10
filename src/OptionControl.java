/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Reagiert auf Mausklicks in OptionView und nimmt gegebenenfalls Veränderugen im OptionManager vor
 * @author jbrose
 */
public class OptionControl implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if(s.equals("Beenden")){
            System.exit(0);
        } else if(s.equals("Credits")){
            JOptionPane.showMessageDialog(null, "© Die Neu-Installisierer\nMitglieder : Der Präsident der Preußischen Staats-Union,\n Der Informator und Southernswampfrog.\n Alle Dokumente sind streng vertraulich zu behandeln,\n Weitergabe an Dritte ist nur auf Nachfrage legalisiert.\n Danke fürs Spielen :)", "Credits", JOptionPane.INFORMATION_MESSAGE);
        } else if (s.equals("startGame")){
            if (isGameRunning()){
                JOptionPane.showMessageDialog(null, "Das Spiel läuft bereits.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Main.gameManager.startNewGame();
        } else if (s.equals("Speichern")) {
            Main.gameManager.save(Main.gameManager,Main.optionManager);
        } else if (s.equals("Laden")) {
            Main.gameManager.load();
        } else if (s.equals("Preise")){           
            JOptionPane.showMessageDialog(null, new JLabel(ImageManager.getImageIcon("img/sonstige/baukarte.png", new Dimension(340, 388))), "Baukosten", JOptionPane.PLAIN_MESSAGE);
        } else if (s.equals("reset")){
            Main.gameManager.resetGame();
        } else if (s.equals("diceAnimation")){
            Main.optionManager.setDiceAnimation( ((JCheckBoxMenuItem) e.getSource()).isSelected());
        } else if (s.equals("playerCount")){
            if (isGameRunning()){
                showError();
                return;
            }
            
            JPanel p = new JPanel(new BorderLayout());
            p.add(new JLabel("Spielerzahl:"), BorderLayout.NORTH);
            SpinnerModel model = new SpinnerNumberModel(Main.optionManager.getAnzahlSpieler(), 2, 10, 1);
            JSpinner spinner = new JSpinner(model);
            p.add(spinner);
            
            JOptionPane.showMessageDialog(null, p, "Spieleranzahl", JOptionPane.INFORMATION_MESSAGE);
            int value = ((Integer) (spinner.getValue())).intValue();
            Main.optionManager.setAnzahlSpieler(value);
        } else if (s.equals("siegpunkte")){
            if (isGameRunning()){
                showError();
                return;
            }
            
            JPanel p = new JPanel(new BorderLayout());
            p.add(new JLabel("Benötigte Siegpunkte:"), BorderLayout.NORTH);
            SpinnerModel model = new SpinnerNumberModel(Main.optionManager.getSiegpunktlimit(), 3, 100, 1);
            JSpinner spinner = new JSpinner(model);
            p.add(spinner);
            
            JOptionPane.showMessageDialog(null, p, "Siegpunkte", JOptionPane.INFORMATION_MESSAGE);
            int value = ((Integer) (spinner.getValue())).intValue();
            Main.optionManager.setSiegpunktlimit(value);
        } else if (s.equals("diagonale")){
            if (isGameRunning()){
                showError();
                return;
            }
            
            JPanel p = new JPanel(new BorderLayout());
            p.add(new JLabel("Diagonale"), BorderLayout.NORTH);
            SpinnerModel model = new SpinnerNumberModel(Main.optionManager.getSpielfelddiagonale(), 5, 25, 2);
            JSpinner spinner = new JSpinner(model);
            p.add(spinner);
            
            JOptionPane.showMessageDialog(null, p, "Diagonale (mit Wasser):", JOptionPane.INFORMATION_MESSAGE);
            int value = ((Integer) (spinner.getValue())).intValue();
            Main.optionManager.setSpielfelddiagonale(value);
            Main.gameManager.resetGame();
        }
    }      
    
    private boolean isGameRunning(){
        return Main.gameManager.getGameState() != GameManager.WAITING_FOR_GAMESTART;
    }
    
    private void showError(){
        JOptionPane.showMessageDialog(null, "Diese Einstellung kann nicht geändert werden, während das Spiel läuft.", "Fehler", JOptionPane.ERROR_MESSAGE);
    }
}