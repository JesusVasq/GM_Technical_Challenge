package com.jesusvasquez.gmtechnical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.*;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "GM_Challenge";
    final static String PUBLIC_REPO = "https://api.github.com/repos/JesusVasq/GM_Technical_Challenge/commits";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchCommits();
    }

    public void fetchCommits() {
        Log.d(TAG,"Fetching commits...");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, PUBLIC_REPO, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++){
                        JSONObject commitObject = response.getJSONObject(i);
                        JSONObject commitInfo = commitObject.getJSONObject("commit");
                        JSONObject authorInfo = commitInfo.getJSONObject("author");

                        String commitHash = commitObject.getString("sha");
                        String authorName = authorInfo.getString("name");
                        String message = commitInfo.getString("message");

                        Log.d(TAG,"hash: " + commitHash);
                        Log.d(TAG,"authorName: " + authorName);
                        Log.d(TAG,"message: " + message);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,"API call succeeded!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"API call failed!");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}