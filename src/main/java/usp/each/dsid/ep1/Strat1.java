package usp.each.dsid.ep1;

import static usp.each.dsid.ep1.utils.Constants.INSTANCES_FILE_PATH;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usp.each.dsid.ep1.model.SchemaFactory;

//Strat1 uses RDDs
@Service
public class Strat1 {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    @Autowired SchemaFactory schemaFactory;

    public void run() {
        final long start = System.currentTimeMillis();
        final JavaRDD<String> instances = sparkContext.textFile(INSTANCES_FILE_PATH);
        log.info("******************** Starting reduction");
        final double sum = instances.map(s -> s.split(","))
                .map((ss) -> {
                    if(ss.length < 7 || ss[6].equals("resource_request.memory")) {
                        return "0";
                    }
                    else {
                        return ss[6];
                    }
                })
                .map(Double::parseDouble)
                .reduce(Double::sum);
        final long elapsedTime = System.currentTimeMillis() - start;
        log.info("******************** took {} milliseconds to find sum {} of column", elapsedTime, sum);
    }
}
