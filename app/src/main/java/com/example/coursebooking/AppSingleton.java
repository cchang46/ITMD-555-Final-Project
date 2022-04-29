package com.example.coursebooking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.coursebooking.models.Course;

import java.util.List;

public class AppSingleton {

    private static AppSingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    public final static String AWS_S3_URL = "https://course-booking-app-baking.s3.us-east-2.amazonaws.com/public/";
    public static int selectedCourse;
    public static List<Course> courses;

    private AppSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized AppSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new AppSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
