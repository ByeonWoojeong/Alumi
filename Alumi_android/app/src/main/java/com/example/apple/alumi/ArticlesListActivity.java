package com.example.apple.alumi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ArticlesListActivity extends AppCompatActivity {

    private ListView listView;
    private ArticlesAdapter adapter;
    private ArrayList<ArticlesVO> articleList = new ArrayList<ArticlesVO>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_list);

        adapter = new ArticlesAdapter(articleList);

        listView = (ListView)findViewById(R.id.articlesList);
        listView.setAdapter(adapter);   // listView와 adapter 연결

        listView.deferNotifyDataSetChanged();

        listView.setOnItemClickListener(mOnListSelect);
    }

    private AdapterView.OnItemClickListener mOnListSelect = new AdapterView.OnItemClickListener() {

        public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
            Log.i("alumi", "리스트 선택함 : " + position);

            ArticlesVO article = articleList.get(position);

            Intent intent = new Intent(ArticlesListActivity.this, ArticlesViewAcitivity.class);
            intent.putExtra("id", article.getId());

            ArticlesListActivity.this.startActivity(intent);
        }
    };

    //액티비티가 사용자에게 보이기 직전에 호출
    protected void onStart() {
        super.onStart();

        recvArticlesFromServer();
    }

    public void recvArticlesFromServer(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ConstValues.SERVER_IP + "/articles", new AsyncHttpResponseHandler() {   //Loopj
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String body = new String(responseBody);
                ArticlesVO[] serverArticles = new Gson().fromJson(body, ArticlesVO[].class);

                articleList.clear();

                for (int n=0; n<serverArticles.length; n++) {
                    articleList.add(serverArticles[n]);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                System.out.print("error");
            }
        });
    }

}
