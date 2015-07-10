/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
/**
 *  Die Benutzeroberfläche, die zum Handeln, Würfeln, Zug beenden, etc. genutzt wird
 * @author jgraeger
 */
public class AktionsPanel extends JPanel implements Observer {

    //Dekleration der Elementsvaraiblen
    private JButton btnRoll;
    private JButton btnTrade;
    private JButton btnBuyCard;
    private JButton btnPlayCard;
    private JButton btnEnd;    
    private JLabel  lblCurrentPlayer;
    
    public AktionsPanel(AktionsControl ac) {
        Main.gameManager.addObserver(this);
        this.setLayout(new GridLayout(6,1,1,5));

        btnRoll = new JButton();
        btnRoll.setText("Würfeln");
        btnRoll.setActionCommand("throwDices");
        btnRoll.addActionListener(ac);
        this.add(btnRoll);

        btnTrade = new JButton();
        btnTrade.setText("Handeln");
        btnTrade.setActionCommand("trade");
        btnTrade.addActionListener(ac);
        this.add(btnTrade);

        btnBuyCard = new JButton();
        btnBuyCard.setText("Karte kaufen");
        btnBuyCard.setActionCommand("buyCard");
        btnBuyCard.addActionListener(ac);
        this.add(btnBuyCard);
        
        btnPlayCard = new JButton();
        btnPlayCard.setText("Karte spielen");
        btnPlayCard.setActionCommand("playCard");
        btnPlayCard.addActionListener(ac);
        this.add(btnPlayCard);
                       
        btnEnd = new JButton();
        btnEnd.setText("Zug beenden");
        btnEnd.setActionCommand("nextTurn");
        btnEnd.addActionListener(ac);
        this.add(btnEnd);
        
        lblCurrentPlayer = new JLabel();
        lblCurrentPlayer.setText("Spiel läuft nicht");
        this.add(lblCurrentPlayer);        
        
        enableButtons(GameManager.WAITING_FOR_DICE);
    }

    @Override
    public void update(Observable o, Object o1) {
        GameManager gm = (GameManager) o;
        enableButtons(gm.getPhase());
    }

    private void enableButtons(int state){      
        if (Main.gameManager.getGameState() == GameManager.WAITING_FOR_GAMESTART){
            btnRoll.setEnabled(false);
            btnTrade.setEnabled(false);
            btnBuyCard.setEnabled(false);
            btnPlayCard.setEnabled(false);
            btnEnd.setEnabled(false);
            lblCurrentPlayer.setText("Spiel läuft nicht");
        } else if (Main.gameManager.isInitialPhase()){            
            btnRoll.setEnabled(false);
            btnTrade.setEnabled(false);
            btnBuyCard.setEnabled(false);
            btnPlayCard.setEnabled(false);
            btnEnd.setEnabled(false);
            lblCurrentPlayer.setText(Main.gameManager.getCurrentPlayer().getName() + " ist am Zug");
        } else if (state == GameManager.WAITING_FOR_DICE){
            btnRoll.setEnabled(true);
            btnTrade.setEnabled(false);
            btnBuyCard.setEnabled(false);
            btnPlayCard.setEnabled(false);
            btnEnd.setEnabled(false);
            lblCurrentPlayer.setText(Main.gameManager.getCurrentPlayer().getName() + " ist am Zug");
        } else if (state == GameManager.WAITING_FOR_BUILD_TRADE_FINISH){
            btnRoll.setEnabled(false);
            btnTrade.setEnabled(true);
            btnBuyCard.setEnabled(Main.gameManager.getCurrentPlayer().kannKarteKaufen());
            btnPlayCard.setEnabled(Main.gameManager.getCurrentPlayer().hatEntwicklungskarten());
            btnEnd.setEnabled(true);
            lblCurrentPlayer.setText(Main.gameManager.getCurrentPlayer().getName() + " ist am Zug");            
        } else if (state == GameManager.WAITING_FOR_HOTZENPLOTZ_MOVING){
            btnRoll.setEnabled(false);
            btnTrade.setEnabled(false);
            btnBuyCard.setEnabled(false);
            btnPlayCard.setEnabled(false);
            btnEnd.setEnabled(false);
            lblCurrentPlayer.setText(Main.gameManager.getCurrentPlayer().getName() + " muss den Räuber versetzen");
        }
    }
}