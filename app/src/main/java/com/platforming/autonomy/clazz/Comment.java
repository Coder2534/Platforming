package com.platforming.autonomy.clazz;

import java.util.Map;

public class Comment {
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
