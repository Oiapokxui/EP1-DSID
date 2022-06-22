package usp.each.dsid.ep1

import usp.each.dsid.ep1.SparkSessionConfig.spark
import usp.each.dsid.ep1.util.Constants.INSTANCE_EVENTS_URL

object App {
  def main(args: Array[String]): Unit = {
    println(greeting())

    val instances = spark.read.csv(INSTANCE_EVENTS_URL).cache()
    instances.agg(Map("_c6" -> "avg")).first()
  }

  def greeting(): String = "Hello, world! FABD2F"
}
