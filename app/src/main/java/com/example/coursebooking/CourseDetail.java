package com.example.coursebooking;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursebooking.models.Course;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CourseDetail extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mCourseDetail;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("info", "Detail view");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);
        Intent i = getIntent();
        mPosition = AppSingleton.selectedCourse;
        Course course = AppSingleton.courses.get(mPosition);

        mImageView = findViewById(R.id.image);
        mCourseDetail = findViewById(R.id.courseDetail);
        Picasso.get().load(AppSingleton.AWS_S3_URL + course.getName().toLowerCase() + "_hd_1.jpg").into(mImageView);
        mCourseDetail.setText(course.getDetail());


        //schedule button
        Button scheduleButton;
        scheduleButton = (Button) findViewById(R.id.schedule);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),
                        LoginActivity.class);
                startActivity(i);

            }
        });
    }
}
