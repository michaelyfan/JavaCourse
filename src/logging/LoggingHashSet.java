package logging;

import java.util.Collection;
import java.util.HashSet;

public class LoggingHashSet<E> extends HashSet<E> {
    @Override
    public boolean add(E e) {
        System.out.println("LoggingHashSet add()");
        return super.add(e);
    }

    // this override is intentionally bugged
    // this will log once for add all, and then once for every item in the collection
    @Override
    public boolean addAll(Collection<? extends E> c) {
        System.out.println("LoggingHashSet addAll()");
        return super.addAll(c);
    }
}
