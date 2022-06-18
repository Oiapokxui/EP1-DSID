package usp.each.dsid.ep1.beans;

import static usp.each.dsid.ep1.utils.Constants.APP_NAME;
import static usp.each.dsid.ep1.utils.Constants.MASTER_URL;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Whenever you need spark's context, just autowire it in your class and use it.
 */
@Configuration
public class SparkContextFactory {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public static JavaSparkContext getContext() {
        return JavaSparkContext.fromSparkContext(new SparkContext(MASTER_URL, APP_NAME));
    }
}
