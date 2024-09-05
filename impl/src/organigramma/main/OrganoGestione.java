package organigramma.main;

import java.util.*;

public class OrganoGestione implements UnitaIF {
    private String nome;
    private UnitaIF.Tipologia tipologia;
    private List<UnitaIF> children;
    private Map<Dipendente, String> dipendenti;

    public OrganoGestione(String nome, UnitaIF.Tipologia tipologia) {
        this.nome = nome;
        this.tipologia = tipologia;
        children = new LinkedList<>();
        dipendenti = new HashMap<>();
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
        notifyObservers();
    }//setNome

    @Override
    public void addChild(UnitaIF unita) {
        if (!children.contains(unita)) {
            children.add(unita);
            notifyObservers();
        }
    }//addChild

    @Override
    public void removeChild(UnitaIF unita) {
        if (children.contains(unita)) {
            children.remove(unita);
            notifyObservers();
        }
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
    public <T> T accept(Visitor<T> v) {
        return v.visitOrganoGestione(this);
    }//accept

    // DIPENDENTI
    public void addDipendente(Dipendente d, String ruolo) {
        if( !dipendenti.containsKey(d) || !dipendenti.get(d).equals(ruolo) ) {
            dipendenti.put(d, ruolo);
            notifyObservers();
        }
    }//addDipendente

    public void removeDipendente(Dipendente d) {
        dipendenti.remove(d);
        notifyObservers();
    }//removeDipendente

    public Set<Dipendente> getDipendenti() {
        return new HashSet<>(dipendenti.keySet());
    }//getDipendenti

    public Map<Dipendente, String> getDipendentiERuoli() {
        return new HashMap<>(dipendenti);
    }//getDipendentiRuoli

}//OrganoGestione
