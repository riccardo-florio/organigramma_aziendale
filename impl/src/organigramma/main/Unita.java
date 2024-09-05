package organigramma.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Unita implements UnitaIF {
    private String nome;
    private Tipologia tipologia;
    private Map<Dipendente, String> dipendenti;

    public Unita(String nome, Tipologia tipologia) {
        this.nome = nome;
        this.tipologia = tipologia;
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
        // Non applicabile per le foglie
    }//addChild

    @Override
    public void removeChild(UnitaIF unita) {
        // Non applicabile per le foglie
    }//removeChild

    @Override
    public UnitaIF getChild(int i) {
        // Non applicabile per le foglie
        return null;
    }//getChild

    // ITERATOR PATTERN
    @Override
    public Iterator<UnitaIF> iterator() {
        return new DepthFirstIterator(this);
    }//iterator

    // VISITOR PATTERN
    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visitUnita(this);
    }//accept

    // DIPENDENTI
    public void addDipendente(Dipendente d, String ruolo) {
        if (!dipendenti.containsKey(d) || !dipendenti.get(d).equals(ruolo)) {
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

}//Unita
