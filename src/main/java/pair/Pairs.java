package pair;

import java.util.ArrayList;
import java.util.List;

public class Pairs {

    public static <A,B> List<Pair<A,B>> zip(List<A> listA, List<B> listB) {
        if (listA.size() != listB.size()) {
            throw new IllegalArgumentException("Lists must have the same number of elements");
        }

        List<Pair<A, B>> toReturn = new ArrayList<>();
        for (int i = 0; i < listA.size(); i++) {
            toReturn.add(new Pair<A,B>(listA.get(i), listB.get(i)));
        }
        return toReturn;
    }

    public static <T> void copy(List<? extends T> src, List<? super T> dest) {
        // naive copy by reference, not a deep copy
        for (T t : src) {
            // TODO: could replace with Collection.addAll()
            dest.add(t);
        }
    }

    // Invariant version, for contrast with copy() above. Cannot accept
    // (List<Integer>, List<Object>) etc. — both parameters must be the same List<T>.
    public static <T> void copyInvariant(List<T> src, List<T> dest) {
        for (T t : src) {
            dest.add(t);
        }
    }

    public static <T extends Comparable<T>> T max(List<T> list) {
        T current = null;
        for (T t : list) {
            if (current == null || t.compareTo(current) > 0) {
                current = t;
            }
        }
        return current;
    }
}
