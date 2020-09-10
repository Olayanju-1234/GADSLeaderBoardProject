package com.joe.projecta.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.joe.projecta.Adapter.TopLearnerAdapter;
import com.joe.projecta.R;
import com.joe.projecta.ui.TopLearner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TabLayout tab;
    TabItem skillIQTab;

    RecyclerView topLearner;
    List<TopLearner> topLearnerList;
    TopLearnerAdapter topLearnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topLearner = findViewById(R.id.toplearnersview);
        topLearnerList = new ArrayList<>();
        toplearnerfunc();

        tab = findViewById(R.id.tab);
        skillIQTab = findViewById(R.id.skilliqtab);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        Button appBarSubmit = findViewById(R.id.appBarSubmit);


        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
//                        codes related to the first tab
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, TopSkillActivity.class));
//                        codes related to the second tab
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        appBarSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProjectSubmission.class));
            }
        });


    }
    private void toplearnerfunc() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String TOP_LEARNER_JSON_URL = "https://gadsapi.herokuapp.com/api/hours";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, TOP_LEARNER_JSON_URL,
                (JSONArray) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length() ; i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        TopLearner topLearner = new TopLearner();
                        topLearner.setName(object.getString("name"));
                        topLearner.setCountry(object.getString("country"));
                        topLearner.setHours(object.getString("hours"));
                        topLearner.setBadgeUrl(object.getString("badgeUrl"));

                        topLearnerList.add(topLearner);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                topLearner.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                topLearnerAdapter = new TopLearnerAdapter(getApplicationContext(), topLearnerList);
                topLearner.setAdapter(topLearnerAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error fetching Json", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);
    }

}