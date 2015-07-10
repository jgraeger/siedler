/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.util.ArrayList;

/**
 * Repräsentiert die Insel Catan als Model
 * @author aloeser
 */
public class SpielfeldModel {

    public static int getDiagonale(){ return Main.optionManager.getSpielfelddiagonale(); }
    public static int getRechtslinksausdehnung() { return getDiagonale()/2+1; }
    public static int getAnzahlFelder() { return (int) (0.75*getDiagonale()*getDiagonale() + 0.25); }

    private SechseckModel[] sechsecke;
    private ArrayList<Point> koordinaten;

    public SpielfeldModel(){
        init();
    }

    private void init(){
                sechsecke = new SechseckModel[getAnzahlFelder()];

		int index = 0;
		Integer typ;
		Integer nr;

		ArrayList<Integer> typen = getTypliste();
		ArrayList<Integer> nummern = getNummernliste();
		ArrayList<Integer> zweierhafen = getZweierhafenListe();
		ArrayList<Point> hafenkoordinaten = new ArrayList<Point>();
		ArrayList<Integer> hafentypen = new ArrayList<Integer>();
		final int ZWEIERHAFEN = -2;
		int[] typabfolge = {SechseckModel.WASSER, ZWEIERHAFEN, SechseckModel.WASSER, SechseckModel.HAFEN_ALLGEMEIN};
		int hafenindex = 0;

		//Erstellen der Liste mit den Koordinaten und dem zugeh�rigen Hafentyp
		for (int j = 0;j < getRechtslinksausdehnung();j++, hafenindex++){
			hafenindex %= 4;
			hafenkoordinaten.add(new Point(-j, 0));
			hafentypen.add(typabfolge[hafenindex]);
		}

		for (int i = 1;i < getDiagonale()-getRechtslinksausdehnung()+1;i++, hafenindex++){
			hafenindex %= 4;
			hafenkoordinaten.add(new Point(1-getRechtslinksausdehnung(), i));
			hafentypen.add(typabfolge[hafenindex]);
		}
		for (int j = getRechtslinksausdehnung()-2;j >= 0;j--, hafenindex++){
			hafenindex %= 4;
			hafenkoordinaten.add(new Point(-j, getDiagonale()-j-1));
			hafentypen.add(typabfolge[hafenindex]);
		}
		for (int j = 1;j < getRechtslinksausdehnung();j++, hafenindex++){
			hafenindex %= 4;
			hafenkoordinaten.add(new Point(j, getDiagonale()-j-1));
			hafentypen.add(typabfolge[hafenindex]);
		}
		for (int i = getDiagonale()-getRechtslinksausdehnung()-1;i >= 0;i--, hafenindex++){
			hafenindex %= 4;
			hafenkoordinaten.add(new Point(getRechtslinksausdehnung()-1, i));
			hafentypen.add(typabfolge[hafenindex]);
		}
		for (int j = getRechtslinksausdehnung()-2;j > 0;j--, hafenindex++){
			hafenindex %= 4;
			hafenkoordinaten.add(new Point(j, 0));
			hafentypen.add(typabfolge[hafenindex]);
		}

		koordinaten = new ArrayList<Point>();

		//Generierung des Spielfeldes
		for (int j = 0;j < getRechtslinksausdehnung();j++){
			for (int i = 0;i < getDiagonale()-j;i++, index++){

				//Theoretisch w�rde nur ein SechseckModel als Parameter �bergeben werden, von dem die Klasse die notwendigen Daten abfragen kann
				if (i == 0 || i+1 == getDiagonale()-j || j+1 == getRechtslinksausdehnung()){
					try {
						typ = hafentypen.get(hafenkoordinaten.indexOf(new Point(j, i)));
					} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
						System.err.println("j = " + j);
						System.err.println("i = " + i);
						typ = SechseckModel.WUESTE;
					}

					if (typ == ZWEIERHAFEN){
						typ = zweierhafen.get(random(zweierhafen.size()));
						zweierhafen.remove(typ);

						if (zweierhafen.size() == 0){
							zweierhafen = getZweierhafenListe();
						}
					}
				
					nr = -1;
				} else {
					typ = typen.get(random(typen.size()));
					nr = nummern.get(random(nummern.size()));
	

					typen.remove(typ);

					if (typ == SechseckModel.WUESTE || typ == SechseckModel.WASSER){
						nr = -1;
					} else {
						nummern.remove(nr);
					}
				}

				koordinaten.add(new Point(j, i));
				sechsecke[index] = new SechseckModel(typ, nr, j, i, this);
				
				

				if (nummern.isEmpty()){
					nummern = getNummernliste();
				}
				if (typen.isEmpty()){
					typen = getTypliste();
				}

				if (j == 0) continue;

				index++;

				if (i == 0 || i+1 == getDiagonale()-j || j+1 == getRechtslinksausdehnung()){
					try {
						typ = hafentypen.get(hafenkoordinaten.indexOf(new Point(-j, i)));
					} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
						System.err.println("j = " + j);
						System.err.println("i = " + i);
						typ = SechseckModel.WUESTE;
					}

					if (typ == ZWEIERHAFEN){
						typ = zweierhafen.get(random(zweierhafen.size()));
						zweierhafen.remove(typ);

						if (zweierhafen.size() == 0){
							zweierhafen = getZweierhafenListe();
						}
					}
			

					nr = -1;
				} else {
					typ = typen.get(random(typen.size()));
					nr = nummern.get(random(nummern.size()));


					typen.remove(typ);

					if (typ == SechseckModel.WUESTE || typ == SechseckModel.WASSER){
						nr = -1;
					} else {
						nummern.remove(nr);
					}
				}

				koordinaten.add(new Point(-j, i));
				sechsecke[index] = new SechseckModel(typ, nr, -j, i, this);
				

				if (nummern.size() == 0){
					nummern = getNummernliste();
				}
				if (typen.size() == 0){
					typen = getTypliste();
				}
			}
		}
	}

    public void initialUpdate(){
        for (SechseckModel s : sechsecke){
            s.notifyer();
        }
    }
    
    private static ArrayList<Integer> getTypliste(){
		ArrayList<Integer> erg = new ArrayList<Integer>();

		addElement(erg, SechseckModel.GETREIDE, 4); //Getreide
		addElement(erg, SechseckModel.ERZ, 3); //Erz
		addElement(erg, SechseckModel.LEHM, 3); //Lehm
		addElement(erg, SechseckModel.SCHAF, 4); //Schaf
		addElement(erg, SechseckModel.HOLZ, 4); //Holz
		addElement(erg, SechseckModel.WUESTE, 1); //W�ste

		return erg;
	}

	private static ArrayList<Integer> getNummernliste(){
		ArrayList<Integer> erg = new ArrayList<Integer>();

		addElement(erg, 2, 1);
		for (int i = 3;i < 12;i++){
			if (i == 7) continue;
			addElement(erg, i, 2);
		}
		addElement(erg, 12, 1);

		return erg;
	}

	private static ArrayList<Integer> getZweierhafenListe(){
		ArrayList<Integer> erg = new ArrayList<Integer>();

		for (int i = SechseckModel.HAFEN_GETREIDE;i < SechseckModel.HAFEN_ALLGEMEIN;i++){
			addElement(erg, i, 1);
		}

		return erg;
	}

	private static int random(int bis){
            return Main.gameManager.random(bis);
	}

        @SuppressWarnings({ "unchecked", "rawtypes" })
	private static void addElement(ArrayList list, Object element, int anzahl){
		for (int i = 0;i < anzahl;i++){
			list .add(element);
		}
	}

	public SechseckModel getFeld(int j, int i){
		int index = koordinaten.indexOf(new Point(j, i));
		if(index == -1) return null;
		return sechsecke[index];
	}
        
        

    public void gibRohstoffe(int nummer){
        for (SechseckModel s : sechsecke){
            if (s.getNummer() == nummer){
                s.gibRohstoffe();
            }
        }
    }
    
    /**
     * Diese Methode dient dazu den Observer aller SechseckModels zu benachrichtigen.
     * Sie wird nach dem Laden benötigt um die Bauplatzzustände wiederherzustellen.
     */
    public void sechseckNotifyer() {
       for (SechseckModel s : sechsecke) {
           s.notifyer();
       }
    }
}
