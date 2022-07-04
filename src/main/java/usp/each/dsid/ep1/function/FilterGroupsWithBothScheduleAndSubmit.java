package usp.each.dsid.ep1.function;

import org.apache.spark.api.java.function.Function;

import scala.Tuple2;

import java.util.HashSet;
import java.util.Set;
import usp.each.dsid.ep1.model.EventType;

public class FilterGroupsWithBothScheduleAndSubmit implements Function<Tuple2<Long, Iterable<long[]>>, Boolean> {

    private static final int TIME_INDEX = 0;

    private static final int TYPE_INDEX = 1;

    private static final int COLLECTION_ID_INDEX = 2;

    private static final int INDEX = 3;

    @Override public Boolean call(final Tuple2<Long, Iterable<long[]>> group) throws Exception {
        final Iterable<long[]> javaGroupIterable = group._2;
        final Set<Long> submitIndexes = new HashSet<Long>();

        for (long[] taskStep : javaGroupIterable) {
            if (EventType.get((int)taskStep[TYPE_INDEX]).equals(EventType.SUBMIT)) {
                submitIndexes.add(taskStep[COLLECTION_ID_INDEX]);
            }
        }

        for (long[] taskStep : javaGroupIterable) {
            if (EventType.get((int)taskStep[TYPE_INDEX]).equals(EventType.SCHEDULE) && submitIndexes.contains(taskStep[COLLECTION_ID_INDEX])) {
                return true;
            }
        }
        return false;
    }
}