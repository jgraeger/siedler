/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;

/**
 * Repräsentiert die Insel Catan grafisch
 * @author aloeser
 */
public class SpielfeldView extends JPanel {

    public static final int SECHSECKLAENGE = 120;
    public static final int SECHSECKHOEHE = (int) (SECHSECKLAENGE * Math.sqrt(0.75));
    private SechseckView[] sechsecke;

    public SpielfeldView(SpielfeldModel sm) {
        setLayout(null);
        setSize((int) ((SpielfeldModel.getDiagonale() / 2 + 1 + SpielfeldModel.getDiagonale() / 2 * 0.5) * SECHSECKLAENGE + SechseckView.STADTBREITE), SpielfeldModel.getDiagonale() * SECHSECKHOEHE + SechseckView.STADTHOEHE);
        setPreferredSize(getSize());

        sechsecke = new SechseckView[SpielfeldModel.getAnzahlFelder()];
        init(sm);

        setVisible(true);
    }

    private void init(SpielfeldModel sm) {
        int drehwinkel = 0;
        int index = 0;

        for (int j = 0; j < SpielfeldModel.getRechtslinksausdehnung(); j++) {
            for (int i = 0; i < SpielfeldModel.getDiagonale() - j; i++, index++) {
//                if (i == 0 || i + 1 == SpielfeldModel.getDiagonale() - j || j + 1 == SpielfeldModel.getRechtslinksausdehnung()) {
//                    if (i == 0 && j == 0) {
//                        drehwinkel = 120;
//                    } else if (i == 0) {
//                        drehwinkel = 180;
//                    } else if (j + 1 == SpielfeldModel.getRechtslinksausdehnung()) {
//                        drehwinkel = 240;
//                    } else if (i + 1 == SpielfeldModel.getDiagonale() - j) {
//                        drehwinkel = 300;
//                    }
//                } else {
//                    drehwinkel = 0;
//                }

                drehwinkel = getDrehwinkel(j, i);
                
                sechsecke[index] = new SechseckView(sm.getFeld(j, i), drehwinkel);
                sechsecke[index].setLocation((getWidth() - SECHSECKLAENGE) / 2 + 3 * j * SECHSECKLAENGE / 4, i * SECHSECKHOEHE + j * SECHSECKHOEHE / 2);
                this.add(sechsecke[index]);

                if (j == 0) {
                    continue;
                }

                index++;

//                if (i == 0 || i + 1 == SpielfeldModel.getDiagonale() - j || j + 1 == SpielfeldModel.getRechtslinksausdehnung()) {
//                    if (j + 1 == SpielfeldModel.getRechtslinksausdehnung() && i + 1 == SpielfeldModel.getDiagonale() - j) {
//                        drehwinkel = 0;
//                    } else if (j + 1 == SpielfeldModel.getRechtslinksausdehnung()) {
//                        drehwinkel = 60;
//                    } else if (i == 0) {
//                        drehwinkel = 120;
//                    } else if (i + 1 == SpielfeldModel.getDiagonale() - j) {
//                        drehwinkel = 0;
//                    }
//                } else {
//                    drehwinkel = 0;
//                }
                
                drehwinkel = getDrehwinkel(-j, i);

                sechsecke[index] = new SechseckView(sm.getFeld(-j, i), drehwinkel);
                sechsecke[index].setLocation((getWidth() - SECHSECKLAENGE) / 2 - 3 * j * SECHSECKLAENGE / 4, i * SECHSECKHOEHE + j * SECHSECKHOEHE / 2);
                this.add(sechsecke[index]);
            }
        }
    }

    public static int getDrehwinkel(int j, int i) {
        int drehwinkel = 0;
        if (i == 0 || i + 1 == SpielfeldModel.getDiagonale() - Math.abs(j) || Math.abs(j) + 1 == SpielfeldModel.getRechtslinksausdehnung()) {
            if (i == 0 && j == 0) {
                drehwinkel = 120;
            } else if (i == 0 && j > 0) {
                drehwinkel = 180;
            } else if (j + 1 == SpielfeldModel.getRechtslinksausdehnung()) {
                drehwinkel = 240;
            } else if (i + 1 == SpielfeldModel.getDiagonale() - j) {
                drehwinkel = 300;
            } else if (-j + 1 == SpielfeldModel.getRechtslinksausdehnung() && i + 1 == SpielfeldModel.getDiagonale() + j) {
                drehwinkel = 0;
            } else if (-j + 1 == SpielfeldModel.getRechtslinksausdehnung()) {
                drehwinkel = 60;
            } else if (i == 0 && j < 0) {
                drehwinkel = 120;
            } else if (i + 1 == SpielfeldModel.getDiagonale() + j) {
                drehwinkel = 0;
            }
        } else {
            drehwinkel = 0;
        }


        return drehwinkel;
    }
}
