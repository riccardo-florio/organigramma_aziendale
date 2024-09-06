package organigramma.gui;

import organigramma.main.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CommandsPanel extends JPanel {
    private JButton btnSalvaOrganigramma;
    private JButton btnApriOrganigramma;
    private JButton btnNuovoOrganigramma;
    private JButton btnAggiornaGrafico;
    private UnitaIF root;
    private ViewerPanel centerPanel;

    public CommandsPanel(UnitaIF root, ViewerPanel centerPanel) {
        this.root = root;
        this.centerPanel = centerPanel;

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
            String xmlContent = root.accept(new VisitorXML());
            IOManager.salvaSuFile(xmlContent);
        });//btnSalvaOrganigramma

        btnApriOrganigramma.addActionListener(e -> {
            UnitaIF openedRoot = IOManager.apriDaFile();

            root = openedRoot;

            if (openedRoot != null) {
                // Aggiorna il root del ViewerPanel e ridisegna il grafo
                centerPanel.setRoot(openedRoot); // Aggiungi un metodo per aggiornare il root nel ViewerPanel
                centerPanel.drawGraph(); // Ridisegna il grafo con il nuovo root
            }
        });//btnApriOrganigramma

        btnNuovoOrganigramma.addActionListener(e -> {

        });//btnNuovoOrganigramma

        btnAggiornaGrafico.addActionListener(e -> root.setNome(root.getNome()));//btnAggiornaGrafico
    }//initListeners
}//Commands
