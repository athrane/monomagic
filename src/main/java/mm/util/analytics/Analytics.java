package mm.util.analytics;

/**
 * Interface for mod analytics.
 */
public interface Analytics {
   
	/**
	 * Post exception.
	 * 
	 * @param uid user ID.
	 * @param e   exception to report.
	 * 
	 * @throws Exception
	 */
	public void postException(String uid, Throwable e) throws Exception;
    
	/**
	 * Post error.
	 * 
	 * @param uid user ID.
	 * @param msg error to report.
	 * 
	 * @throws Exception
	 */
	public void postError(String uid, String msg) throws Exception;

	/**
	 * Start client session.
	 * 
	 * @param uid user ID.
	 * 
	 * @throws Exception
	 */
	public void startSession(String uid) throws Exception;

}
