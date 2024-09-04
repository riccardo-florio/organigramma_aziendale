package organigramma.test;

import org.junit.Before;
import org.junit.Test;
import organigramma.main.*;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DepthFirstIteratorTest {
    private OrganoGestione root;

    @Before
    public void setup() {
        // Inizializza la variabile membro root
        root = new OrganoGestione("Root", UnitaIF.Tipologia.DIREZIONE);

        // Aggiungi figli alla root
        root.addChild(new Unita("Acquisti", UnitaIF.Tipologia.UFFICIO));

        // Crea e aggiungi un'unità figlia "Comitato tecnico"
        UnitaIF comTecnico = new OrganoGestione("Comitato tecnico", UnitaIF.Tipologia.SEZIONE);
        comTecnico.addChild(new Unita("Ricerca e sviluppo", UnitaIF.Tipologia.UFFICIO));
        comTecnico.addChild(new Unita("Prova", UnitaIF.Tipologia.UFFICIO));
        root.addChild(comTecnico);
    }//setup

    @Test
    public void testDepthFirstIteration() {
        // Creare un iteratore per il nodo root
        DepthFirstIterator iterator = new DepthFirstIterator(root);

        // Testare che l'iterazione avvenga nell'ordine corretto
        assertTrue(iterator.hasNext());
        assertEquals("Root", iterator.next().getNome());

        assertTrue(iterator.hasNext());
        assertEquals("Comitato tecnico", iterator.next().getNome());

        assertTrue(iterator.hasNext());
        assertEquals("Prova", iterator.next().getNome());

        assertTrue(iterator.hasNext());
        assertEquals("Ricerca e sviluppo", iterator.next().getNome());

        assertTrue(iterator.hasNext());
        assertEquals("Acquisti", iterator.next().getNome());

        // Non ci devono essere più elementi
        assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextThrowsExceptionWhenNoMoreElements() {
        // Creare un iteratore e consumare tutti gli elementi
        DepthFirstIterator iterator = new DepthFirstIterator(root);
        while (iterator.hasNext()) {
            iterator.next();
        }

        // Questo dovrebbe lanciare una NoSuchElementException
        iterator.next();
    }
}//DepthFirstIteratorTest