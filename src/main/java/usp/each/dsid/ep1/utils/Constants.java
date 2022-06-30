package usp.each.dsid.ep1.utils;

public class Constants {
    public final static String COLLECTIONS_FILE_PATH = "sample-traces/collection_events/*";
    public final static String INSTANCES_FILE_PATH = "google-traces/instance_events/*";
    public final static String INSTANCE_HEADER = "time,type,collection_id,priority,instance_index,resource_request.cpus,resource_request.memory";
    public final static String COLLECTION_HEADER = "time,type,collection_id,priority";
    public final static String MASTER_URL = "local[*]";
    public final static String APP_NAME = "EP1-DSID";
    public final static String SCHEDULER_MODE = "spark.scheduler.mode";
    public final static String DRIVER_MEMORY = "spark.driver.memory";
    public final static String POOL_NAME = "spark.scheduler.pool";
    public final static String FAIR_SCHEDULING_POOL = "fair_pool";
    public final static String FAIR_SCHEDULING = "FAIR";
    public final static String FIFO_SCHEDULING = "FIFO";
    public final static Long ONE_HOUR_IN_MICROSECONDS = 3600_000_000L;
}
