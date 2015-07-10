/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Observable;

/**
 * Speichert die Einstellungen des Spiels
 * @author jbrose
 */
public class OptionManager extends Observable {
    private int siegpunktlimit = 10;

    private int anzahlSpieler = 3;
    
    private int diagonale = 7;
    
    private boolean diceAnimation;
    
    
    public int getSiegpunktlimit() {
        return siegpunktlimit;
    }

    public void setSiegpunktlimit(int siegpunkt) {
        siegpunktlimit = siegpunkt;
    }
    
    public int getAnzahlSpieler(){
        return anzahlSpieler;
    }
    
    public void setAnzahlSpieler(int anzahl){
        this.anzahlSpieler = anzahl;
    }
    
    public int getSpielfelddiagonale(){
        return diagonale;
    }
    
    public void setSpielfelddiagonale(int diagonale){
        this.diagonale = diagonale;
    }
    
    public void setDiceAnimation(boolean on){
        this.diceAnimation = on;
    }
    
    public boolean getDiceAnimation(){
        return diceAnimation;
    }
}