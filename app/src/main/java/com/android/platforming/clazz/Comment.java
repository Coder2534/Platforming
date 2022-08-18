package com.android.platforming.clazz;

import java.util.Map;

public class Comment {
    String uid;
    int profileIndex;
    String nickname;
    long date;
    String comment;

    public Comment(Map<String, Object> data){

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
