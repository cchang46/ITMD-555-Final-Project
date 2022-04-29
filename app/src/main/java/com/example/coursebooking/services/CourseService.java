package com.example.coursebooking.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.coursebooking.AppSingleton;
import com.example.coursebooking.models.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseService {

    public static final String COURSES_API = "https://3bov72ru9k.execute-api.us-east-2.amazonaws.com/courses";

    Context context;

    public CourseService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(List<Course> courses);
    }

    public void getCourses(VolleyResponseListener volleyResponseListener) {
        List<Course> courses = new ArrayList<>();

        // get the json object
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, COURSES_API, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray coursesJson = response.getJSONArray("Items");
                    for (int i = 0; i < coursesJson.length(); i++) {
                        JSONObject courseJson = (JSONObject) coursesJson.get(i);
                        Course course = new Course();
                        course.setName(courseJson.getString("name"));
                        course.setDetail(courseJson.getString("detail"));
                        courses.add(course);
                    }
                    volleyResponseListener.onResponse(courses);

                } catch (JSONException e) {
                    e.printStackTrace();
                    volleyResponseListener.onError(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                volleyResponseListener.onError(error.getMessage());
            }
        });

        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

}

