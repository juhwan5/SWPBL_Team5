package com.example.precticeproject.functions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.precticeproject.R;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.ViewHolder> {

    ArrayList<CommunityItem> items = new ArrayList<CommunityItem>();

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
        TextView name;
        TextView date;
        TextView textual;
        TextView comment;

        public ViewHolder(View v){
            super(v);

            name = v.findViewById(R.id.commu_profile_name);
            date = v.findViewById(R.id.commu_profile_date);
            textual = v.findViewById(R.id.commu_textual);
            comment = v.findViewById(R.id.commu_comment);
        }

        public void setItem(CommunityItem item){
            name.setText(item.getProfileName());
            date.setText(item.getProfileDate());
            textual.setText(item.getTextual());
            comment.setText(item.getComment());
        }
    }


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
}
