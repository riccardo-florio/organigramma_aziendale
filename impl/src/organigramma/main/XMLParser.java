package organigramma.main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParser {

    public static UnitaIF parseXMLToOrganigramma(File file) {
        UnitaIF root = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            Element rootElement = doc.getDocumentElement();
            if (!rootElement.getNodeName().equals("organogestione")) {
                return null; // Il contenitore principale deve essere un organo di gestione
            }

            root = parseOrganoGestione(rootElement);  // Chiamata al parsing del nodo radice

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return root;
    }//parseXMLToOrganigramma

    // Parsing ricorsivo per gli organi di gestione
    private static OrganoGestione parseOrganoGestione(Element ogElement) {
        String nome = ogElement.getAttribute("nome");
        String tipologia = ogElement.getAttribute("tipo");

        OrganoGestione organoGestione = new OrganoGestione(nome, UnitaIF.Tipologia.valueOf(tipologia));

        // Aggiungi i dipendenti all'organo di gestione
        NodeList dipendentiList = ogElement.getElementsByTagName("dipendente");
        for (int i = 0; i < dipendentiList.getLength(); i++) {
            Element dipendenteElement = (Element) dipendentiList.item(i);
            Dipendente dipendente = parseDipendente(dipendenteElement);
            String ruolo = dipendenteElement.getAttribute("ruolo");
            organoGestione.addDipendente(dipendente, ruolo);
        }

        // Processa i figli dell'organo di gestione, che possono essere sia 'unita' che altri 'organogestione'
        NodeList childNodes = ogElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                if (childElement.getNodeName().equals("unita")) {
                    organoGestione.addChild(parseUnita(childElement));
                } else if (childElement.getNodeName().equals("organogestione")) {
                    organoGestione.addChild(parseOrganoGestione(childElement)); // Chiamata ricorsiva
                }
            }
        }

        return organoGestione;
    }//parseOrganoGestione

    // Parsing per l'unità semplice
    private static Unita parseUnita(Element unitaElement) {
        String nome = unitaElement.getAttribute("nome");
        String tipologia = unitaElement.getAttribute("tipologia");

        Unita unita = new Unita(nome, UnitaIF.Tipologia.valueOf(tipologia));

        // Aggiungi i dipendenti all'unità
        NodeList dipendentiList = unitaElement.getElementsByTagName("dipendente");
        for (int i = 0; i < dipendentiList.getLength(); i++) {
            Element dipendenteElement = (Element) dipendentiList.item(i);
            Dipendente dipendente = parseDipendente(dipendenteElement);
            String ruolo = dipendenteElement.getAttribute("ruolo");
            unita.addDipendente(dipendente, ruolo);
        }

        return unita;
    }//parseUnita

    // Parsing per i dipendenti
    private static Dipendente parseDipendente(Element dipendenteElement) {
        String nome = dipendenteElement.getAttribute("nome");
        int matricola = Integer.parseInt(dipendenteElement.getAttribute("matricola"));

        return new Dipendente(nome, matricola); // Creazione dell'oggetto Dipendente
    }//parseDipendente

}//XMLParser
