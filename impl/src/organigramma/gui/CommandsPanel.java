package organigramma.gui;

import organigramma.main.UnitaIF;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommandsPanel extends JPanel {
    private JButton btnSalvaOrganigramma;
    private JButton btnApriOrganigramma;
    private JButton btnNuovoOrganigramma;
    private JButton btnAggiornaGrafico;
    private UnitaIF root;

    public CommandsPanel(UnitaIF root) {
        this.root = root;

        btnSalvaOrganigramma = new JButton("Salva organigramma");
        btnApriOrganigramma = new JButton("Apri organigramma");
        btnNuovoOrganigramma = new JButton("Nuovo Organigramma");
        btnAggiornaGrafico = new JButton("Aggiorna grafico");
        add(btnSalvaOrganigramma);
        add(btnApriOrganigramma);
        add(btnNuovoOrganigramma);
        add(btnAggiornaGrafico);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        initListeners();
    }//Costruttore

    private void initListeners() {
        btnSalvaOrganigramma.addActionListener(e -> {

        });//btnSalvaOrganigramma

        btnApriOrganigramma.addActionListener(e -> {

        });//btnApriOrganigramma

        btnNuovoOrganigramma.addActionListener(e -> {

        });//btnNuovoOrganigramma

        btnAggiornaGrafico.addActionListener(e -> root.setNome(root.getNome()));//btnAggiornaGrafico
    }//initListeners
}//Commands
