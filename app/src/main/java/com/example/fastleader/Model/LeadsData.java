package com.example.fastleader.Model;

public class LeadsData {

    private String L_ID,L_LeadName,L_Company,L_MobileNumber,L_Email,L_Address,L_AddEvent,L_Image,DataTime;

    public LeadsData() {
    }

    public LeadsData(String l_ID, String l_LeadName, String l_Company,
                     String l_MobileNumber, String l_Email, String l_Address, String l_AddEvent, String l_Image, String dataTime) {
        L_ID = l_ID;
        L_LeadName = l_LeadName;
        L_Company = l_Company;
        L_MobileNumber = l_MobileNumber;
        L_Email = l_Email;
        L_Address = l_Address;
        L_AddEvent = l_AddEvent;
        L_Image = l_Image;
        DataTime = dataTime;
    }

    public String getL_ID() {
        return L_ID;
    }

    public void setL_ID(String l_ID) {
        L_ID = l_ID;
    }

    public String getL_LeadName() {
        return L_LeadName;
    }

    public void setL_LeadName(String l_LeadName) {
        L_LeadName = l_LeadName;
    }

    public String getL_Company() {
        return L_Company;
    }

    public void setL_Company(String l_Company) {
        L_Company = l_Company;
    }

    public String getL_MobileNumber() {
        return L_MobileNumber;
    }

    public void setL_MobileNumber(String l_MobileNumber) {
        L_MobileNumber = l_MobileNumber;
    }

    public String getL_Email() {
        return L_Email;
    }

    public void setL_Email(String l_Email) {
        L_Email = l_Email;
    }

    public String getL_Address() {
        return L_Address;
    }

    public void setL_Address(String l_Address) {
        L_Address = l_Address;
    }

    public String getL_AddEvent() {
        return L_AddEvent;
    }

    public void setL_AddEvent(String l_AddEvent) {
        L_AddEvent = l_AddEvent;
    }

    public String getL_Image() {
        return L_Image;
    }

    public void setL_Image(String l_Image) {
        L_Image = l_Image;
    }

    public String getDataTime() {
        return DataTime;
    }

    public void setDataTime(String dataTime) {
        DataTime = dataTime;
    }
}
