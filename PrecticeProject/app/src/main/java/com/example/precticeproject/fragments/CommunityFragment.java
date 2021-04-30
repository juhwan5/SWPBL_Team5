package com.example.precticeproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.precticeproject.R;
import com.example.precticeproject.functions.AstroAdapter;
import com.example.precticeproject.functions.AstroItem;
import com.example.precticeproject.functions.CommunityAdapter;
import com.example.precticeproject.functions.CommunityItem;

public class CommunityFragment extends Fragment {
    public CommunityFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.commu_frag,container,false);

        RecyclerView recyclerView = v.findViewById(R.id.community_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CommunityAdapter adapter = new CommunityAdapter();

        adapter.addItem(new CommunityItem("신주환", "4월 26일",
                "개발 작업중.\n 틀은 이정도면 되나요?","네 괜찮은듯요"));
        adapter.addItem(new CommunityItem("김성수", "5월 1일",
                "업로드 완료. 수정바람","괜찮은것 같은데요"));
        adapter.addItem(new CommunityItem("한성현", "5월 6일",
                "줄바꿈\n테스트\n으\n아\n아\n아아","진정 좀;;;"));

        recyclerView.setAdapter(adapter);

        return v;
    }

}
