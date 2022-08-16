package com.android.platforming.clazz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class Post {
    private static ArrayList<Post> posts;

    String id;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    String uid;
    int profileIndex;
    String nickname;
    String date;
    String title;
    String detail;
    int thumb_up;
    ArrayList<Comment> comments = new ArrayList<>();

    public Post (String id, Map<String, Object> data){
        this.id = id;
        this.uid = (String) data.get("uid");
        this.profileIndex = Integer.parseInt(String.valueOf(data.get("profileIndex")));
        this.nickname = (String) data.get("name");
        this.date = (String) data.get("name");
        this.title = (String) data.get("title");
        this.detail = (String) data.get("detail");
        this.thumb_up = Integer.parseInt(String.valueOf(data.get("thumb_up")));
    }

    public static ArrayList<Post> getPosts() {
        return posts;
    }

    public static void setPosts(ArrayList<Post> posts) {
        Post.posts = posts;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public int getProfileIndex() {
        return profileIndex;
    }

    public String getNickname() {
        return nickname;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public int getThumb_up() {
        return thumb_up;
    }
}
