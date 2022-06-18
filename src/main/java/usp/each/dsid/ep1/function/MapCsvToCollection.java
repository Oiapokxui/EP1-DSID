package usp.each.dsid.ep1.function;

import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function;
import org.springframework.stereotype.Service;

import usp.each.dsid.ep1.model.Collection;
import usp.each.dsid.ep1.model.EventFactory;

@Service
public class MapCsvToCollection implements Function<String, Optional<Collection>> {
    @Override public Optional<Collection> call(final String s) {
        return (new EventFactory()).buildCollection(s);
    }
}