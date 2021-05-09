package com.example.precticeproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.precticeproject.R;
import com.example.precticeproject.functions.*;

public class AstroFragment extends Fragment {
    public AstroFragment() {    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.astro_frag, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.astro_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        AstroAdapter adapter = new AstroAdapter();


        adapter.addItem(new AstroItem(R.drawable.shooting_star, "사자자리 유성우", "4월 26일"));
        adapter.addItem(new AstroItem(R.drawable.shooting_star, "페르세우스 유성우", "5월 26일"));
        adapter.addItem(new AstroItem(R.drawable.shooting_star, "사분의자리 유성우", "9월 15일"));

        recyclerView.setAdapter(adapter);

        return v;
    }
}