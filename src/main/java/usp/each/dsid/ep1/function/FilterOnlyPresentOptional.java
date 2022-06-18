package usp.each.dsid.ep1.function;

import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.Function;
import org.springframework.stereotype.Service;

@Service
public class FilterOnlyPresentOptional<T> implements Function<Optional<T>, Boolean> {
    @Override public Boolean call(final Optional<T> a) {
        return a.isPresent();
    }
}
