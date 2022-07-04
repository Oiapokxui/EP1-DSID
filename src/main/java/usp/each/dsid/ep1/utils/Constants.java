package usp.each.dsid.ep1.utils;

public class Constants {
    public final static String COLLECTIONS_SAMPLES_FILE_PATH = "gs://borg-traces/sample-traces/collection_events/*";
    public final static String INSTANCES_SAMPLES_FILE_PATH = "gs://borg-traces/sample-traces/instance_events/*";
    public final static String COLLECTIONS_FILE_PATH = "/tmp/ep1-dsid/traces-dir/google-traces/collection_events/*";
    public final static String INSTANCES_FILE_PATH = "/tmp/ep1-dsid/traces-dir/google-traces/instance_events/*";
    public final static String INSTANCE_HEADER = "time,type,collection_id,priority,instance_index,resource_request.cpus,resource_request.memory";
    public final static String INSTANCE_SCHEMA = "time STRING, "
            + "type STRING, "
            + "collection_id STRING, "
            + "priority STRING, "
            + "instance_index STRING, "
            + "resource_request_cpus DOUBLE, "
            + "resource_request_memory DOUBLE";
    public final static String COLLECTION_HEADER = "time,type,collection_id,priority";
    public final static String LOCAL_STANDALONE_MASTER_URL = "local[*]";
    public final static String TRAVAZAP_MASTER_URL = "spark://iniciativa-travazap.duckdns.org:7077";
    public final static String LOCAL_CLUSTER_MASTER_URL = "spark://192.168.0.200:7077";
    public final static String APP_NAME = "EP1-DSID";
    public final static String SCHEDULER_MODE = "spark.scheduler.mode";
    public final static String DRIVER_MEMORY = "spark.driver.memory";
    public final static String DRIVER_PORT = "spark.driver.port";
    public final static String POOL_NAME = "spark.scheduler.pool";
    public final static String ENABLE_GS = "spark.hadoop.google.cloud.auth.service.account.enable";
    public final static String GS_KEYFILE = "spark.hadoop.google.cloud.auth.service.account.json.keyfile";
    public final static String GS_IMPL = "spark.hadoop.fs.AbstractFileSystem.gs.impl";
    public final static String GS_PROJECT = "spark.hadoop.fs.gs.project.id";
    public final static String FAIR_SCHEDULING_POOL = "fair_pool";
    public final static String FAIR_SCHEDULING = "FAIR";
    public final static String FIFO_SCHEDULING = "FIFO";
    public final static Long ONE_HOUR_IN_MICROSECONDS = 3600_000_000L;
}
