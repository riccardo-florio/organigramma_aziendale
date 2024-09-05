package organigramma.gui;

import organigramma.main.OrganoGestione;
import organigramma.main.UnitaIF;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("Organigramma Aziendale Builder");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creo un organo di gestione iniziale vuoto
        UnitaIF root = new OrganoGestione("Root", UnitaIF.Tipologia.DIREZIONE);

        JPanel pnlNord = new CommandsPanel(root);
        JPanel pnlCenter = new ViewerPanel(this, root);
        getContentPane().add(pnlNord, BorderLayout.NORTH);
        getContentPane().add(pnlCenter, BorderLayout.CENTER);

        setVisible(true);
    }//Costruttore

    public static void main(String[] args) {
        new MainWindow();
    }//main
}//Main
