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

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.otherViewHolder> {
    private ArrayList<User> userData;

    public OtherAdapter(ArrayList<User> data){
        userData = data;
    }
    
    public class otherViewHolder extends RecyclerView.ViewHolder {
        private ImageView viewPhoto;
        private TextView viewName;
        private TextView viewAge;
        private TextView viewResidence;
        private TextView viewJob;
        private TextView viewHobby;
        private TextView viewContact;

        public otherViewHolder(@NonNull View itemView) {
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
    public OtherAdapter.otherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.entry_others, viewGroup, false);
        return new otherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OtherAdapter.otherViewHolder myViewHolder, int i) {
        myViewHolder.bind();
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

}