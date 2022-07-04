package usp.each.dsid.ep1;

import static usp.each.dsid.ep1.utils.Constants.INSTANCES_FILE_PATH;
import static usp.each.dsid.ep1.utils.Constants.INSTANCE_HEADER;
import static usp.each.dsid.ep1.utils.Constants.ONE_HOUR_IN_MICROSECONDS;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usp.each.dsid.ep1.function.IntegerComparatorButItIsSerializable;

@Service
public class Problem4 {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    public void run() {
        final JavaRDD<String> jobs = sparkContext.textFile(INSTANCES_FILE_PATH);
        final JavaRDD<Integer> timeRdd = jobs.filter(str -> !str.equals(INSTANCE_HEADER))
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
        final Long elapsedTime = System.currentTimeMillis() - startTime;

        log.info("******* PROBLEM4");
        log.info("******* Max timestamp: {}", max);
        log.info("******* Min timestamp: {}", min);
        log.info("******* Total hours: {}", hours);
        log.info("******* avg jobs per hour: {}", avg);
        timeRdd.collect().forEach(time -> log.info("{}, ", time));
        log.info("******* Took {} ms to calculate", elapsedTime);
    }
}
