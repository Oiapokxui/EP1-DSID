package usp.each.dsid.ep1;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usp.each.dsid.ep1.utils.Constants;

@Service
public class Problem4 {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    public void run() {
        final JavaRDD<String> jobs = sparkContext.textFile(Constants.INSTANCES_FILE_PATH);
        final JavaRDD<Long> timeRdd = jobs.filter(str -> !str.equals(Constants.INSTANCE_HEADER))
                .map(str -> str.split(","))
                .map(arr -> arr[0])
                .map(timeString -> Long.parseLong(timeString))
                .filter(time -> time != 0L)
                .filter(time -> time != Long.MAX_VALUE).cache();

        final Long startTime = System.currentTimeMillis();
        final Long max = timeRdd.max(Long::compare);
        final Long min = timeRdd.min(Long::compare);
        final int hours = (int)Math.ceil((max - min) / (double)Constants.ONE_HOUR_IN_MICROSECONDS);
        final Long count = timeRdd.count();
        final Double avg = hours / (double)count;
        final Long elapsedTime = System.currentTimeMillis() - startTime;
        log.info("Jobs per hour: {}", avg);
        log.info("Took {} ms to calculate", elapsedTime);
    }
}
