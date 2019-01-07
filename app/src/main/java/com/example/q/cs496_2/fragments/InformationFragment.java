package com.example.q.cs496_2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.cs496_2.R;
import com.example.q.cs496_2.https.HttpGetRequest;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

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

        //Some url endpoint that you may have
        String myUrl = "http://143.248.140.106:2580/members";
        //String to place our result in
        String result;
        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();
        //Perform the doInBackground method, passing in our url

        try {
            result = getRequest.execute(myUrl).get();
            Log.d("hoho: ", result);
            JSONObject reader = new JSONObject(result);
            Log.d("!!!!","실행은 된다.");

            String uId = reader.getString("uId");
            String age = reader.getString("age");
            String date_of_birth = reader.getString("date_of_birth");
            String job = reader.getString("job");
            String hobby = reader.getString("hobby");
            String gender = reader.getString("gender");
            String contact = reader.getString("photo");
            String gave = reader.getString("gave");
            String received = reader.getString("received");
            String success = reader.getString("success");
            String last_updated_time = reader.getString("last_updated_time");
            String residence = reader.getString("residence");
        } catch (ExecutionException e) {
            Log.d("error", "haha");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("error", "haha");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //TODO : ID를 기반으로 데이터베이스에 접근하여 변수에 적절한 값을 넣어줘야한다.
        String id = Profile.getCurrentProfile().getId();
        String name="Name";
        String gender="Gender";
        int age=24;
        String contact ="010-1234-1234";
        String residence = "Daejeon";
        String job = "Student";
        String hobby = "LOL";

        //TODO : 아래 ""안에 데이터베이스에서 꺼낸 값을 넣어줘야한다.
        /*
        viewImage.setImageResource("이미지 위치찾기");
        */
        viewName.setText(name);
        viewGender.setText(gender);
        viewAge.setText(age+"");
        viewContact.setText(contact);
        viewResidence.setText(residence);
        viewJob.setText(job);
        viewHobby.setText(hobby);


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