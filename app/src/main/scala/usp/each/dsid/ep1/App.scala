package usp.each.dsid.ep1

import usp.each.dsid.ep1.SparkSessionConfig.spark
import usp.each.dsid.ep1.util.Constants.{HEADER, INSTANCE_EVENTS_URL}

object App {
  def main(args: Array[String]): Unit = {
    println(greeting())

    val instances = spark.sparkContext.textFile(INSTANCE_EVENTS_URL).cache
    val sum = instances.filter(s => !s.equals(HEADER))
      .map(s => s.split(","))
      .filter(s => s.length == 7)
      .map(s => s(6))
      .map(s => s.toDouble)
      .reduce((a, b) => a + b)
    //    val instances = spark.read.csv(INSTANCE_EVENTS_URL).cache()
    //    instances.agg(Map("_c6" -> "avg")).first()
  }

  def greeting(): String = "Hello, world! FABD2F"
}
