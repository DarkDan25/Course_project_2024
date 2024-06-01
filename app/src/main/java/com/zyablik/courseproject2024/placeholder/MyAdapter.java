package com.zyablik.courseproject2024.placeholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.RecyclerView;

import com.zyablik.courseproject2024.R;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VHolder> {
    private List<String> itemsName;
    private List<String> itemsSpec;
    private List<String> itemsRooms;

    public MyAdapter(List<String> itemsName, List<String> itemsSpec, List<String> itemsRooms) {
        this.itemsName = itemsName;
        this.itemsRooms = itemsRooms;
        this.itemsSpec = itemsSpec;

    }

    @NonNull
    @Override
    public MyAdapter.VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.doctors_list, parent, false);
        return new VHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.VHolder holder, int position) {
        String itemName;
        String itemRoom;
        String itemSpec;

        if (position < itemsName.size()){
            itemName = itemsName.get(position);
        }
        else{
            itemName = "Apple";
        }
        if (position < itemsRooms.size()) {
            itemRoom = itemsRooms.get(position);
        } else {
            itemRoom = "Lol";
        }
        if (position < itemsSpec.size()) {
            itemSpec = itemsSpec.get(position);
        }else{
            itemSpec = "Boop";
        }

        holder.doc_name.setText(itemName);
        holder.doc_spec.setText(itemSpec);
        holder.doc_room.setText(itemRoom);
    }

    @Override
    public int getItemCount() {
        return itemsName.size();
    }

    static class VHolder extends RecyclerView.ViewHolder {
        TextView doc_name;
        TextView doc_spec;
        TextView doc_room;

        VHolder(View view) {
            super(view);
            doc_name = view.findViewById(R.id.doc_name);
            doc_spec = view.findViewById(R.id.doc_speciality);
            doc_room = view.findViewById(R.id.doc_room);
        }
    }
}
