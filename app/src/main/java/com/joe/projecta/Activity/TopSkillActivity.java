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
import com.joe.projecta.Adapter.TopSkillAdapter;
import com.joe.projecta.R;
import com.joe.projecta.ui.TopSkill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopSkillActivity extends AppCompatActivity {
    RecyclerView topSkill;
    List<TopSkill> topSkillList;
    TopSkillAdapter topSkillAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_skill);
        Button appBarSubmit = findViewById(R.id.appBarSubmit);

        appBarSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TopSkillActivity.this, ProjectSubmission.class));
                finish();
            }
        });

        topSkill = findViewById(R.id.topskill);
        topSkillList = new ArrayList<>();
        topskillfunc();
    }

    private void topskillfunc() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String TOP_SKILL_JSON_URL = "https://gadsapi.herokuapp.com/api/skilliq";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, TOP_SKILL_JSON_URL,
                (JSONArray) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i <response.length() ; i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        TopSkill topSkill = new TopSkill();
                        topSkill.setName(object.getString("name"));
                        topSkill.setScore(object.getString("score"));
                        topSkill.setCountry(object.getString("country"));
                        topSkill.setBadgeUrl(object.getString("badgeUrl"));

                        topSkillList.add(topSkill);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                topSkill.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                topSkillAdapter = new TopSkillAdapter(getApplicationContext(), topSkillList);
                topSkill.setAdapter(topSkillAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TopSkillActivity.this, "Error Fetching Json", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }
}