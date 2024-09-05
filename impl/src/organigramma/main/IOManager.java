package organigramma.main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Stack;
import java.util.StringTokenizer;

public class IOManager {

    private static JFileChooser fileChooser;

    private static void initFileChooser(String title) {
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
    }//initFileChooser

    public static void salvaSuFile(String xmlContent) {
        // Finestra di dialogo per chiedere all'utente dove salvare il file
        initFileChooser("Salva organigramma come XML");

        // Imposta la modalità di salvataggio
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Assicurati che il file abbia estensione .xml
            if (!fileToSave.getAbsolutePath().endsWith(".xml")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xml");
            }

            // Salva il contenuto XML nel file selezionato
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                writer.write(xmlContent);
                JOptionPane.showMessageDialog(null, "File salvato con successo: " +
                        fileToSave.getAbsolutePath());
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Errore durante il salvataggio del file.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
                ioException.printStackTrace();
            }
        }
    }//salvaSuFile

    public static UnitaIF apriDaFile() {
        initFileChooser("Apri organigramma XML");

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();

            // Assicurati che il file selezionato sia un file XML
            if (fileToOpen.getAbsolutePath().endsWith(".xml")) {
                try {
                    // Parsing del file XML
                    UnitaIF root = XMLParser.parseXMLToOrganigramma(fileToOpen);

                    // Assegna il nuovo organigramma al root corrente
                    if (root != null) {
                        JOptionPane.showMessageDialog(null,
                                "Organigramma aperto con successo.");
                        return root;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Errore durante l'apertura del file XML.", "Errore",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Errore durante il parsing del file XML.", "Errore",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Seleziona un file XML valido.", "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        return null;
    }//apriDaFile

}//IOManager
