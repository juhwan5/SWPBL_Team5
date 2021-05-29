package com.example.precticeproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.precticeproject.functions.CommunityAdapter;
import com.example.precticeproject.functions.CommunityItem;
import com.example.precticeproject.functions.ImageDTO;
import com.example.precticeproject.functions.ProcessJSONData;
import com.example.precticeproject.functions.RecyclerDecoration;
import com.example.precticeproject.functions.UploadedImageAdapter;
import com.example.precticeproject.network.CommuLookupRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyTextualActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ImageDTO> imageDTOList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar mProgressCircle;
    private DatabaseReference firebaseDatabase;
    private RecyclerDecoration recyclerDecoration;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_textual);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        firebaseDatabase = FirebaseDatabase.getInstance().getReference("uploads"); // 파이어베이스 데이터 베이스 연동 , DB테이블연결
        recyclerView = findViewById(R.id.mytextual_recyclerview); // 연결
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mProgressCircle = findViewById(R.id.progress_circle2);
        recyclerDecoration = new RecyclerDecoration(5);

        recyclerView.addItemDecoration(recyclerDecoration);


        Button refresh = (Button)findViewById(R.id.mytextual_refresh);
        refresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showRecyclerView();
            }
        });

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
        //옵저버 패턴 --> 변화가 있으면 클라이언트에 알려준다.
        firebaseDatabase.child("profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  //변화된 값이 DataSnapshot 으로 넘어온다.
                //데이터가 쌓이기 때문에  clear()
                imageDTOList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren())           //여러 값을 불러와 하나씩 반복
                {
                    ImageDTO imageDTO = ds.getValue(ImageDTO.class);
                    String writer = imageDTO.getUsername();
                    if(!(TextUtils.isEmpty(writer))) {
                        if(writer.equals(username)) {
                            imageDTOList.add(imageDTO); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                        }
                    }
                }

                // ArrayAdapter<Object> uploadedImageAdapter;
                mAdapter.notifyDataSetChanged(); // 리스트 저장 및 새로 고침
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 디비를 가져오던 중 에러 발생시
                Toast.makeText(getApplicationContext(), "저장 실패", Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter = new UploadedImageAdapter(imageDTOList, getApplicationContext());
        recyclerView.setAdapter(mAdapter); // 리사이클러뷰에 어댑터 연결
    }
}