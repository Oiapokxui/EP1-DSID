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

        log.info("******* avg(mem){}", sparkSession.sql("select avg(resource_request_memory) from memory").first().getDouble(0)); //0.003731752225502231
        log.info("******* stddev_samp(mem) {}", sparkSession.sql("select stddev_samp(resource_request_memory) from memory").first().getDouble(0)); //0.008394476093498329
        log.info("******* max(mem) {}", sparkSession.sql("select max(resource_request_memory) from memory").first().getDouble(0)); //0.9990234375
        log.info("******* min(mem) {}", sparkSession.sql("select min(resource_request_memory) from memory").first().getDouble(0)); //0.0

        log.info("******* avg(mem){}", sparkSession.sql("select avg(resource_request_cpus) from cpu").first().getDouble(0)); //0.009820125959549
        log.info("******* stddev_samp(cpu) {}", sparkSession.sql("select stddev_samp(resource_request_cpus) from cpu").first().getDouble(0)); //0.008437076488258302
        log.info("******* max(cpu) {}", sparkSession.sql("select max(resource_request_cpus) from cpu").first().getDouble(0)); //1.0
        log.info("******* min(cpu) {}", sparkSession.sql("select min(resource_request_cpus) from cpu").first().getDouble(0)); //0.0

    }
}
