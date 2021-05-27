package com.example.precticeproject.functions;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.precticeproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class UploadedImageAdapter extends RecyclerView.Adapter<UploadedImageAdapter.ViewHolder>
{
    private List<ImageDTO> imageDTOList; //ImageDTO 객체를 담을 어레이 리스트(어댑터 쪽으로)
   // private List<String> uidList = new ArrayList<>();
    private FirebaseStorage storage ;
    private Context context;

    public UploadedImageAdapter(List<ImageDTO> imageDTOList, Context context) {
        this.imageDTOList = imageDTOList;
        this.storage = storage.getInstance("gs://pushtest-b28ba.appspot.com"); // 스토리지에 따라 고쳐줘야하는 코드
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.uploaded_image_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.item_title.setText(imageDTOList.get(position).getTitle());
        holder.item_desc.setText(imageDTOList.get(position).getDescription());
        StorageReference storageRef = storage.getReference();
        storageRef.child(imageDTOList.get(position).getImageUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                Glide.with(context.getApplicationContext())
                        .load(uri)
                        .into(holder.item_image);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(context.getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageDTOList.size();
    }

    //ViewHolder 클래스
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView item_title;
        TextView item_desc;
        ImageView item_image;
        // 사진이랑 글 보여주는 코드 인듯
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.item_title = itemView.findViewById(R.id.item_title);
            this.item_desc = itemView.findViewById(R.id.item_desc);
            this.item_image = itemView.findViewById(R.id.item_image);
        }
    }
}


