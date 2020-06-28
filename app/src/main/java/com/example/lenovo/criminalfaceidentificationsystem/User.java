package com.example.lenovo.criminalfaceidentificationsystem;

public class User {

    private   String name,email,phone,DOB,pincode,gender,imageURL;

    public User()
    {

    }
    public User(String name, String email, String phone, String DOB, String pincode,String gender,String imageURL) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.DOB = DOB;
        this.pincode = pincode;
        this.gender = gender;
        this.imageURL=imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
