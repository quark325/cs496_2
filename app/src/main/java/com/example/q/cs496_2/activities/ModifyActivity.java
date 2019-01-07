package com.example.q.cs496_2.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.cs496_2.R;
import com.example.q.cs496_2.https.HttpPostRequest;
import com.facebook.Profile;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.Future;

import cz.msebera.android.httpclient.entity.StringEntity;
import info.hoang8f.android.segmented.SegmentedGroup;

public class ModifyActivity extends AppCompatActivity {
    private String name;
    private String gender;
    private String contact;
    private String job;
    private String hobby;
    private String birthday;
    private String residence;
    private RadioButton male, female;
    private boolean isMember;
    String path;
    public File f;
    public String file_name;
    public ImageView editPhoto;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //맨위 TITEL_BAR 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.mypage_modify);

        //layout과의 연결을 담당하는 부분
        editPhoto = (ImageView) findViewById(R.id.modifyImage);
        final TextView editName = (TextView) findViewById(R.id.modifyName);
        final SegmentedGroup editGender = (SegmentedGroup) findViewById(R.id.segmented);
        final TextView editBirthday = (TextView) findViewById(R.id.modifyBirthDay);
        final EditText editContact = (EditText) findViewById(R.id.modifyContact);
        final EditText editResidence = (EditText) findViewById(R.id.modifyResidence);
        final EditText editJob = (EditText) findViewById(R.id.modifyJob);
        final EditText editHobby = (EditText) findViewById(R.id.modifyHobby);
        male = (RadioButton)findViewById(R.id.radioMale);
        female = (RadioButton)findViewById(R.id.radioFemale);

        //초기 로그인 : id, name, birthday, gender 받기
        Intent intent=getIntent();
        final String id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        birthday = changeOrder(intent.getStringExtra("birthday"));//생년월일 순서 정렬
        gender = intent.getStringExtra("gender");

        //TODO 이미 회원인 경우 모든 데이터를 이전과 동일하게 채워넣는다.
        if (intent.getStringExtra("isMember")!=null){
            Log.d("!!!!", "이미 회원인 경우 작동한다.");
        }

        //받아온 정보 입력
        editName.setText(name);
        editBirthday.setText(birthday);
        if (gender.equals("female")){
            female.setChecked(true);
        }else{
            male.setChecked(true);
        }

        //이미지 버튼 클릭시
        editPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //TODO PHOTO SELECT 화면으로 넘어가는 기능
                Intent fintent = new Intent(Intent.ACTION_GET_CONTENT);
                fintent.setType("image/jpeg");
                try {
                    startActivityForResult(fintent, 100);
                } catch (ActivityNotFoundException e) {

                }
            }
        });

        //완료버튼 클릭시
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.modifyConfirm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터 유효성 검사
                if(notAllWritten()){
                    Toast toast = Toast.makeText(getApplicationContext(), "should fill all blanks and add Image", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                //gender check, 생년월일 -> 나이
                String gender;
                if (female.isChecked()){
                    gender = "female";
                }else {
                    gender = "male";
                }
                //Image Upload
                //파일이름 : file_name
                f = new File(path);
                file_name = f.getName();
                Future uploading = Ion.with(ModifyActivity.this)
                        .load("http://143.248.140.106:2980/upload")
                        .setMultipartFile("image", f)
                        .asString()
                        .withResponse()
                        .setCallback(new FutureCallback<Response<String>>() {
                            @Override
                            public void onCompleted(Exception e, Response<String> result) {
                                try {
                                    JSONObject jobj = new JSONObject(result.getResult());
                                    Toast.makeText(getApplicationContext(), jobj.getString("response"), Toast.LENGTH_SHORT).show();

                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        });

                /*TODO 여기가 데이터 보내는 부분. 아래있는 형식대로 데이터를 넘기면 된다.
                ID정보 : id;
                이름 : editName.getText().toString();
                성별 : gender;
                나이 : age; -- 이거만 int 타입
                연락처 : editContact.getText().toString();
                거주지 : editResidence.getText().toString();
                직업 : editJob.getText().toString();
                취미 : editHobby.getText().toString();

                무슨값을 넘겼는지 확인하는 Log
                */
                JSONObject json = new JSONObject();
                try {
                    json.put("uId",id);
                    //date of birth 변수가 없음!!!!
                    json.put("date_of_birth","1997/10/03");
                    json.put("job", editJob.getText().toString());
                    json.put("hobby",editHobby.getText().toString());
                    json.put("gender",gender);
                    json.put("contact",editContact.getText().toString());
                    json.put("residence",editResidence.getText().toString());
                    json.put("name",editName.getText().toString());
                    //json_test.put("value","7");
                    //json_test.put("propName","id");
                    //linkerList.add(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    //http에 넣을 수 있는 형식으로 만들기
                    StringEntity json_string = new StringEntity(json.toString());
                    //httprequestclass 로 보내서 실행시키기
                    new HttpPostRequest(json_string).execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("ID!!!", id);
                Log.d("Name!!!", editName.getText().toString());
                Log.d("Gender!!!", gender);
                Log.d("Age!!!", editBirthday.getText().toString());
                Log.d("Contact!!!", editContact.getText().toString());
                Log.d("Residence!!!", editResidence.getText().toString());
                Log.d("Job!!!", editJob.getText().toString());
                Log.d("Hobby!!!", editHobby.getText().toString());


                //다음 Activity로 이동
                startActivity(new Intent(ModifyActivity.this, FragmentActivity.class));
                finish();
            }

            private boolean notAllWritten() {
                if (editContact.getText().toString().matches("")) return true;
                if (editResidence.getText().toString().matches("")) return true;
                if (editJob.getText().toString().matches("")) return true;
                if (editHobby.getText().toString().matches("")) return true;
                return false;
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    path = getRealPathFromURI(this,data.getData());
                    //file_name = f.getName();
                    Log.d(" Real Path : ", path);
                    editPhoto.setImageURI(data.getData());
                    //upload.setVisibility(View.VISIBLE);
                }
        }
    }
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    @NonNull
    private String changeOrder(String birthday) {
        String[] date = birthday.split("/");
        return date[2]+"/"+date[0]+"/"+date[1];
    }
}
