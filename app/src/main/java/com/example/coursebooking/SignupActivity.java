package com.example.coursebooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursebooking.services.LoginService;
import com.example.coursebooking.services.SignupService;

import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    //Define variable
    TextView login;
    Button cancel;
    Button signup;

    EditText ed1, ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ed1 = (EditText) findViewById(R.id.editText_name);
        ed2 = (EditText) findViewById(R.id.editText_password);

        //Add Listener for cancel
        cancel = (Button) findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //Add Listener for login
        signup = (Button) findViewById(R.id.button_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SignupService signupService = new SignupService(SignupActivity.this);
                signupService.signup(new SignupService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), "Something went wrong... " , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(getBaseContext(), ScheduleActivity.class);
                        startActivity(intent);
                    }
                }, ed1.getText().toString(), ed2.getText().toString());
            }
        });
    }
}
