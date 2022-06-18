package usp.each.dsid.ep1;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import usp.each.dsid.ep1.functions.MapCsvToCollection;
import usp.each.dsid.ep1.functions.MapCsvToInstance;
import usp.each.dsid.ep1.model.Collection;
import usp.each.dsid.ep1.model.Instance;

@SpringBootApplication
public class App implements CommandLineRunner {
    final static String COLLECTIONS_FILE_PATH = "sample-traces/collection_events/*";
    final static String INSTANCES_FILE_PATH = "sample-traces/instance_events/*";
    final static String MASTER_URL = "local[*]";

    @Autowired MapCsvToCollection mapCsvToCollection;

    @Autowired MapCsvToInstance mapCsvToInstance;

    public static void main(final String[] args) {
        final SpringApplication application = new SpringApplication(App.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override public void run(final String[] args) {
        final SparkConf sparkConf = new SparkConf().setAppName("EP1-DSID").setMaster(MASTER_URL);
        final JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        final JavaRDD<Optional<Instance>> instances = sparkContext.textFile(INSTANCES_FILE_PATH).map(mapCsvToInstance);
        final JavaRDD<Optional<Collection>> collections = sparkContext.textFile(COLLECTIONS_FILE_PATH).map(mapCsvToCollection);
    }
}
