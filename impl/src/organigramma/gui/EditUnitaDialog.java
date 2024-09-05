package organigramma.gui;

import organigramma.main.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class EditUnitaDialog extends JDialog {
    private Frame parent;
    private UnitaIF unitaCorrente;
    UnitaIF root;
    private JButton btnRenameUnita;
    private JButton btnAddChild;
    private JButton btnAddDipendente;
    private JButton btnDeleteUnita;
    private JLabel lblTipologia;

    public EditUnitaDialog(Frame parent, UnitaIF unitaCorrente, UnitaIF root) {
        super(parent, "Modifica unità - " + unitaCorrente.getNome(), true);
        this.parent = parent;
        this.unitaCorrente = unitaCorrente;
        this.root = root;

        init();

        setVisible(true);
    }//Costruttore

    private void init() {
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel pnlNord = new JPanel();
        JPanel pnlCenter = new JPanel();

        btnRenameUnita = new JButton("Rinomina unità");
        pnlNord.add(btnRenameUnita);

        btnAddChild = new JButton("Aggiungi sotto-unità");
        pnlNord.add(btnAddChild);
        if (unitaCorrente.getClass().equals(OrganoGestione.class)) {
            lblTipologia = new JLabel("Tipo: Organo di gestione");
        } else {
            btnAddChild.setEnabled(false);
            lblTipologia = new JLabel("Tipo: Unità semplice");
        }

        btnAddDipendente = new JButton("Aggiungi dipendente");
        pnlNord.add(btnAddDipendente);

        btnDeleteUnita = new JButton("Elimina unita");
        btnDeleteUnita.setBackground(Color.RED);
        pnlNord.add(btnDeleteUnita);

        lblTipologia.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCenter.add(lblTipologia);
        pnlCenter.add(Box.createRigidArea(new Dimension(0, 10)));

        pnlCenter.add(new JLabel("Tipologia: " + unitaCorrente.getTipologia()));
        pnlCenter.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblDipendenti = new JLabel("Dipendenti:");
        lblDipendenti.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlCenter.add(lblDipendenti);
        pnlCenter.add(Box.createRigidArea(new Dimension(0, 10)));

        pnlCenter.add(new ElencoDipendenti());

        pnlNord.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlNord.setBorder(new EmptyBorder(8, 8, 8, 8));
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBorder(new EmptyBorder(12, 12, 12, 12));
        getContentPane().add(pnlNord, BorderLayout.NORTH);
        getContentPane().add(pnlCenter, BorderLayout.CENTER);

        initListeners();
    }//init()

    private void initListeners() {
        btnRenameUnita.addActionListener(e -> {
            System.out.println("prova");
            String nuovoNome = JOptionPane.showInputDialog(EditUnitaDialog.this,
                    "Rinomina unità",
                    unitaCorrente.getNome());
            // Se l'utente ha inserito un nuovo nome (e non ha annullato)
            if (nuovoNome != null && !nuovoNome.trim().isEmpty()) {
                // Aggiorna il nome dell'unità unitaCorrente
                unitaCorrente.setNome(nuovoNome);
                // Aggiorna il titolo della finestra con il nuovo nome
                setTitle("Modifica unità - " + unitaCorrente.getNome());
            }
        });//btnRenameUnita

        btnAddChild.addActionListener(e -> {
            new CreaUnitaDialog(parent, unitaCorrente);
        });//btnAddChild

        btnAddDipendente.addActionListener(e -> {
            new AddDipendenteDialog(parent, unitaCorrente, root.accept(new VisitorListDipendenti()));
        });//btnAddDipendente

    }//initListeners

    // INNER CLASS
    private class ElencoDipendenti extends JPanel implements Observer {
        private Map<Dipendente, String> dipendentiERuoli;

        public ElencoDipendenti() {
            dipendentiERuoli = unitaCorrente.getDipendentiERuoli();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setAlignmentX(Component.LEFT_ALIGNMENT);

            unitaCorrente.attach(this);

            aggiornaElencoDipendenti();
        }//Costruttore

        private void aggiornaElencoDipendenti() {
            removeAll();
            if (dipendentiERuoli.isEmpty()) {
                add(new JLabel("Non ci sono dipendenti in questa Unita"));
            } else {
                for (Map.Entry<Dipendente, String> entry : dipendentiERuoli.entrySet()) {
                    Dipendente d = entry.getKey();
                    String ruolo = entry.getValue();
                    JPanel p = new JPanel();
                    p.setLayout(new FlowLayout(FlowLayout.LEFT));
                    p.add(new JLabel("Nome: " + d.getNome() + ", Matricola: " + d.getMatricola() + ", Ruolo: " + ruolo));
                    JButton btnDeleteDipendente = new JButton("Elimina");
                    p.add(btnDeleteDipendente);
                    add(p);

                    btnDeleteDipendente.addActionListener(e -> {
                        int reply = JOptionPane.showConfirmDialog(EditUnitaDialog.this,
                                "Sicuro di voler eliminare il dipendente?",
                                "Confermare scelta", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            unitaCorrente.removeDipendente(d);
                        }
                    });
                }
            }

            revalidate();
            repaint();
        }//aggiornaElencoDipendenti

        // OBSERVER PATTERN
        @Override
        public void update(UnitaIF unita) {
            dipendentiERuoli = unitaCorrente.getDipendentiERuoli();
            aggiornaElencoDipendenti();
        }//update
    }//ElencoDipendenti

}//EditUnitaWindow
