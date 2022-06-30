package usp.each.dsid.ep1.model;

public enum EventType {
    SUBMIT,
    QUEUE,
    ENABLE,
    SCHEDULE,
    EVICT,
    FAIL,
    FINISH,
    KILL,
    LOST,
    UPDATE_PENDING,
    UPDATE_RUNNING;

    public static EventType get(int index) {
        return EventType.values()[index];
    }
}
