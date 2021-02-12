package com.jesusvasquez.gmtechnical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "GM_Challenge";
    final static String PUBLIC_REPO = "https://api.github.com/repos/GeekyAnts/NativeBase/commits";
    final static String MY_REPO = "https://api.github.com/repos/JesusVasq/GM_Technical_Challenge/commits?sha=develop&per_page=50";
    // Views
    RecyclerView recyclerView;
    ProgressBar progressBar;
    // Data Structs
    MyAdapter myAdapter;
    ArrayList<Commit> myCommits = new ArrayList<Commit>();
    // Network Singleton
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // init views
        progressBar = findViewById(R.id.progress_circle);
        // init network singleton
        requestQueue = RequestSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        // Set up Recycler view
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.commit_list);
        recyclerView.setLayoutManager(mLayoutManager);
        myAdapter = new MyAdapter(myCommits);
        recyclerView.setAdapter(myAdapter);
        // Make view invisible while network request completes
        recyclerView.setVisibility(View.GONE);
        // async call to github API
        fetchCommits();
    }

    public void fetchCommits() {
        Log.d(TAG,"Fetching commits...");

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, PUBLIC_REPO, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    // Parse out information from response
                    for(int i = 0; i < response.length(); i++){
                        JSONObject commitObject = response.getJSONObject(i);
                        JSONObject commitInfo = commitObject.getJSONObject("commit");
                        JSONObject authorInfo = commitInfo.getJSONObject("author");

                        String commitHash = commitObject.getString("sha");
                        String authorName = authorInfo.getString("name");
                        String message = commitInfo.getString("message");

                        Log.d(TAG,"--------------------------");
                        Log.d(TAG,"hash: " + commitHash);
                        Log.d(TAG,"authorName: " + authorName);
                        Log.d(TAG,"message: " + message);

                        Commit singleCommit = new Commit(authorName, commitHash, message);
                        myCommits.add(singleCommit);
                    }
                    // Reset adapter now that async call has finished
                    myAdapter = new MyAdapter(myCommits);
                    recyclerView.setAdapter(myAdapter);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG,"API call succeeded!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"API call failed: " + error.getMessage());
            }
        });
        this.requestQueue.add(jsonArrayRequest);
    }
}