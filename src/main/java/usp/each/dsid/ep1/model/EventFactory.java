package usp.each.dsid.ep1.model;

import java.io.Serializable;

import org.apache.spark.api.java.Optional;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Service
public class EventFactory implements Serializable {
    private final CsvSchema.Builder baseSchema = CsvSchema.builder()
            .addColumn("time")
            .addColumn("type")
            .addColumn("collection_id")
            .addColumn("priority");

    private final CsvSchema collectionEventSchema = baseSchema.build();

    private final CsvSchema instanceEventSchema = baseSchema.addColumn("instance_index")
            .addColumn("resource_request.cpu")
            .addColumn("resource_request.memory")
            .build();

    public Optional<Instance> buildInstance(final String csv) {
        try {
            final Instance it = new CsvMapper()
                    .readerFor(Instance.class)
                    .with(instanceEventSchema)
                    .readValue(csv);
            return Optional.ofNullable(it);
        }
        catch(final Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Collection> buildCollection(final String csv) {
        try {
            final Collection it = new CsvMapper()
                    .readerFor(Collection.class)
                    .with(collectionEventSchema)
                    .readValue(csv);
            return Optional.ofNullable(it);
        }
        catch(final Exception e) {
            return Optional.empty();
        }
    }
}
