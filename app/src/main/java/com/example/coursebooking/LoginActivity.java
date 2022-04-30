package com.example.coursebooking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.coursebooking.services.LoginService;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button b1, b2;
    EditText ed1, ed2;

    int mPosition;

    TextView tx1, signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent i = getIntent();
        mPosition = i.getIntExtra("position", 0);

        b1 = (Button) findViewById(R.id.button);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);

        b2 = (Button) findViewById(R.id.button2);
        signup = (TextView) findViewById(R.id.textSignUp);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LoginService loginService = new LoginService(LoginActivity.this);
                loginService.signin(new LoginService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials... " , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(getBaseContext(), ScheduleActivity.class);
                        startActivity(intent);
                    }
                }, ed1.getText().toString(), ed2.getText().toString());
            }
        });

        //Add Listener for cancel
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Add Listener for signup
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to Sign Up Activity
                Intent intent = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
