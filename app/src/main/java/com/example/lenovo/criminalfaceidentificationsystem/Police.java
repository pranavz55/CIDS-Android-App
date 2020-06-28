package com.example.lenovo.criminalfaceidentificationsystem;

public class Police {

    public String pzone,pdivision,pname,pinspector,contact,pmail,imageURL;

    public Police()
    {

    }
    public Police(String pzone, String pdivision, String pname,String contact,String pinspector,String pmail,String imageURL) {
        this.pzone = pzone;
        this.pdivision = pdivision;
        this.pname = pname;
        this.contact = contact;
        this.pinspector=pinspector;
        this.pmail=pmail;
        this.imageURL=imageURL;
    }

    public String getPzone() {
        return pzone;
    }

    public void setPzone(String pzone) {
        this.pzone = pzone;
    }

    public String getPdivision() {
        return pdivision;
    }

    public void setPdivision(String pdivision) {
        this.pdivision = pdivision;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPinspector() {
        return pinspector;
    }

    public void setPinspector(String pinspector) {
        this.pinspector = pinspector;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPmail() {
        return pmail;
    }

    public void setPmail(String pmail) {
        this.pmail = pmail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
