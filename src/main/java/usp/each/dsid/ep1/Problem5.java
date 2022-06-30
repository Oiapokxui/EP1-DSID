package usp.each.dsid.ep1;

import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usp.each.dsid.ep1.function.GetTimeToSchedule;
import usp.each.dsid.ep1.model.EventType;
import usp.each.dsid.ep1.utils.Constants;

@Service
public class Problem5 {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final int TIME = 0;

    private static final int TYPE = 1;

    private static final int ID = 2;

    private static final int INDEX = 4;

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    public void run() {
        final Long startTime = System.currentTimeMillis();

        final JavaRDD<Long> jobs = sparkContext.textFile(Constants.INSTANCES_FILE_PATH)
                .filter(str -> !str.equals(Constants.INSTANCE_HEADER))
                .map(str -> str.split(","))
                .map(arr -> List.of(Long.valueOf(arr[TIME]), Long.valueOf(arr[TYPE]), Long.valueOf(arr[ID]), Long.valueOf(arr[INDEX])))
                .filter(list -> list.get(TIME) != 0L)
                .filter(list -> list.get(TIME) != Long.MAX_VALUE)
                .filter(list -> list.get(TYPE) == EventType.SUBMIT.ordinal() || list.get(TYPE) == EventType.SCHEDULE.ordinal())
                .groupBy(list -> list.get(ID)) // Tuple2<Long, Iterable<List<Long>>>,  {_1: ID , _2: List(time, type, collection_id, instance_index))
                .map(new GetTimeToSchedule()).cache();

        final Long sum = jobs.reduce(Long::sum);
        final Long count = jobs.count();
        final Long elapsedTime = System.currentTimeMillis() - startTime;
        final Long result = sum / count;

        log.info("(RESULT) Mean of time to schedule from submit: {}", result);
        log.error("Took {} ms to calculate", elapsedTime);
    }
}

