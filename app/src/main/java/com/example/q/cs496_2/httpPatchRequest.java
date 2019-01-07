package com.example.q.cs496_2;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPatch;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

class httpPatchRequest extends AsyncTask<String, Void, JsonArray> {
    HttpClient httpClient = HttpClientBuilder.create().build();
    StringEntity mJsonString;
    String user_id;

    public httpPatchRequest(StringEntity json_string, String id) {
        mJsonString = json_string;
        user_id = id;
    }


    public JsonArray doInBackground(String... params) {
        try {
            HttpPatch request = new HttpPatch("http://143.248.140.106:2580/members/17");
            //StringEntity params = new StringEntity(params);
            request.addHeader("content-type","application/json");
            request.addHeader("Accept","application/json");
            request.setEntity(mJsonString);
            Log.d("Request is ", request.toString());
            HttpResponse response = httpClient.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}