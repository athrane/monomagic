package mm.util.proxy;

/**
 * Interface for resolving Minecraft sided resources.
 */
public interface Proxy {
    
    /**
     * Get the current Minecraft user. 
     * 
     * This method will only client side, not server side.
     * 
     * @return the current Minecraft user
     */
    String getUser();

	/**
	 * Start analytics session.
	 */
	public void startAnalyticsSession();
}
