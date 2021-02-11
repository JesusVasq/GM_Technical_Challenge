package com.jesusvasquez.gmtechnical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "GM_Challenge";
    final static String PUBLIC_REPO = "https://api.github.com/repos/twbs/bootstrap/commits";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchCommitHistory(PUBLIC_REPO);
    }
    public void fetchCommitHistory(String url){
        Log.d(TAG,"getting user info");
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        ((TextView)findViewById(R.id.hello)).setText("Response succeeded");
                        Log.d(TAG,"call succeeded: " + response);
                        try {
                            JSONArray myArray = new JSONArray((response));
                            JSONObject first = myArray.getJSONObject(0);
                            String SHA = first.getString("sha");
                            Log.d(TAG, "SHA is: " + SHA);
                        }catch (JSONException err){
                            Log.d(TAG, "Error: "+ err.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ((TextView)findViewById(R.id.hello)).setText("That didn't work!");
                Log.d(TAG,"call didnt succeed");
            }
        });
        queue.add(stringRequest);
    }

    public void parseCommitJSON(String data){

    }
}