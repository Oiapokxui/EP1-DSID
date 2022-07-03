package usp.each.dsid.ep1;

import static usp.each.dsid.ep1.utils.Constants.INSTANCES_FILE_PATH;
import static usp.each.dsid.ep1.utils.Constants.INSTANCE_SCHEMA;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Problem1 {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    public void run() {
        final Dataset<Row> instances = sparkSession.read().schema(INSTANCE_SCHEMA).option("header", "false").csv(INSTANCES_FILE_PATH);
        instances.printSchema();

        final Dataset<Double> memoryDf = instances.select("resource_request_memory").as(Encoders.DOUBLE());
        memoryDf.createOrReplaceTempView("memory");

        final Dataset<Double> cpuDf = instances.select("resource_request_cpus").as(Encoders.DOUBLE());
        cpuDf.createOrReplaceTempView("cpu");

        log.info("******* {}", sparkSession.sql("select avg(resource_request_memory) from memory").first().getDouble(0));
        log.info("******* {}", sparkSession.sql("select avg(resource_request_cpus) from cpu").first().getDecimal(0));
        log.info("******* {}", sparkSession.sql("select stddev_sample(resource_request_memory) from memory").first().getDouble(0));
        log.info("******* {}", sparkSession.sql("select stddev_sample(resource_request_cpus) from cpu").first().getDouble(0));
        log.info("******* {}", sparkSession.sql("select max(resource_request_memory) from memory").first().getDouble(0));
        log.info("******* {}", sparkSession.sql("select max(resource_request_cpus) from cpu").first().getDouble(0));
        log.info("******* {}", sparkSession.sql("select min(resource_request_memory) from memory").first().getDouble(0));
        log.info("******* {}", sparkSession.sql("select min(resource_request_cpus) from cpu").first().getDouble(0));
    }
}
