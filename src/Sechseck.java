/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

/**
 * Sorgt für die Darstellung eines Sechsecks
 * @author jbrose
 */
public class Sechseck extends JPanel implements Observer {

    
    private static final String[] bildnamen = {"img/sechsecke/getreide.png", "img/sechsecke/schaf.png", "img/sechsecke/lehm.png", "img/sechsecke/erz.png", "img/sechsecke/holz.png", "img/sechsecke/wueste.png", "img/sechsecke/wasser.png", "img/sechsecke/hafen_getreide.png", "img/sechsecke/hafen_schaf.png", "img/sechsecke/hafen_lehm.png", "img/sechsecke/hafen_erz.png", "img/sechsecke/hafen_holz.png", "img/sechsecke/hafen_allgemein.png"};
    
    private int nummer;
    private int angle;
    private int typ;
    private boolean raeuber;
    
    private ImageIcon imageIcon;
    
    
    public Sechseck(int t, int n, int a, boolean r){
        setLayout(null);
	this.nummer = n;
	this.angle = a;
        this.typ = t;
        this.raeuber = r;
        
	imageIcon = ImageManager.getImageIcon(bildnamen[t], new Dimension(SpielfeldView.SECHSECKLAENGE, SpielfeldView.SECHSECKHOEHE));
        setOpaque(false);
	setDoubleBuffered(true);
        setVisible(true);        
    }   
    
    private Image getRotatedImage(int angle){
	BufferedImage erg;
	erg = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
	Graphics2D g2 = (Graphics2D) erg.getGraphics();
	AffineTransform at = new AffineTransform();
	
	at.rotate(Math.toRadians(angle),erg.getWidth()/2,erg.getHeight()/2);
	g2.setTransform(at);
	g2.drawImage(imageIcon.getImage(), 0, 0, this);
		
        return erg;
    }
    
    @Override
    public void paint(Graphics g){
        if (angle != 0){
            g.drawImage(getRotatedImage(angle), 0, 0, this);
	} else {
            g.drawImage(imageIcon.getImage(), 0, 0, this);
	}
				
	//Zeichnen des Chips mitsamt Zahl bzw. des R�ubers 
	if (nummer != -1 || raeuber){

            if (typ != SechseckModel.WUESTE && !raeuber){
                //Der Chip
                int d = Math.max(30, getWidth()/4);
                int x = (getWidth()-d)/2;
                int y = (getHeight()-d)/2;
                g.setColor(new Color(0xff, 0xcc, 0x99));
                g.fillOval(x, y, d, d);
                
                //Die Zahl auf dem Chip
                if (nummer == 6 || nummer == 8){
                  g.setColor(Color.red);
                } else {
                  g.setColor(Color.black);
                }
              
                g.setFont(g.getFont().deriveFont((float) (g.getFont().getSize()+d/3)));
                g.drawString(Integer.toString(nummer), getWidth()/2-g.getFontMetrics().stringWidth(Integer.toString(nummer))/2, (getHeight()+g.getFontMetrics().getMaxAscent()-7)/2);
            } else if (raeuber) {              
                int height = getHeight()/3*2;
                int width = height*180/238;
                
                Image i = ImageManager.getImage("img/sonstige/rauber.png", new Dimension(width, height));
                g.drawImage(i, (getWidth()-width)/2, (getHeight()-height)/2, this);
            }                                  
            
	} else {
            //Es handelt sich um ein Wasserfeld oder um die Wüste, wo der Räuber schon weggesetzt wurde. Es muss nichts mehr getan werden.
        }
    }

    @Override
    public void update(Observable obj, Object arg) {
        SechseckModel sm = (SechseckModel) obj;
        
        //Nummer und Drehwinkel können sich nie verändern und brauchen daher auch nicht aktualisiert zu werden
        this.raeuber = sm.hatRaeuber();

        repaint();
    }

    @Override
    public String toString(){
        return "Typ: " + typ + ", Nr.: " + nummer;
    }
}