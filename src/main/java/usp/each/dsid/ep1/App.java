package usp.each.dsid.ep1;

import static usp.each.dsid.ep1.utils.Constants.INSTANCES_FILE_PATH;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import usp.each.dsid.ep1.model.SchemaFactory;

@SpringBootApplication
public class App implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    @Autowired SchemaFactory schemaFactory;

    public static void main(final String[] args) {
        final SpringApplication application = new SpringApplication(App.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override public void run(final String[] args) {
        final Dataset<Row> instances = sparkSession.read().schema(schemaFactory.eventSchema()).csv(INSTANCES_FILE_PATH);
        instances.createOrReplaceTempView("instance");
        final long start = System.currentTimeMillis();
        log.info("******************** Starting reduction");
        final Double sum = sparkSession.sql("select sum(resource_requests_memory) from instance").first().getDouble(0);
        final long elapsedTime = System.currentTimeMillis() - start;
        log.info("****************** took {} milliseconds to find sum {} of column", elapsedTime, sum);
    }
}
