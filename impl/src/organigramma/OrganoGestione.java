package organigramma;

import java.util.LinkedList;
import java.util.List;

public class OrganoGestione implements UnitaIF {
    private String nome;
    private UnitaIF.Tipologia tipologia;
    private List<UnitaIF> children;

    public OrganoGestione(String nome, UnitaIF.Tipologia tipologia) {
        this.nome = nome;
        this.tipologia = tipologia;
        children = new LinkedList<>();
    }//Costruttore

    public Tipologia getTipologia() {
        return tipologia;
    }//getTipologia

    // COMPOSITE PATTERN
    @Override
    public String getNome() {
        return nome;
    }//getNome

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }//setNome

    @Override
    public void addChild(UnitaIF unita) {
        if (!children.contains(unita))
            children.add(unita);
    }//addChild

    @Override
    public void removeChild(UnitaIF unita) {
        if (children.contains(unita))
            children.remove(unita);
    }//removeChild

    @Override
    public UnitaIF getChild(int i) {
        return children.get(i);
    }//getChild

    public List<UnitaIF> getChildren() {
        return new LinkedList<>(children);
    }//getChildren

    // ITERATOR PATTERN
    public Iterator<UnitaIF> iterator() {
        return new DepthFirstIterator(this);
    }//iterator

    // VISITOR PATTERN
    @Override
    public String accept(Visitor v) {
        return v.visitOrganoGestione(this);
    }//accept

}//OrganoGestione
