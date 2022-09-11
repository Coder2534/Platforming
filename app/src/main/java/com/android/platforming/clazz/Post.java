package com.android.platforming.clazz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Post {
    public static final int FREE_BULLETIN_BOARD = 0;
    public static final int QUESTION_BULLETIN_BOARD = 1;
    public static final int SCHOOL_BULLETIN_BOARD = 2;

    public static final int POST = 0;
    public static final int POST_RECENT = 1;
    public static final int POST_MY = 2;

    private static ArrayList<Post> posts = new ArrayList<>();
    private static ArrayList<Post> recentPosts = new ArrayList<>();
    private static ArrayList<Post> myPosts = new ArrayList<>();

    private static ArrayList<String> types = new ArrayList<String>(){{
        add("자유게시판");
        add("질문게시판");
        add("학교시판");
    }};

    String id;
    int type;
    String uid;
    int profileIndex;
    String nickname;
    long date;
    String title;
    String detail;
    ArrayList<String> likes = new ArrayList<>();
    int commentSize;
    ArrayList<Comment> comments = new ArrayList<>();

    public Post (String id, Map<String, Object> data){
        this.id = id;
        this.type = Integer.parseInt(String.valueOf(data.get("type")));;
        this.uid = (String) data.get("uid");
        this.profileIndex = Integer.parseInt(String.valueOf(data.get("profileIndex")));
        this.nickname = (String) data.get("nickname");
        this.date = (long) data.get("date");
        this.title = (String) data.get("title");
        this.detail = (String) data.get("detail");
        this.likes.addAll ((List<String>) data.get("likes"));
    }

    public static ArrayList<Post> getPosts() {
        return posts;
    }

    public static ArrayList<Post> getRecentPosts() {
        return recentPosts;
    }

    public static ArrayList<Post> getMyPosts() {
        return myPosts;
    }

    public static ArrayList<String> getTypes() {
        return types;
    }

    public static void setPosts(ArrayList<Post> posts) {
        Post.posts = posts;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
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

    public long getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void setCommentSize(int commentSize) {
        this.commentSize = commentSize;
    }

    public int getCommentSize() {
        return commentSize;
    }
}
