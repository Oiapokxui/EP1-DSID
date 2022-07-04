package usp.each.dsid.ep1;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    @Autowired Problem1 problem1;

    @Autowired Problem2 problem2;

    @Autowired Problem3 problem3;

    @Autowired Problem4 problem4;

    @Autowired Problem5 problem5;

    public static void main(final String[] args) {
        final SpringApplication application = new SpringApplication(App.class);
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @Override public void run(final String[] args) {
        problem2.run();
    }
}
