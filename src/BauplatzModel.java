/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Observable;

/**
 *  Speichert den Zustand eines Bauplatzes
 *  Ein Bauplatz kann die Zustände UNBEBAUT, SIEDLUNG, STADT und WEG haben
 * @author jbrose
 */
public class BauplatzModel extends Observable {


    private int status;
    private int besitzer;
    
    //nur für Siedlungen
    private int hafenbonus;
    
    public static final int UNBEBAUT = 0;
    public static final int SIEDLUNG = 1;
    public static final int STADT = 2;
    public static final int WEG = 3;

    public BauplatzModel() {
        besitzer = -1;
    }

    public int getBesitzer() {
        return besitzer;
    }

    public int getStatus() {
        return status;
    }

    public void setHafenbonus(int type){       
        hafenbonus = type;
    }
    
    public void siedlungBauen(boolean notifyView){
        this.besitzer = Main.gameManager.getCurrentPlayerID();
        this.status = BauplatzModel.SIEDLUNG;

        if (hafenbonus != 0){
            Main.gameManager.getCurrentPlayer().addHandelsbonus(hafenbonus);
        }
        
        if (notifyView){
            setChanged();
            notifyObservers();
        }                
    }

    public void stadtBauen(boolean notifyView){        
        this.status = BauplatzModel.STADT;

        if (notifyView){
            setChanged();
            notifyObservers();
        }
    }

    public void wegBauen(boolean notifyView, int bauplatzID){

        this.besitzer = Main.gameManager.getCurrentPlayerID();
        this.status = BauplatzModel.WEG;

        if (notifyView){
            setChanged();
            notifyObservers(new Integer(bauplatzID % 3));
        }    
    }
    
    public void notifyer(Object arg) {
        setChanged();
        notifyObservers(arg);
    }
    
    public void notifyer(){
        notifyer(null);
    }
}
