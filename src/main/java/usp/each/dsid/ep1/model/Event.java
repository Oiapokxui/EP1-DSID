package usp.each.dsid.ep1.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Models an Instance events table
 */
@AllArgsConstructor
@NoArgsConstructor
abstract class Event implements Serializable {
    @Getter @Setter
    @JsonProperty("time")
    Long time;

    @Getter @Setter
    @JsonProperty("type")
    EventType type;

    @Getter @Setter
    @JsonProperty("collection_id")
    Double collectionId;

    @Getter
    public PriorityType priority;

    @JsonProperty("priority")
    void priority(final int priority) {
        this.priority = PriorityType.fromInteger(priority);
    }

    @Override public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        final Event event = (Event)o;
        return priority == event.priority && time.equals(event.time) && type == event.type && collectionId.equals(event.collectionId);
    }

    @Override public int hashCode() {
        return Objects.hash(time, type, collectionId, priority);
    }
}