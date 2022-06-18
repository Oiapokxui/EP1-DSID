package usp.each.dsid.ep1;

import static usp.each.dsid.ep1.utils.Constants.INSTANCES_FILE_PATH;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import scala.math.BigDecimal;
import usp.each.dsid.ep1.function.FilterOnlyPresentOptional;
import usp.each.dsid.ep1.function.GetObject;
import usp.each.dsid.ep1.function.MapCsvToCollection;
import usp.each.dsid.ep1.function.MapCsvToInstance;
import usp.each.dsid.ep1.model.Collection;
import usp.each.dsid.ep1.model.EventFactory;
import usp.each.dsid.ep1.model.Instance;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired MapCsvToCollection mapCsvToCollection;

    @Autowired MapCsvToInstance mapCsvToInstance;

    @Autowired GetObject<Instance> getInstance;

    @Autowired FilterOnlyPresentOptional<Instance> filterOnlyPresentInstances;

    @Autowired GetObject<Collection> getCollection;

    @Autowired FilterOnlyPresentOptional<Collection> filterOnlyPresentCollections;

    @Autowired JavaSparkContext sparkContext;

    @Autowired EventFactory eventFactory;

    public static void main(final String[] args) {
        final SpringApplication application = new SpringApplication(App.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override public void run(final String[] args) {
        final JavaRDD<Instance> instances = sparkContext.textFile(INSTANCES_FILE_PATH)
                .map(mapCsvToInstance)
                .filter(filterOnlyPresentInstances)
                .map(getInstance);
        final JavaRDD<BigDecimal> mem = instances.map(Instance::getMemoryResourcesRequested);
        final String b = mem.reduce(BigDecimal::$plus).toString();
    }
}
