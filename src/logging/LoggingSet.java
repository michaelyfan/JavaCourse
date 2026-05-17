package logging;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class LoggingSet<E> implements Set<E> {
    private final Set<E> delegate;

    public LoggingSet(Set<E> delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean add(E e) {
        return false;
    }
    @Override
    public boolean addAll(Collection<? extends E> c) {
        System.out.println("LoggingSet addAll");
        return delegate.addAll(c);
    }
    @Override
    public boolean remove(Object o) {
        System.out.println("LoggingSet remove");
        return delegate.remove(o);
    }
    @Override
    public boolean removeAll(Collection<?> c) {
        System.out.println("LoggingSet removeAll");
        return delegate.removeAll(c);
    }
    @Override
    public boolean retainAll(Collection<?> c) {
        System.out.println("LoggingSet retainAll");
        return delegate.retainAll(c);
    }
    @Override
    public void clear() {
        System.out.println("LoggingSet clear");
        delegate.clear();
    }

    @Override
    public int size() { return delegate.size(); }
    @Override
    public boolean isEmpty() { return delegate.isEmpty(); }
    @Override
    public boolean contains(Object o) { return delegate.contains(o); }
    @Override
    public Iterator<E> iterator() { return delegate.iterator(); }
    @Override
    public Object[] toArray() { return delegate.toArray(); }
    @Override
    public <T> T[] toArray(T[] a) { return delegate.toArray(a); }
    @Override
    public boolean containsAll(Collection<?> c) { return delegate.containsAll(c); }



}
