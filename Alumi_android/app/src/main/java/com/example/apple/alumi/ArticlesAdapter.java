package com.example.apple.alumi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by apple on 2018. 5. 19..
 */


// List 액티비티 와 VO 의 어댑터
public class ArticlesAdapter extends BaseAdapter {

    private ArrayList<ArticlesVO> mArticleList;

    //생성자
    public ArticlesAdapter(ArrayList<ArticlesVO> articleList) {
        super();
        mArticleList = articleList;

        // inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override   //어댑터에 몇개의 항목이 있는지 - 배열의 크기 리턴
    public int getCount() { // 리스트 갯수
        return mArticleList.size();
    }

    @Override   //position 위치의 항목 조사 - 배열의 position번째 리턴
    public Object getItem(int position) {   // Index(몇번째 게시글?)
        return mArticleList.get(position);
    }

    @Override   //position 위치의 항목 ID
    public long getItemId(int position) {
        return position;
    }

    @Override   //각 항목의 뷰 생성 (생성할 항목 순서값, 이전에 생성된 차일드뷰, 생성되는 뷰의 부모)
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        //이전에 생선된 차일드뷰가 없으면 뷰를 새로 생성
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView textTitle = (TextView)convertView.findViewById(R.id.articlesTitle);
        TextView textUserName = (TextView)convertView.findViewById(R.id.articlesUserName);

        ArticlesVO articlesVO = mArticleList.get(position);

        textTitle.setText(articlesVO.getTitle());
        textUserName.setText(articlesVO.getUserName());

        return convertView;
    }
}
