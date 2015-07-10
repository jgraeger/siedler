/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 *  Repräsentiert ein Sechseck.
 *  Speichert dessen Typ, Zahl, ob es Räuberstandort ist, etc.
 * @author jbrose
 */

public class SechseckModel extends Observable {
    
    public static final int GETREIDE = 0;
    public static final int SCHAF = 1;
    public static final int LEHM = 2;
    public static final int ERZ = 3;
    public static final int HOLZ = 4;
    public static final int WUESTE = 5;
    public static final int WASSER = 6;
    public static final int HAFEN_GETREIDE = 7;
    public static final int HAFEN_SCHAF = 8;
    public static final int HAFEN_LEHM = 9;
    public static final int HAFEN_ERZ = 10;
    public static final int HAFEN_HOLZ = 11;
    public static final int HAFEN_ALLGEMEIN = 12;

    public static final int LINKS = 0;
    public static final int UNTEN_LINKS = 1;
    public static final int UNTEN_RECHTS = 2;
    public static final int RECHTS = 3;
    public static final int OBEN_RECHTS = 4;
    public static final int OBEN_LINKS = 5;
    
    public static final int LINKS_OBEN = 0;
    public static final int LINKS_UNTEN = 1;
    public static final int UNTEN = 2;
    public static final int RECHTS_UNTEN = 3;
    public static final int RECHTS_OBEN = 4;
    public static final int OBEN = 5;


    private SpielfeldModel s;
    private boolean raeuber;
    
    private int nummer;
    private int typ;

    private int i;
    private int j;
    
    private BauplatzModel[] siedlungen;
    private BauplatzModel[] wege;
    
    public SechseckModel(int typ, int nummer, int j, int i, SpielfeldModel s){
        this.typ = typ;
        this.nummer = nummer;
        this.j = j;
        this.i = i;
        this.s = s;

        siedlungen = new BauplatzModel[6];
        wege = new BauplatzModel[6];
               
        for (int x = 0;x < 6;x++){
           siedlungen[x] = new BauplatzModel();
           wege[x] = new BauplatzModel();
           
           int bonusLocation = (((360-SpielfeldView.getDrehwinkel(j, i))/60)+3)%6;
           int bonusLocation2 = (bonusLocation+1)%6;
           if ((typ >= HAFEN_GETREIDE && typ <= HAFEN_ALLGEMEIN) && (x == bonusLocation || x == bonusLocation2)){               
               siedlungen[x].setHafenbonus(typ);
           }
                                 
        }        
        
        raeuber = (typ == WUESTE) && (Main.gameManager.getRaeuberFeld() == null);
        
        if (raeuber){
            Main.gameManager.setRaeuberFeld(this);
        }
    }
    
    public boolean istWasserfeld(){
        return typ >= WASSER && typ <= HAFEN_ALLGEMEIN;
    }

    public BauplatzModel getSiedlung(int index){
        return siedlungen[index];
    }
    
    public BauplatzModel getWeg(int index){
        return wege[index];
    }
    
    public void raueberWegsetzen(){
        raeuber = false;
        setChanged();
        notifyObservers();
    }
    
    public void raeuberHinsetzen(){
        raeuber = true;
        setChanged();
        notifyObservers();
    }
    
    public boolean hatRaeuber(){
        return raeuber;
    }
    
    public SpielerModel[] getOwningPlayers(){
        ArrayList<SpielerModel> players = new ArrayList<SpielerModel>();
        
        SpielerModel p;
        int owner;
        for (BauplatzModel b : siedlungen){
            owner = b.getBesitzer();
           
            if (owner == -1) continue;
            
            p = Main.gameManager.getPlayer(owner);
            
            if (!players.contains(p)) {
                players.add(p);                
            }
        }
        
        if (players.isEmpty()) return null;
        return players.toArray(new SpielerModel[players.size()]);
    }
    
    public int getTyp(){
        return typ;
    }
    
    public int getNummer(){
        return nummer;
    }

    public boolean istDorfBaubar(int location){
		int r, l;
		r = (location+1) % 6;
		l = (location+5) % 6;

          return siedlungen[l].getStatus() == BauplatzModel.UNBEBAUT && siedlungen[r].getStatus() == BauplatzModel.UNBEBAUT;
    }

    public boolean infrastrukturVorhanden(int location){

         int l, r;
        
         //Wenn sich das Spiel nicht in der Initialisierungsphase befindet, muss eine Verkehrsanbindung verfügbar sein, damit Siedlungen gebaut werden dürfen
         if (Main.gameManager.isInitialPhase()){
              return true;
          } else {
              l = location;
              r = (location+1) % 6;
                    
              return (wege[l].getStatus() == BauplatzModel.WEG && wege[l].getBesitzer() == Main.gameManager.getCurrentPlayerID()) || (wege[r].getStatus() == BauplatzModel.WEG && wege[r].getBesitzer() == Main.gameManager.getCurrentPlayerID());
          }
    }
    
    private int getI(int rl, int hr){
		int erg;

		if (j == 0){
			if (hr == OBEN){
				erg = i-1;
			} else {
				erg = i;
			}
		} else if (j > 0){
			if (rl == RECHTS && hr == OBEN){
				erg = i-1;
			} else if (rl == RECHTS && hr == UNTEN){
				erg = i;
			} else if (rl == LINKS && hr == OBEN){
				erg = i;
			} else {
				erg = i+1;
			}
		} else {
			if (rl == RECHTS && hr == OBEN){
				erg = i;
			} else if (rl == RECHTS && hr == UNTEN){
				erg = i+1;
			} else if (rl == LINKS && hr == OBEN){
				erg = i-1;
			} else {
				erg = i;
			}
		}

		return erg;
	}

	private void dorfbau(int location, boolean notifyView){
		//System.out.println(this + " baut auf Bauplatz " + location);
		//Baut nur ein Dorf, f�hrt aber keine Berechtigungs�berpr�fungen durch
		if (siedlungen[location].getStatus() == BauplatzModel.UNBEBAUT){
                    siedlungen[location].siedlungBauen(notifyView);
                    
                    //Wenn sich das Spiel in der Initialisierungsphase befindet, werden in der 2. Runde Rohstoffe vergeben
                    if (Main.gameManager.getGameState() == GameManager.WAITING_FOR_INITIAL_DISTRIBUTION2){
                        if (typ >= GETREIDE && typ <= HOLZ){
                            Main.gameManager.getCurrentPlayer().addRohstoff(typ);
                        }
                    }
		} else if (siedlungen[location].getStatus() == BauplatzModel.SIEDLUNG){
                    siedlungen[location].stadtBauen(notifyView);
                }
                
	}

	public void baueDorf(int location){
                               
		SechseckModel p1, p2;
		if (location == RECHTS){
			p1 = s.getFeld(j+1, getI(RECHTS, OBEN));
			p2 = s.getFeld(j+1, getI(RECHTS, UNTEN));
		} else if (location == UNTEN_RECHTS){
			p1 = s.getFeld(j+1, getI(RECHTS, UNTEN));
			p2 = s.getFeld(j, i+1);
		} else if (location == UNTEN_LINKS){
			p1 = s.getFeld(j, i+1);
			p2 = s.getFeld(j-1, getI(LINKS, UNTEN));
		} else if (location == LINKS){
			p1 = s.getFeld(j-1, getI(LINKS, UNTEN));
			p2 = s.getFeld(j-1, getI(LINKS, OBEN));
		} else if (location == OBEN_LINKS){
			p1 = s.getFeld(j-1, getI(LINKS, OBEN));
			p2 = s.getFeld(j, i-1);
		} else {
			p1 = s.getFeld(j, i-1);
			p2 = s.getFeld(j+1, getI(RECHTS, OBEN));
		}

		if (p1 == null || p2 == null){
			JOptionPane.showMessageDialog(null, "Siedlungen können nur auf Festland gebaut werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}

                //Prüfen, ob die Siedlung schon dem aktuellen Spieler gehört
                if (siedlungen[location].getBesitzer() != Main.gameManager.getCurrentPlayerID() && siedlungen[location].getStatus() != BauplatzModel.UNBEBAUT){
                    JOptionPane.showMessageDialog(null, "Die Siedlung gehört dem aktuellen Spieler nicht", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
		int p1loc, p2loc;
		p1loc = (location+4) % 6;
		p2loc = (location+2) % 6;

		if (!istDorfBaubar(location) || !p1.istDorfBaubar(p1loc) || !p2.istDorfBaubar(p2loc)){
			JOptionPane.showMessageDialog(null, "Siedlungen und Städte müssen immer mindestens zwei Straßen Abstand zueinander haben.", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (siedlungen[location].getStatus() == BauplatzModel.STADT){
			JOptionPane.showMessageDialog(null, "Bereits vollständig ausgebaut!", "Fehler", JOptionPane.ERROR_MESSAGE);
			return;
		}

                //Prüfen, ob sich das Spiel in der INITIAL-DISTRIBUTION-Phase befindet und ob schon eine Siedlung gebaut wurde
                if (Main.gameManager.isInitialPhase()){
                    if (Main.gameManager.getCurrentPlayer().isInitialCityBuild()){
                        JOptionPane.showMessageDialog(null, "Es wurde bereits eine Siedlung gebaut.", "Fehler", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                if (siedlungen[location].getStatus() == BauplatzModel.UNBEBAUT){
                    if (!Main.gameManager.getCurrentPlayer().kannSiedlungBauen()){
                        JOptionPane.showMessageDialog(null, "Die Rohstoffe reichen nicht aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else if (siedlungen[location].getStatus() == BauplatzModel.SIEDLUNG){
                    if (!Main.gameManager.getCurrentPlayer().kannStadtBauen()){
                        JOptionPane.showMessageDialog(null, "Die Rohstoffe reichen nicht aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                //Prüfen, ob die nötige Infrastruktur (-> eine Straße) vorhanden ist{
                if (infrastrukturVorhanden(location) || p1.infrastrukturVorhanden(p1loc) || p2.infrastrukturVorhanden(p2loc)){
                    //Wenn die Infrastruktur (=Straßenanbindung) da ist, ist alles ok
                } else {
                    JOptionPane.showMessageDialog(null, "Zu dieser Stelle besteht keine Verbindung.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                //Dem SpielerModel Bescheid geben, dass gebaut wird
                if (siedlungen[location].getStatus() == BauplatzModel.UNBEBAUT){
                    Main.gameManager.getCurrentPlayer().siedlungBauen();
                    Main.gameManager.log(Main.gameManager.getCurrentPlayer().getName() + " hat eine Siedlung gebaut", false);
                } else if (siedlungen[location].getStatus() == BauplatzModel.SIEDLUNG){
                    Main.gameManager.getCurrentPlayer().stadtBauen();
                    Main.gameManager.log(Main.gameManager.getCurrentPlayer().getName() + " hat eine Stadt gebaut", false);
                }                                
                                
		dorfbau(location, true);
		p1.dorfbau(p1loc, false);
		p2.dorfbau(p2loc, false);
	}

        public void baueWeg(int location){            
            //Ist bereits ein Weg gebaut?
            if (wege[location].getStatus() == BauplatzModel.WEG){
                JOptionPane.showMessageDialog(null, "Dort befindet sich bereits eine Straße", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            SechseckModel sm = null;
            
            if (location == LINKS_OBEN){
                sm = s.getFeld(j-1, getI(LINKS, OBEN));
            } else if (location == LINKS_UNTEN){
                sm = s.getFeld(j-1, getI(LINKS, UNTEN));
            } else if (location == UNTEN){
                sm = s.getFeld(j, i+1);
            } else if (location == RECHTS_UNTEN){
                sm = s.getFeld(j+1, getI(RECHTS, UNTEN));                
            } else if (location == RECHTS_OBEN){
                sm = s.getFeld(j+1, getI(RECHTS, OBEN));
            } else if (location == OBEN){
                sm = s.getFeld(j, i-1);
            } else {
                System.out.println("Unbekannte Location: " + location);
            }
            
            if (sm == null){
                System.out.println("test: " + typ);
            }
                        
            //Befindet sich das Spiel in der Initialisierungsphase und es wurde bereits ein Weg gebaut?
            if (Main.gameManager.isInitialPhase()){
                if (Main.gameManager.getCurrentPlayer().isInitialWayBuild()){                    
                    JOptionPane.showMessageDialog(null, "Es wurde bereits ein Weg gebaut.", "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            if (!Main.gameManager.getCurrentPlayer().kannWegBauen()){
                JOptionPane.showMessageDialog(null, "Die Rohstoffe reichen nicht aus.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }                                              
                        
            int smLoc = (location+3) % 6;
            
            if (Main.gameManager.isInitialPhase() && !Main.gameManager.getCurrentPlayer().isInitialCityBuild()){
                JOptionPane.showMessageDialog(null, "Zuerst muss eine Siedlung gebaut werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (istWegBaubar(location) || sm.istWegBaubar(smLoc)){
                wegbau(location, true);
                sm.wegbau(smLoc, false);
                                
                //Dem SpielerModel Bescheid geben, dass gebaut wird
                Main.gameManager.getCurrentPlayer().wegBauen();
                Main.gameManager.log(Main.gameManager.getCurrentPlayer().getName() + " hat eine Stra&szlig;e gebaut", false);
                
                //Wenn das Spiel sich in der INITIAL-DISTRIBUTION-Phase befindet, muss der GameManager über das Bauen
                //des Weges informiert werden, da die Spieler hier übergangslos, d.h. ohne Knopfdruck wechseln
                //Mit dem Bau der Straße wird angezeigt, dass der aktuelle Spieler seine Siedlung und seine Straße gebaut hat
                if (Main.gameManager.getCurrentPlayer().hasInitialPhaseCompleted()){
                    Main.gameManager.initialBau();
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Zu dieser Stelle besteht keine Verbindung", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        public void wegbau(int location, boolean notifyView){
            //Baut einen Weg, ohne Berechtigungsüberprüfungen durchzuführen
            wege[location].wegBauen(notifyView, location);            
        }
        
        public boolean istWegBaubar(int location){
            int linkeSiedlung = (location+5)%6;
            
            if (siedlungen[linkeSiedlung].getStatus() != BauplatzModel.UNBEBAUT || siedlungen[location].getStatus() != BauplatzModel.UNBEBAUT){
                return true;
            }
            
            int linkerWeg = (location+5)%6;
            int rechterWeg = (location+1)%6;
            
            if ((wege[linkerWeg].getStatus() == BauplatzModel.WEG && wege[linkerWeg].getBesitzer() == Main.gameManager.getCurrentPlayerID())|| (wege[rechterWeg].getStatus() == BauplatzModel.WEG && wege[rechterWeg].getBesitzer() == Main.gameManager.getCurrentPlayerID())){
                return true;
            }
                        
            return false;
        }

        public void gibRohstoffe(){
            if (raeuber) return;

            for (BauplatzModel b : siedlungen){
                if (b.getStatus() == BauplatzModel.SIEDLUNG){
                    Main.gameManager.getPlayer(b.getBesitzer()).addRohstoff(typ);
                    Main.gameManager.log(Main.gameManager.getPlayer(b.getBesitzer()).getName() + " erh&auml;lt ein " + RohstoffView.ROHSTOFFNAMEN[typ], false);
                } else if (b.getStatus() == BauplatzModel.STADT){
                    Main.gameManager.getPlayer(b.getBesitzer()).addRohstoff(typ);
                    Main.gameManager.getPlayer(b.getBesitzer()).addRohstoff(typ);
                    Main.gameManager.log(Main.gameManager.getPlayer(b.getBesitzer()).getName() + " erh&auml;lt zwei " + RohstoffView.ROHSTOFFNAMEN[typ], false);
                }
            }                    
        }
        
        public void notifyer() {
            for (BauplatzModel bm : siedlungen) {
                bm.notifyer();
            }
            for (int i = 0;i < 6;i++){
                wege[i].notifyer(new Integer(i%3));
            }
            setChanged();
            notifyObservers();
        }
}