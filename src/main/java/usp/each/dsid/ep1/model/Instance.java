package usp.each.dsid.ep1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Models an entry on the instance events table
 */

@NoArgsConstructor
@AllArgsConstructor
public class Instance extends Event {
    @Getter
    @JsonProperty("instance_index")
    int index;
    @Getter
    @JsonProperty("resource_request.cpu")
    double cpuResourcesRequested;
    @Getter
    @JsonProperty("resource_request.memory")
    double memoryResourcesRequested;

}
