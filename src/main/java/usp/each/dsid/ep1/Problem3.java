package usp.each.dsid.ep1;

import static usp.each.dsid.ep1.utils.Constants.COLLECTIONS_FILE_PATH;
import static usp.each.dsid.ep1.utils.Constants.COLLECTION_HEADER;
import static usp.each.dsid.ep1.utils.Constants.ONE_HOUR_IN_MICROSECONDS;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usp.each.dsid.ep1.function.IntegerComparatorButItIsSerializable;

@Service
public class Problem3 {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;


    public void run() {
        final JavaRDD<String> jobs = sparkContext.textFile(COLLECTIONS_FILE_PATH);
        final JavaRDD<Integer> timeRdd = jobs.filter(str -> !str.equals(COLLECTION_HEADER))
                .map(str -> str.split(","))
                .map(arr -> arr[0])
                .map(Long::parseLong)
                .filter(time -> time != 0L)
                .filter(time -> time != Long.MAX_VALUE)
                .map(time -> time / (double)ONE_HOUR_IN_MICROSECONDS)
                .map(time -> (int)Math.ceil(time))
                .cache();

        final Long startTime = System.currentTimeMillis();
        final int max = timeRdd.max((new IntegerComparatorButItIsSerializable()));
        final int min = timeRdd.min((new IntegerComparatorButItIsSerializable()));
        final int hours = max - min;
        final Long jobCount = timeRdd.count();
        final Double avg = jobCount / (double)hours;
        final ConcurrentMap<Integer, Integer> hoursMap = new ConcurrentHashMap<Integer, Integer>(hours);
        timeRdd.foreach(time -> hoursMap.compute(time, (hourKey, hourCount) -> {
            if (hourCount == null) return 1;
            else return hourCount + 1;
        }));
        final Long elapsedTime = System.currentTimeMillis() - startTime;

        log.info("******* PROBLEM3");
        log.info("******* Max timestamp: {}", max);
        log.info("******* Min timestamp: {}", min);
        log.info("******* Total hours: {}", hours);
        log.info("******* avg jobs per hour: {}", avg);
        for(int hour = 0 ; hour < hours; hour++) {
            log.info("******* [Hour, Count]: [{}, {}]", hour, hoursMap.getOrDefault(hour, 0));
        }
        hoursMap.forEach((hour, hourCount) -> System.out.println());
        log.info("******* Took {} ms to calculate", elapsedTime);
    }
}
