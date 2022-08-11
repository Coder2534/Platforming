package com.android.platforming.clazz;

import java.util.List;

public class TelephoneItem {
    private String name;
    private String clazz;
    private String phone;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getClazz(){
        return  clazz;
    }
    public void setClazz(String clazz){
        this.clazz = clazz;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public TelephoneItem(String clazz, String name, String phone){
        this.name = name;
        this.clazz = clazz;
        this.phone = phone;
    }

}
