package com.android.platforming.clazz;

import com.example.platforming.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static User user = null;

    private final static ArrayList<Integer> profiles = new ArrayList<Integer>(){{
        add(R.drawable.ic_baseline_10mp_24);
        add(R.drawable.ic_baseline_11mp_24);
        add(R.drawable.ic_baseline_12mp_24);
    }};

    //personal info
    private String uid;
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
    private int profileIndex;

    public User(String uid, Map<String, Object> data){
        this.uid = uid;
        userName = (String)data.get("userName");
        nickName = (String)data.get("nickName");
        telephone = (String)data.get("telephone");
        sex = Integer.parseInt(String.valueOf(data.get("sex")));
        grade = Integer.parseInt(String.valueOf(data.get("grade")));
        room = Integer.parseInt(String.valueOf(data.get("room")));
        number = Integer.parseInt(String.valueOf(data.get("number")));
        profileIndex = Integer.parseInt(String.valueOf(data.get("profileIndex")));
        profile = profiles.get(profileIndex);
        point = Integer.parseInt(String.valueOf(data.get("point")));
        note = (String) data.get("note");
    }

    public User(String userName, String nickName, String telephone, int sex, int grade, int room, int number, int profile){
        this.userName = userName;
        this.nickName = nickName;
        this.telephone = telephone;
        this.sex = sex;
        this.grade = grade;
        this.room = room;
        this.number = number;
        this.profile = profiles.get(profile);
        this.point = 0;
        this.note = "";
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
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public int getProfileIndex() {
        return profileIndex;
    }

    public void setProfileIndex(int profileIndex) {
        this.profileIndex = profileIndex;
        profile = profiles.get(profileIndex);
    }
}
