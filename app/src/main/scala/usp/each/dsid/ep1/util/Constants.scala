package usp.each.dsid.ep1.util

object Constants {
  val APP_NAME = "EP1-DSID"
  val MASTER_URL = "local[*]"
  val INSTANCE_EVENTS_URL = "google-traces/instance_events/*"
  val DRIVER_MEMORY = "spark.driver.memory"
  val EXECUTOR_MEMORY = "spark.executor.memory"
  val MEMORY_SIZE = "7g"
  val HEADER = "time,type,collection_id,priority,instance_index,resource_request.cpus,resource_request.memory"
}
