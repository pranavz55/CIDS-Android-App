package com.example.lenovo.criminalfaceidentificationsystem;

public class Infosuspect {
    public String photo,gender,last_seen,reason,desc,heignt;

    public Infosuspect(){

    }

    public Infosuspect(String photo, String gender, String last_seen, String reason, String desc, String heignt) {
        this.photo = photo;
        this.gender = gender;
        this.last_seen = last_seen;
        this.reason = reason;
        this.desc = desc;
        this.heignt = heignt;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHeignt() {
        return heignt;
    }

    public void setHeignt(String heignt) {
        this.heignt = heignt;
    }
}
