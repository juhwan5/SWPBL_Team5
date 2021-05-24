package com.example.precticeproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.precticeproject.CommunityEditTextActivity;
import com.example.precticeproject.LoginActivity;
import com.example.precticeproject.MainActivity;
import com.example.precticeproject.R;
import com.example.precticeproject.functions.CommunityAdapter;
import com.example.precticeproject.functions.CommunityItem;
import com.example.precticeproject.functions.ProcessJSONData;
import com.example.precticeproject.network.CommuLookupRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class CommunityFragment extends Fragment {
    public CommunityFragment() {}
    String asterisk = "*";
    CommunityAdapter adapter;
    RecyclerView recyclerView;
    String username;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        adapter = new CommunityAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.commu_frag,container,false);

        username = getArguments().getString("username");
        recyclerView = v.findViewById(R.id.community_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setUsername(username);

        Button writeText = v.findViewById(R.id.commu_write_textual);
        writeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommunityEditTextActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("textual","null");
                startActivity(intent);
            }
        });

        Button refresh = v.findViewById(R.id.commu_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecyclerView();
            }
        });

        showRecyclerView();

        return v;
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
                        Toast.makeText(getContext(),"실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"불능", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };

        CommuLookupRequest lookupRequest = new CommuLookupRequest(asterisk,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(lookupRequest);
    }
}
