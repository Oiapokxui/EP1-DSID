package usp.each.dsid.ep1;

import static usp.each.dsid.ep1.utils.Constants.COLLECTIONS_FILE_PATH;
import static usp.each.dsid.ep1.utils.Constants.COLLECTION_HEADER;
import static usp.each.dsid.ep1.utils.Constants.ONE_HOUR_IN_MICROSECONDS;

import org.apache.hadoop.shaded.org.apache.kerby.kerberos.kerb.crypto.fast.FastUtil;
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
                .map(time -> (double)time/(double)ONE_HOUR_IN_MICROSECONDS)
                .map(time -> (int)Math.ceil(time))
                .cache();
        timeRdd.take(5).forEach(System.out::println);
        final Long startTime = System.currentTimeMillis();
        final Long max = timeRdd.max((new IntegerComparatorButItIsSerializable()));
        final Long min = timeRdd.min((new IntegerComparatorButItIsSerializable()));
        final int hours = (int)Math.ceil((max - min));
        final Long jobCount = timeRdd.count();
        final Double avg = jobCount / (double)hours;
        final Long elapsedTime = System.currentTimeMillis() - startTime;
        log.error("Max timestamp: {}", max);
        log.error("Min timestamp: {}", min);
        log.error("Total hours: {}", hours);
        log.error("Jobs per hour: {}", avg);
        log.error("Took {} ms to calculate", elapsedTime);
    }
}
