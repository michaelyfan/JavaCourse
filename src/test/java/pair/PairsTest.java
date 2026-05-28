package pair;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PairsTest {

    @Test
    public void testPair() {
        Pair<String, Integer> pair = new Pair<>("a", 1);

        assertEquals("a", pair.a());
        assertEquals(1, pair.b());
    }

    @Test
    public void testPairsZip() {
        List<Pair<String, Integer>> zipped = Pairs.zip(
            List.of("a", "b", "c"),
            List.of(1, 2, 3)
        );

        assertEquals(3, zipped.size());
        assertEquals(new Pair<>("a", 1), zipped.get(0));
        assertEquals(new Pair<>("b", 2), zipped.get(1));
        assertEquals(new Pair<>("c", 3), zipped.get(2));
    }

    @Test
    public void testPairsZipMismatchedLengths() {
        assertThrows(IllegalArgumentException.class, () ->
            Pairs.zip(List.of("a", "b"), List.of(1, 2, 3))
        );
    }

    @Test
    public void testPairsCopy() {
        List<Integer> l = new ArrayList<>();
        Pairs.copy(List.of(1, 2, 3), l);

        assertEquals(1, l.get(0));
        assertEquals(3, l.get(2));

        // PECS in action: List<Integer> -> List<Number> and List<Integer> -> List<Object>
        List<Integer> ints = new ArrayList<>(List.of(1, 2, 3));
        List<Number> nums = new ArrayList<>();
        List<Object> objs = new ArrayList<>();

        Pairs.copy(ints, nums);
        Pairs.copy(ints, objs);

        assertEquals(3, nums.size());
        assertEquals(3, objs.size());

        // The invariant version rejects the same calls. Uncomment to read the compile error:
        //   "incompatible types: List<Integer> cannot be converted to List<Number>"
        // Pairs.copyInvariant(ints, nums);
        // Pairs.copyInvariant(ints, objs);

        // Same-type calls still work with the invariant version:
        List<Integer> sink = new ArrayList<>();
        Pairs.copyInvariant(ints, sink);
        assertEquals(List.of(1, 2, 3), sink);
    }

    @Test
    public void testMaxIntegers() {
        assertEquals(9, Pairs.max(List.of(3, 1, 9, 4, 1, 5)));
    }

    // Pairs.max requires <T extends Comparable<T>>. Object is not Comparable, so the
    // following will not compile. Uncomment to read:
    //   "type argument Object is not within bounds of type-variable T"
    //
    // @Test
    // public void testMaxObjectsDoesNotCompile() {
    //     List<Object> objs = List.of(new Object(), new Object());
    //     Pairs.max(objs);
    // }
}
