package usp.each.dsid.ep1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Models an Instance events table
 */
@AllArgsConstructor
@NoArgsConstructor
abstract class Event {
    @Getter
    @JsonProperty("time")
    Long time;

    @Getter
    @JsonProperty("type")
    EventType type;

    @Getter
    @JsonProperty("collection_id")
    Double collectionId;

    @Getter
    @JsonProperty("priority")
    int priority;

}