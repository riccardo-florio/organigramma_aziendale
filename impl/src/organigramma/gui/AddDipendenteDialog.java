package organigramma.gui;

import organigramma.main.Dipendente;
import organigramma.main.UnitaIF;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddDipendenteDialog extends JDialog {
    private UnitaIF unitaCorrente;
    private List<Dipendente> allDipendenti;

    private JComboBox<Dipendente> existingDipendentiCombo;
    private JTextField nuovoDipendenteField;
    private JTextField ruoloEsistenteField;
    private JTextField nuovoRuoloField;
    private JRadioButton esistenteRadioButton;
    private JRadioButton nuovoRadioButton;
    private JButton aggiungiButton;
    private JButton annullaButton;

    public AddDipendenteDialog(Frame parent, UnitaIF unitaCorrente, List<Dipendente> allDipendenti) {
        super(parent, "Aggiungi dipendente", true);
        this.unitaCorrente = unitaCorrente;
        this.allDipendenti = allDipendenti;

        init();

        setLocationRelativeTo(parent); // Centra la finestra rispetto al frame principale
        setVisible(true);
    }//Costruttore

    private void init() {
        setSize(400, 350);
        setResizable(false);

        // Layout della finestra
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding per i componenti
        gbc.anchor = GridBagConstraints.WEST;

        // Inizializza i RadioButton e ButtonGroup
        esistenteRadioButton = new JRadioButton("Aggiungi dipendente esistente");
        nuovoRadioButton = new JRadioButton("Crea nuovo dipendente");
        ButtonGroup group = new ButtonGroup();
        group.add(esistenteRadioButton);
        group.add(nuovoRadioButton);

        // Aggiungi il primo radio button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(esistenteRadioButton, gbc);

        // Pannello per selezionare un dipendente esistente
        gbc.gridy++;
        gbc.gridwidth = 1;
        contentPanel.add(new JLabel("Dipendenti esistenti:"), gbc);

        gbc.gridx = 1;
        existingDipendentiCombo = new JComboBox<>(allDipendenti.toArray(new Dipendente[0]));
        contentPanel.add(existingDipendentiCombo, gbc);

        // Campo di testo per il ruolo del dipendente esistente
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new JLabel("Ruolo dipendente esistente:"), gbc);

        gbc.gridx = 1;
        ruoloEsistenteField = new JTextField(15);
        contentPanel.add(ruoloEsistenteField, gbc);

        // Aggiungi uno spazio verticale tra i gruppi di campi
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        contentPanel.add(Box.createVerticalStrut(20), gbc); // Spazio verticale

        // Aggiungi il secondo radio button
        gbc.gridy++;
        gbc.gridwidth = 2;
        contentPanel.add(nuovoRadioButton, gbc);

        // Campo di testo per il nome del nuovo dipendente
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        contentPanel.add(new JLabel("Nome del nuovo dipendente:"), gbc);

        gbc.gridx = 1;
        nuovoDipendenteField = new JTextField(15);
        contentPanel.add(nuovoDipendenteField, gbc);

        // Campo di testo per il ruolo del nuovo dipendente
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(new JLabel("Ruolo del nuovo dipendente:"), gbc);

        gbc.gridx = 1;
        nuovoRuoloField = new JTextField(15);
        contentPanel.add(nuovoRuoloField, gbc);

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        annullaButton = new JButton("Annulla");
        aggiungiButton = new JButton("Aggiungi");
        buttonPanel.add(annullaButton);  // Pulsanti in ordine: Annulla -> Aggiungi
        buttonPanel.add(aggiungiButton);

        // Controllo su allDipendenti.size()
        if (allDipendenti.size() > 0) {
            esistenteRadioButton.setEnabled(true);
            esistenteRadioButton.setSelected(true);
            existingDipendentiCombo.setEnabled(true);
            ruoloEsistenteField.setEnabled(true);

            nuovoRadioButton.setEnabled(true);
            nuovoDipendenteField.setEnabled(false);
            nuovoRuoloField.setEnabled(false);
        } else {
            esistenteRadioButton.setEnabled(false);
            existingDipendentiCombo.setEnabled(false);
            ruoloEsistenteField.setEnabled(false);

            nuovoRadioButton.setSelected(true);
            nuovoDipendenteField.setEnabled(true);
            nuovoRuoloField.setEnabled(true);
        }

        // Inizializza i listeners
        initListeners();

        // Aggiungi i pannelli alla finestra
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }//init

    private void initListeners() {
        // Aggiungi listener per i RadioButton
        esistenteRadioButton.addActionListener(e -> {
            existingDipendentiCombo.setEnabled(true);
            ruoloEsistenteField.setEnabled(true);
            nuovoDipendenteField.setEnabled(false);
            nuovoRuoloField.setEnabled(false);
        });//esistenteRadioButton

        nuovoRadioButton.addActionListener(e -> {
            existingDipendentiCombo.setEnabled(false);
            ruoloEsistenteField.setEnabled(false);
            nuovoDipendenteField.setEnabled(true);
            nuovoRuoloField.setEnabled(true);
        });//nuovoRadioButton

        // Listener per il pulsante "Annulla"
        annullaButton.addActionListener(e -> {
            dispose(); // Chiude il dialogo
        });//annullaButton

        // Listener per il pulsante "Aggiungi"
        aggiungiButton.addActionListener(e -> {
            if (esistenteRadioButton.isSelected()) {
                // Aggiungi il dipendente esistente
                Dipendente selezionato = (Dipendente) existingDipendentiCombo.getSelectedItem();
                String ruoloEsistente = ruoloEsistenteField.getText().trim();
                if (selezionato != null && !ruoloEsistente.isEmpty()) {
                    unitaCorrente.addDipendente(selezionato, ruoloEsistente);
                } else {
                    JOptionPane.showMessageDialog(AddDipendenteDialog.this,
                            "Il ruolo non può essere vuoto.",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                    return; // Esci se il ruolo è vuoto
                }
            } else {
                // Crea un nuovo dipendente
                String nomeNuovo = nuovoDipendenteField.getText().trim();
                String ruoloNuovo = nuovoRuoloField.getText().trim();
                if (!nomeNuovo.isEmpty() && !ruoloNuovo.isEmpty()) {
                    Dipendente nuovoDipendente = new Dipendente(nomeNuovo);
                    allDipendenti.add(nuovoDipendente); // Aggiungi alla lista generale
                    unitaCorrente.addDipendente(nuovoDipendente, ruoloNuovo); // Aggiungi all'unità corrente
                } else {
                    JOptionPane.showMessageDialog(AddDipendenteDialog.this,
                            "Il nome e il ruolo del nuovo dipendente non possono essere vuoti.",
                            "Errore", JOptionPane.ERROR_MESSAGE);
                    return; // Esci se il nome o il ruolo sono vuoti
                }
            }
            dispose(); // Chiudi il dialogo dopo l'aggiunta
        });//aggiungiButton

    }//initListeners

}//AddDipendenteDialog
