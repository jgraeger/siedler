/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Einstiegspunkt ins Programm
 * @author jbrose
 */
public class Main {
    public static GameManager gameManager = new GameManager();
    public static OptionManager optionManager = new OptionManager();

    private Main() {
        
    }

    public static void main(String[] args){
        SplashScreen screen = new SplashScreen("img/sonstige/splash2.jpg");
        screen.activate();
        gameManager.initSpielfeld();
        screen.deactivate();
        gameManager.startGame();
    }    
}
