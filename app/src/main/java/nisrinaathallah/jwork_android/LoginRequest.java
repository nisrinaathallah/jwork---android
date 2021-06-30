package nisrinaathallah.jwork_android;
/**
 * @author Nisrina Athallah - 1806148813
 * @version 27-06-2021
 */
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;
import java.util.Map;

/**
 * inisiasi class untuk Login Request
 */
public class LoginRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/jobseeker/login";
    private Map<String, String> params;

    /**
     * constructor untuk Login Request
     * @param email
     * @param password
     * @param listener
     */
    public LoginRequest(String email, String password,
                        Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    /**
     * method untuk getter param
     * @return param
     * @throws AuthFailureError
     */
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}