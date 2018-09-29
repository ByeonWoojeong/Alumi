package com.example.apple.alumi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class QuestionAddActivity extends AppCompatActivity {

    private EditText mEditAddSubject;
    private EditText mEditAddDescription;
    private Button mBtnSend;

    public static String SERVER_HOST = ConstValues.SERVER_IP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_add);

        //바인딩
        mEditAddSubject = (EditText)findViewById(R.id.addSubject);
        mEditAddDescription = (EditText)findViewById(R.id.addDescription);
        mBtnSend = (Button)findViewById(R.id.btnSend);

    }

    public void mOnClickSendQuestion(View view){

        //put(서버 파라미터, )
        RequestParams params = new RequestParams();
        params.put("title", mEditAddSubject.getText().toString());
        params.put("userName", "tom");
        params.put("content", mEditAddDescription.getText().toString());

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(SERVER_HOST+"/article_add", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Intent intent = new Intent(QuestionAddActivity.this, ArticlesListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                QuestionAddActivity.this.finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
