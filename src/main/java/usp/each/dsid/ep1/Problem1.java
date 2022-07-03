package usp.each.dsid.ep1;

import static usp.each.dsid.ep1.utils.Constants.INSTANCES_FILE_PATH;
import static usp.each.dsid.ep1.utils.Constants.INSTANCE_HEADER;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Problem1 {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    public void run() {
        final JavaRDD<String[]> jobs = sparkContext.textFile(INSTANCES_FILE_PATH)
            .filter(str -> !str.equals(INSTANCE_HEADER))
            .map(str -> str.split(",")).cache();

        // StructType doubleSchema = DataTypes.createStructType(List.of(DataTypes.createStructField("value", DataTypes.DoubleType, false)));

        final JavaRDD<String> memory = jobs.filter(arr -> arr.length == 7)
            .map(arr -> arr[6]);

        Dataset<Row> memoryDf = sparkSession.createDataFrame(memory, scala.Double.class);
        memoryDf.createOrReplaceTempView("memory");

        final JavaRDD<String> cpu = jobs.filter(arr -> arr.length == 7)
            .map(arr -> arr[5]);

        Dataset<Row> cpuDf = sparkSession.createDataFrame(cpu, scala.Double.class);
        cpuDf.createOrReplaceTempView("cpu");

        memoryDf.show(3);

        sparkSession.sql("select avg(_c1) from memory").first().getDecimal(0);
        sparkSession.sql("select avg(_c1) from cpu").first().getDecimal(0);
        sparkSession.sql("select stddev_sample(_c1) from memory").first().getDecimal(0);
        sparkSession.sql("select stddev_sample(_c1) from cpu").first().getDecimal(0);
        sparkSession.sql("select max(_c1) from memory").first().getDecimal(0);
        sparkSession.sql("select max(_c1) from cpu").first().getDecimal(0);
        sparkSession.sql("select min(_c1) from memory").first().getDecimal(0);
        sparkSession.sql("select min(_c1) from cpu").first().getDecimal(0);
    }
}
