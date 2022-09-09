package com.android.platforming.clazz;

public class TableItem {
    private String mainText = null;
    private String subText = null;

    public TableItem(){}

    public TableItem(String mainText){
        this.mainText = mainText;
    }

    public TableItem(String mainText, String subText){
        this.mainText = mainText;
        this.subText = subText;
    }

    public String getMainText() {
        return mainText;
    }

    public String getSubText() {
        return subText;
    }
}
