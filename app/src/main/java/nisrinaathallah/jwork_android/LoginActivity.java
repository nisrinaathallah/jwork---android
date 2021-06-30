package nisrinaathallah.jwork_android;
/**
 * @author Nisrina Athallah - 1806148813
 * @version 27-06-2021
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * insiasi class Login Activity
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * inisiasi class on create untuk membuat layout view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.ButtonText);
        TextView plainTextRegister = findViewById(R.id.ViewText);

        /**
         * insiasi button login listener
         */
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            /**
             * insiasi method untuk onCreate
             * @param view
             */
            @Override
            public void onClick(View view){
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    /**
                     * inisiasi method untuk menunggu jawaban jwork
                     * @param response
                     */
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if ( jsonObject != null ) {
                                Toast.makeText(LoginActivity.this, "Login Successful",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                intent.putExtra("jobseekerid", jsonObject.getInt("id"));
                                startActivity(intent);
                            }
                        } catch (JSONException error){
                            Toast.makeText(LoginActivity.this, "Login Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        plainTextRegister.setOnClickListener(new View.OnClickListener(){
            /**
             * insiasi method menunggu jawaban ketika button diklik
             * @param view
             */
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}