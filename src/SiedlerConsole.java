/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;

/**
 * Die Konsole, über die im Laufe des Spiels Mitteilungen ausgegeben werden
 * @author jgraeger
 */
public class SiedlerConsole extends JEditorPane {

    public SiedlerConsole() {
        super("text/html","<font color=\"black\" size=\"4\">Willkommen bei \"Die Siedler von Catan\"</font><br />");
        JScrollPane scrollPane = new JScrollPane(this);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200,145));
        scrollPane.setMinimumSize(new Dimension(10,10));

        this.setEditable(false);
        this.setContentType("text/html");
    }

    public void cout(String s)
    {
        this.append("<font color=\"black\" size=\"3\">" + s + "</font><br />");
    }

    public void cerr(String s)
    {
        String newText = "<font color=\"red\" size=\"4\"><b>" + s + "</b></font><br />";
        this.append(newText);
    }

    private void append(String s)
    {
        String current = this.getText().substring(0,this.getText().length()-16);
        String newText = current + s + "\n </body> \n  </html>";
        this.setText(newText);
    }
}
