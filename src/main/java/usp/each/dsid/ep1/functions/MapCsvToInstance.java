package usp.each.dsid.ep1.functions;

import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function;
import org.springframework.stereotype.Service;

import usp.each.dsid.ep1.model.EventFactory;
import usp.each.dsid.ep1.model.Instance;

@Service
public class MapCsvToInstance implements Function<String, Optional<Instance>> {
    @Override public Optional<Instance> call(final String s) {
        return (new EventFactory()).buildInstance(s);
    }
}