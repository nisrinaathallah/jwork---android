package nisrinaathallah.jwork_android;
/**
 * @author Nisrina Athallah - 1806148813
 * @version 27-06-2021
 */
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * inisiasi class Bonus Request
 */
public class BonusRequest extends StringRequest {
    private Map<String,String> params;
    private static final String URL = "http://10.0.2.2:8080/bonus/";

    /**
     * constructor pada Bonus Request
     * @param promoCode
     * @param listener
     */
    public BonusRequest(String promoCode, Response.Listener<String> listener) {
        super(Method.GET, URL + promoCode, listener, null);
        params = new HashMap<>();
    }

    /**
     * Method untuk getParam pada Bonus Request
     * @return
     * @throws AuthFailureError
     */
    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}