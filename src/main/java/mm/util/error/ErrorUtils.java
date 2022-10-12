package mm.util.error;

import static java.util.Optional.ofNullable;
import static mm.MonoMagic.getLogger;
import static mm.MonoMagic.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * Helper class with error handling utilities.
 */
public class ErrorUtils {
    
	/**
	 * Exception reporting facility.
	 * 
	 * @param e exception to report and log.
	 */
	public static void reportAndLogException(Throwable e) {
		Optional<String> nullableString = ofNullable(e.getMessage());
		nullableString.ifPresent(s -> getLogger().error(s));

		// get and log stack trace
		var sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		var stacktraceString = sw.toString();
		getLogger().error(stacktraceString);
		reportException(e);
	}

	/**
	 * Exception reporting facility.
	 * 
	 * @param e exception to report.
	 */
	public static void reportException(Throwable e) {		
		try {
			var mod = getMod();
			var analytics = mod.getAnalytics();
			var proxy = mod.getProxy();
			analytics.postException(proxy.getUser(), e);
		} catch (Exception ex) {

			getLogger().error("Posting exception:" + e + " failed with: " + ex);

			// get stack trace for original exception
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			getLogger().error("Stack trace for original exception:" + sw);

			// get stack trace for positing exception
			sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			getLogger().error("Stack trace for posting exception:" + sw);
		}
	}

	/**
	 * Exception reporting facility.
	 * 
	 * @param msg error to report.
	 */
	public static void reportAndLogError(String msg) {
		try {
			var mod = getMod();
			var analytics = mod.getAnalytics();
			var proxy = mod.getProxy();
			analytics.postError(proxy.getUser(), msg);
			getLogger().error(msg);
		} catch (Exception ex) {

			getLogger().error("Posting error:" + msg + " failed with: " + ex);

			// get stack trace for positing exception
			StringWriter sw = new StringWriter();
			ex.printStackTrace(new PrintWriter(sw));
			getLogger().error("Stack trace for posting exception:" + sw);
		}
	}

}
