/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Reagiert auf Mausklicks von BauplatzView und gibt gegebenenfalls SechseckModel den Baubefehl
 * @author A
 */
public class BauplatzControl implements MouseListener {

    private SechseckModel model;
    
    public BauplatzControl(SechseckModel m){
        this.model = m;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!Main.gameManager.istBauphase()){
            return;
        }
        
        BauplatzView bp = (BauplatzView) e.getSource();
        String name = bp.getName();
        
        int location = Integer.parseInt(Character.toString(name.charAt(name.length()-1)));
        
        if (name.startsWith("siedlung")){
            model.baueDorf(location);
        } else if (name.startsWith("weg")){
            model.baueWeg(location);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Unnoetig
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Unnoetig
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Unnoetig
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Unnoetig
    }   
}