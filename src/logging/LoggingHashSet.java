package logging;

import java.util.Collection;
import java.util.HashSet;

public class LoggingHashSet<E> extends HashSet<E> {
    @Override
    public boolean add(E e) {
        System.out.println("LoggingHashSet add()");
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        System.out.println("LoggingHashSet addAll()");
        return super.addAll(c);
    }
}
