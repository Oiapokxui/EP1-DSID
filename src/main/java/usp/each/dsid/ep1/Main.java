package usp.each.dsid.ep1;

import usp.each.dsid.ep1.model.Collection;
import usp.each.dsid.ep1.model.EventFactory;
import usp.each.dsid.ep1.model.Instance;

public class Main {
    final static String FILE_PATH = "/google-traces/*";
    final static String MASTER_URL = "local[*]";

    public static void main(final String[] args) {
        //                final SparkConf sparkConf = new SparkConf().setAppName("EP1-DSID").setMaster(MASTER_URL);
        //                final JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        //                final var text = sparkContext.textFile(FILE_PATH);
        //                System.out.println(text.first());
        final EventFactory eventFactory = new EventFactory();
        final Collection a = eventFactory.buildCollection("571244456538,0,281336019053,100").orElse(null);
        final Instance b = eventFactory.buildInstance("503028964266,8,9907124724,200,2,0.00440216064453125,0.0009412765502929688").orElse(null);
    }
}
