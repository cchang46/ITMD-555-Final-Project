package com.example.coursebooking;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;


import androidx.appcompat.app.AppCompatActivity;

import java.time.OffsetDateTime;

public class ConfirmActivity extends AppCompatActivity {

    // Define the variable
    private Button okButton, syncButton;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        //Get
        Intent i = getIntent();
        time = i.getLongExtra("time", 0);

        //TODO: send e-mail

        //Add Listener for okButton
        okButton = (Button)
                findViewById(R.id.ok_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //back to Homepage
                    Intent intent = new Intent(getBaseContext(), CourseReaderActivity.class);
                    startActivity(intent);
                    finish();
            }
        });

        //Add Listener for syncButton
        syncButton = (Button)
                findViewById(R.id.sync_button);

        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, "Backing Course - " + AppSingleton.courses.get(AppSingleton.selectedCourse).getName())
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, time)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, time + 9000000);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }

}
