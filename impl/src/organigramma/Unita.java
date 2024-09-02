package organigramma;

public class Unita implements UnitaIF {
    private String nome;
    private Tipologia tipologia;

    public Unita(String nome, Tipologia tipologia) {
        this.nome = nome;
        this.tipologia = tipologia;
    }//Costruttore


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
    public String accept(Visitor v) {
        return v.visitUnita(this);
    }//accept

}//Unita
