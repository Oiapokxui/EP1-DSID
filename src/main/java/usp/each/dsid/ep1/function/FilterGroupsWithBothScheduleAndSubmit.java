package usp.each.dsid.ep1.function;

import org.apache.spark.api.java.function.Function;

import scala.Tuple2;

import java.util.HashSet;
import java.util.Set;
import usp.each.dsid.ep1.function.MapInstanceToSubset.InstanceSubset;
import usp.each.dsid.ep1.model.EventType;

public class FilterGroupsWithBothScheduleAndSubmit implements Function<Tuple2<Long, Iterable<InstanceSubset>>, Boolean> {

    @Override public Boolean call(final Tuple2<Long, Iterable<InstanceSubset>> group) throws Exception {
        final Iterable<InstanceSubset> javaGroupIterable = group._2;
        final Set<Long> submitIndexes = new HashSet<Long>();

        for (InstanceSubset taskStep : javaGroupIterable) {
            if (taskStep.type().equals(EventType.SUBMIT)) {
                submitIndexes.add(taskStep.instanceIndex());
            }
        }

        for (InstanceSubset taskStep : javaGroupIterable) {
            if (taskStep.type().equals(EventType.SCHEDULE) && submitIndexes.contains(taskStep.instanceIndex())) {
                return true;
            }
        }
        return false;
    }
}