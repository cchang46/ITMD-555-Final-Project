package com.example.coursebooking;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.OffsetDateTime;

public class ScheduleActivity extends AppCompatActivity {

    // Define the variable of CalendarView type
    // and TextView type;
    CalendarView calendar;
    TextView date_view;
    RadioGroup radioGroupButton;
    int calendarYear, calendarMonth, calendarDay;
    int selectedRadioButtonId;

    Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Intent i = getIntent();

        // By ID we can use each component
        // which id is assign in xml file
        // use findViewById() to get the
        // CalendarView and TextView
        calendar = (CalendarView)
                findViewById(R.id.calendar);

        radioGroupButton = (RadioGroup)
                findViewById(R.id.radioGroup);
        radioGroupButton.clearCheck();

        confirm = (Button) findViewById(R.id.confirm);
        confirm.setEnabled(false);

        // Add listeners

        // Add Listener in calendar
        calendar
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            // In this Listener have one method
                            // and in this method we will
                            // get the value of DAYS, MONTH, YEARS
                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth) {

                                // Add 1 in month because month
                                // index is start with 0
                                calendarYear = year;
                                calendarMonth = month + 1;
                                calendarDay = dayOfMonth;

                                radioGroupButton.setVisibility(View.VISIBLE);
                            }
                        });

        radioGroupButton.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {

                        // Get the selected Radio Button
                        RadioButton
                                radioButton
                                = (RadioButton) group
                                .findViewById(checkedId);
                        selectedRadioButtonId = radioButton.getId();
                        confirm.setEnabled(true);
                    }
                });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateTime = calendarYear
                        + "-" + (calendarMonth < 10 ? "0" + calendarMonth : calendarMonth)
                        + "-" + (calendarDay < 10 ? "0" + calendarDay : calendarDay) + "T";
                if (selectedRadioButtonId == R.id.radioButton1) {
                    dateTime += "09:00:00-05:00";
                } else if (selectedRadioButtonId == R.id.radioButton2) {
                    dateTime += "15:00:00-05:00";
                } else if (selectedRadioButtonId == R.id.radioButton3) {
                    dateTime += "18:30:00-05:00";
                }
                Log.i("info", dateTime);

                OffsetDateTime odt = OffsetDateTime.parse(dateTime);
                long time = odt.toEpochSecond() * 1000;

                //go to confirm activity
                Intent intent = new Intent(getBaseContext(), ConfirmActivity.class);
                intent.putExtra("time", time);
                startActivity(intent);
//
//                Intent intent = new Intent(Intent.ACTION_INSERT)
//                        .setData(CalendarContract.Events.CONTENT_URI)
//                        .putExtra(CalendarContract.Events.TITLE, "Backing Course - " + AppSingleton.courses.get(AppSingleton.selectedCourse).getName())
//                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "location")
//                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, time)
//                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, time + 60 * 60 * 2.5 * 1000);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                }
            }
        });

    }
}