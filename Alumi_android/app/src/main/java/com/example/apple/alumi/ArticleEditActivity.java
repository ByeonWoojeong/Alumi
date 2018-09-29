package com.example.apple.alumi;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class ArticleEditActivity extends AppCompatActivity {

    private EditText mEditTitle;
    private EditText mEditContent;
    private Button  mBtnUpdateImage;
    private Button mBtnUpdate;

    public static String SERVER_HOST = ConstValues.SERVER_IP;


    private int mArticleId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_edit);

        //바인딩
        mEditTitle = (EditText)findViewById(R.id.updateSubject);
        mEditContent = (EditText)findViewById(R.id.updateDescription);
        mBtnUpdateImage = (Button)findViewById(R.id.btnUpdateImage);
        mBtnUpdate = (Button)findViewById(R.id.btnUpdate);

        String title = this.getIntent().getStringExtra("title");
        String content = this.getIntent().getStringExtra("content");
        mArticleId = this.getIntent().getIntExtra("id", 0);

        mEditTitle.setText(title);
        mEditContent.setText(content);
    }

    public void mOnClickUpdate(View view){

        //Post 방식
        RequestParams params = new RequestParams();
        params.put("id", new Integer(mArticleId).toString());
        params.put("title", mEditTitle.getEditableText().toString());
        params.put("content", mEditContent.getEditableText().toString());

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(String.format("%s/article_update",SERVER_HOST), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String body = new String(responseBody);

                if(body.equalsIgnoreCase("1")){
                    new AlertDialog.Builder(ArticleEditActivity.this)
                            .setTitle("게시글 수정")
                            .setMessage("게시글을 수정하였습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ArticleEditActivity.this.finish();
                                }
                            })
                            .show();
                }
                else{
                    new AlertDialog.Builder(ArticleEditActivity.this)
                            .setTitle("게시글 수정")
                            .setMessage("게시글 수정을 실패하였습니다.")
                            .setNegativeButton("확인", null)
                            .show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}
