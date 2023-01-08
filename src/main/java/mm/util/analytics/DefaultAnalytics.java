package mm.util.analytics;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static mm.ModConstants.ANALYTICS_URL;
import static mm.ModConstants.GA_API_SECRET;
import static mm.ModConstants.GA_API_VERSION;
import static mm.ModConstants.GA_APP_ID;
import static mm.ModConstants.GA_HITTYPE_EXCEPTION;
import static mm.ModConstants.GA_PROPERTY;
import static mm.ModConstants.GA_SOURCE;
import static mm.ModConstants.NAME;
import static mm.ModConstants.NUMBER_HTTP_THREADS;
import static mm.ModConstants.VERSION;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.FutureRequestExecutionService;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import mm.util.analytics.event.AnalyticsPayload;
import mm.util.analytics.event.DefaultAnalyticsPayload;
import mm.util.analytics.event.StartSessionAnalyticsEvent;
import net.minecraftforge.versions.forge.ForgeVersion;
import net.minecraftforge.versions.mcp.MCPVersion;

/**
 * Default implementation of the {@linkplain Analytics} interface.
 */
public class DefaultAnalytics implements Analytics {

    /**
     * GA event category.
	 * Defined as the mod name and version.
     */
	final static String EVENT_CATEGORY = NAME + "-" + VERSION;
	
	/**
	 * HTTP client.
	 */
	HttpClient httpClient;

	/**
	 * HTTP context.
	 */
	HttpClientContext httpContext;

	/**
	 * HTTP URI builder
	 */
	URIBuilder uriBuilder;

	/**
	 * Executor service.
	 */
	ExecutorService executorService;

    /**
	 * Future request service.
	 */
	FutureRequestExecutionService executionService;
    
	/**
	 * HTTP response handler instance.
	 */
	ResponseHandler<Boolean> responseHandler;

    /**
     * The current JVM arguments.
     */
    String jvmArgs;

    /**
     * Map of registered UIDs.
     */
    HashMap<String, String> uids;

	/**
	 * JSON generator.
	 */
	Gson gson;

	/**
	 * Function to resolve server version. 
	 */
	Supplier<String> splServerVersion;

    /**
     * constructor.
	 * 
	 * @param splServerVersion function to supply the server version.
	 * 
     * @throws Exception if initialization fails.
     */
    public DefaultAnalytics(Supplier<String> splServerVersion) throws Exception {
		this.splServerVersion = splServerVersion;
        httpClient = HttpClientBuilder.create().build();
        httpContext = HttpClientContext.create();
        executorService = newFixedThreadPool(NUMBER_HTTP_THREADS);
        executionService = new FutureRequestExecutionService(httpClient, executorService);
        responseHandler = new NullResponseHandler();
        jvmArgs = getJvmArgs();
		uriBuilder = new URIBuilder(ANALYTICS_URL);
		uriBuilder.addParameters(createRequestParameters());
		gson = new Gson();
        uids = new HashMap<>();
	}

    @Override
	public void postException(String uid, Throwable e) throws Exception {

		// get stack trace as string with some additional meta data
		var sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));

        // post
        postError(uid, sw.toString());
	}

    @Override
	public void postError(String uid, String msg) throws Exception {

        // get user info
        var userInfo = createUserInfo(uid);

		// get error as string with some additional meta data
		String description = new StringBuilder()
            .append(msg)
            .append(System.getProperty("line.separator"))
			.append(userInfo).toString();

        // create parameters
		List<NameValuePair> postParameters = createExceptionParameters(uid, EVENT_CATEGORY, description);
		URIBuilder uriBuilder = new URIBuilder(ANALYTICS_URL);
		uriBuilder.addParameters(postParameters);

		// build request and post
		URI uri = uriBuilder.build();
		HttpPost request = new HttpPost(uri);
		executionService.execute(request, httpContext, responseHandler);
	}

	@Override
	public void startSession(String uid) throws Exception {

		// create JSON payload
		AnalyticsPayload payload = DefaultAnalyticsPayload.getInstance(uid);
		payload.addEvent(StartSessionAnalyticsEvent.getInstance());		
		String json = gson.toJson(payload);
        System.out.println("json="+json); 
		StringEntity jsonEntity = new StringEntity(json, ContentType.APPLICATION_JSON);

		// build request and post
		URI uri = uriBuilder.build();
		HttpPost request = new HttpPost(uri);
		request.setEntity(jsonEntity);
		executionService.execute(request, httpContext, responseHandler);				
	}

	/**
	 * Create parameters for exception hit type.
	 * 
	 * @param uid         user ID.
	 * @param category    event category.
	 * @param description exception description.
	 */
	static List<NameValuePair> createExceptionParameters(String uid, String category, String description) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("v", GA_API_VERSION));
		parameters.add(new BasicNameValuePair("t", GA_HITTYPE_EXCEPTION));
		parameters.add(new BasicNameValuePair("tid", GA_PROPERTY));
		parameters.add(new BasicNameValuePair("ds", GA_SOURCE));
		parameters.add(new BasicNameValuePair("an", NAME));
		parameters.add(new BasicNameValuePair("aid", GA_APP_ID));
		parameters.add(new BasicNameValuePair("av", VERSION));
		parameters.add(new BasicNameValuePair("cid", uid));
		parameters.add(new BasicNameValuePair("uid", uid));
		parameters.add(new BasicNameValuePair("exd", description));
		parameters.add(new BasicNameValuePair("exf", "0"));
		return parameters;
	}
	
	/**
	 * Create user info string.
	 * 
	 * Server can be null in physical client, e.g. if the user info is requested
	 * before the logical server is started.
	 * 
	 * @param uid Minecraft user.
	 * 
	 * @return user info string
	 */
	String createUserInfo(String uid) {

		// get Minecraft version
		var mcVersion = splServerVersion.get();

		// get Forge version
		var forgeVersion = ForgeVersion.getVersion();

		// get MCP version
		var mcpVersion = MCPVersion.getMCPVersion();

		var userInfo = new StringBuilder().append(uid).append(";").append(System.getProperty("os.name")).append(",")
				.append(System.getProperty("os.version")).append(",").append(System.getProperty("os.arch")).append(";")
				.append(System.getProperty("java.version")).append(";").append(jvmArgs).append(mcVersion)
				.append(";").append(forgeVersion).append(";").append(mcpVersion).append(";").toString();
		return userInfo;
	}

	/**
	 * Get JVM arguments used to start server.
	 * 
	 * @return JVM arguments used to start server.
	 */
	String getJvmArgs() {
		try {
			var runtimeMXBean = ManagementFactory.getRuntimeMXBean();
			List<String> jvmArgs = runtimeMXBean.getInputArguments();
			var args = new StringBuilder();
			args.append("JVM args:");
			for (String arg : jvmArgs)
				args.append(arg).append(";");
			return args.toString();
		} catch (Exception e) {
			return "JVM args: Not accessible";
		}
	}

	/**
	 * Create request parameters.
	 * 
	 * @return request parameters.
	 */
	static List<NameValuePair> createRequestParameters() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("api_secret", GA_API_SECRET));
		parameters.add(new BasicNameValuePair("firebase_app_id", GA_API_VERSION));
		return parameters;
	}

    /**
     * Factory method.
     * 
	 * @param splServerVersion function to supply the server version.
	 * 
     * @return analytics instance.
	 * 
     * @throws Exception if initialization fails
     */
    public static Analytics getInstance(Supplier<String> splServerVersion) throws Exception {
        return new DefaultAnalytics(splServerVersion);
    }

}
