package organigramma.gui;

import organigramma.main.OrganoGestione;
import organigramma.main.UnitaIF;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {
        super("Organigramma Aziendale Builder");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pnlNord = new CommandsPanel();
        JPanel pnlCenter = new ViewerPanel();
        getContentPane().add(pnlNord, BorderLayout.NORTH);
        getContentPane().add(pnlCenter, BorderLayout.CENTER);

        setVisible(true);
    }//Costruttore

    public static void main(String[] args) {
        new Main();
    }//main
}//Main
