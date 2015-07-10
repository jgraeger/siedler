/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 * Repräsentiert eine Entwicklungskarte
 * @author jgraeger
 */
public class Entwicklungskarte {
    
    public static final int RITTER = 0;
    public static final int SIEGPUNKT = 1;
    public static final int MONOPOL = 2;
    
    private static final String[] typnamen = new String[] {"Ritter", "Siegpunkt", "Monopol"};
    
    private int typ;
    private boolean gespielt;
    
    private static ArrayList<Integer> types = getTypes();
    
    //Von außen sollte niemand die Möglichkeit haben, diese Klasse zu instantiieren,
    //da es nur eine beschränkte Anzahl von Instanzen geben darf -> Factory
    private Entwicklungskarte(int typ){
        this.typ = typ;
        this.gespielt = false;
    }
    
    public int getTyp(){
        return typ;
    }
    
    public boolean istGespielt(){
        return gespielt;
    }
    
    public void spielen(){
        gespielt = true;
    }
    
    public String getName(){
        return typnamen[typ] + "karte";
    }
    
    //Factory-Methoden
    public static Entwicklungskarte getEntwicklungskarte(){
        
        if (types.isEmpty()){
            return null;
        }
        
        Integer typ = types.get(Main.gameManager.random(types.size()));
        types.remove(typ);        
        
        return new Entwicklungskarte(typ.intValue());
    }
        
    public static void resetEntwicklungskarten(){
        types = getTypes();
    }
    
    private static ArrayList<Integer> getTypes(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        addElement(list, RITTER, 9);
        addElement(list, SIEGPUNKT, 5);
        addElement(list, MONOPOL, 3);
        
        return list;
    }
    
    private static void addElement(ArrayList<Integer> list, int value, int count){
        for (int i = 0;i < count;i++){
            list.add(new Integer(value));
        }
    }
}