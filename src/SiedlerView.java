import sun.java2d.SunGraphicsEnvironment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Das Hauptfenster, in dem das eigentliche Programm läuft
 * @author jgraeger
 *
 */
public class SiedlerView extends JFrame implements Observer {

    private JPanel contentPane;
    private JTextField txtInput;
    private AktionsPanel actionPanel;
    private JPanel rohstoffPanel;
    private JPanel spielfeldPanel;
    private RohstoffView[] rohstoffViews;
    private SiedlerConsole konsole;
    private OptionView menuBar;  

    /**
     * Create the frame.
     */
    public SiedlerView(SpielfeldView spielfeld) {
        Main.gameManager.addObserver(this);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Integer screenHeight = (int) screenSize.getHeight();        
        setTitle("Die Siedler von Catan");
        setIconImage(ImageManager.getImage("img/sonstige/splash2.jpg"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Maximieren des Fenster unabhängig von allen Fenstermanagern
        GraphicsConfiguration graphicalConfig = this.getGraphicsConfiguration();
        Rectangle bounds = SunGraphicsEnvironment.getUsableBounds(graphicalConfig.getDevice());
        setBounds(bounds);

        menuBar = new OptionView(new OptionControl());
        this.setJMenuBar(menuBar);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JSplitPane splitMain = new JSplitPane();
        contentPane.add(splitMain, BorderLayout.CENTER);

        JSplitPane splitVertical = new JSplitPane();
        splitVertical.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitMain.setRightComponent(splitVertical);

        
        
        spielfeldPanel = new JPanel();
        spielfeldPanel.add(spielfeld);
        splitVertical.setTopComponent(new JScrollPane(spielfeldPanel));
        splitVertical.setDividerLocation((screenHeight/4)*3);

        JPanel pnlText = new JPanel();
        splitVertical.setBottomComponent(pnlText);
        pnlText.setLayout(new BorderLayout(0, 0));

        konsole = new SiedlerConsole();
        JScrollPane consoleScroll = new JScrollPane(konsole,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pnlText.add(consoleScroll, BorderLayout.CENTER);


        //JPanel pnlSide = new JPanel();
        //splitMain.setLeftComponent(pnlSide);

        JSplitPane splitLeft = new JSplitPane();
        splitLeft.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitMain.setLeftComponent(splitLeft);

        AktionsControl actionControl = new AktionsControl();
        actionPanel = new AktionsPanel(actionControl);
        splitLeft.setTopComponent(actionPanel);
        //pnlSide.add(actionPanel);

        
        rohstoffPanel = new JPanel();       
        rohstoffPanel.setLayout(new BoxLayout(rohstoffPanel, BoxLayout.PAGE_AXIS));
        rohstoffPanel.setBorder(BorderFactory.createTitledBorder("Rohstoffe"));                
        
        JScrollPane scrollPane = new JScrollPane(rohstoffPanel);           
        splitLeft.setBottomComponent(scrollPane);                
    }

    private void initRohstoffView(){
        int players = Main.optionManager.getAnzahlSpieler();
        rohstoffViews = new RohstoffView[players];
        
        for (int i = 0;i < players;i++){
            rohstoffViews[i] = new RohstoffView(Main.gameManager.getPlayer(i));
            rohstoffPanel.add(rohstoffViews[i]);
        }
    }    
    
    public SiedlerConsole getKonsole() {
        return konsole;
    }
    
    public void setSpielfeldView(SpielfeldView view){
        spielfeldPanel.removeAll();
        spielfeldPanel.add(view);        
        spielfeldPanel.revalidate();        
    }
    
    @Override
    public void update(Observable obj, Object arg){
        GameManager gm = (GameManager) obj;
        
        if (gm.getGameState() == GameManager.WAITING_FOR_GAMESTART){
            rohstoffPanel.removeAll();
            rohstoffPanel.revalidate();
            rohstoffPanel.repaint();
        } else {
            if (rohstoffPanel.getComponentCount() == 0){
                initRohstoffView();
                rohstoffPanel.repaint();
            }
        }
    }
}