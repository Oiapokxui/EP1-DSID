package usp.each.dsid.ep1.function;

import java.util.List;
import java.util.Objects;

import org.apache.spark.api.java.function.Function;

import scala.Tuple2;
import usp.each.dsid.ep1.model.EventType;

public class GetTimeToSchedule implements Function<Tuple2<Long, Iterable<List<Long>>>, Long> {
    private static final int TIME = 0;

    private static final int TYPE = 1;

    private static final int ID = 2;

    private static final int INDEX = 3;

    @Override public Long call(final Tuple2<Long, Iterable<List<Long>>> pair) {
        final Iterable<List<Long>> group = pair._2;

        List<Long> submit = null;
        List<Long> firstSchedule = null;

        Long lesserTime = Long.MAX_VALUE;
        for(final List<Long> taskStep : group) {
            if(taskStep.get(TYPE) == EventType.SUBMIT.ordinal() && taskStep.get(TIME) < lesserTime) {
                submit = taskStep;
                lesserTime = submit.get(TIME);
            }
            else if(taskStep.get(TYPE) == EventType.SCHEDULE.ordinal() && submit != null && Objects.equals(taskStep.get(INDEX), submit.get(INDEX))) {
                firstSchedule = taskStep;
            }
        }

        if(firstSchedule == null || submit == null) {
            System.out.println("aaaaaaaaaaaaaa\n" + group.spliterator().getExactSizeIfKnown());
        }
        if(firstSchedule == null || !Objects.equals(firstSchedule.get(INDEX), submit.get(INDEX))) {
            for(final List<Long> taskStep : group) {
                if(taskStep.get(TYPE) == EventType.SCHEDULE.ordinal() && taskStep.get(INDEX).equals(submit.get(INDEX))) {
                    firstSchedule = taskStep;
                }
            }
        }
        if(firstSchedule == null || submit == null) {
            System.out.println("aaaaaaaaaaaaaa\n" + group.spliterator().getExactSizeIfKnown());
        }
        return firstSchedule.get(TIME) - submit.get(TIME);

    }
}

