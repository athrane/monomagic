package mm.server.proxy;

import mm.util.proxy.Proxy;

/**
 * Implementation of the {@linkplain Proxy} interface.
 * Resolves server side values and resources.
 */
public class ServerProxy implements Proxy {
    
    @Override
    public String getUser() {
        return "N/A, no user defined server side.";
    }

    @Override
    public void startAnalyticsSession() {
        // TODO Auto-generated method stub
        
    }

}
