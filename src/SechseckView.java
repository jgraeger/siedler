/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;

/**
 * Repräsentiert ein Sechseckfeld. Verantwortlich für die Anordnung der Bauplätze
 * @author jbrose
 */
public class SechseckView extends JPanel {


    public static final int STADTBREITE = SpielfeldView.SECHSECKLAENGE*9/24;
    public static final int STADTHOEHE = SpielfeldView.SECHSECKHOEHE*9/24;

    private Sechseck sechseck;
    
    private BauplatzView[] siedlung;
    private BauplatzView[] wege;

    public SechseckView(SechseckModel sm, int drehwinkel){
	init(sm, drehwinkel);
    }
    
    private void init(SechseckModel sm, int drehwinkel){
                setLayout(null);
		setBounds(0, 0, SpielfeldView.SECHSECKLAENGE+STADTBREITE, SpielfeldView.SECHSECKHOEHE+STADTHOEHE);
                setPreferredSize(new Dimension(SpielfeldView.SECHSECKLAENGE+STADTBREITE, SpielfeldView.SECHSECKHOEHE+STADTHOEHE));
		setOpaque(false);

                //Sechseck erzeugen
		sechseck = new Sechseck(sm.getTyp(), sm.getNummer(), drehwinkel, sm.hatRaeuber());

                //Verbindungen nach MVC herstellen
                sm.addObserver(sechseck);                
                SechseckControl sc = new SechseckControl(sm);
                this.addMouseListener(sc);


		siedlung = new BauplatzView[6];
                wege = new BauplatzView[6];
		for (int x = 0;x < siedlung.length;x++){
		    siedlung[x] = new BauplatzView(sm.getSiedlung(x), "siedlung"+x);
                    siedlung[x].addMouseListener(new BauplatzControl(sm));
                    
                    wege[x] = new BauplatzView(sm.getWeg(x), "weg"+x);
                    wege[x].addMouseListener(new BauplatzControl(sm));
                }

                //Bauplätze erzeugen und hinzufügen.
                //Siedlungen
		siedlung[SechseckModel.LINKS].setBounds(0, getHeight()/2-STADTHOEHE/2, STADTBREITE, STADTHOEHE);
		this.add(siedlung[SechseckModel.LINKS]);

		siedlung[SechseckModel.UNTEN_LINKS].setBounds(1*SpielfeldView.SECHSECKLAENGE/4, getHeight()-STADTHOEHE, STADTBREITE, STADTHOEHE);
		this.add(siedlung[SechseckModel.UNTEN_LINKS]);

		siedlung[SechseckModel.UNTEN_RECHTS].setBounds(3*SpielfeldView.SECHSECKLAENGE/4, getHeight()-STADTHOEHE, STADTBREITE, STADTHOEHE);
		this.add(siedlung[SechseckModel.UNTEN_RECHTS]);

		siedlung[SechseckModel.RECHTS].setBounds(SpielfeldView.SECHSECKLAENGE, getHeight()/2-STADTHOEHE/2, STADTBREITE, STADTHOEHE);
		this.add(siedlung[SechseckModel.RECHTS]);

		siedlung[SechseckModel.OBEN_RECHTS].setBounds(3*SpielfeldView.SECHSECKLAENGE/4, 0, STADTBREITE, STADTHOEHE);
		this.add(siedlung[SechseckModel.OBEN_RECHTS]);

		siedlung[SechseckModel.OBEN_LINKS].setBounds(1*SpielfeldView.SECHSECKLAENGE/4, 0, STADTBREITE, STADTHOEHE);
		this.add(siedlung[SechseckModel.OBEN_LINKS]);
                

                //Wege
                int width = SpielfeldView.SECHSECKLAENGE*5/24;
                int height = SpielfeldView.SECHSECKHOEHE/6;
                int width2 = SpielfeldView.SECHSECKLAENGE/4;
                int minus = 10;
                int height2 = SpielfeldView.SECHSECKHOEHE/2-2*minus;

                
                wege[SechseckModel.UNTEN].setBounds(SpielfeldView.SECHSECKLAENGE*28/48, SpielfeldView.SECHSECKHOEHE+STADTHOEHE/2 - height/2, width, height);
                this.add(wege[SechseckModel.UNTEN]);
                
                wege[SechseckModel.OBEN].setBounds(SpielfeldView.SECHSECKLAENGE*28/48, STADTHOEHE/2 - height/2, width, height);
                this.add(wege[SechseckModel.OBEN]);
                
                wege[SechseckModel.LINKS_OBEN].setBounds(SpielfeldView.SECHSECKLAENGE*2/12, STADTHOEHE/2 + minus, width2, height2);
                this.add(wege[SechseckModel.LINKS_OBEN]);
                
                wege[SechseckModel.RECHTS_UNTEN].setBounds(SpielfeldView.SECHSECKLAENGE*11/12, SpielfeldView.SECHSECKHOEHE/2+STADTHOEHE/2 + minus, width2, height2);
                this.add(wege[SechseckModel.RECHTS_UNTEN]);
                
                wege[SechseckModel.LINKS_UNTEN].setBounds(SpielfeldView.SECHSECKLAENGE*2/12, SpielfeldView.SECHSECKHOEHE/2+STADTHOEHE/2 + minus, width2, height2);
                this.add(wege[SechseckModel.LINKS_UNTEN]);
                
                wege[SechseckModel.RECHTS_OBEN].setBounds(SpielfeldView.SECHSECKLAENGE*11/12, STADTHOEHE/2 + minus, width2, height2);
                this.add(wege[SechseckModel.RECHTS_OBEN]);               

                
                //Sechseck hinzufügen
		sechseck.setBounds(STADTBREITE/2, STADTHOEHE/2, SpielfeldView.SECHSECKLAENGE, SpielfeldView.SECHSECKHOEHE);
		this.add(sechseck);                                
                                                                
                        
                setDoubleBuffered(true);
                setVisible(true);              
    }
    
    public BauplatzView getSiedlung(int index){
        return siedlung[index];
    }

    public BauplatzView getWeg(int index){
        return wege[index];
    }
}
