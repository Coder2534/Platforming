package com.android.platforming.clazz;

public class Setting {
    int icon;
    String title;
    String description;

    public Setting(int icon, String title, String description){
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
