package usp.each.dsid.ep1.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Models an entry on the instance events table
 */

@NoArgsConstructor
@AllArgsConstructor
public class Instance extends Event implements Serializable {
    @Getter
    @JsonProperty("instance_index")
    int index;
    @Getter
    @JsonProperty("resource_request.cpu")
    BigDecimal cpuResourcesRequested;
    @Getter
    @JsonProperty("resource_request.memory")
    BigDecimal memoryResourcesRequested;

    // BOILER-PLATE CODE in order to maitain compatibility with spark

    @Override public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        if(!super.equals(o)) {
            return false;
        }
        final Instance instance = (Instance)o;
        return index == instance.index && instance.cpuResourcesRequested.compareTo(cpuResourcesRequested) == 0
                && instance.memoryResourcesRequested.compareTo(memoryResourcesRequested) == 0;
    }

    @Override public int hashCode() {
        return Objects.hash(super.hashCode(), index, cpuResourcesRequested, memoryResourcesRequested);
    }
}
