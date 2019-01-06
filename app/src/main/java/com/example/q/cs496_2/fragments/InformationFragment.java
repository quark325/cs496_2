package com.example.q.cs496_2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.cs496_2.R;
import com.facebook.Profile;

public class InformationFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        ImageView viewImage = view.findViewById(R.id.infoImage);
        TextView viewName = (TextView) view.findViewById(R.id.infoName);
        TextView viewGender = (TextView) view.findViewById(R.id.infoGender);
        TextView viewAge = (TextView) view.findViewById(R.id.infoAge);
        TextView viewContact = (TextView) view.findViewById(R.id.infoContact);
        TextView viewResidence = (TextView) view.findViewById(R.id.infoResidence);
        TextView viewJob = (TextView) view.findViewById(R.id.infoJob);
        TextView viewHobby = (TextView) view.findViewById(R.id.infoHobby);

        //TODO : ID를 기반으로 데이터베이스에 접근하여 변수에 적절한 값을 넣어줘야한다.
        String id = Profile.getCurrentProfile().getId();
        String name;
        String gender;
        int age;
        String contact;
        String residence;
        String job;
        String hobby;

        //TODO : 아래 ""안에 데이터베이스에서 꺼낸 값을 넣어줘야한다.
        /*
        viewImage.setImageResource("이미지 위치찾기");
        viewName.setText(name);
        viewGender.setText(gender);
        viewAge.setText(age.toString());
        viewContact.setText(contact);
        viewResidence.setText(residence);
        viewJob.setText(job);
        viewHobby.setText(hobby);
        */

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.infoEdit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Edit화면으로 넘어감.
            }
        });

        return view;
    }
}
