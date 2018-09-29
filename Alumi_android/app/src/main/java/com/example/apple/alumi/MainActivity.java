package com.example.apple.alumi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //2초 뒤에 로그인 화면 전환
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        gotoLogin();
                    }
                });
            }
        }, 2000);

    }

    //로그인 액티비티
    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_NO_HISTORY);   //뒤로가기 시에 로그인 뷰 삭제

        startActivity(intent);
    }
}
