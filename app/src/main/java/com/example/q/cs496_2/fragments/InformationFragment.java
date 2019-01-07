package com.example.q.cs496_2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.cs496_2.R;
import com.example.q.cs496_2.activities.MainActivity;
import com.example.q.cs496_2.https.HttpGetRequest;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
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

        String name="";
        String gender="";
        int age=0;
        String contact="";
        String residence="";
        String job="";
        String hobby="";
        String photo="";
        //String to place our result in
        String get_result;
        //Instantiate new instance of our class GET
        HttpGetRequest getRequest = new HttpGetRequest();
        
        //Some url endpoint that you may have
        String id = Profile.getCurrentProfile().getId();
        //TODO "17"부분을 id로 바꾸면 진행된다.
        String myUrl = "http://143.248.140.106:2580/members/"+"17";
        String mUrl = "http://143.248.140.106:2580/members/";
        //Perform the doInBackground method, passing in our url
        
        try {
            get_result = getRequest.execute(myUrl).get();
            JSONObject jsonObj = new JSONObject(get_result).getJSONObject("member");

            name = jsonObj.getString("name");
            gender = jsonObj.getString("gender");
            age = jsonObj.getInt("age");
            contact = jsonObj.getString("contact");
            residence = jsonObj.getString("residence");
            job = jsonObj.getString("job");
            hobby = jsonObj.getString("hobby");
        } catch (ExecutionException e) {
            Log.e("error", "haha");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("error", "haha");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        //TODO 간이용 Logout 버튼 추후 수정 예정
        Button logout = (Button) view.findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MainActivity.class);
                LoginManager.getInstance().logOut();
                startActivity(intent);
                getActivity().finish();
            }
        });

        //TODO 간이용 EXIT 버튼 추후 수정 예정
        Button exit = (Button) view.findViewById(R.id.exitButton);
        exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });


        return view;
    }
}