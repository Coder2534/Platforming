package com.android.platforming.clazz;

import android.app.Activity;
import android.util.Log;

import com.android.platforming.interfaze.ListenerInterface;
import com.example.platforming.R;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    public static User user = null;

    private final static ArrayList<Integer> profiles = new ArrayList<Integer>(){{
        add(R.mipmap.ic_profile_dog_round);
        add(R.mipmap.ic_profile_cat_round);
        add(R.mipmap.ic_profile_rabbit_round);
    }};

    //personal info
    private String uid;
    private String email;
    private String username;
    private String nickname;
    private String telephone;
    private int sex; //0: male 1: female
    private String studentId;
    private String grade;

    //etc
    private int point;
    private String note;
    private int profileIndex;
    private List<Long> fonts;
    private List<Long> themes;
    private List<String> myPostIds;
    private long lastSignIn;
    private int point_receipt;
    private List<Long> dailyTasks;
    private ArrayList<ArrayList<TableItem>> schedules = new ArrayList<ArrayList<TableItem>>(){{
        add(new ArrayList<>());
        add(new ArrayList<>());
        add(new ArrayList<>());
        add(new ArrayList<>());
        add(new ArrayList<>());
    }};

    public User(String uid, String email, Map<String, Object> data){
        this.uid = uid;
        this.email = email;
        username = (String)data.get("username");
        nickname = (String)data.get("nickname");
        telephone = (String)data.get("telephone");
        sex = Integer.parseInt(String.valueOf(data.get("sex")));
        studentId = (String)data.get("studentId");
        grade = (String)data.get("grade");
        profileIndex = Integer.parseInt(String.valueOf(data.get("profileIndex")));
        point = Integer.parseInt(String.valueOf(data.get("point")));
        note = (String) data.get("note");
        themes = (List<Long>) data.get("themes");
        fonts = (List<Long>) data.get("fonts");
        myPostIds = (List<String>) data.get("myPostIds");
        lastSignIn = Long.parseLong(String.valueOf(data.get("lastSignIn")));
        attendanceCheck(new ListenerInterface() {
            @Override
            public void onSuccess(long timeInMillis) {
                user.setLastSignIn(timeInMillis);
                user.setPoint_receipt(10);
                user.setDailyTasks(Arrays.asList(1L, 0L, 0L, 0L));
            }

            @Override
            public void onFail() {
                point_receipt = Integer.parseInt(String.valueOf(data.get("point_receipt")));
                dailyTasks = (List<Long>) data.get("dailyTasks");
            }
        });
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public static ArrayList<Integer> getProfiles(){
        return profiles;
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

    public String getGrade(){return grade;}

    public void setGrade(String grade){this.grade = grade;}

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

    public List<Long> getFonts() {
        return fonts;
    }

    public List<Long> getThemes() {
        return themes;
    }

    public List<String> getMyPostIds() {
        return myPostIds;
    }

    public void setLastSignIn(long lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    public long getLastSignIn() {
        return lastSignIn;
    }

    public void setPoint_receipt(int point_receipt) {
        this.point_receipt = point_receipt;
    }

    public void addPoint_receipt(int value){
        this.point_receipt += value;
    }

    public int getPoint_receipt() {
        return point_receipt;
    }

    public void setDailyTasks(List<Long> dailyTasks) {
        this.dailyTasks = dailyTasks;
    }

    public List<Long> getDailyTasks() {
        return dailyTasks;
    }

    public void setSchedules(ArrayList<ArrayList<TableItem>> schedules) {
        this.schedules = schedules;
    }

    public ArrayList<ArrayList<TableItem>> getSchedules() {
        return schedules;
    }

    public void attendanceCheck(ListenerInterface listenerInterface) {
        new Thread(() -> {
            NTPUDPClient lNTPUDPClient = new NTPUDPClient();
            lNTPUDPClient.setDefaultTimeout(3000);
            long returnTime = 0;
            try {
                lNTPUDPClient.open();
                InetAddress lInetAddress = InetAddress.getByName("pool.ntp.org");
                TimeInfo lTimeInfo = lNTPUDPClient.getTime(lInetAddress);
                //returnTime = lTimeInfo.getReturnTime(); // local time
                returnTime = lTimeInfo.getMessage().getTransmitTimeStamp().getTime(); // server time
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lNTPUDPClient.close();
            }
            Date date = new Date(returnTime);

            Calendar calendar_now = Calendar.getInstance();
            calendar_now.setTime(date);
            Calendar calendar_last = Calendar.getInstance();
            calendar_last.setTimeInMillis(user.getLastSignIn());

            Log.d("date", String.format("last: %d, now: %d", calendar_last.get(Calendar.DATE), calendar_now.get(Calendar.DATE)));
            if(calendar_last.get(Calendar.DATE) < calendar_now.get(Calendar.DATE)){
                FirestoreManager firestoreManager = new FirestoreManager();
                firestoreManager.updateUserData(new HashMap<String, Object>(){{
                    put("lastSignIn", calendar_now.getTimeInMillis());
                    put("point_receipt", 10);
                    put("dailyTasks", Arrays.asList(1L, 0L, 0L, 0L));
                }}, calendar_now.getTimeInMillis(), listenerInterface);
            }
            else{
                listenerInterface.onFail();
            }
        }).start();
    }
}
