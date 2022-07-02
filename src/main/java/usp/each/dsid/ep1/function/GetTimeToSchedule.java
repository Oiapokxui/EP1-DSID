package usp.each.dsid.ep1.function;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function;

import scala.Tuple2;
import usp.each.dsid.ep1.function.MapInstanceToSubset.InstanceSubset;
import usp.each.dsid.ep1.model.EventType;

public class GetTimeToSchedule implements Function<Tuple2<Long, Iterable<InstanceSubset>>, Optional<Long>> {

    @Override public Optional<Long> call(final Tuple2<Long, Iterable<InstanceSubset>> pair) {
        final Iterable<InstanceSubset> group = pair._2;

        List<InstanceSubset[]> pairs = new ArrayList<InstanceSubset[]>();

        for(final InstanceSubset taskStep : group) {
            if(taskStep.type() == EventType.SUBMIT) {
                InstanceSubset[] set = new InstanceSubset[2];
                set[0] = taskStep;
                pairs.add(set);
            }
        }

        for(final InstanceSubset taskStep : group) {
            if (taskStep.type() == EventType.SCHEDULE) {
                for (final InstanceSubset[] set : pairs) {
                    final InstanceSubset submit = set[0];
                    if (taskStep.instanceIndex() == submit.instanceIndex()) {
                        set[1] = taskStep;
                    }
                }
            }
        }

        pairs.sort(new SubsetComparator());
        InstanceSubset submit = pairs.get(0)[0];
        
        try {
            InstanceSubset schedule = pairs.get(0)[1];
            return Optional.of(schedule.time() - submit.time());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    class SubsetComparator implements Comparator<InstanceSubset[]> {
        public int compare(InstanceSubset[] as, InstanceSubset[] bs) {
            long a = as[0].time();
            long b = bs[0].time();
            if (a < b) return -1;
            else if (a == b) return 0;
            return 1;
        }  
    }
}

