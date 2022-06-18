package usp.each.dsid.ep1.model;

import java.io.Serializable;

import org.apache.spark.api.java.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Service
public class EventFactory implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(EventFactory.class);

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

    private <T extends Event> Optional<T> buildEvent(final Class<T> clazz, final String csv) {
        final CsvSchema schema = clazz.equals(Instance.class) ? instanceEventSchema : collectionEventSchema;
        try {
            final T it = new CsvMapper()
                    .readerFor(clazz)
                    .with(schema)
                    .readValue(csv);
            return Optional.ofNullable(it);
        }
        catch(final Exception e) {
            log.error("Couldn't map string \"{}\" to an event.", csv);
            log.error("", e);
            return Optional.empty();
        }

    }

    public Optional<Instance> buildInstance(final String csv) {
        return buildEvent(Instance.class, csv);
    }

    public Optional<Collection> buildCollection(final String csv) {
        return buildEvent(Collection.class, csv);
    }
}
