package organigramma;

public interface Visitor {
    String visitUnita(Unita u);
    String visitOrganoGestione(OrganoGestione og);
}//Visitor
