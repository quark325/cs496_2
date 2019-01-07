package com.example.q.cs496_2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.cs496_2.R;
import com.example.q.cs496_2.adapters.OtherAdapter;
import com.example.q.cs496_2.https.HttpGetRequest;
import com.example.q.cs496_2.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


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

        String myUrl = "http://143.248.140.106:2580/members/"+"17";
        String mUrl = "http://143.248.140.106:2580/members";
        //Perform the doInBackground method, passing in our url
        userData=new ArrayList<User>();

        try {
            get_result = getRequest.execute(mUrl).get();
            JSONObject jsonObj = new JSONObject(get_result); //

            // Getting JSON Array node
            JSONArray members = jsonObj.getJSONArray("members");

            for (int i = 0; i < members.length(); ++i)
            {
                Log.d("heyhey", "woooow");
                JSONObject m = members.getJSONObject(i);
                name = m.getString("name");
                gender = m.getString("gender");
                age = m.getInt("age");
                contact = m.getString("contact");
                residence = m.getString("residence");
                job = m.getString("job");
                hobby = m.getString("hobby");
                User user1 = new User();
                user1.setName(name);
                user1.setAge("" + age);
                user1.setResidence(residence);
                user1.setHobby(hobby);
                user1.setJob(job);
                userData.add(user1);
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.othersRecyclerView);

        mLayoutManger = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new OtherAdapter(userData);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}