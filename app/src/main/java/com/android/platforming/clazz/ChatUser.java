package com.android.platforming.clazz;



public class ChatUser {
    private String msg;
    private String nickname;
    public String uid;
    public String pushtoken;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getUid(String uid) {
        return uid;
    }
    public void setUid(){
        this.uid = uid;
    }
    public String getPushToken(String pushtoken){
        return pushtoken;
    }
    public void setPushtoken(){
        this.pushtoken = pushtoken;
    }

}
