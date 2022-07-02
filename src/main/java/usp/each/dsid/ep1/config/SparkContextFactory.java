package usp.each.dsid.ep1.config;

import static usp.each.dsid.ep1.utils.Constants.FAIR_SCHEDULING_POOL;
import static usp.each.dsid.ep1.utils.Constants.POOL_NAME;

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
        final JavaSparkContext context = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());
        // context.setLocalProperty(POOL_NAME, FAIR_SCHEDULING_POOL);
        return context;
    }
}
