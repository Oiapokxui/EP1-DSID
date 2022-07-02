package usp.each.dsid.ep1.config;

import static usp.each.dsid.ep1.utils.Constants.APP_NAME;
import static usp.each.dsid.ep1.utils.Constants.DRIVER_MEMORY;
import static usp.each.dsid.ep1.utils.Constants.ENABLE_GS;
import static usp.each.dsid.ep1.utils.Constants.FAIR_SCHEDULING;
import static usp.each.dsid.ep1.utils.Constants.GS_IMPL;
import static usp.each.dsid.ep1.utils.Constants.GS_KEYFILE;
import static usp.each.dsid.ep1.utils.Constants.GS_PROJECT;
import static usp.each.dsid.ep1.utils.Constants.MASTER_URL;
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
                .master(MASTER_URL)
                .appName(APP_NAME)
                .config(SCHEDULER_MODE, FAIR_SCHEDULING)
                .config(DRIVER_MEMORY, "10g")
                .config(GS_IMPL, "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
                .config(GS_PROJECT, "ep1-dsid")
                .config(ENABLE_GS, "true")
                .config(GS_KEYFILE, "/tmp/ep1-dsid/ep1-dsid-9ee559e3b7e8.json")
                .getOrCreate();
    }
}
