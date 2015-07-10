/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;

/**
 * Zeigt einen SplashScreen an
 * @author jbrose
 */
public class SplashScreen extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 648578556730122783L;        
	
	public SplashScreen(String filename){
		this(filename, filename);
	}
	
	public SplashScreen(String filename, String iconfile) {
		ImageIcon icon = ImageManager.getImageIcon(filename);		
		
		this.setSize(icon.getIconWidth(), icon.getIconHeight());
		JLabel lbl = new JLabel(icon);
                lbl.setOpaque(false);
                this.add(lbl);
		
                if (iconfile != null){
                    ImageIcon appIcon = ImageManager.getImageIcon(iconfile);
                    this.setIconImage(appIcon.getImage());
                }
		
		
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
                this.requestFocus();
                
                this.setAlwaysOnTop(false);
                
	}

	public void activate(){
		setVisible(true);
	}
	
	public void deactivate(){
		setVisible(false);
	}
	
	private static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void showSplash(String path, long time){
		SplashScreen splash;
		splash = new SplashScreen(path);
		splash.activate();
		sleep(time);
		splash.deactivate();
	}
}