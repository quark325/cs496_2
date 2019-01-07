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
import com.facebook.Profile;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.Future;

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
    public File f;
    ImageView editPhoto;
    String path;
    String file_name;
    boolean image_add = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.mypage_modify);

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

        //페이스북에서 받아온 id, name, birthday, gender 받기
        Intent intent=getIntent();
        final String id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        birthday = changeOrder(intent.getStringExtra("birthday"));//생년월일 순서 정렬
        gender = intent.getStringExtra("gender");

        //받아온 정보 입력
        editName.setText(name);
        editBirthday.setText(birthday);
        if (gender.equals("female")){
            female.setChecked(true);
        }else{
            male.setChecked(true);
        }

        //TODO 사진 받아오기
        //TODO 받아온 사진 서버로 옮기기

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
                Log.e("ID!!!", id);
                Log.e("Name!!!", editName.getText().toString());
                Log.e("Gender!!!", gender);
                Log.e("Age!!!", editBirthday.getText().toString());
                Log.e("Contact!!!", editContact.getText().toString());
                Log.e("Residence!!!", editResidence.getText().toString());
                Log.e("Job!!!", editJob.getText().toString());
                Log.e("Hobby!!!", editHobby.getText().toString());


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
