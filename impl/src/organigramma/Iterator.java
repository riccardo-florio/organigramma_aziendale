package organigramma;

public interface Iterator<T> {
    T next();
    boolean hasNext();
    T currentItem();
}//Iterator
