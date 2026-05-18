package iterable;

import java.util.Iterator;

public class RangeIterable implements Iterable<Integer> {
    private final int start;
    private final int endExclusive;

    public RangeIterable(int start, int endExclusive) {
        this.start = start;
        this.endExclusive = endExclusive;
    }

    static class RangeIterator implements Iterator<Integer> {
        private int cursor;
        private final int endExclusive;

        public RangeIterator(int cursor, int endExclusive) {
            this.cursor = cursor;
            this.endExclusive = endExclusive;
        }

        @Override
        public boolean hasNext() {
            return cursor < endExclusive;
        }

        @Override
        public Integer next() {
            cursor++;
            return cursor - 1;
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new RangeIterator(start, endExclusive);
    }
}
