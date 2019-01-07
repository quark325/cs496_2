package com.example.q.cs496_2.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONObject;
import com.example.q.cs496_2.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //FB Login 구현
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.setReadPermissions("public_profile","user_birthday", "user_friends","user_gender");

        token = AccessToken.getCurrentAccessToken();


        //TODO 로그인 되어있다면, 회원일 경우(uid를 통해서 해결) 바로 information으로 이동한다. 회원이 아닌경우 로그아웃을 한다.
        if (token != null){
            String id = Profile.getCurrentProfile().getId();
            Log.d("@@Login success ID","id");
            LoginManager.getInstance().logOut();
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    //if 이미 회원가입 되어있다면 -> 바로 FragmentActivity로 이동

                                    //회원가입되어있지 않다면 ->아래있는 코드로 이동
                                    Intent intent = new Intent(MainActivity.this, ModifyActivity.class);
                                    Log.d("user profile", object.toString());
                                    intent.putExtra("id", object.getString("id"));
                                    intent.putExtra("name", object.getString("name"));
                                    intent.putExtra("birthday", object.getString("birthday"));
                                    intent.putExtra("gender", object.getString("gender"));
                                    startActivity(intent);
                                    finish();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,birthday,gender");
                request.setParameters(parameters);
                request.executeAsync();
            }


            @Override
            public void onCancel() {
                Log.d("TAG", "Canceled");
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
