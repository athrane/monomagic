package mm.util.analytics;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

/**
 * A null response handler for handling response from {@linkplain FutureRequestExecutionService}.
 */
public class NullResponseHandler implements ResponseHandler<Boolean> {

    @Override
    public Boolean handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        return response.getStatusLine().getStatusCode() == 200;    
    }
    
}
