package mm.util.analytics.event;

/**
 * Abstract base class for Analytics event.
 * 
 * https://developers.google.com/analytics/devguides/collection/protocol/ga4/reference?client_type=firebase#payload
 * https://developers.google.com/analytics/devguides/collection/protocol/ga4/reference/events
 */
abstract public class AbstractAnalyticsEvent implements AnalysisEvent {

    /**
     * Event parameters;
     */
    String name;
    
}
