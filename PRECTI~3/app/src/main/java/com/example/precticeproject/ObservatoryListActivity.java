package com.example.precticeproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ObservatoryListActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list1);

        list = (ListView)findViewById(R.id.list);
        Intent intent = getIntent();
        String[] listitem = intent.getStringArrayExtra("Observatory");

        List<String> data = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        list.setAdapter(adapter);

        for(int i = 0; i < listitem.length; i++){
            data.add(listitem[i]);
        }

        adapter.notifyDataSetChanged();

    }
}
