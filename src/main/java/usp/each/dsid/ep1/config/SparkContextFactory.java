package usp.each.dsid.ep1.config;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    public JavaSparkContext sparkContext(final SparkSession sparkSession) {
        return JavaSparkContext.fromSparkContext(sparkSession.sparkContext());
    }
}
