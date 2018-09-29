package com.example.apple.alumi;

/**
 * Created by apple on 2018. 5. 19..
 */

//서버의 모델과 같아야함
public class ArticlesVO {
    private String title = "";
    private String userName = "";
    private String content = "";
    private int id = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

}
