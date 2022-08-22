package com.android.platforming.clazz;

import com.example.platforming.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private static User user = null;

    private final static ArrayList<Integer> profiles = new ArrayList<Integer>(){{
        add(R.mipmap.ic_profile_dog_round);
        add(R.mipmap.ic_profile_cat_round);
        add(R.mipmap.ic_profile_rabbit_round);
    }};

    //personal info
    private String uid;
    private String username;
    private String nickname;
    private String telephone;
    private int sex; //0: male 1: female
    private String studentId;

    //etc
    private int point;
    private String note;
    private int profileIndex;
    private List<Integer> fonts;
    private List<Integer> themes;
    private List<Integer> textColors;

    public User(String uid, Map<String, Object> data){
        this.uid = uid;
        username = (String)data.get("username");
        nickname = (String)data.get("nickname");
        telephone = (String)data.get("telephone");
        sex = Integer.parseInt(String.valueOf(data.get("sex")));
        studentId = (String)data.get("studentId");
        profileIndex = Integer.parseInt(String.valueOf(data.get("profileIndex")));
        point = Integer.parseInt(String.valueOf(data.get("point")));
        note = (String) data.get("note");
        themes = (List<Integer>) data.get("themes");
        fonts = (List<Integer>) data.get("fonts");
        textColors = (List<Integer>) data.get("textColors");
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickName(String nickName) {
        this.nickname = nickName;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getProfile() {
        return profiles.get(profileIndex);
    }

    public int getProfileIndex() {
        return profileIndex;
    }

    public void setProfileIndex(int profileIndex) {
        this.profileIndex = profileIndex;
    }

    public List<Integer> getFonts() {
        return fonts;
    }

    public List<Integer> getThemes() {
        return themes;
    }

    public List<Integer> getTextColors() {
        return textColors;
    }
}
