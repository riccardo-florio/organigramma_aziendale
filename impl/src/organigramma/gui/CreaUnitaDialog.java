package organigramma.gui;

import organigramma.main.OrganoGestione;
import organigramma.main.Unita;
import organigramma.main.UnitaIF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreaUnitaDialog extends JDialog {
    private UnitaIF root;
    private JTextField nomeField;
    private JComboBox<UnitaIF.Tipologia> tipologiaCombo;
    private JComboBox<String> tipoCombo;
    private JButton annullaButton;
    private JButton creaButton;

    public CreaUnitaDialog(Frame parent, UnitaIF root) {
        super(parent, "Crea sotto-unità", true); // Dialogo modale
        this.root = root;
        init();
        setLocationRelativeTo(parent);
    }//Costruttore

    private void init() {
        // Layout della finestra
        setLayout(new BorderLayout());
        setResizable(false);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campo di testo per il nome
        JPanel nomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nomePanel.add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        nomePanel.add(nomeField);
        contentPanel.add(nomePanel);

        // ComboBox per il tipo
        JPanel tipoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipoPanel.add(new JLabel("Tipo:"));
        String[] tipi = {"Organo di gestione", "Unità"};
        tipoCombo = new JComboBox<>(tipi);
        tipoPanel.add(tipoCombo);
        contentPanel.add(tipoPanel);

        // ComboBox per la tipologia
        JPanel tipologiaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tipologiaPanel.add(new JLabel("Tipologia:"));
        tipologiaCombo = new JComboBox<>(UnitaIF.Tipologia.values());
        tipologiaCombo.setSelectedItem("REPARTO"); // Valore predefinito
        tipologiaPanel.add(tipologiaCombo);
        contentPanel.add(tipologiaPanel);

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        annullaButton = new JButton("Annulla");
        creaButton = new JButton("Crea");

        // Aggiungi i pulsanti al pannello
        buttonPanel.add(annullaButton);
        buttonPanel.add(creaButton);

        // Aggiungi listener per i pulsanti
        initListeners();

        // Aggiungi i pannelli alla finestra
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Imposta la dimensione della finestra
        setSize(300, 200);
    }//init

    private void initListeners() {
        annullaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Chiudi il dialogo
            }
        });

        creaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String tipo = (String) tipoCombo.getSelectedItem();
                UnitaIF.Tipologia tipologia = (UnitaIF.Tipologia) tipologiaCombo.getSelectedItem();

                if (nome.equals("")) {
                    new JDialog(CreaUnitaDialog.this, "Inserire un nome per poter continuare");
                    return;
                }

                if (tipo.equals("Unità")){
                    root.addChild(new Unita(nome, tipologia));
                } else {
                    root.addChild(new OrganoGestione(nome, tipologia));
                }

                // Chiudi il dialogo
                dispose();
            }
        });
    }//initListeners
}//CreaUnitaDialog
