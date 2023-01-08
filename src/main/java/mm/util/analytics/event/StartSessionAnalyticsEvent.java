package mm.util.analytics.event;

/**
 * Start session analysis event.
 * 
 * Used to signal user has started client side gameplay session.
 * Sub class of {@linkplain AbstractAnalyticsEvent}. 
 */
public class StartSessionAnalyticsEvent extends AbstractAnalyticsEvent {
    
    /**
     * SessionStartAnalyticsEvent constructor.
     */
    StartSessionAnalyticsEvent() {
        this.name = "start_session";
    }

    /**
     * Factory method.
     * 
     * @return start session event.
     */
    public static AnalysisEvent getInstance() {
        return new StartSessionAnalyticsEvent();
    }
}
