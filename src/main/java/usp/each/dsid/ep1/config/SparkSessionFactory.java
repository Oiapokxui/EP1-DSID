package usp.each.dsid.ep1.config;

import static usp.each.dsid.ep1.utils.Constants.APP_NAME;
import static usp.each.dsid.ep1.utils.Constants.DRIVER_MEMORY;
import static usp.each.dsid.ep1.utils.Constants.FAIR_SCHEDULING;
import static usp.each.dsid.ep1.utils.Constants.SCHEDULER_MODE;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Whenever you need spark's context, just autowire it in your class and use it.
 */
@Configuration
public class SparkSessionFactory {

    @Bean()
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SparkSession sparkSession() {
        return SparkSession.builder()
                .appName(APP_NAME)
                .config(SCHEDULER_MODE, FAIR_SCHEDULING)
                .config(DRIVER_MEMORY, "10g")
                .getOrCreate();
    }
}
