/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 *  Die Benutzeroberfläche, über die Handel betrieben werden kann
 * @author jgraeger
 */

public class HandelView extends JFrame {

	    private JPanel contentPane;
        private JLabel lblTradingPlayer;
        private JLabel lblTradedPlayer;
        private JLabel lblTrade;
        
        private JComboBox cbPlayer;
        private JSpinner tradingPlayerWheat;
        private JSpinner tradingPlayerSheep;
        private JSpinner tradingPlayerIron;
        private JSpinner tradingPlayerWood;
        private JSpinner tradingPlayerBrick;
        private JSpinner tradedPlayerWheat;
        private JSpinner tradedPlayerSheep;
        private JSpinner tradedPlayerIron;
        private JSpinner tradedPlayerWood;
        private JSpinner tradedPlayerBrick;
                
        private ArrayList<JSpinner> tradingPlayerRessources;
        private ArrayList<JSpinner> tradedPlayerRessources;

	/**
	 * Create the frame.
	 */
	public HandelView(HandelControl c) {
		setTitle("Handeln");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 100, 300, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
                
                this.tradingPlayerRessources = new ArrayList<JSpinner>();
                this.tradedPlayerRessources = new ArrayList<JSpinner>();
		
		JPanel pnlContent = new JPanel();
		contentPane.add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel pnlTradingPlayer = new JPanel();
		pnlTradingPlayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		pnlContent.add(pnlTradingPlayer);
		pnlTradingPlayer.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTradingPlayerHead = new JPanel();
		pnlTradingPlayerHead.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		FlowLayout fl_pnlTradingPlayerHead = (FlowLayout) pnlTradingPlayerHead.getLayout();
		fl_pnlTradingPlayerHead.setAlignment(FlowLayout.LEFT);
		pnlTradingPlayer.add(pnlTradingPlayerHead, BorderLayout.NORTH);
		
		lblTradingPlayer = new JLabel(Main.gameManager.getCurrentPlayer().getName() + " gibt:");
		lblTradingPlayer.setFont(new Font("Tahoma", Font.BOLD, 11));
		pnlTradingPlayerHead.add(lblTradingPlayer);
		
		JPanel pnlTradingPlayerContent = new JPanel();
		pnlTradingPlayer.add(pnlTradingPlayerContent, BorderLayout.CENTER);
		pnlTradingPlayerContent.setLayout(new GridLayout(3, 2, 0, 0));
		
		JPanel pnlTradingPlayerContentWheat = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlTradingPlayerContentWheat.getLayout();
		pnlTradingPlayerContent.add(pnlTradingPlayerContentWheat);
		
		JLabel lblTradingPlayerWheat = new JLabel(ImageManager.getImageIcon("img/sonstige/iconGetreide.png", new Dimension(30, 30)));
		lblTradingPlayerWheat.setFont(new Font("Tahoma", Font.BOLD, 10));
		pnlTradingPlayerContentWheat.add(lblTradingPlayerWheat);
		                
		tradingPlayerWheat = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
                tradingPlayerRessources.add(tradingPlayerWheat);
		pnlTradingPlayerContentWheat.add(tradingPlayerWheat);
		
		JPanel pnlTradingPlayerContentSheep = new JPanel();
		pnlTradingPlayerContent.add(pnlTradingPlayerContentSheep);
		
		JLabel lblTradingPlayerSheep = new JLabel(ImageManager.getImageIcon("img/sonstige/iconSchaf.png", new Dimension(30, 30)));
		pnlTradingPlayerContentSheep.add(lblTradingPlayerSheep);
		lblTradingPlayerSheep.setFont(new Font("Tahoma", Font.BOLD, 10));
		
		tradingPlayerSheep = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
                tradingPlayerRessources.add(tradingPlayerSheep);
		pnlTradingPlayerContentSheep.add(tradingPlayerSheep);
		
                JPanel pnlTradingPlayerContentBrick = new JPanel();
		pnlTradingPlayerContent.add(pnlTradingPlayerContentBrick);
				
                JLabel lblTradingPlayerBrick = new JLabel(ImageManager.getImageIcon("img/sonstige/iconLehm.png", new Dimension(30, 30)));
		lblTradingPlayerBrick.setFont(new Font("Tahoma", Font.BOLD, 10));
		pnlTradingPlayerContentBrick.add(lblTradingPlayerBrick);		
                
		tradingPlayerBrick = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
                tradingPlayerRessources.add(tradingPlayerBrick);
		pnlTradingPlayerContentBrick.add(tradingPlayerBrick);
                
		JPanel pnlTradingPlayerContentIron = new JPanel();
		pnlTradingPlayerContent.add(pnlTradingPlayerContentIron);
		
		JLabel lblTradingPlayerIron = new JLabel(ImageManager.getImageIcon("img/sonstige/iconErz.png", new Dimension(30, 30)));
		lblTradingPlayerIron.setFont(new Font("Tahoma", Font.BOLD, 10));
		pnlTradingPlayerContentIron.add(lblTradingPlayerIron);
		
		tradingPlayerIron = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
                tradingPlayerRessources.add(tradingPlayerIron);
		pnlTradingPlayerContentIron.add(tradingPlayerIron);
		
		JPanel pnlTradingPlayerContentWood = new JPanel();
		pnlTradingPlayerContent.add(pnlTradingPlayerContentWood);
		
		JLabel lblTradingPlayerWood = new JLabel(ImageManager.getImageIcon("img/sonstige/iconHolz.png", new Dimension(30, 30)));
		lblTradingPlayerWood.setFont(new Font("Tahoma", Font.BOLD, 10));
		pnlTradingPlayerContentWood.add(lblTradingPlayerWood);
		
		tradingPlayerWood = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
                tradingPlayerRessources.add(tradingPlayerWood);
		pnlTradingPlayerContentWood.add(tradingPlayerWood);
						
		JPanel pnlTradedPlayer = new JPanel();
		pnlTradedPlayer.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnlContent.add(pnlTradedPlayer);
		pnlTradedPlayer.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTradedPlayerHead = new JPanel();
		FlowLayout fl_pnlTradedPlayerHead = (FlowLayout) pnlTradedPlayerHead.getLayout();
		fl_pnlTradedPlayerHead.setAlignment(FlowLayout.LEFT);
		pnlTradedPlayerHead.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlTradedPlayer.add(pnlTradedPlayerHead,BorderLayout.NORTH);
		
		lblTradedPlayer = new JLabel(Main.gameManager.getCurrentPlayer().getName() + " fordert:");
		lblTradedPlayer.setFont(new Font("Tahoma", Font.BOLD, 11));
		pnlTradedPlayerHead.add(lblTradedPlayer);
		
		JPanel pnlTradedPlayerContent = new JPanel();
		pnlTradedPlayer.add(pnlTradedPlayerContent);
		pnlTradedPlayerContent.setLayout(new GridLayout(3, 2, 0, 0));
		
		JPanel pnlTradedPlayerContentWheat = new JPanel();
		pnlTradedPlayerContent.add(pnlTradedPlayerContentWheat);
		
		JLabel lblTradedPlayerWheat_1 = new JLabel(ImageManager.getImageIcon("img/sonstige/iconGetreide.png", new Dimension(30, 30)));
		lblTradedPlayerWheat_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		pnlTradedPlayerContentWheat.add(lblTradedPlayerWheat_1);
		
		tradedPlayerWheat = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
                tradedPlayerRessources.add(tradedPlayerWheat);
		pnlTradedPlayerContentWheat.add(tradedPlayerWheat);
		
		JPanel pnlTradedPlayerContentSheep = new JPanel();
		pnlTradedPlayerContent.add(pnlTradedPlayerContentSheep);
		
		JLabel lblTradedPlayerSheep = new JLabel(ImageManager.getImageIcon("img/sonstige/iconSchaf.png", new Dimension(30, 30)));
		lblTradedPlayerSheep.setFont(new Font("Tahoma", Font.BOLD, 10));
		pnlTradedPlayerContentSheep.add(lblTradedPlayerSheep);
		
		tradedPlayerSheep = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
                tradedPlayerRessources.add(tradedPlayerSheep);
		pnlTradedPlayerContentSheep.add(tradedPlayerSheep);
		
                JPanel pnlTradedPlayerContentBrick = new JPanel();
		pnlTradedPlayerContent.add(pnlTradedPlayerContentBrick);
		
		JLabel lblTradedPlayerBrick = new JLabel(ImageManager.getImageIcon("img/sonstige/iconLehm.png", new Dimension(30, 30)));
		lblTradedPlayerBrick.setFont(new Font("Tahoma", Font.BOLD, 10));
		pnlTradedPlayerContentBrick.add(lblTradedPlayerBrick);
		
		tradedPlayerBrick = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
                tradedPlayerRessources.add(tradedPlayerBrick);
		pnlTradedPlayerContentBrick.add(tradedPlayerBrick);
                
		JPanel pnlTradedPlayerContentIron = new JPanel();
		pnlTradedPlayerContent.add(pnlTradedPlayerContentIron);
		
		JLabel lblTradedPlayerIron = new JLabel(ImageManager.getImageIcon("img/sonstige/iconErz.png", new Dimension(30, 30)));
		lblTradedPlayerIron.setFont(new Font("Tahoma", Font.BOLD, 10));
		pnlTradedPlayerContentIron.add(lblTradedPlayerIron);
		
		tradedPlayerIron = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
                tradedPlayerRessources.add(tradedPlayerIron);
		pnlTradedPlayerContentIron.add(tradedPlayerIron);
		
		JPanel pnlTradedPlayerContentWood = new JPanel();
		pnlTradedPlayerContent.add(pnlTradedPlayerContentWood);
		
		JLabel lblTradedPlayerWood = new JLabel(ImageManager.getImageIcon("img/sonstige/iconHolz.png", new Dimension(30, 30)));
		lblTradedPlayerWood.setFont(new Font("Tahoma", Font.BOLD, 10));
		pnlTradedPlayerContentWood.add(lblTradedPlayerWood);
		
		tradedPlayerWood = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
                tradedPlayerRessources.add(tradedPlayerWood);
		pnlTradedPlayerContentWood.add(tradedPlayerWood);
						
		JPanel pnlHead = new JPanel();
		contentPane.add(pnlHead, BorderLayout.NORTH);
		
		lblTrade = new JLabel(Main.gameManager.getCurrentPlayer().getName() + " handelt mit:");
		lblTrade.setFont(new Font("Tahoma", Font.BOLD, 12));
		pnlHead.add(lblTrade);
		                                                                
                ArrayList<Object> playerList = new ArrayList<Object>();
                for (int i = 0;i < Main.optionManager.getAnzahlSpieler();i++){
                    if (Main.gameManager.getCurrentPlayerID() != i){
                        playerList.add(Main.gameManager.getPlayer(i).getName());
                    }
                }
                
                playerList.add("Bank");
                
                cbPlayer = new JComboBox(playerList.toArray());
		lblTrade.setLabelFor(cbPlayer);
		pnlHead.add(cbPlayer);
                
                JButton execute = new JButton();
                execute.setText("Arrangement zustandebringen");
                execute.addActionListener(c);
                execute.setActionCommand("submit");
                this.add(execute,BorderLayout.SOUTH);
                
                this.setVisible(true);
	}

    public JComboBox getCbPlayer() {
        return cbPlayer;
    }
        
    public ArrayList<JSpinner> getTradingPlayerRessources() {
        return tradingPlayerRessources;
    }

    public ArrayList<JSpinner> getTradedPlayerRessources() {
        return tradedPlayerRessources;
    }
}

