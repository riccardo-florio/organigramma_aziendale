package organigramma;

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
}//DepthFirstIterator
