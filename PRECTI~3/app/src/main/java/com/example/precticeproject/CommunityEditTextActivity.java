package com.example.precticeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.precticeproject.functions.CommunityItem;
import com.example.precticeproject.functions.ProcessJSONData;
import com.example.precticeproject.network.CommuLookupRequest;
import com.example.precticeproject.network.CommuWriteRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommunityEditTextActivity extends AppCompatActivity {
    EditText editText;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commu_edit_text);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        editText = findViewById(R.id.commu_edittext);
    }



    public void editTextualListener (View v){
        String textual = editText.getText().toString();
        doSQL(username, textual);
    }

    public void doSQL(String username, String textual){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success){
                        Toast.makeText(getApplicationContext(),"글 쓰기 성공, 새로고침 해주세요", Toast.LENGTH_SHORT).show();
                        finish();
                    } else{
                        Toast.makeText(getApplicationContext(),"실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"불능", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };

        CommuWriteRequest writeRequest = new CommuWriteRequest(username,textual,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(writeRequest);
    }
}