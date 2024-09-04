package organigramma.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CommandsPanel extends JPanel {
    public CommandsPanel() {
        JButton btnAggiungiUnita = new JButton("Aggiungi unita");
        JButton btnSalvaOrganigramma = new JButton("Salva organigramma");
        JButton btnApriOrganigramma = new JButton("Apri organigramma");
        JButton btnNuovoOrganigramma = new JButton("Nuovo Organigramma");
        add(btnAggiungiUnita);
        add(btnSalvaOrganigramma);
        add(btnApriOrganigramma);
        add(btnNuovoOrganigramma);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(new EmptyBorder(20, 20, 20, 20));
    }//Costruttore
}//Commands
