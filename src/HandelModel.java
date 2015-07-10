/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Speichert die handelnden Spieler und führt die Transaktion durch
 * @author Johannes
 */
public class HandelModel {
    
    private SpielerModel tradingPlayer;
    private SpielerModel tradedPlayer;
    private GameManager gameManager;

    public HandelModel(SpielerModel tradingPlayer, GameManager gm) {
        this.tradingPlayer = tradingPlayer;
        this.gameManager = gm;        
    }
    
    public SpielerModel getTradingPlayer() {
        return tradingPlayer;
    }

    public SpielerModel getTradedPlayer() {
        return tradedPlayer;
    }

    public void setTradedPlayer(SpielerModel tradedPlayer) {
        this.tradedPlayer = tradedPlayer;
    }
    
    /**
     * Diese Methode führt letzendlich die Transaktion aus. <br />
     * @params v = Die gehandelte Rohstoffanzahl
     * @params Die zu handelnde Ressource (Konstanten in SechseckModel)
     * @params direction = Die Richtung in der die Rohstoffe gehandelt werden.
     *     1 = tradingPlayer -> tradedPlayer
     *     2 = tradedPlayer -> tradingPlayer
     */
    public void performTransaction(int value,int ressource, int direction) {
        if (direction == 1) {
            this.tradedPlayer.addRohstoffe(ressource, value);
            this.tradingPlayer.remRohstoffe(ressource, value);
        } else {
            this.tradingPlayer.addRohstoffe(ressource, value);            
            this.tradedPlayer.remRohstoffe(ressource, value);
        }
    }    
}
