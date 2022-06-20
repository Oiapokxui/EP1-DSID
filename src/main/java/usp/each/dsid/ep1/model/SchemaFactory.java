package usp.each.dsid.ep1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SchemaFactory implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SchemaFactory.class);

    List<StructField> baseSchema() {
        final List<StructField> fields = new ArrayList<>();
        fields.add(DataTypes.createStructField("time", DataTypes.LongType, false));
        fields.add(DataTypes.createStructField("type", DataTypes.StringType, false));
        fields.add(DataTypes.createStructField("collection_id", DataTypes.LongType, false));
        fields.add(DataTypes.createStructField("priority", DataTypes.IntegerType, false));
        return fields;
    }

    public StructType collectionSchema() {
        final List<StructField> fields = baseSchema();
        return DataTypes.createStructType(fields);
    }

    public StructType eventSchema() {
        final List<StructField> fields = baseSchema();
        fields.add(DataTypes.createStructField("instance_index", DataTypes.LongType, false));
        fields.add(DataTypes.createStructField("resource_requests_cpu", DataTypes.DoubleType, true));
        fields.add(DataTypes.createStructField("resource_requests_memory", DataTypes.DoubleType, true));
        return DataTypes.createStructType(fields);
    }
}
