package organigramma.main;

public interface Visitor<T> {
    T visitUnita(Unita u);
    T visitOrganoGestione(OrganoGestione og);
}//Visitor
