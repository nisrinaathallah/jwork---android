package nisrinaathallah.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText editEmailText = findViewById(R.id.EmailText);
        EditText editPasswordText = findViewById(R.id.PasswordText);
        @SuppressLint("WrongViewCast") EditText buttonText = findViewById(R.id.ButtonText);
        TextView ViewText = findViewById(R.id.ViewText);
        buttonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                String email = editEmailText.getText().toString();
                String password = editEmailText.getText().toString();
                if (email.equals("test@test.com") && password.equals("test")) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            ;


            {

            }
        });


        }}
