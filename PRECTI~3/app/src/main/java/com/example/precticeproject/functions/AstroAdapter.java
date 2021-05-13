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

public class AstroAdapter extends RecyclerView.Adapter<AstroAdapter.ViewHolder> {

    ArrayList<AstroItem> items = new ArrayList<AstroItem>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.astro_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AstroItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView date;

        public ViewHolder(View v){
            super(v);

            img = v.findViewById(R.id.astro_item_img);
            name = v.findViewById(R.id.astro_item_name);
            date = v.findViewById(R.id.astro_item_date);
        }

        public void setItem(AstroItem item){
            img.setImageResource(item.getImgID());
            name.setText(item.getAstroName());
            date.setText(item.getAstroDate());
        }
    }


    public void addItem(AstroItem item){
        items.add(item);
    }

    public void setItems(ArrayList<AstroItem> items){
        this.items = items;
    }

    public AstroItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, AstroItem item){
        items.set(position, item);
    }
}
