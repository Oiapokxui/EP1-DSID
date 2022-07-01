package usp.each.dsid.ep1.function;

import java.util.Objects;

import org.apache.spark.api.java.function.Function;

import scala.Tuple2;
import usp.each.dsid.ep1.function.MapInstanceToSubset.InstanceSubset;
import usp.each.dsid.ep1.model.EventType;

public class GetTimeToSchedule implements Function<Tuple2<Long, Iterable<InstanceSubset>>, Long> {

    @Override public Long call(final Tuple2<Long, Iterable<InstanceSubset>> pair) {
        final Iterable<InstanceSubset> group = pair._2;

        InstanceSubset submit = null;
        InstanceSubset firstSchedule = null;

        Long lesserTime = Long.MAX_VALUE;
        for(final InstanceSubset taskStep : group) {
            if(taskStep.type() == EventType.SUBMIT && taskStep.time() < lesserTime) {
                submit = taskStep;
                lesserTime = submit.time();
            }
            else if(taskStep.type() == EventType.SCHEDULE
                    && submit != null
                    && Objects.equals(taskStep.instanceIndex(),
                    submit.instanceIndex())
            ) {
                firstSchedule = taskStep;
            }
        }

        if(firstSchedule == null || !Objects.equals(firstSchedule.instanceIndex(), submit.instanceIndex())) {
            for(final InstanceSubset taskStep : group) {
                if(taskStep.type() == EventType.SCHEDULE
                        && taskStep.instanceIndex().equals(submit.instanceIndex())
                ) {
                    firstSchedule = taskStep;
                }
            }
        }
        return firstSchedule.time() - submit.time();

    }
}

