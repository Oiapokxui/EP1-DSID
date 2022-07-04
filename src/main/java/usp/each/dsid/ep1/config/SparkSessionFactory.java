package usp.each.dsid.ep1.config;

import static usp.each.dsid.ep1.utils.Constants.APP_NAME;
import static usp.each.dsid.ep1.utils.Constants.DRIVER_MEMORY;
import static usp.each.dsid.ep1.utils.Constants.ENABLE_GS;
import static usp.each.dsid.ep1.utils.Constants.EXECUTOR_MEMORY;
import static usp.each.dsid.ep1.utils.Constants.FIFO_SCHEDULING;
import static usp.each.dsid.ep1.utils.Constants.GS_IMPL;
import static usp.each.dsid.ep1.utils.Constants.GS_KEYFILE;
import static usp.each.dsid.ep1.utils.Constants.GS_PROJECT;
import static usp.each.dsid.ep1.utils.Constants.LOCAL_STANDALONE_MASTER_URL;
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
                .master(LOCAL_STANDALONE_MASTER_URL)
                .config(SCHEDULER_MODE, FIFO_SCHEDULING)
                .config(DRIVER_MEMORY, "6g")
                .config(EXECUTOR_MEMORY, "6g")
                .config(GS_IMPL, "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
                .config(GS_PROJECT, "ep1-dsid")
                .config(ENABLE_GS, "true")
                .config(GS_KEYFILE, "/tmp/ep1-dsid/key.json")
                .getOrCreate();
    }
}
