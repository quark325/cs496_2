package com.example.q.cs496_2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.cs496_2.R;
import com.example.q.cs496_2.adapters.OtherAdapter;
import com.example.q.cs496_2.models.User;

import java.util.ArrayList;

import info.hoang8f.android.segmented.SegmentedGroup;

public class OtherFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    private ArrayList<User> userData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others,  container, false);

        userData=new ArrayList<User>();
        User user1 = new User();
        user1.setName("홍길동");
        user1.setAge("22");
        user1.setResidence("대전");
        user1.setHobby("롤");
        user1.setContact("010-1234-4567");
        user1.setJob("학생");
        userData.add(user1);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.othersRecyclerView);

        mLayoutManger = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new OtherAdapter(userData);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}