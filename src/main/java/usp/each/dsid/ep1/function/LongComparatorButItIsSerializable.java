package usp.each.dsid.ep1.function;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Spark didn't leave me no choice.
 */
public class LongComparatorButItIsSerializable implements Comparator<Long>, Serializable {
    @Override public int compare(final Long x, final Long y) {
        return Long.compare(x, y);
    }

    @Override public int hashCode() {
        return super.hashCode();
    }

    @Override public boolean equals(final Object obj) {
        return super.equals(obj);
    }
}
