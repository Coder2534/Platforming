package com.android.platforming.object;

import android.net.Uri;

import com.example.platforming.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static User user = null;

    private final static ArrayList<Integer> profiles = new ArrayList<Integer>(){{
        add(R.drawable.ic_launcher_foreground);
        add(R.drawable.ic_launcher_foreground);
        add(R.drawable.ic_launcher_foreground);
    }};

    //personal info
    private String userName;
    private String nickName;
    private String telephone;
    private int sex; //0: male 1: female
    private int grade;
    private int room;
    private int number;
    //etc
    private int profile;
    private int point;
    private String note;

    public User(Map<String, Object> data){
        userName = (String)data.get("userName");
        nickName = (String)data.get("nickName");
        telephone = (String)data.get("telephone");
        sex = (int)data.get("sex");
        grade = (int)data.get("grade");
        room = (int)data.get("room");
        number = (int)data.get("number");

        profile = profiles.get((int)data.get("profileIndex"));
        point = (int)data.get("point");
        note = (String) data.get("note");
    }

    public Map<String, Object> getDataMap(){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("userName", userName);
        data.put("nickName", userName);
        data.put("telephone", userName);
        data.put("sex", userName);
        data.put("grade", userName);
        data.put("number", userName);

        data.put("profileIndex", profiles.indexOf(profile));
        data.put("point", point);
        data.put("note", note);
        return data;
    }

    public static User getUser() {
        return user;
    }

    public static ArrayList<Integer> getProfiles(){
        return profiles;
    }

    public static void setUser(User user) {
        User.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }
}
