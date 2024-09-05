package organigramma.gui;

import organigramma.main.Dipendente;
import organigramma.main.Observer;
import organigramma.main.OrganoGestione;
import organigramma.main.UnitaIF;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class EditUnitaWindow extends JDialog {
    private Frame parent;
    private UnitaIF root;
    private JButton btnRenameUnita;
    private JButton btnAddChild;
    private JButton btnAddDipendente;
    private JButton btnDeleteUnita;
    private JLabel lblTipologia;

    public EditUnitaWindow(Frame parent, UnitaIF root) {
        super(parent, "Modifica unità - " + root.getNome(), true);
        this.parent = parent;
        this.root = root;

        init();

        setVisible(true);
    }//Costruttore

    private void init() {
        setSize(650, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel pnlNord = new JPanel();
        JPanel pnlCenter = new JPanel();

        btnRenameUnita = new JButton("Rinomina unità");
        pnlNord.add(btnRenameUnita);

        btnAddChild = new JButton("Aggiungi sotto-unità");
        pnlNord.add(btnAddChild);
        if (root.getClass().equals(OrganoGestione.class)) {
            lblTipologia = new JLabel("Tipo: Organo di gestiona");
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

        pnlCenter.add(new JLabel("Tipologia: " + root.getTipologia()));
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
    }//init()

    private class ElencoDipendenti extends JPanel implements Observer {
        private Set<Dipendente> dipendenti;

        public ElencoDipendenti() {
            dipendenti = root.getDipendenti();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setAlignmentX(Component.LEFT_ALIGNMENT);

            root.attach(this);
            initListeners();

            aggiornaElenecoDipendenti();
        }//Costruttore

        private void initListeners() {
            btnRenameUnita.addActionListener(e -> {
                System.out.println("prova");
                String nuovoNome = JOptionPane.showInputDialog(EditUnitaWindow.this,
                        "Rinomina unità",
                        root.getNome());
                // Se l'utente ha inserito un nuovo nome (e non ha annullato)
                if (nuovoNome != null && !nuovoNome.trim().isEmpty()) {
                    // Aggiorna il nome dell'unità root
                    root.setNome(nuovoNome);
                    // Aggiorna il titolo della finestra con il nuovo nome
                    setTitle("Modifica unità - " + root.getNome());
                }
            });//btnRenameUnita

            btnAddChild.addActionListener(e -> {
                CreaUnitaDialog dialog = new CreaUnitaDialog(parent, root);
                dialog.setVisible(true);
            });//btnAddChild

            btnAddDipendente.addActionListener(e -> {

            });//btnAddDipendente

        }//initListeners

        private void aggiornaElenecoDipendenti() {
            removeAll();
            if (dipendenti.isEmpty()) {
                add(new JLabel("Non ci sono dipendenti in questa Unita"));
            } else {
                for (Dipendente d : dipendenti) {
                    JPanel p = new JPanel();
                    p.setLayout(new FlowLayout(FlowLayout.LEFT));
                    p.add(new JLabel(d.getNome()));
                    JButton btnDeleteDipendente = new JButton("Elimina");
                    p.add(btnDeleteDipendente);
                    add(p);

                    btnDeleteDipendente.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int reply = JOptionPane.showConfirmDialog(EditUnitaWindow.this,
                                    "Sicuro di voler eliminare il dipendente?",
                                    "Confermare scelta", JOptionPane.YES_NO_OPTION);
                            if (reply == JOptionPane.YES_OPTION) {
                                root.removeDipendente(d);
                                System.out.println("Dipendente " + d + " rimosso");
                            }
                        }
                    });
                }
            }

            revalidate();
            repaint();
        }//aggiornaElenecoDipendenti

        // OBSERVER PATTERN
        @Override
        public void update(UnitaIF unita) {
            dipendenti = root.getDipendenti();
            aggiornaElenecoDipendenti();
        }//update
    }//ElencoDipendenti

}//EditUnitaWindow
