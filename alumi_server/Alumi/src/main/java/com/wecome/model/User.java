package com.wecome.model;

import java.io.Serializable;

public class User implements Serializable {
  
    private static final long serialVersionUID = 1L;
    
    public static final int LOGIN_TYPE_ADMIN = 0;
    public static final int LOGIN_TYPE_STUDENT = 1;
  
    private String title = "";
    private int loginType = 0;
    private String userName = "";
    private String userPw = "";
    
    public User(String userName, String userPw, int loginType) {
    	this.userName = userName;
    	this.userPw = userPw;
    	this.loginType = loginType;
    }
    
	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

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
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
        
    
}