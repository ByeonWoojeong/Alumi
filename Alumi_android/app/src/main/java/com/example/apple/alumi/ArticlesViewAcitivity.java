package com.example.apple.alumi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ArticlesViewAcitivity extends AppCompatActivity {

    public static String SERVER_HOST = ConstValues.SERVER_IP;

    //멤버변수
    private TextView mTextTitle;
    private TextView mTextName;
    private TextView mTextContent;

    private boolean deleteSuccess = false;

    private int mArticleId = 0;
    private String mTitle = "";
    private String mContent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_view);

        mTextTitle = (TextView)findViewById(R.id.txtTitle);
        mTextName = (TextView)findViewById(R.id.txtNameUser);
        mTextContent = (TextView)findViewById(R.id.txtContent);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        mArticleId = intent.getIntExtra("id", -1);

        if (mArticleId >= 0){

            //LoopJ - Async
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(String.format("%s/article_view?id=%d", SERVER_HOST, mArticleId), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String body = new String(responseBody);
                    //문자열을 object로 바꾸기
                    ArticlesVO article = new Gson().fromJson(body, ArticlesVO.class);

                    mTextTitle.setText(article.getTitle());
                    mTextName.setText(article.getUserName());
                    mTextContent.setText(article.getContent());

                    mTitle = article.getTitle();
                    mContent = article.getContent();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }
    }

    public void deleteArticle(int id){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format("%s/article_delete?id=%d",SERVER_HOST, id), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String body = new String(responseBody);

                if(body.equalsIgnoreCase("1")){
                    //대화상자
                    new AlertDialog.Builder(ArticlesViewAcitivity.this)
                            .setTitle("삭제 성공")
                            .setMessage("게시글이 성공적으로 삭제되었습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //액티비티 이동 (ArticlesListActivity로~)
                                    //Intent(현재 액티비티, 이동할 클래스)
                                    Intent intent = new Intent(ArticlesViewAcitivity.this, ArticlesListActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 액티비티가 기존 스택에 있으면 생성하지 말고 재사용 하세요.
                                    startActivity(intent);

                                    ArticlesViewAcitivity.this.finish();
                                }
                            })
                            .show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    public void mOnClick(View v){
        switch(v.getId()){
            case R.id.btnDelete:    //게시글 삭제 버튼
                new AlertDialog.Builder(this)
                        .setTitle("게시글 삭제")
                        .setMessage("게시글을 삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteArticle(mArticleId);
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();
            break;
            case R.id.btnEdit:  //게시글 수정 버튼
                Intent intent = new Intent(ArticlesViewAcitivity.this, ArticleEditActivity.class);
                intent.putExtra("id", mArticleId);
                intent.putExtra("title", mTitle);
                intent.putExtra("content", mContent);
                startActivity(intent);

                this.finish();

                break;
        }
    }
}
