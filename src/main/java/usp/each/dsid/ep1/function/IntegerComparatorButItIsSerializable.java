package usp.each.dsid.ep1.function;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Spark didn't leave me no choice.
 */
public class IntegerComparatorButItIsSerializable implements Comparator<Integer>, Serializable {
    @Override public int compare(final Integer x, final Integer y) {
        return Integer.compare(x, y);
    }

    @Override public int hashCode() {
        return super.hashCode();
    }

    @Override public boolean equals(final Object obj) {
        return super.equals(obj);
    }
}
