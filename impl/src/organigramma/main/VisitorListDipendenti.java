package organigramma.main;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class VisitorListDipendenti implements Visitor<List<Dipendente>> {

    @Override
    public List<Dipendente> visitUnita(Unita u) {
        List<Dipendente> ris = new LinkedList<>();
        for (Dipendente d : u.getDipendenti())
            ris.add(d);
        return ris;
    }//visitUnita

    @Override
    public List<Dipendente> visitOrganoGestione(OrganoGestione og) {
        HashSet<Dipendente> dipendenti = new HashSet<>();
        Iterator<UnitaIF> iterator = og.iterator();
        while( iterator.hasNext() )
            dipendenti.addAll( iterator.next().getDipendenti() );

        return new LinkedList<Dipendente>( dipendenti );
    }//visitOrganoGestione

}//VisitorListDipendenti
