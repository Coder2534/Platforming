package com.platforming.autonomy.clazz;

public class TableItem {
    private String mainText = "";
    private String subText = "";

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

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }
}
