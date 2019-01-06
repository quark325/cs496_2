package com.example.q.cs496_2.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.cs496_2.R;
import com.example.q.cs496_2.models.User;

import java.util.ArrayList;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.viewHolder> {
    private ArrayList<User> userData;

    public OtherAdapter(ArrayList<User> data){
        userData = data;
    }
    
    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView viewPhoto;
        private TextView viewName;
        private TextView viewAge;
        private TextView viewResidence;
        private TextView viewJob;
        private TextView viewHobby;
        private TextView viewContact;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            viewPhoto = itemView.findViewById(R.id.entryPhoto);
            viewName = itemView.findViewById(R.id.entryName);
            viewAge = itemView.findViewById(R.id.entryAge);
            viewResidence = itemView.findViewById(R.id.entryResidence);
            viewJob = itemView.findViewById(R.id.entryJob);
            viewHobby =itemView.findViewById(R.id.entryHobby);
            viewContact =itemView.findViewById(R.id.entryContacts);
        }
    }
    
    @NonNull
    @Override
    public OtherAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.entry_others, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherAdapter.viewHolder holder, int i) {
        //TODO 이미지 Bind할 방법 생각하기 0123132
        //holder.viewPhoto;
        holder.viewName.setText(userData.get(i).getName());
        holder.viewAge.setText(userData.get(i).getAge());
        holder.viewResidence.setText(userData.get(i).getResidence());
        holder.viewJob.setText(userData.get(i).getJob());
        holder.viewHobby.setText(userData.get(i).getHobby());
        holder.viewContact.setText(userData.get(i).getContact());
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }
}