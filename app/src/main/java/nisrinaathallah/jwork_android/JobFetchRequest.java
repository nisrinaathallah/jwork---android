package nisrinaathallah.jwork_android;
/**
 * @author Nisrina Athallah - 1806148813
 * @version 27-06-2021
 */
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * inisiasi class
 */
public class JobFetchRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/invoice/jobseeker/";
    private Map<String, String> params;

    /**
     * constructor untuk Job Fetch Request
     * @param jobseekerid
     * @param listener
     */
    public JobFetchRequest(String jobseekerid, Response.Listener<String> listener){
        super(Method.GET, URL+jobseekerid, listener, null);
        params = new HashMap<>();
    }

    /**
     * method getParam untuk Job Fetch Request
     * @return
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}


