package organigramma.main;

public class Dipendente {
    public static int cont;
    private String nome;
    private int matricola;

    public Dipendente(String nome) {
        this.nome = nome;
        matricola = cont++;
    }//Costruttore

    public Dipendente(String nome, int matricola) {
        this.nome = nome;
        this.matricola = matricola;
        if (matricola > cont)
            Dipendente.cont = matricola;
    }//Costruttore

    public String getNome() {
        return nome;
    }//getNome

    public int getMatricola() {
        return matricola;
    }//getMatricola

    @Override
    public String toString() {
        return "Nome: " + nome + ", Matricola: " + matricola;
    }//toString

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Dipendente))
            return false;
        if (obj == this)
            return true;
        Dipendente d = (Dipendente) obj;
        return matricola == d.getMatricola();
    }//equals

    @Override
    public int hashCode() {
        return matricola;
    }//hashCode
}//Dipendente
