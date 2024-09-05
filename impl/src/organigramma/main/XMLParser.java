package organigramma.main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
            if (!rootElement.getNodeName().equals("organogestione"))
                return null; // il contenitore principale deve essere un organo di gestione

            root = parseOrganoGestione(rootElement);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return root;
    }//parseXMLToOrganigramma

    private static OrganoGestione parseOrganoGestione(Element ogElement) {
        String nome = ogElement.getAttribute("nome");
        String tipologia = ogElement.getAttribute("tipo");

        OrganoGestione ret = new OrganoGestione(nome, UnitaIF.Tipologia.valueOf(tipologia));

        // Aggiungi dipendenti
        NodeList dipendentiList = ogElement.getElementsByTagName("dipendente");
        for (int i = 0; i < dipendentiList.getLength(); i++) {
            Element dipendenteElement = (Element) dipendentiList.item(i);
            Dipendente dipendente = parseDipendente(dipendenteElement);
            String ruolo = dipendenteElement.getAttribute("ruolo");
            ret.addDipendente(dipendente, ruolo);
        }

        // Aggiungi sotto-unità
        NodeList unitaList = ogElement.getElementsByTagName("unita");
        for (int i = 0; i < unitaList.getLength(); i++) {
            Element unitaElement = (Element) unitaList.item(i);
            ret.addChild(parseUnita(unitaElement));
        }

        // Aggiungi sotto-organi di gestione
        NodeList organiList = ogElement.getElementsByTagName("organogestione");
        for (int i = 0; i < organiList.getLength(); i++) {
            Element organoElement = (Element) organiList.item(i);
            ret.addChild(parseOrganoGestione(organoElement));
        }

        return ret;
    }//parseOrganoGestione

    private static Dipendente parseDipendente(Element dipendenteElement) {
        String nome = dipendenteElement.getAttribute("nome");
        int matricola = Integer.parseInt(dipendenteElement.getAttribute("matricola"));

        // Crea l'oggetto Dipendente
        return new Dipendente(nome, matricola);
    }//parseDipendente

    private static Unita parseUnita(Element unitaElement) {
        String nome = unitaElement.getAttribute("nome");
        String tipologia = unitaElement.getAttribute("tipologia");

        // Crea l'oggetto Unita
        Unita unita = new Unita(nome, UnitaIF.Tipologia.valueOf(tipologia));

        // Aggiungi dipendenti
        NodeList dipendentiList = unitaElement.getElementsByTagName("dipendente");
        for (int i = 0; i < dipendentiList.getLength(); i++) {
            Element dipendenteElement = (Element) dipendentiList.item(i);
            Dipendente dipendente = parseDipendente(dipendenteElement);
            String ruolo = dipendenteElement.getAttribute("ruolo");
            unita.addDipendente(dipendente, ruolo);
        }

        return unita;
    }//parseUnita

}//XMLParser
