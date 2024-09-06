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
            int reply = JOptionPane.showConfirmDialog(null,
                    "Tutte le modifiche non salvate dell'organigramma\n attuale andranno perse. Continuare?",
                    "Attenzione", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {

                UnitaIF openedRoot = IOManager.apriDaFile();

                root = openedRoot;
                if (openedRoot != null) {
                    // Aggiorna il root del ViewerPanel e ridisegna il grafo
                    centerPanel.setRoot(openedRoot);
                    centerPanel.drawGraph();
                }
            }
        });//btnApriOrganigramma

        btnNuovoOrganigramma.addActionListener(e -> {
            int reply = JOptionPane.showConfirmDialog(null,
                    "Sicuro di voler creare un nuovo organigramma?\n Tutte le " +
                            "modifiche non salvate andranno perse.",
                    "Confermare scelta", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                UnitaIF newRoot = new OrganoGestione("Root", UnitaIF.Tipologia.DIREZIONE);
                root = newRoot;
                centerPanel.setRoot(newRoot);
                centerPanel.drawGraph();
            }
        });//btnNuovoOrganigramma

        btnAggiornaGrafico.addActionListener(e -> centerPanel.drawGraph());//btnAggiornaGrafico
    }//initListeners
}//Commands
