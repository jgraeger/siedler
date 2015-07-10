/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Stellt Bilder (in gewünschter Größe) bereit und speichert sie, sodass jedes Bild nur ein Mal geladen werden muss
 * @author jbrose
 */
public class ImageManager {

	private static ArrayList<ImageIcon> imgIcons = new ArrayList<ImageIcon>();
	private static ArrayList<String> files = new ArrayList<String>();
	
	private ImageManager(){
		// Der Konstruktor sollte niemals aufgerufen werden, die Methoden sind allesamt statisch
	}
	
	public static Image getImage(String filename){
		return getImageIcon(filename).getImage();
	}
	
	public static Image getImage(String filename, Dimension d){
		return getImageIcon(filename, d).getImage();
	}
	
	public static ImageIcon getImageIcon(String filename){
		ImageIcon icon = null;
		
		URL url = ImageManager.class.getClassLoader().getResource(filename);
		if (url == null){
			icon = new ImageIcon(filename);
		} else {
			icon = new ImageIcon(url);
		}
		
		files.add(filename);
		imgIcons.add(icon);
		
		return icon;
	}
	
	public static ImageIcon getImageIcon(String filename, Dimension d){
		ImageIcon icon = getIconWithNameAndSize(filename, d);		

		if (icon != null){
			//Bereits vorhanden.
//			System.out.println(filename + " ist bereits vorhanden");
		} else if (files.contains(filename)){
			//Vorhanden, aber falsche Gr��e
//			System.out.println(filename + " ist bereits vorhanden, aber in der falschen Gr��e");
			icon = new ImageIcon(imgIcons.get(files.indexOf(filename)).getImage());
			icon.setImage(icon.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH));
			
			files.add(filename);
			imgIcons.add(icon);
		} else {
//			System.out.println(filename + " ist noch nicht vorhanden");
			URL url = ImageManager.class.getClassLoader().getResource(filename);
			if (url == null){
				icon = new ImageIcon(filename);
			} else {
				icon = new ImageIcon(url);
			}
			
			icon.setImage(icon.getImage().getScaledInstance(d.width, d.height, Image.SCALE_SMOOTH));
			files.add(filename);
			imgIcons.add(icon);
		}								
		
		return icon;
	}
	
	private static ImageIcon getIconWithNameAndSize(String s, Dimension d){
		for (int i = 0;i < files.size();i++){
			if (files.get(i).equals(s) && imgIcons.get(i).getIconWidth() == d.width && imgIcons.get(i).getIconHeight() == d.height){
				return imgIcons.get(i);
			}
		}
		
		return null;
	}
}
