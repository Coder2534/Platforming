package com.platforming.autonomy.clazz;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BulletinBoard {

    private String id;
    private ArrayList<Post> posts = new ArrayList<>();

    public BulletinBoard(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public static class Post{
        String id;
        String bulletinId;
        String uid;
        int profileIndex;
        String nickname;
        long date;
        String title;
        String detail;
        ArrayList<String> likes = new ArrayList<>();
        int commentSize;
        ArrayList<Comment> comments = new ArrayList<>();

        public Post(String id, Map<String, Object> data){
            this.id = id;
            this.bulletinId = (String) data.get("bulletinId");
            this.uid = (String) data.get("uid");
            this.profileIndex = Integer.parseInt(String.valueOf(data.get("profileIndex")));
            this.nickname = (String) data.get("nickname");
            this.date = (long) data.get("date");
            this.title = (String) data.get("title");
            this.detail = (String) data.get("detail");
            this.likes.addAll ((List<String>) data.get("likes"));
        }

        public String getId() {
            return id;
        }

        public String getBulletinId() {
            return bulletinId;
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

    public static class Comment {
        private String id;
        private String uid;
        private int profileIndex;
        private String nickname;
        private long date;
        private String comment;

        public Comment(String id, Map<String, Object> data){
            this.id = id;
            uid = (String) data.get("uid");
            profileIndex = Integer.parseInt(String.valueOf(data.get("profileIndex")));
            nickname = (String) data.get("nickname");
            date = (long) data.get("date");
            comment = (String) data.get("comment");
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

        public String getComment() {
            return comment;
        }

        public String getId() {
            return id;
        }
    }

    public static class Manager{
        public static ArrayList<String> ids = new ArrayList<>();
        public static LinkedHashMap<String, BulletinBoard> bulletinBoards = new LinkedHashMap<String, BulletinBoard>(){{
            put("_MY", new BulletinBoard("_MY"));
        }};
    }
}
