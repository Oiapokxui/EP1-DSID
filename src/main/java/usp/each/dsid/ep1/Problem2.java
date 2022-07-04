package usp.each.dsid.ep1;

import static usp.each.dsid.ep1.utils.Constants.INSTANCES_FILE_PATH;
import static usp.each.dsid.ep1.utils.Constants.INSTANCE_HEADER;
import static usp.each.dsid.ep1.utils.Constants.ONE_HOUR_IN_MICROSECONDS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usp.each.dsid.ep1.model.PriorityType;

@Service
public class Problem2 {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    @Autowired JavaSparkContext sparkContext;

    @Autowired SparkSession sparkSession;

    private static final int CPU_INDEX = 0;

    private static final int MEM_INDEX = 1;


    public void run() {
        final JavaRDD<double[]> jobs = sparkContext.textFile(INSTANCES_FILE_PATH)
                .filter(str -> !str.equals(INSTANCE_HEADER))
                .map(str -> str.split(","))
                .filter(arr -> arr.length == 7)
                .map(arr -> new double[] {Double.parseDouble(arr[3]), Double.parseDouble(arr[5]), Double.parseDouble(arr[6])})
                .cache();

        final Dataset<Double> free_cpu = getDatasetFromRdd(jobs, CPU_INDEX, PriorityType.FREE);

        final Dataset<Double> free_mem = getDatasetFromRdd(jobs, MEM_INDEX, PriorityType.FREE);

        final Dataset<Double> best_cpu = getDatasetFromRdd(jobs, CPU_INDEX, PriorityType.BEST_EFFORT_BATCH);

        final Dataset<Double> best_mem = getDatasetFromRdd(jobs, MEM_INDEX, PriorityType.BEST_EFFORT_BATCH);

        final Dataset<Double> mid_cpu = getDatasetFromRdd(jobs, CPU_INDEX, PriorityType.MID_TIER);

        final Dataset<Double> mid_mem = getDatasetFromRdd(jobs, MEM_INDEX, PriorityType.MID_TIER);           

        final Dataset<Double> production_cpu = getDatasetFromRdd(jobs, CPU_INDEX, PriorityType.PRODUCTION_TIER);  

        final Dataset<Double> production_mem = getDatasetFromRdd(jobs, MEM_INDEX, PriorityType.PRODUCTION_TIER); 

        final Dataset<Double> monitoring_cpu = getDatasetFromRdd(jobs, CPU_INDEX, PriorityType.MONITORING_TIER); 

        final Dataset<Double> monitoring_mem = getDatasetFromRdd(jobs, MEM_INDEX, PriorityType.MONITORING_TIER); 
        

        List<Dataset<Double>> datasets = List.of(
            free_cpu, 
            free_mem, 
            best_cpu, 
            best_mem, 
            mid_cpu, 
            mid_mem,
            production_cpu,
            production_mem,
            monitoring_cpu,
            monitoring_mem);

        String[] datasetNames = new String[] {
            "free_cpu", 
            "free_mem", 
            "best_cpu", 
            "best_mem", 
            "mid_cpu", 
            "mid_mem",
            "production_cpu",
            "production_mem",
            "monitoring_cpu",
            "monitoring_mem"};

        String[] operations = new String[] {"max", "min", "avg", "stddev_samp"};

        log.info("******* PROBLEM 2");

        for (int i = 0; i < datasets.size(); i++) {
            String datasetName = datasetNames[i];
            Dataset<Double> dataset = datasets.get(i);
            log.info("{}", dataset.columns());
            System.out.println(dataset.columns());

            for (String operation : operations) {
                final Map<String, String> queryMap = new HashMap<>();
                queryMap.put("value", operation);
                Double result = dataset.agg(queryMap).first().getDouble(0);
                log.info("******* {}({}) - {}", operation, datasetName, result);
            }
        }
        

    }

    Dataset<Double> getDatasetFromRdd(final JavaRDD<double[]> jobs, final int index, final PriorityType priorityType) {
        Dataset<Double> dataset = sparkSession.createDataset(
                    jobs.filter(arr -> PriorityType.get((int) arr[0]).equals(priorityType))
                        .map(arr -> arr[index])
                        .rdd(),
                    Encoders.DOUBLE());

        dataset.createOrReplaceTempView("table");
        return dataset;


    }
    // private static void doFunction(Dataset<Double> rdd, Function2<Long, Long, Long> function, PriorityType priority, String functionType) {
    //     log.info("RESULT - {} {} {}", functionType, priority, rdd.reduce(function));
    // }

}


// Function2<Long, Long, Long>[] functions = new Function2<Long, Long, Long>[] {
        //         (a,b) ->  a>b ? a : b, 
        //         (a,b) -> a<b ? a : b, 
        //         (a,b) -> a + b / 2};
        // PriorityType priorities =  PriorityType.values();
        // String[] functionTypes = new String[] { "Max", "Min", "Avg", "StdDev"};
        // for ()

        // log.info("RESULT - avg FREE cpu {}",freeInstances_cpu.reduce(functions[0]));
        // log.info("RESULT - avg BEST cpu {}",bestEffortInstances_cpu.reduce(functions[0]));
        // log.info("RESULT - avg MID TIER cpu {}",midTierInstances_cpu.reduce((a,b) -> a + b / 2));
        // log.info("RESULT - avg PRODUCTION TIER cpu {}",productionTierInstances_cpu.reduce((a,b) -> a + b / 2));
        // log.info("RESULT - avg MONITORING TIER cpu {}",monitoringTierInstances_cpu.reduce((a,b) -> a + b / 2));
        // log.info("RESULT - avg FREE mem {}",freeInstances_mem.reduce((a,b) -> a + b / 2));
        // log.info("RESULT - avg BEST mem {}",bestEffortInstances_mem.reduce((a,b) -> a + b / 2));
        // log.info("RESULT - avg MID TIER mem {}",midTierInstances_mem.reduce((a,b) -> a + b / 2));
        // log.info("RESULT - avg PRODUCTION TIER mem {}",productionTierInstances_mem.reduce((a,b) -> a + b / 2));
        // log.info("RESULT - avg MONITORING TIER mem {}",monitoringTierInstances_mem.reduce((a,b) -> a + b / 2));