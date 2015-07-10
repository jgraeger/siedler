/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Stellt einen Bauplatz grafisch dar. Besitzer wird durch ToolTipText angezeigt
 * @author jbrose
 */
public class BauplatzView extends JPanel implements Observer {

    private ImageIcon imgIcon;    
    
    private int typ;
    private int rotationAngle;
    
    private final String name;
    
    public BauplatzView(BauplatzModel bm, String name) {
        setOpaque(false);
        bm.addObserver(this);
        
        this.name = name;
        
        //Der MouseListener wird in SechseckView geaddet.
    }
    
    @Override
    public String getName(){
        return name;
    }

    @Override
    public void paint(Graphics g){
        //Das Bild zeichnen
        if (imgIcon != null){
            g.drawImage(imgIcon.getImage(), 0, 0, this);            
        }
    }

    @Override
    public void update(Observable obj, Object arg) {
        BauplatzModel bm = (BauplatzModel) obj;
        
        typ = bm.getStatus();
        
        
        switch (typ){
            
            case BauplatzModel.UNBEBAUT:
                imgIcon = null;
                break;
                
            case BauplatzModel.SIEDLUNG:
                imgIcon = ImageManager.getImageIcon("img/sonstige/siedlung.png", getSize());          
                break;
                
            case BauplatzModel.STADT:
                imgIcon = ImageManager.getImageIcon("img/sonstige/stadt.png", getSize());
                break;
                
            case BauplatzModel.WEG:                
                rotationAngle = ((Integer) arg).intValue();
                
                switch(rotationAngle){
                    case 0:
                        imgIcon = ImageManager.getImageIcon("img/sonstige/StrasseR.png", getSize());
                        break;
                        
                    case 1:
                        imgIcon = ImageManager.getImageIcon("img/sonstige/strasseL.png", getSize());
                        break;
                        
                    case 2:
                        imgIcon = ImageManager.getImageIcon("img/sonstige/strasse.png", getSize());
                        break;
                        
                    default:
                        throw new RuntimeException("Ungültige Straßenposition: " + rotationAngle);
                }
                
                break;
                
            default:
                System.err.println("Ungültiger Zustand vom BauplatzModel: " + Integer.toString(typ));
        }

        //Wegen Laden nötig
        if (bm.getBesitzer() != -1){
            this.setToolTipText(Main.gameManager.getPlayer(bm.getBesitzer()).getName());
        }
                
        repaint();
    }  
}