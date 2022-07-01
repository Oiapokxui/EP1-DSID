package usp.each.dsid.ep1;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
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

    public void run() {
        final Long startTime = System.currentTimeMillis();
        final JavaPairRDD<Long, Iterable<InstanceSubset>> jobs = sparkContext.textFile(Constants.INSTANCES_FILE_PATH)
                .filter(str -> !str.equals(Constants.INSTANCE_HEADER))
                .map(str -> str.split(","))
                .map(new MapInstanceToSubset())
                .filter(subset -> subset.time() != 0L)
                .filter(subset -> subset.time() != Long.MAX_VALUE)
                .filter(subset -> subset.type() == EventType.SUBMIT || subset.type().equals(EventType.SCHEDULE))
                .groupBy(InstanceSubset::collectionId) // Tuple2<Long, Iterable<InstanceSubset>>
                .filter(new FilterGroupsWithBothScheduleAndSubmit());

        final JavaRDD<Long> times = jobs.map(new GetTimeToSchedule()).cache();

        final Long sum = times.reduce(Long::sum);
        final Long count = jobs.count();
        final Long result = sum / count;

        final Long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("(RESULT) Mean of time to schedule from submit: {}", result);
        log.error("Took {} ms to calculate", elapsedTime);
    }
}

