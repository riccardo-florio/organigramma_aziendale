package organigramma.main;

public class VisitorXML implements Visitor<String> {

    @Override
    public String visitUnita(Unita u) {
        return null;
    }//visitUnita

    @Override
    public String visitOrganoGestione(OrganoGestione og) {
        return null;
    }//visitOrganoGestione
}//VisitorXML
