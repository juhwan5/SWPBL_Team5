package com.example.precticeproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.precticeproject.functions.CommunityAdapter;
import com.example.precticeproject.functions.CommunityItem;
import com.example.precticeproject.functions.ProcessJSONData;
import com.example.precticeproject.network.CommuLookupRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyTextualActivity extends AppCompatActivity {

    CommunityAdapter adapter;
    RecyclerView recyclerView;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_textual);

        adapter = new CommunityAdapter(this);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        recyclerView = findViewById(R.id.mytextual_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setUsername(username);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showRecyclerView();
    }

    public void mytextualRefreshListener(View v){
        showRecyclerView();
    }


    public void showRecyclerView(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success){
                        adapter.clear();
                        ArrayList<CommunityItem> itemArrayList = ProcessJSONData.processCommuLookupData(jsonResponse.getJSONArray("data"));
                        for (int i = 0; i<itemArrayList.size(); i++){
                            adapter.addItem(itemArrayList.get(i));
                        }
                        recyclerView.setAdapter(adapter);
                    } else{
                        Toast.makeText(getApplicationContext(),"실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"불능", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };

        CommuLookupRequest lookupRequest = new CommuLookupRequest(username,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(lookupRequest);
    }
}