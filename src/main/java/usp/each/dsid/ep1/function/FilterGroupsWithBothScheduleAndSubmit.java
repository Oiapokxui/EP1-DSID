package usp.each.dsid.ep1.function;

import org.apache.spark.api.java.function.Function;

import scala.Tuple2;
import usp.each.dsid.ep1.function.MapInstanceToSubset.InstanceSubset;
import usp.each.dsid.ep1.model.EventType;

public class FilterGroupsWithBothScheduleAndSubmit implements Function<Tuple2<Long, Iterable<InstanceSubset>>, Boolean> {

    @Override public Boolean call(final Tuple2<Long, Iterable<InstanceSubset>> groups) throws Exception {
        final Iterable<InstanceSubset> javaGroupIterable = groups._2;
        final scala.collection.Iterable<InstanceSubset> objetos = scala.jdk.CollectionConverters.IterableHasAsScala(javaGroupIterable).asScala();
        final Boolean anySubmit = objetos.find(obj -> obj.type().equals(EventType.SUBMIT)).isDefined();
        final Boolean anySchedule = objetos.find(obj -> obj.type().equals(EventType.SCHEDULE)).isDefined();
        return anySubmit && anySchedule;
    }
}

