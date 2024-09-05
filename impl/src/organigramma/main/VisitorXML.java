package organigramma.main;

public class VisitorXML implements Visitor<String> {

    @Override
    public String visitUnita(Unita u) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<unita nome='%s' tipologia='%s'>%n", u.getNome(), u.getTipologia().toString()));
        sb.append("<dipendenti>\n");
        for (Dipendente d : u.getDipendentiERuoli().keySet())
            sb.append(String.format("<dipendente matricola='%d' nome='%s' ruolo='%s'/>%n",
                    d.getMatricola(), d.getNome(), u.getDipendentiERuoli().get(d)));
        sb.append("</dipendenti>\n");
        sb.append("</unita>\n");

        return sb.toString();
    }//visitUnita

    @Override
    public String visitOrganoGestione(OrganoGestione og) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<organogestione nome='%s' tipo='%s'>%n",
                og.getNome(), og.getTipologia().toString()));
        sb.append("<dipendenti>\n");
        for (Dipendente d : og.getDipendentiERuoli().keySet())
            sb.append(String.format("<dipendente matricola='%d' nome='%s' ruolo='%s'/>%n",
                    d.getMatricola(), d.getNome(), og.getDipendentiERuoli().get(d)));
        sb.append("</dipendenti>\n");
        for (UnitaIF u : og.getChildren())
            sb.append(u.accept(this));
        sb.append("</organogestione>\n");
        return sb.toString();
    }//visitOrganoGestione
}//VisitorXML
