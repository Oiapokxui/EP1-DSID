package usp.each.dsid.ep1;

import static usp.each.dsid.ep1.utils.Constants.COLLECTIONS_FILE_PATH;
import static usp.each.dsid.ep1.utils.Constants.INSTANCES_FILE_PATH;

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
import usp.each.dsid.ep1.model.EventFactory;
import usp.each.dsid.ep1.model.Instance;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired MapCsvToCollection mapCsvToCollection;

    @Autowired MapCsvToInstance mapCsvToInstance;

    @Autowired JavaSparkContext sparkContext;

    @Autowired EventFactory eventFactory;

    public static void main(final String[] args) {
        final SpringApplication application = new SpringApplication(App.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override public void run(final String[] args) {
        final JavaRDD<Optional<Instance>> instances = sparkContext.textFile(INSTANCES_FILE_PATH).map(mapCsvToInstance);
        final JavaRDD<Optional<Collection>> collections = sparkContext.textFile(COLLECTIONS_FILE_PATH).map(mapCsvToCollection);
    }
}
