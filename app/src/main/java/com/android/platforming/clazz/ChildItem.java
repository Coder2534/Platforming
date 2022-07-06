package com.android.platforming.clazz;

public class ChildItem {
    private String title;
    private int level;

    public ChildItem() {
    }

    public ChildItem(String title, int level) {
        this.title = title;
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
