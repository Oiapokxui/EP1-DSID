package usp.each.dsid.ep1.utils;

public class Constants {
    public final static String COLLECTIONS_FILE_PATH = "sample-traces/collection_events/*";
    public final static String INSTANCES_FILE_PATH = "google-traces/instance_events/*";
    public final static String MASTER_URL = "local[*]";
    public final static String APP_NAME = "EP1-DSID";
    public final static String SCHEDULER_MODE = "spark.scheduler.mode";
    public final static String POOL_NAME = "spark.scheduler.pool";
    public final static String FAIR_SCHEDULING_POOL = "fair_pool";
    public final static String FAIR_SCHEDULING = "FAIR";
    public final static String FIFO_SCHEDULING = "FIFO";
}
