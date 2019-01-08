package com.example.q.cs496_2.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.example.q.cs496_2.R;
import com.example.q.cs496_2.https.HttpPatchRequest;
import com.example.q.cs496_2.https.HttpPostRequest;
import com.example.q.cs496_2.models.User;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;

public class OtherAdapter extends RecyclerView.Adapter<OtherAdapter.viewHolder> {
    private ArrayList<User> userData;

    public OtherAdapter(ArrayList<User> data){
        userData = data;
    }
    
    public class viewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout viewEntry;
        private ImageView viewPhoto;
        private TextView viewName;
        private TextView viewAge;
        private TextView viewResidence;
        private TextView viewJob;
        private TextView viewHobby;
        private ImageButton heartButton;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            viewPhoto = itemView.findViewById(R.id.oEntryPhoto);
            viewName = itemView.findViewById(R.id.oEntryName);
            viewAge = itemView.findViewById(R.id.oEntryAge);
            viewResidence = itemView.findViewById(R.id.oEntryResidence);
            viewJob = itemView.findViewById(R.id.oEntryJob);
            viewHobby =itemView.findViewById(R.id.oEntryHobby);
            heartButton = itemView.findViewById(R.id.heartSignalButton);

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
    public void onBindViewHolder(@NonNull OtherAdapter.viewHolder holder, final int i) {
        //holder.viewPhoto;


        Uri uri = null;
        ImageAdapter imageAdapter = new ImageAdapter(holder.viewPhoto.getContext(), uri);
        //ImageView imageView = new ImageView(getContext());
        RequestManager requestManager = Glide.with(imageAdapter.getContext());
        // Create request builder and load image.
        RequestBuilder requestBuilder = requestManager.load("http://143.248.140.106:2980/uploads/"+userData.get(i).getPhoto());
        //requestBuilder = requestBuilder.apply(new RequestOptions().override(250, 250));
        // Show image into target imageview.
        Log.d("PHOTOPHOTO",userData.get(i).getPhoto()==null?"1":"WOOOW");
        requestBuilder.into(holder.viewPhoto);
        final String takerId = userData.get(i).getId();
        final String myId = Profile.getCurrentProfile().getId();
        holder.viewPhoto.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        holder.viewName.setText(userData.get(i).getName());
        holder.viewAge.setText(userData.get(i).getAge());
        holder.viewResidence.setText(userData.get(i).getResidence());
        holder.viewJob.setText(userData.get(i).getJob());
        holder.viewHobby.setText(userData.get(i).getHobby());
        holder.heartButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //하트 받는 사람의 received 에 내 id 넣기
                JSONObject json = new JSONObject();
                List<JSONObject> linkerList = new ArrayList<>();
                try {
                    json.put("propName","received");
                    json.put("value",myId);
                    linkerList.add(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    //http에 넣을 수 있는 형식으로 만들기
                    StringEntity json_string = new StringEntity(linkerList.toString());
                    //httprequestclass 로 보내서 실행시키기
                    new HttpPatchRequest(json_string,takerId).execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //내 gave에 하트 받는 사람 id 넣기
                JSONObject json_2 = new JSONObject();
                List<JSONObject> linkerList_2 = new ArrayList<>();
                try {
                    json_2.put("propName","gave");
                    json_2.put("value",takerId);
                    linkerList_2.add(json_2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    //http에 넣을 수 있는 형식으로 만들기
                    StringEntity json_string = new StringEntity(linkerList_2.toString());
                    //httprequestclass 로 보내서 실행시키기
                    new HttpPatchRequest(json_string,myId).execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return userData.size();
    }
}