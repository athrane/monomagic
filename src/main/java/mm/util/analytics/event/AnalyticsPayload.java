package mm.util.analytics.event;

/**
 * Interface for Google analytics 4 Firebase request payload class.
 * 
 * https://developers.google.com/analytics/devguides/collection/protocol/ga4/reference?client_type=firebase#payload
 */
public interface AnalyticsPayload {
    
    /**
     * Add event to payload.
     * 
     * @param event event to add.
     */
    public void addEvent(AnalysisEvent event);
}
