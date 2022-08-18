package com.android.platforming.clazz;

public class SchoolIntroduceItem {
    private String explain;
    private int img;

    public String getExplain(){
        return explain;
    }
    public void setExplain(String name){
        this.explain = explain;
    }
    public int getImg(){
        return  img;
    }
    public void setImg(String clazz){
        this.img = img;
    }


    public SchoolIntroduceItem(String explain, int img){
        this.explain = explain;
        this.img = img;
    }
}
