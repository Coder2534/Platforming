package com.android.platforming.clazz;

import java.util.ArrayList;

public class Post {
    String UUID;
    int profile;
    String nickname;
    String date;
    String title;
    String detail;
    int thumb_up;
    ArrayList<Comment> comments = new ArrayList<>();
}
