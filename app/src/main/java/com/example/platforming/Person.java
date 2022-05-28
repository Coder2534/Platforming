package com.example.platforming;

public class Person {
    String NAME;
    String NICKNAME;
    int POINT;
    int TELEPHONE;
    int GRADE;
    int CLASS;
    int NUMBER;
    String NOTE;
    int PROFILE;

    boolean FIRST_LOGIN = true;

    public String getNAME() {
        return NAME;
    }

    public String getNICKNAME() {
        return NICKNAME;
    }

    public int getPOINT() {
        return POINT;
    }

    public int getTELEPHONE() {
        return TELEPHONE;
    }

    public int getCLASS() {
        return CLASS;
    }

    public int getGRADE() {
        return GRADE;
    }

    public int getNUMBER() {
        return NUMBER;
    }

    public String getNOTE() {
        return NOTE;
    }

    public int getPROFILE() {
        return PROFILE;
    }

    public void setGRADE(int GRADE) {
        this.GRADE = GRADE;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setNICKNAME(String NICKNAME) {
        this.NICKNAME = NICKNAME;
    }

    public void setPOINT(int POINT) {
        this.POINT = POINT;
    }

    public void setTELEPHONE(int TELEPHONE) {
        this.TELEPHONE = TELEPHONE;
    }

    public void setCLASS(int CLASS) {
        this.CLASS = CLASS;
    }

    public void setNOTE(String NOTE) {
        this.NOTE = NOTE;
    }

    public void setNUMBER(int NUMBER) {
        this.NUMBER = NUMBER;
    }

    public void setPROFILE(int PROFILE) {
        this.PROFILE = PROFILE;
    }
}
