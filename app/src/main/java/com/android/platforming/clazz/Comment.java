package com.android.platforming.clazz;

import java.util.Map;

public class Comment {
    String uid;
    int profileIndex;
    String nickname;
    long date;
    String comment;

    public Comment(Map<String, Object> data){
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
}
