package usp.each.dsid.ep1

import org.apache.spark.sql.SparkSession
import usp.each.dsid.ep1.util.Constants.{APP_NAME, MASTER_URL}

object SparkSessionConfig {
  val spark: SparkSession = SparkSession.builder()
    .appName(APP_NAME)
    .master(MASTER_URL)
    .getOrCreate()

  private val ctx = spark.sparkContext.getConf.set("", "");
}
