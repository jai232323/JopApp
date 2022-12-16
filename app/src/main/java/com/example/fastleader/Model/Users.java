package com.example.fastleader.Model;

public class Users {

    private String Uid;
    private String Uname;
    private String Umobilenumber;
    private String Upassword;

    public Users() {
    }

    public Users(String uid, String uname, String umobilenumber, String upassword) {
        Uid = uid;
        Uname = uname;
        Umobilenumber = umobilenumber;
        Upassword = upassword;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUmobilenumber() {
        return Umobilenumber;
    }

    public void setUmobilenumber(String umobilenumber) {
        Umobilenumber = umobilenumber;
    }

    public String getUpassword() {
        return Upassword;
    }

    public void setUpassword(String upassword) {
        Upassword = upassword;
    }
}
