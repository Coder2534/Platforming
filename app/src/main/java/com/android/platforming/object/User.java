package com.android.platforming.object;

import android.net.Uri;

import java.util.Map;

public class User {

    private static User user = null;

    private String userName;
    private String nickName;
    private int point;
    private int telephone;
    private int grade;
    private int room;
    private int number;
    private String note;
    private Uri profile;

    private enum key{
        userName(String.class),
        nickName(String.class),
        point(int.class),
        telephone(int.class),
        grade(int.class),
        room(int.class),
        number(int.class),
        note(String.class),
        profileIndex(int.class);

        private final Class clazz;

        key(Class clazz){
            this.clazz = clazz;
        }

        public Class getClazz(){
            return clazz;
        }
    }

    public User(Map<String, Object> datas){
        key[] keys = key.values();
        setUserName((keys[0].getClazz()) datas.get(keys[0].name()));
    }

    public static User getUser() {
        return user;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
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

    public Uri getProfile() {
        return profile;
    }

    public void setProfile(Uri profile) {
        this.profile = profile;
    }
}
