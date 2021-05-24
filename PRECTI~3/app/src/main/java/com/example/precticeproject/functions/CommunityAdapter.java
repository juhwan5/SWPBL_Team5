package com.example.precticeproject.functions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.precticeproject.CommunityEditTextActivity;
import com.example.precticeproject.R;
import com.example.precticeproject.TTTTTTTTTT;
import com.example.precticeproject.network.CommuDeleteRequest;
import com.example.precticeproject.network.CommuLookupRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {

    ArrayList<CommunityItem> items = new ArrayList<CommunityItem>();
    static String username;
    static Context context;



    public CommunityAdapter(Context context){
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.commu_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommunityItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView img;
        int textnumber;
        TextView name;
        TextView date;
        TextView textual;
        TextView comment;
        Button update, delete;

        public ViewHolder(View v){
            super(v);



            name = v.findViewById(R.id.commu_profile_name);
            date = v.findViewById(R.id.commu_profile_date);
            textual = v.findViewById(R.id.commu_textual);
            comment = v.findViewById(R.id.commu_comment);
            update = v.findViewById(R.id.commu_updatebutton);
            delete = v.findViewById(R.id.commu_deletebutton);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isThatUser(username,name.getText().toString())){
                        updateTextual(String.valueOf(textnumber), textual.getText().toString(), username);
                    }else{
                        Toast.makeText(context,"작성자가 아닙니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isThatUser(username,name.getText().toString())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("이 글을 삭제하시겠습니까?");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteTextual(String.valueOf(textnumber));
                            }
                        });
                        builder.setNeutralButton("아니오",null);
                        builder.create().show();
                    }else{
                        Toast.makeText(context,"작성자가 아닙니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        public void updateTextual(String textnumber, String textual, String username){
                Intent intent = new Intent(context, CommunityEditTextActivity.class);
                intent.putExtra("textnumber", textnumber);
                intent.putExtra("textual",textual);
                intent.putExtra("username",username);
                context.startActivity(intent);
        }

        public void deleteTextual(String textnumber){
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success){
                            Toast.makeText(context,"삭제 완료", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(context,"실패", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context,"불능", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            };
            CommuDeleteRequest deleteRequest = new CommuDeleteRequest(textnumber,responseListener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(deleteRequest);
        }


        public static boolean isThatUser(String username, String profilename){
            if(username.equals(profilename)) {
                return true;
            }else{
                return false;
            }
        }

        public void setItem(CommunityItem item){
            textnumber = item.getTextnumber();
            name.setText(item.getProfileName());
            date.setText(item.getProfileDate());
            textual.setText(item.getTextual());
            comment.setText(item.getComment());
        }
    }

    public void clear() {
        int size = getItemCount();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                items.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

    public String getName(int position){return items.get(position).profileName;}

    public void addItem(CommunityItem item){
        items.add(item);
    }

    public void setItems(ArrayList<CommunityItem> items){
        this.items = items;
    }

    public CommunityItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, CommunityItem item){
        items.set(position, item);
    }

    public void setUsername(String name){ this.username = name; }
}
