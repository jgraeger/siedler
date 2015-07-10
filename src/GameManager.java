/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.thoughtworks.xstream.XStream;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *  Steuert den Programmablauf, speichert dazu Phase und Spielzustand
 *  Bietet Methoden zum Speichern und Laden von Spielständen an
 * @author jgraeger
 */

public class GameManager extends Observable {

    //Verschiedene Spielzustände
    public static final int WAITING_FOR_GAMESTART = 0;
    public static final int WAITING_FOR_INITIAL_DISTRIBUTION = 1;
    public static final int WAITING_FOR_INITIAL_DISTRIBUTION2 = 2;
    public static final int GAME_RUNNING = 3;
    //Verschiedene Spielphasen
    public static final int WAITING_FOR_DICE = 0;
    public static final int WAITING_FOR_BUILD_TRADE_FINISH = 1;
    public static final int WAITING_FOR_HOTZENPLOTZ_MOVING = 2;
    
    private int gameState;
    
    private SpielfeldModel spielfeldModel;
    
    private SpielfeldView spielfeldView;
    
    private SiedlerView siedlerView;
    
    private int phase;
    
    private int currentPlayer;
    
    private ArrayList<SpielerModel> players;
    
    private SechseckModel raeuberFeld;
    
    private Random random;

    public GameManager() {
        players = new ArrayList<SpielerModel>();
        random = new Random();

        raeuberFeld = null;

        currentPlayer = 0;
        gameState = WAITING_FOR_GAMESTART;
    }

    public void resetGame() {
        gameState = WAITING_FOR_GAMESTART;
        phase = WAITING_FOR_DICE;
        currentPlayer = 0;

        raeuberFeld = null;

        spielfeldModel = new SpielfeldModel();
        spielfeldView = new SpielfeldView(spielfeldModel);
        siedlerView.setSpielfeldView(spielfeldView);

        log("Das Spiel wurde neugestartet", true);

        players.clear();
        Entwicklungskarte.resetEntwicklungskarten();


        notifyer();
    }

    public void notifyer() {
        setChanged();
        notifyObservers();
    }

    private int zahlWuerfeln() {
        return random(6) + 1;
    }

    public SechseckModel getRaeuberFeld() {
        return raeuberFeld;
    }


    public void log(String logText, boolean isError) {
        if (isError) {
            siedlerView.getKonsole().cerr(logText);
        } else {
            siedlerView.getKonsole().cout(logText);
        }
    }

    public void wuerfeln() {
        int z1 = zahlWuerfeln(), z2 = zahlWuerfeln(), s = z1 + z2;


        //Grafisch anzeigen        
        JOptionPane.showMessageDialog(null, "Es wurde eine " + s + " gewürfelt.", "Würfelergebnis", JOptionPane.INFORMATION_MESSAGE);
        this.log("Es wurde eine " + s + " gewürfelt.", false);

        if (s == 7) {
            for (SpielerModel player : players) {
                player.rohstoffeAbgeben();
            }

            phase = WAITING_FOR_HOTZENPLOTZ_MOVING;
        } else {
            //Rohstoffe verteilen
            spielfeldModel.gibRohstoffe(s);
            phase = WAITING_FOR_BUILD_TRADE_FINISH;
        }

        setChanged();
        notifyObservers();
    }

    public void hotzenplotzMoved() {
        phase = WAITING_FOR_BUILD_TRADE_FINISH;
        setChanged();
        notifyObservers();
    }

    public void naechsteRunde() {
        if (getCurrentPlayer().getSiegpunkte() >= Main.optionManager.getSiegpunktlimit()) {
            JOptionPane.showMessageDialog(null, "Herzlichen Glückwunsch, " + getCurrentPlayer().getName() + ", du hast gewonnen!", "Sieg", JOptionPane.INFORMATION_MESSAGE);
            log(getCurrentPlayer().getName() + " hat das Spiel gewonnen!", false);
            log("Das Spiel wird sich in 30 Sekunden neustarten", true);

            new Thread() {
                @Override
                public void run() {
                    try {

                        Thread.sleep(30000);
                        resetGame();
                    } catch (InterruptedException ex) {
                        System.err.println("Ein schwerwiegender Fehler ist aufgetreten");
                    }
                }
            }.start();

            return;
        }

        currentPlayer++;
        currentPlayer %= Main.optionManager.getAnzahlSpieler();

        phase = WAITING_FOR_DICE;

        setChanged();
        notifyObservers();
    }

    public int getPhase() {
        return phase;
    }

    public boolean isInitialPhase() {
        return gameState == WAITING_FOR_INITIAL_DISTRIBUTION || gameState == WAITING_FOR_INITIAL_DISTRIBUTION2;
    }

    public int getGameState() {
        return gameState;
    }

    public SpielfeldModel getSpielfeldModel() {
        return spielfeldModel;
    }

    public ArrayList<SpielerModel> getPlayers() {
        return players;
    }

    public void setRaeuberFeld(SechseckModel newRaeuberFeld) {
        this.raeuberFeld = newRaeuberFeld;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void initSpielfeld() {
        spielfeldModel = new SpielfeldModel();

        spielfeldView = new SpielfeldView(spielfeldModel);

        siedlerView = new SiedlerView(spielfeldView);
    }

    public void startGame() {
        siedlerView.setVisible(true);
    }

    public void startNewGame() {

        for (int i = 0; i < Main.optionManager.getAnzahlSpieler(); i++) {
            players.add(new SpielerModel("Spieler " + (i + 1), new Color(random(0xffffff))));
        }


        //Warten, dass Spieler ihre Startpositionen wählen
        gameState = WAITING_FOR_INITIAL_DISTRIBUTION;

        log("Initialisierungsphase beginnt...", true);

        setChanged();
        notifyObservers();

        //Für den ersten Spieler anzeigen lassen, dass er eine Siedlung und eine Straße bauen muss
        //Für die darauffolgenden Spieler übernimmt dies initialBau()
        JOptionPane.showMessageDialog(null, getCurrentPlayer().getName() + ", baue bitte eine Siedlung und eine Straße.", "Statusmeldung", JOptionPane.INFORMATION_MESSAGE);
    }

    public void handel() {
        HandelModel hm = new HandelModel(getCurrentPlayer(), this);
        HandelControl hc = new HandelControl(hm,this);
        HandelView hv = new HandelView(hc);
    }

    public void initialBau() {
        if (gameState == WAITING_FOR_INITIAL_DISTRIBUTION) {
            if (currentPlayer < players.size() - 1) {
                currentPlayer++;
            } else {
                gameState = WAITING_FOR_INITIAL_DISTRIBUTION2;

                for (SpielerModel player : players) {
                    player.resetInitials();
                }
            }
        } else if (gameState == WAITING_FOR_INITIAL_DISTRIBUTION2) {
            if (currentPlayer > 0) {
                currentPlayer--;
            } else {
                gameState = GAME_RUNNING;
                log("...Initialisierungsphase abgeschlossen", true);
            }
        }

        setChanged();
        notifyObservers();

        if (gameState != GAME_RUNNING) {
            JOptionPane.showMessageDialog(null, getCurrentPlayer().getName() + ", baue bitte eine Siedlung und eine Straße.", "Statusmeldung", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean istBauphase() {
        return phase == WAITING_FOR_BUILD_TRADE_FINISH || gameState == WAITING_FOR_INITIAL_DISTRIBUTION || gameState == WAITING_FOR_INITIAL_DISTRIBUTION2;
    }

    public SpielerModel getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public SpielerModel getPlayer(int index) {
        return players.get(index);
    }

    public SpielerModel getPlayerByName(String name) {
        for (SpielerModel s : players) {
            if (s.getName().equals(name)) {
                return s;
            }
        }

        return null;
    }

    public int random(int bis) {
        return random.nextInt(bis);
    }

    public int getCurrentPlayerID() {
        return currentPlayer;
    }

    public void save(GameManager gm, OptionManager om) {
        ArrayList<Object> data = new ArrayList<Object>();
        data.add(gm);
        data.add(om);
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Catan Speicherstand(.csf)", "csf");
        fc.setFileFilter(filter);
        fc.setDialogTitle("Speichern unter");

        int state = fc.showSaveDialog(null);
        if (state == JFileChooser.APPROVE_OPTION) {
            this.log("<font color\"blue\">Der Speichervorgang läuft - Bitte warten sie einen Moment</font>", false);
            try {
                XStream stream = new XStream();
                stream.alias("GameManager", GameManager.class);
                stream.alias("OptionManager", OptionManager.class);
                BufferedOutputStream bw = new BufferedOutputStream(
                        new FileOutputStream(fc.getSelectedFile()));
                Writer w = new OutputStreamWriter(bw, "utf-8");

//                    w.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\\n");
                stream.toXML(data, bw);
                bw.close();
                w.close();
            } catch (IOException ex) {
                Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        this.log("<font color=\"blue\">Speichervorgang erfolgreich</font>", false);
    }

    public void load() {
        if (gameState == 0) {
            for (int i = 0; i < Main.optionManager.getAnzahlSpieler(); i++) {
                players.add(new SpielerModel("Spieler " + (i + 1), new Color(random(0xffffff))));
            }
            gameState = WAITING_FOR_INITIAL_DISTRIBUTION;
        }
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Catan Speicherstand(.csf)", "csf");
        fc.setFileFilter(filter);
        fc.setDialogTitle("Laden:");

        int state = fc.showOpenDialog(null);
        if (state == JFileChooser.APPROVE_OPTION) {
            int confirm = JOptionPane.showConfirmDialog(null, "Möchten sie wirklich einen Spielstand laden? Ihr bisheriger Fortschritt wird dabei gelöscht! Warnung: Der ladevorgang kann einige Zeit dauern.", "Bitte bestätigen", JOptionPane.YES_NO_OPTION);
            if (confirm == 0) {
                this.log("<font color=\"blue\">Der Ladevorgang läuft. Bitte warten </font>", false);
                InputStreamReader input = null;
                try {
                    XStream stream = new XStream();
                    stream.alias("GameManager", GameManager.class);
                    stream.alias("OptionManager", OptionManager.class);
                    ArrayList<Object> data = new ArrayList<Object>();
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fc.getSelectedFile()));
                    input = new InputStreamReader(bis, "utf-8");
                    naechsteRunde();
                    data = (ArrayList<Object>) stream.fromXML(input);
                    bis.close();
                    input.close();
                    GameManager gm = (GameManager) data.get(0);
                    Main.optionManager = (OptionManager) data.get(1);

                    this.gameState = gm.getGameState();
                    this.players = gm.getPlayers();
                    this.phase = gm.getPhase();
                    this.raeuberFeld = gm.getRaeuberFeld();
                    this.spielfeldModel = gm.getSpielfeldModel();
                    this.siedlerView.dispose();

                    this.spielfeldView = new SpielfeldView(spielfeldModel);
                    this.siedlerView = new SiedlerView(spielfeldView);
                    startGame();
                    this.currentPlayer = gm.getCurrentPlayerID();
                    this.notifyer();
                    this.spielfeldModel.sechseckNotifyer();
                } catch (FileNotFoundException ex) {
                    this.log("Die Angegebenen Datei existiert nicht", true);
                } catch (UnsupportedEncodingException ex) {
                    this.log("Die Codierung der Datei konnte nicht erkannt werden", true);
                } catch (IOException ex) {
                    this.log("Während des Ladevorgangs ist ein Fehler aufgetreten (StreamError)", true);
                } finally {
                    try {
                        input.close();
                    } catch (IOException ex) {
                        this.log("Während des Ladevorgangs ist ein Fehler aufgetreten (StreamError)", true);
                    }
                }

                this.spielfeldModel.initialUpdate();
                this.log("<font color=\"blue\">Ladevorgang erfolgreich!</font>", false);
            }
        }
    }
}