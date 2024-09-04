package organigramma.main;

import java.util.NoSuchElementException;
import java.util.Stack;

public class DepthFirstIterator implements Iterator<UnitaIF> {
    private Stack<UnitaIF> stack;
    private UnitaIF curr;

    public DepthFirstIterator(UnitaIF unita) {
        stack = new Stack<UnitaIF>();
        stack.push(unita);
    }//Costruttore

    @Override
    public UnitaIF next() {
        if (!hasNext())
            throw new NoSuchElementException();
        curr = stack.pop();
        if (curr.getClass().equals(OrganoGestione.class))
            for (UnitaIF s : ((OrganoGestione) curr).getChildren())
                stack.push(s);
        return curr;
    }//next

    @Override
    public boolean hasNext() {
        return stack.size() > 0;
    }//hasNext

    @Override
    public UnitaIF currentItem() {
        return curr;
    }//currentItem

    public static void main(String[] args) {
        /*UnitaIF root = new OrganoGestione("Root", UnitaIF.Tipologia.DIREZIONE);

        // Aggiungi figli alla root
        root.addChild(new Unita("Acquisti", UnitaIF.Tipologia.UFFICIO));

        // Crea e aggiungi un'unità figlia "Comitato tecnico"
        UnitaIF comTecnico = new OrganoGestione("Comitato tecnico", UnitaIF.Tipologia.SEZIONE);
        comTecnico.addChild(new Unita("Ricerca e sviluppo", UnitaIF.Tipologia.UFFICIO));
        comTecnico.addChild(new Unita("Prova", UnitaIF.Tipologia.UFFICIO));
        root.addChild(comTecnico);

        DepthFirstIterator iterator = new DepthFirstIterator(root);
        System.out.println("Iterating over the organizational chart:");

        while (iterator.hasNext()) {
            UnitaIF unit = iterator.next();
            System.out.println(unit.getNome());
        }*/
    }
}//DepthFirstIterator
