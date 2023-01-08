package mm.util.analytics;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.function.Supplier;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DefaultAnalyticsTest {
    Analytics analytics;
    String serverVersion = RandomStringUtils.randomAlphanumeric(10);

    @BeforeEach
    public void setup() throws Exception {
        Supplier<String> splServerVersion = () -> this.serverVersion;    
        analytics = DefaultAnalytics.getInstance(splServerVersion);
    }
    
    @Test
    void testCreate() {
        assertNotNull(analytics);
    }

    @Test
    void testStartSession() {
        String uid = RandomStringUtils.randomAlphanumeric(10);
        try {
            analytics.startSession(uid);
        } catch (final Exception e) {
            fail(e);
        }    
    }

}
