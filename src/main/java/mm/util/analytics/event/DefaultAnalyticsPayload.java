package mm.util.analytics.event;

import static mm.ModConstants.GA_FIREBASE_APP_ID;

/**
 * Default implementation of {@linkplain AnalyticsPayload} interface.
 * 
 * https://developers.google.com/analytics/devguides/collection/protocol/ga4/reference?client_type=firebase#payload_post_body
 */
public class DefaultAnalyticsPayload implements AnalyticsPayload {

    /**
     *  Uniquely identifies a specific installation of a Firebase app.
     */
    String app_instance_id = GA_FIREBASE_APP_ID;

    /**
     * Set to true to indicate these events should not be used for personalized ads.
     * Is set to false.
     */
    boolean non_personalized_ads = false;

    /**
     * A unique identifier for a user.
     */
    String user_id;


    /**
     * Analytics Events.
     */
    AnalysisEvent[] events = {};

    /**
     * Constructor.
     * 
     * @param uid user ID.
     */
    DefaultAnalyticsPayload(String uid) {
        user_id = uid;
    }
    
    /**
     * Factory method.
     * 
     * @param uid user ID.
     * 
     * @return analytics payload.
     */
    public static DefaultAnalyticsPayload getInstance(String uid) {
        return new DefaultAnalyticsPayload(uid);
    }

    @Override
    public void addEvent(AnalysisEvent event) {
        AnalysisEvent[] singleton = {event};
        events = singleton;
    }
    
}
