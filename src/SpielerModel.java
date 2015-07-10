/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Speichert Informationen über die einzelnen Spieler
 * wie z.B. Name, Farbe, Karten, Siegpunkte (SP), etc.
 *
 * @author jbrose
 */
public class SpielerModel extends Observable {

    private int siegpunkte;
    private String name;
    private Color farbe;
    private int[] rohstoffe;
    private ArrayList<Entwicklungskarte> entwicklungskarten;
    private ArrayList<Integer> handelsboni;
    private boolean initialCityBuild;
    private boolean initialWayBuild;

    public SpielerModel(Color color) {
        this(System.getProperty("user.name"), color);
    }

    public SpielerModel(String name, Color farbe) {
        this.name = name;
        this.farbe = farbe;
        this.siegpunkte = 0;
        this.rohstoffe = new int[5];

        for (int i = 0; i < rohstoffe.length; i++) {
            rohstoffe[i] += 0;
        }

        this.entwicklungskarten = new ArrayList<Entwicklungskarte>();
        this.handelsboni = new ArrayList<Integer>();
    }

    public boolean isInitialCityBuild() {
        return initialCityBuild;
    }

    public boolean isInitialWayBuild() {
        return initialWayBuild;
    }

    public boolean hasInitialPhaseCompleted() {
        return initialWayBuild && initialCityBuild;
    }

    public void resetInitials() {
        initialWayBuild = false;
        initialCityBuild = false;
    }

    public int getSiegpunkte() {
        return siegpunkte;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getFarbe() {
        return farbe;
    }

    public void setFarbe(Color farbe) {
        this.farbe = farbe;
        notifyer();
    }

    public void addHandelsbonus(int type) {
        if (!handelsboni.contains(new Integer(type))) {
            handelsboni.add(new Integer(type));
        }
    }

    public boolean hatHandelsbonus(int type) {
        return handelsboni.contains(new Integer(type));
    }

    public boolean hatEntwicklungskarten() {
        for (Entwicklungskarte k : entwicklungskarten) {
            if (!k.istGespielt()) return true;
        }

        return false;
    }

    public void addEntwicklungskarte(Entwicklungskarte karte) {
        entwicklungskarten.add(karte);
    }

    public boolean kannKarteKaufen() {
        return getAnzahlRohstoffe(SechseckModel.ERZ) >= 1 && getAnzahlRohstoffe(SechseckModel.GETREIDE) >= 1 && getAnzahlRohstoffe(SechseckModel.SCHAF) >= 1;
    }

    public void karteKaufen() {
        rohstoffe[SechseckModel.ERZ]--;
        rohstoffe[SechseckModel.GETREIDE]--;
        rohstoffe[SechseckModel.SCHAF]--;

        notifyer();
        Main.gameManager.notifyer();
    }

    public void addRohstoff(int typ) {
        rohstoffe[typ]++;

        notifyer();
    }

    public void addRohstoffe(int typ, int anzahl) {
        rohstoffe[typ] = rohstoffe[typ] + anzahl;
        notifyer();
    }

    public void remRohstoff(int typ) {
        if (rohstoffe[typ] >= 1) {
            rohstoffe[typ]--;
        }

        notifyer();
    }

    public void remRohstoffe(int typ, int anzahl) {
        for (int i = 0; i < anzahl; i++) {
            remRohstoff(typ);
        }

        notifyer();
    }

    public int getAnzahlRohstoffe(int typ) {
        return rohstoffe[typ];
    }

    public ArrayList<Entwicklungskarte> getEntwicklungskarten() {
        return entwicklungskarten;
    }

    public boolean kannWegBauen() {
        //In der Initialisierungsphase kostet bauen nichts.
        if (Main.gameManager.isInitialPhase()) {
            return true;
        }

        return (getAnzahlRohstoffe(SechseckModel.HOLZ) >= 1 && getAnzahlRohstoffe(SechseckModel.LEHM) >= 1);
    }

    public boolean kannSiedlungBauen() {
        //In der Initialisierungsphase kostet bauen nichts.
        if (Main.gameManager.isInitialPhase()) {
            return true;
        }

        return (getAnzahlRohstoffe(SechseckModel.GETREIDE) >= 1 && getAnzahlRohstoffe(SechseckModel.HOLZ) >= 1 && getAnzahlRohstoffe(SechseckModel.LEHM) >= 1 && getAnzahlRohstoffe(SechseckModel.SCHAF) >= 1);
    }

    public boolean kannStadtBauen() {
        return (getAnzahlRohstoffe(SechseckModel.GETREIDE) >= 2 && getAnzahlRohstoffe(SechseckModel.ERZ) >= 3);
    }

    public void wegBauen() {
        if (Main.gameManager.isInitialPhase()) {
            initialWayBuild = true;
        } else {
            rohstoffe[SechseckModel.LEHM]--;
            rohstoffe[SechseckModel.HOLZ]--;
        }

        notifyer();
    }

    public void addSiegpunkt() {
        siegpunkte++;
    }

    public void siedlungBauen() {
        if (Main.gameManager.isInitialPhase()) {
            initialCityBuild = true;
        } else {
            rohstoffe[SechseckModel.GETREIDE]--;
            rohstoffe[SechseckModel.HOLZ]--;
            rohstoffe[SechseckModel.LEHM]--;
            rohstoffe[SechseckModel.SCHAF]--;
        }

        siegpunkte++;

        notifyer();
    }

    public void stadtBauen() {
        rohstoffe[SechseckModel.ERZ]--;
        rohstoffe[SechseckModel.ERZ]--;
        rohstoffe[SechseckModel.ERZ]--;
        rohstoffe[SechseckModel.GETREIDE]--;
        rohstoffe[SechseckModel.GETREIDE]--;

        siegpunkte++;

        notifyer();
    }

    public void rohstoffeAbgeben() {
        int anzahl = 0;
        for (int i = 0; i < rohstoffe.length; i++) {
            anzahl = anzahl + rohstoffe[i];
        }
        if (anzahl > 7) {
            for (int i = 0; i < anzahl / 2; i++) {
                boolean entscheider = false;
                while (!entscheider) {
                    int j = (int) (Math.random() * rohstoffe.length);
                    if (rohstoffe[j] != 0) {
                        rohstoffe[j]--;
                        entscheider = true;
                    }
                }
            }
        }
        notifyer();
    }

    public void notifyer() {
        setChanged();
        notifyObservers();
    }
}
