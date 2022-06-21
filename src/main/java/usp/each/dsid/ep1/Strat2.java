package usp.each.dsid.ep1;

import static org.apache.spark.sql.functions.col;
import static usp.each.dsid.ep1.utils.Constants.INSTANCES_FILE_PATH;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usp.each.dsid.ep1.model.SchemaFactory;

// Strat2 uses Datasets and agg function
@Service
public class Strat2 {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    @Autowired SchemaFactory schemaFactory;

    public void run() {
        final Dataset<Row> instances = sparkSession.read().schema(schemaFactory.eventSchema()).csv(INSTANCES_FILE_PATH);
        final long start = System.currentTimeMillis();
        log.info("******************** Starting reduction");
        final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
        map.put("resource_requests_memory", "avg");
        final double sum = instances.select(col("resource_requests_memory")).agg(map).first().getDouble(0);
        final long elapsedTime = System.currentTimeMillis() - start;
        log.info("******************** took {} milliseconds to find sum {} of column", elapsedTime, sum);
    }
}
