package com.example.fastleader.Model;

public class CustomerData {

    private String C_ID,C_CustomerName,C_Company,C_MobileNumber,C_Email,C_Address,C_AddEvent,C_Image,DataTime;


    public CustomerData() {
    }

    public CustomerData(String c_ID, String c_CustomerName, String c_Company, String c_MobileNumber,
                        String c_Email, String c_Address, String c_AddEvent, String c_Image, String dataTime) {
        C_ID = c_ID;
        C_CustomerName = c_CustomerName;
        C_Company = c_Company;
        C_MobileNumber = c_MobileNumber;
        C_Email = c_Email;
        C_Address = c_Address;
        C_AddEvent = c_AddEvent;
        C_Image = c_Image;
        DataTime = dataTime;
    }

    public String getC_ID() {
        return C_ID;
    }

    public void setC_ID(String c_ID) {
        C_ID = c_ID;
    }

    public String getC_CustomerName() {
        return C_CustomerName;
    }

    public void setC_CustomerName(String c_CustomerName) {
        C_CustomerName = c_CustomerName;
    }

    public String getC_Company() {
        return C_Company;
    }

    public void setC_Company(String c_Company) {
        C_Company = c_Company;
    }

    public String getC_MobileNumber() {
        return C_MobileNumber;
    }

    public void setC_MobileNumber(String c_MobileNumber) {
        C_MobileNumber = c_MobileNumber;
    }

    public String getC_Email() {
        return C_Email;
    }

    public void setC_Email(String c_Email) {
        C_Email = c_Email;
    }

    public String getC_Address() {
        return C_Address;
    }

    public void setC_Address(String c_Address) {
        C_Address = c_Address;
    }

    public String getC_AddEvent() {
        return C_AddEvent;
    }

    public void setC_AddEvent(String c_AddEvent) {
        C_AddEvent = c_AddEvent;
    }

    public String getC_Image() {
        return C_Image;
    }

    public void setC_Image(String c_Image) {
        C_Image = c_Image;
    }

    public String getDataTime() {
        return DataTime;
    }

    public void setDataTime(String dataTime) {
        DataTime = dataTime;
    }
}
