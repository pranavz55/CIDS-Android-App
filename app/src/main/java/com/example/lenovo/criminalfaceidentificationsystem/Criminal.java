package com.example.lenovo.criminalfaceidentificationsystem;

public class Criminal {

    public String cname,cnbw,cloc,crcn,desc,locality,imageURL,police_name,police_phone,crimeid;


    public Criminal()
    {

    }
    public Criminal(String cname, String cnbw, String cloc, String crcn, String desc ,String locality,String imageURL,String crimeid,String police_name,String police_phone) {
        if(cname.trim().equals(""))
        {
            cname="NO Name";
        }
        this.cname=cname;
        this.cnbw = cnbw;
        this.cloc = cloc;
        this.crcn = crcn;
        this.desc = desc;
        this.locality=locality;
        this.imageURL = imageURL;
        this.police_name=police_name;
        this.police_phone=police_phone;
        this.crimeid=crimeid;

    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCnbw() {
        return cnbw;
    }

    public void setCnbw(String cnbw) {
        this.cnbw = cnbw;
    }

    public String getCloc() {
        return cloc;
    }

    public void setCloc(String cloc) {
        this.cloc = cloc;
    }

    public String getCrcn() {
        return crcn;
    }

    public void setCrcn(String crcn) {
        this.crcn = crcn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPolice_name() {
        return police_name;
    }

    public void setPolice_name(String police_name) {
        this.police_name = police_name;
    }

    public String getPolice_phone() {
        return police_phone;
    }

    public void setPolice_phone(String police_phone) {
        this.police_phone = police_phone;
    }

    public String getCrimeid() {
        return crimeid;
    }

    public void setCrimeid(String crimeid) {
        this.crimeid = crimeid;
    }
}
