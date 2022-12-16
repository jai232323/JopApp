package com.example.fastleader.Model;

public class Company {

    private String Cid;
    private String Cname;
    private String Ccontent;
    private String Cemail;
    private String Cmobilenumber;
    private String Caddress;
    private String Clogo;
    private String Umobilenumber;

    public Company() {
    }

    public Company(String cid, String cname, String ccontent,
                   String cemail, String cmobilenumber, String caddress, String clogo, String umobilenumber) {
        Cid = cid;
        Cname = cname;
        Ccontent = ccontent;
        Cemail = cemail;
        Cmobilenumber = cmobilenumber;
        Caddress = caddress;
        Clogo = clogo;
        Umobilenumber = umobilenumber;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getCcontent() {
        return Ccontent;
    }

    public void setCcontent(String ccontent) {
        Ccontent = ccontent;
    }

    public String getCemail() {
        return Cemail;
    }

    public void setCemail(String cemail) {
        Cemail = cemail;
    }

    public String getCmobilenumber() {
        return Cmobilenumber;
    }

    public void setCmobilenumber(String cmobilenumber) {
        Cmobilenumber = cmobilenumber;
    }

    public String getCaddress() {
        return Caddress;
    }

    public void setCaddress(String caddress) {
        Caddress = caddress;
    }

    public String getClogo() {
        return Clogo;
    }

    public void setClogo(String clogo) {
        Clogo = clogo;
    }

    public String getUmobilenumber() {
        return Umobilenumber;
    }

    public void setUmobilenumber(String umobilenumber) {
        Umobilenumber = umobilenumber;
    }
}
