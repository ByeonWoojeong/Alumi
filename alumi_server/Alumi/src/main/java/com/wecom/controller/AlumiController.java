package com.wecom.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wecome.model.Article;
import com.wecome.model.Greeting;
import com.wecome.model.LoginRes;
import com.wecome.model.User;
import com.wecome.model.Users;

@RestController
public class AlumiController {
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	// 키:id, 값:Article
	private static Map<Integer, Article> DB_ARTICLE = new HashMap<>();
	private static Map<String, User> DB_USER = new HashMap<>();
	
	private static int globalId = 0;
	
    @RequestMapping("/greeting")
    public Greeting greeting(
    		@RequestParam(value="name", defaultValue="World") String name
    		) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
    
    @RequestMapping("/users")
    public Users getAllUsers() {
        Users users = new Users();
        users.setUsers(new ArrayList<>(DB_USER.values()));
        return users;
    }    

    @RequestMapping("/articles")
    public ArrayList<Article> getAllArticles() {
    	ArrayList<Article> articles = new ArrayList<>(DB_ARTICLE.values());
        return articles;
    }    
	
    // /article_view?id=10
    @RequestMapping("/article_view")
    public Article getArticle(@RequestParam(value="id", defaultValue="") String id) {
    	
    	if (id.equalsIgnoreCase("")) {
    		return new Article();
    	}
    	
    	Article article = DB_ARTICLE.get(Integer.parseInt(id));
    	return article;
    }
    
    @RequestMapping("/article_delete")
    public String deleteArticle(@RequestParam(value="id", defaultValue="") String id) {
    	
    	if (id.equalsIgnoreCase("")) {
    		// 실패리
    		return "0";
    	}
    		    	
    	try {
    		DB_ARTICLE.remove(Integer.parseInt(id));	
    	}
    	catch (Exception e) {
    		// 실패 리
    		return "0";
    	}
    	
    	// 성공 리
    	return "1";
    }
    
    @RequestMapping("/article_update")
    public String updateArticle(@RequestParam(value="id", defaultValue="") String id,
    							@RequestParam(value="title", defaultValue="") String title,
    							@RequestParam(value="content", defaultValue="") String content) {
    	
    	if(id.equalsIgnoreCase("")) {
    		System.out.println("id.equalsIgnoreCase(\"\")");
    		return "0";
    	}
    	
    	Article article = DB_ARTICLE.get(Integer.parseInt(id));
    	//map에서 key(id)로 찾기 - value값(article)
    	
    	if(article == null) {	//게시글이 만약 5개일 때 6을 찾으면 null
    		System.out.println("if(article == null)");
    		return "0";
    	}
    	
    	article.setTitle(title);
    	article.setContent(content);
    	//실제 변경값 적용
    	

    	
    	return "1";
    }
    
    @RequestMapping("/article_add")
    public String addArticle(
    		@RequestParam(value="title", defaultValue="")String title, 
    		@RequestParam(value="userName", defaultValue="") String userName, 
    		@RequestParam(value="content", defaultValue="")String content) {
    	
    	Article article1 = new Article();
    	article1.setTitle(title);
    	article1.setUserName(userName);
    	article1.setContent(content);
    	article1.setId(globalId);
    	DB_ARTICLE.put(globalId, article1);
    	
    	globalId++;
    	
    	return "1";
    }
    
    @RequestMapping("/login")
    public String login(
    		@RequestParam(value="userName", defaultValue="")String userName, 
    		@RequestParam(value="userPW", defaultValue="")String userPW,
    		@RequestParam(value="loginType", defaultValue="")int loginType) {
    	
    	//    	
    	User user = DB_USER.get(userName);
    	if (user == null) {
    		return "0";
    	}
    	
    	if (false == user.getUserPw().equals(userPW)) {
    		return "0";
    	}
    	
	   if (user.getLoginType() != loginType) {
		   return "0";
    	}
    	
    	// userName, userPw 동일
    	return "1";
    }
    
    static
    {
		DB_USER.put("tom", new User("tom", "1234", User.LOGIN_TYPE_ADMIN));
		DB_USER.put("serra", new User("serra", "5678", User.LOGIN_TYPE_STUDENT));
    	
    	for (int n=0; n<30; n++) {
    		
        	Article article1 = new Article();
        	article1.setTitle("title"+n);
        	article1.setUserName("john"+n);
        	article1.setContent("content"+n);
        	article1.setId(globalId);
        	DB_ARTICLE.put(globalId, article1);
        	
        	globalId++;
    	}
    	
    }
    
}
