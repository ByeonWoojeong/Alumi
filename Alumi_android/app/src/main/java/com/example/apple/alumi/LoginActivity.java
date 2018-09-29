package com.example.apple.alumi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by apple on 2018. 5. 12..
 */

public class LoginActivity extends AppCompatActivity {

    private EditText mEditLoginId;
    private EditText mEditLoginPw;

    private RadioButton mRadioAdmin;
    private RadioButton mRadioStudent;

    private int mLoginType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditLoginId = (EditText)findViewById(R.id.editLoginId);
        mEditLoginPw = (EditText)findViewById(R.id.editLoginPW);

        mRadioAdmin = (RadioButton)findViewById(R.id.radioAdmin);
        mRadioStudent = (RadioButton)findViewById(R.id.radioStudent);

        //라디오 버튼 초기 설정 값
        mRadioStudent.setChecked(true);
    }

    private boolean validate() {

        String strLoginId = mEditLoginId.getText().toString();

        if (strLoginId.equalsIgnoreCase("")) {
            Toast.makeText(this, "ID를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    public void mOnClickLogin(View v){

        if (false == validate()) {
            return;
        }

        if(mRadioAdmin.isChecked()){
            mLoginType = 0;
        }else if(mRadioStudent.isChecked()){
            mLoginType = 1;
        }

        String userName = mEditLoginId.getEditableText().toString();
        String userPW = mEditLoginPw.getEditableText().toString();

        //LoopJ - Async
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format("%s/login?userName=%s&userPW=%s&loginType=%d", ConstValues.SERVER_IP, userName, userPW, mLoginType), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String body = new String(responseBody);

                // body == "1" or "0"
                if(body.equalsIgnoreCase("0")){
                    Toast.makeText(LoginActivity.this, "Login정보가 올바르지 않습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

                // 성공시
                String str = String.format("ID:%s 님이 로그인하셨습니다.",  mEditLoginId.getText().toString());

                Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT).show();

                //액티비티  전환
                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                intent.putExtra("login_type", mLoginType);

                startActivity(intent);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(LoginActivity.this, "네트워크 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}