package usp.each.dsid.ep1;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usp.each.dsid.ep1.function.FilterGroupsWithBothScheduleAndSubmit;
import usp.each.dsid.ep1.function.GetTimeToSchedule;
import usp.each.dsid.ep1.function.MapInstanceToSubset;
import usp.each.dsid.ep1.function.MapInstanceToSubset.InstanceSubset;
import usp.each.dsid.ep1.model.EventType;
import usp.each.dsid.ep1.utils.Constants;

@Service
public class Problem5 {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    private static final int TIME_INDEX = 0;

    private static final int TYPE_INDEX = 1;

    private static final int COLLECTION_ID_INDEX = 2;

    private static final int INDEX = 3;

    public void run() {
        final Long startTime = System.currentTimeMillis();
        final JavaPairRDD<Long, Iterable<long[]>> jobs = sparkContext.textFile(Constants.INSTANCES_FILE_PATH)
                .filter(str -> !str.equals(Constants.INSTANCE_HEADER))
                .map(str -> str.split(","))
                .filter(arr -> arr.length == 7)
                .map(arr -> new long[] {
                    Long.parseLong(arr[0]),
                    Long.parseLong(arr[1]),
                    Long.parseLong(arr[2]),
                    Long.parseLong(arr[4])})
                .filter(arr -> arr[TIME_INDEX] != 0L)
                .filter(arr -> arr[TIME_INDEX] != Long.MAX_VALUE)
                .filter(arr -> EventType.get((int)arr[TYPE_INDEX]) == EventType.SUBMIT || EventType.get((int)arr[TYPE_INDEX]).equals(EventType.SCHEDULE))
                .groupBy(arr -> arr[COLLECTION_ID_INDEX]) // Tuple2<Long, Iterable<InstanceSubset>>
                .filter(new FilterGroupsWithBothScheduleAndSubmit());

        final JavaRDD<Long> times = jobs.map(new GetTimeToSchedule())
                .filter(Optional::isPresent)
                .map(Optional::get).cache();

        final Long sum = times.reduce(Long::sum);
        final Long count = jobs.count();
        final Long result = sum / count;

        final Long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("(RESULT) Mean of time to schedule from submit: {}", result);
        log.info("Took {} ms to calculate", elapsedTime);
    }
}
