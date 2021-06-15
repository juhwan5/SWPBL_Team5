package com.example.precticeproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.precticeproject.R;
import com.example.precticeproject.UploadActivity;
import com.example.precticeproject.functions.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class CommunityFragment extends Fragment {
    public CommunityFragment() {}

    String username;
    private RecyclerView recyclerView;
    private List<CommuityItem> commuityItemList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar mProgressCircle;
    private DatabaseReference firebaseDatabase;
    private RecyclerDecoration recyclerDecoration;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.commu_frag,container,false);
        Bundle bundle = getArguments();

        username = bundle.getString("username");
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("uploads"); // 파이어베이스 데이터 베이스 연동 , DB테이블연결
        recyclerView = v.findViewById(R.id.community_recyclerview); // 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mProgressCircle = v.findViewById(R.id.progress_circle);
        recyclerDecoration = new RecyclerDecoration(5);

        recyclerView.addItemDecoration(recyclerDecoration);

        // 게시물 작성 화면으로 넘어가는 버튼 코드
        Button add_board = (Button) v.findViewById(R.id.commu_write_textual);
        add_board.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UploadActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        Button refresh = (Button) v.findViewById(R.id.commu_refresh);
        refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showRecyclerView();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        showRecyclerView();
    }

    public void showRecyclerView(){
        //옵저버 패턴 --> 변화가 있으면 클라이언트에 알려준다.
        firebaseDatabase.child("profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  //변화된 값이 DataSnapshot 으로 넘어온다.
                //데이터가 쌓이기 때문에  clear()
                commuityItemList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())           //여러 값을 불러와 하나씩 반복
                {
                    CommuityItem commuityItem = ds.getValue(CommuityItem.class);
                    commuityItemList.add(commuityItem); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }

                // ArrayAdapter<Object> uploadedImageAdapter;
                mAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로 고침
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 디비를 가져오던 중 에러 발생시
                Toast.makeText(getContext(), "저장 실패", Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter = new CommunityAdapter(commuityItemList, getContext());
        recyclerView.setAdapter(mAdapter); // 리사이클러뷰에 어댑터 연결
    }
}
