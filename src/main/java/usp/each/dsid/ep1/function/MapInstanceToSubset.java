package usp.each.dsid.ep1.function;

import java.io.Serializable;

import org.apache.spark.api.java.function.Function;

import usp.each.dsid.ep1.model.EventType;

public class MapInstanceToSubset implements Function<String[], MapInstanceToSubset.InstanceSubset> {
    public static class InstanceSubset implements Serializable {
        private final String[] pair;

        private final int TIME_INDEX = 0;

        private final int TYPE_INDEX = 1;

        private final int COLLECTION_ID_INDEX = 2;

        private final int INDEX = 4;

        public Long time() {
            return Long.parseLong(pair[TIME_INDEX]);
        }

        public final EventType type() {
            final int ordinal = Integer.parseInt(pair[TYPE_INDEX]);
            return EventType.get(ordinal);
        }

        public final Long collectionId() {
            return Long.parseLong(pair[COLLECTION_ID_INDEX]);
        }

        public final long instanceIndex() {
            return Long.parseLong(pair[INDEX]);
        }

        InstanceSubset(final String[] pair) {
            this.pair = pair;
        }
    }

    @Override public InstanceSubset call(final String[] pair) {
        return new InstanceSubset(pair);
    }
}

