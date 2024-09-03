package organigramma;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UnitaIF {
    enum Tipologia {DIREZIONE, SETTORE, UFFICIO, SEZIONE};

    // Composite pattern IF
    List<UnitaIF> children = new LinkedList<>();
    String getNome();
    void setNome(String nome);
    void addChild(UnitaIF unita);
    void removeChild(UnitaIF unita);
    UnitaIF getChild(int i);

    // Observer pattern IF
    List<Observer> observers = new LinkedList<>();

    default void attach(Observer obs) {
        if (!observers.contains(obs))
            observers.add(obs);
    }//attach

    default void detach(Observer obs) {
        if (observers.contains(obs))
            observers.remove(obs);
    }//detach

    default void notifyObservers() {
        for (Observer o : observers)
            o.update(this);
    }//notify

    // Iterator pattern IF
    Iterator<UnitaIF> iterator();

    // Visitor pattern IF
    String accept(Visitor v);

    // Dipendenti
    void addDipendente(Dipendente d, String ruolo);
    void removeDipendente(Dipendente d);
    Set<Dipendente> getDipendenti();
}//UnitaIF
