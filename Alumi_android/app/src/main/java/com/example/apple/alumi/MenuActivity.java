package com.example.apple.alumi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnAddArticles = (Button)findViewById(R.id.btnAddArticles);  //게시글 추가
        Button btnAddQuestionPapers = (Button)findViewById(R.id.btnAddQuestionPapers);  //질문글 추가
        Button btnSendNotification = (Button)findViewById(R.id.btnSendNotification);    //공지글 추가
        Button btnViewArticles = (Button)findViewById(R.id.btnViewArticles);    //게시글 보기
        Button btnViewQuestionPapers = (Button)findViewById(R.id.btnViewQuestionPapers);    //질문글 보기
        Button btnViewFaculty = (Button)findViewById(R.id.btnViewFaculty);  //학부 보기

        Intent intent = getIntent();
        int loginType = intent.getIntExtra("login_type", 0);

        if (loginType == 0) {
            // admin
            btnAddArticles.setVisibility(View.GONE);   //INVISIBLE-자리는 차지하되, 안보임    GONE-아예 자리조차 사라짐.
            btnAddQuestionPapers.setVisibility(View.GONE);
         }
        else if (loginType == 1) {
            // student
            btnSendNotification.setVisibility(View.GONE);
        }


    }

    //Add Articles (게시글 올리기) 액티비티 전환
    public void mOnClickAddArticles(View v) {
        Intent intent = new Intent(this,ArticlesAddActivity.class);
        startActivity(intent);
    }

    //View Articles (게시글 보기) 액티비티 전환
    public void mOnClickViewArticles(View v) {
        Intent intent = new Intent(this,ArticlesListActivity.class);
        startActivity(intent);
    }

    public void mOnClickViewQuestionPapers(View v){
        Intent intent = new Intent(this, QuestionListActivity.class);
        startActivity(intent);
    }

    public void mOnClickAddQuestionPapers(View v){
        Intent intent = new Intent(this, QuestionAddActivity.class);
        startActivity(intent);
    }

    public void mOnClickViewFaculty(View v){
        Intent intent = new Intent(this, FacultyViewActivity.class);
        startActivity(intent);
    }

}
