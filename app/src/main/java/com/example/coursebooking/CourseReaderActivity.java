package com.example.coursebooking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coursebooking.models.Course;
import com.example.coursebooking.services.CourseService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CourseReaderActivity extends AppCompatActivity {

    public class CourseAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater mInflator;

        public CourseAdapter(Context c) {
            mContext = c;
            mInflator = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return AppSingleton.courses.size();
        }

        @Override
        public Object getItem(int position) {
            return AppSingleton.courses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView thumbnail;
            TextView course;

            if (convertView == null) {
                convertView = mInflator.inflate(R.layout.list_item_layout,
                        parent, false);
            }

            thumbnail = convertView.findViewById(R.id.courseImage);
            // Use picasso to load image
            Picasso.get().load(AppSingleton.AWS_S3_URL + AppSingleton.courses.get(position).getName().toLowerCase() + "_1.jpg").into(thumbnail);
            course = convertView.findViewById(R.id.courseName);
            course.setText(AppSingleton.courses.get(position).getName());

            return convertView;
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_reader);

        ListView mListView = findViewById(R.id.courses_list);
        final CourseService courseService = new CourseService(CourseReaderActivity.this);
        courseService.getCourses(new CourseService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(CourseReaderActivity.this, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<Course> courses) {
                AppSingleton.courses = courses;
                mListView.setAdapter(new CourseAdapter(CourseReaderActivity.this));
                Log.i("info", "something here");
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView arg0, View arg1, int
                            position, long arg3) {
                        Log.i("info", String.valueOf(position));
                        AppSingleton.selectedCourse = position;
                        Intent i = new Intent(CourseReaderActivity.this,
                                CourseDetail.class);
                        startActivity(i);
                    }
                });
            }
        });

    }
}
