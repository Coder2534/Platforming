package com.platforming.autonomy.clazz;

public class ExpandableListItem {
    private String title;
    int category = -1;

    public ExpandableListItem() {
    }

    public ExpandableListItem(String title) {
        this.title = title;
    }

    public ExpandableListItem(String title, int category) {
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public int getCategory() {
        return category;
    }
}
