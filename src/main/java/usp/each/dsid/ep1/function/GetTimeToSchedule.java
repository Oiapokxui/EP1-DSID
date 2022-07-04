package usp.each.dsid.ep1.function;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function;

import scala.Tuple2;
import usp.each.dsid.ep1.model.EventType;

public class GetTimeToSchedule implements Function<Tuple2<Long, Iterable<long[]>>, Optional<Long>> {

    private static final int TIME_INDEX = 0;

    private static final int TYPE_INDEX = 1;

    private static final int COLLECTION_ID_INDEX = 2;

    private static final int INDEX = 3;

    @Override public Optional<Long> call(final Tuple2<Long, Iterable<long[]>> pair) {
        final Iterable<long[]> group = pair._2;

        List<long[][]> pairs = new ArrayList<long[][]>();

        for(final long[] taskStep : group) {
            if(EventType.get((int)taskStep[TYPE_INDEX]) == EventType.SUBMIT) {
                long[][] set = new long[2][4];
                set[0] = taskStep;
                pairs.add(set);
            }
        }

        for(final long[] taskStep : group) {
            if (EventType.get((int)taskStep[TYPE_INDEX]) == EventType.SCHEDULE) {
                for (final long[][] set : pairs) {
                    final long[] submit = set[0];
                    if (taskStep[COLLECTION_ID_INDEX] == submit[COLLECTION_ID_INDEX]) {
                        set[1] = taskStep;
                    }
                }
            }
        }

        pairs.sort(new SubsetComparator());
        long[] submit = pairs.get(0)[0];
        
        try {
            long[] schedule = pairs.get(0)[1];
            return Optional.of(schedule[TIME_INDEX] - submit[TIME_INDEX]);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    class SubsetComparator implements Comparator<long[][]> {
        public int compare(long[][] as, long[][] bs) {
            long a = as[0][TIME_INDEX];
            long b = bs[0][TIME_INDEX];
            if (a < b) return -1;
            else if (a == b) return 0;
            return 1;
        }  
    }
}

