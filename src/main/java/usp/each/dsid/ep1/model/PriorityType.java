package usp.each.dsid.ep1.model;

public enum PriorityType {
    FREE,
    BEST_EFFORT_BATCH,
    MID_TIER,
    PRODUCTION_TIER,
    MONITORING_TIER;

    public static PriorityType get(final int priority) {
        if(priority <= 99) {
            return FREE;
        }
        else if(priority <= 115) {
            return BEST_EFFORT_BATCH;
        }
        else if(priority <= 119) {
            return MID_TIER;
        }
        else if(priority <= 359) {
            return PRODUCTION_TIER;
        }
        return MONITORING_TIER;
    }
}
