package com.example.precticeproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.precticeproject.functions.ImageDTO;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



public class UploadActivity extends AppCompatActivity {
    private Button btnBack, btnOk;
    private ImageView ivProfile;
    private TextInputEditText etTitle, etDesc;
    private String imageUrl = "";
    private int GALLEY_CODE = 10;

    private FirebaseAuth mAuth;
    private StorageReference storage;
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance().getReference("uploads");
        database = FirebaseDatabase.getInstance().getReference("uploads");
        initView();
        listener();
    }

    private void initView() {
        btnBack = (Button) findViewById(R.id.btn_profile_back);  // 뒤로 가기 버튼
        btnOk = (Button) findViewById(R.id.btn_profile_Ok);  // 글 등록
        ivProfile = (ImageView) findViewById(R.id.iv_profile);  // 사진이 들어가는 공간
        etTitle = (TextInputEditText) findViewById(R.id.dt_profile_title);  // 게시글 제목
        etDesc = (TextInputEditText) findViewById(R.id.dt_profile_desc);    // 게시글 내용
    }

    private void listener() {
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(imageUrl);
            }
        });
        //이미지 업로드
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로컬 사진첩으로 넘어간다.
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLEY_CODE);
            }
        });
    }

    //사진 고른 후 돌아오는 코드
    //로컬 파일에서 업로드
    // 저장된 사진을 가져오는 코드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == GALLEY_CODE) {
            imageUrl = String.valueOf(data.getData());
            RequestOptions cropOptions = new RequestOptions();
            Glide.with(getApplicationContext())
                    .load(imageUrl)
                    .into(ivProfile);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected String getFileExtension(String uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(Uri.parse(uri)));
    }



    private void uploadFile(String imageUrl){
        UploadTask uploadTask;
        if(this.imageUrl != null){
            StorageReference fileReference = storage.child(System.currentTimeMillis()+"."+getFileExtension(this.imageUrl));

            // 파이어베이스에 데이터베이스 업로드
            uploadTask = fileReference.putFile(Uri.parse(imageUrl));

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "업로드 성공!!", Toast.LENGTH_LONG).show();
                            String A3 = fileReference.getPath();
                            Log.d("A3", A3);
                            // 파이어베이스에 데이터베이스 업로드
                            ImageDTO imageDTO = new ImageDTO();
                            imageDTO.setImageUrl(A3);
                            imageDTO.setTitle(etTitle.getText().toString());
                            imageDTO.setDescription(etDesc.getText().toString());
                            database.child("profile").push().setValue(imageDTO);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                    } else {
                    }
                }
            });


        }else{
            Toast.makeText(this, "이미지가 없습니다.", Toast.LENGTH_LONG).show();

        }
    }
}







