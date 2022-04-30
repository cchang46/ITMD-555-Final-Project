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

import java.util.List;

public class LoginService {

    public static final String SIGNIN_API = "https://3bov72ru9k.execute-api.us-east-2.amazonaws.com/users/signin";

    Context context;

    public LoginService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(JSONObject response);
    }

    public void signin(VolleyResponseListener volleyResponseListener, String username, String password) {
        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, SIGNIN_API, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                volleyResponseListener.onResponse(response);
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

