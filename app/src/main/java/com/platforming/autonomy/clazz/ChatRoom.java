package com.platforming.autonomy.clazz;

import java.util.HashMap;
import java.util.Map;

public class ChatRoom {
    private String id;
    public Map<String,Boolean> users = new HashMap<>(); //채팅방 유저
    private Map<String, Chat> chats = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Boolean> getUsers() {
        return users;
    }

    public Map<String, Chat> getChats() {
        return chats;
    }

    public static class Chat{
        private String uid;
        private String message;
        private long date;
    }
}
