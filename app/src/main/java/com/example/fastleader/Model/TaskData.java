package com.example.fastleader.Model;

public class TaskData {

    private String C_CustomerName,T_TaskDecription,T_ReminderDate;


    private String Tid;
    private String Billamount;
    private String Jobcreateby;
    private String Jobassignby;
    private String Jobcreatedate;
    private String Jobclosedate;
    private String Jobstatus;
    private String Jobname;
    private String Jobimage;



    public TaskData() {
    }

    public TaskData(String c_CustomerName, String t_TaskDecription, String t_ReminderDate, String tid, String billamount, String jobcreateby,
                    String jobassignby, String jobcreatedate, String jobclosedate, String jobstatus, String jobname, String jobimage) {
        C_CustomerName = c_CustomerName;
        T_TaskDecription = t_TaskDecription;
        T_ReminderDate = t_ReminderDate;
        Tid = tid;
        Billamount = billamount;
        Jobcreateby = jobcreateby;
        Jobassignby = jobassignby;
        Jobcreatedate = jobcreatedate;
        Jobclosedate = jobclosedate;
        Jobstatus = jobstatus;
        Jobname = jobname;
        Jobimage = jobimage;
    }

    public String getC_CustomerName() {
        return C_CustomerName;
    }

    public void setC_CustomerName(String c_CustomerName) {
        C_CustomerName = c_CustomerName;
    }

    public String getT_TaskDecription() {
        return T_TaskDecription;
    }

    public void setT_TaskDecription(String t_TaskDecription) {
        T_TaskDecription = t_TaskDecription;
    }

    public String getT_ReminderDate() {
        return T_ReminderDate;
    }

    public void setT_ReminderDate(String t_ReminderDate) {
        T_ReminderDate = t_ReminderDate;
    }

    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getBillamount() {
        return Billamount;
    }

    public void setBillamount(String billamount) {
        Billamount = billamount;
    }

    public String getJobcreateby() {
        return Jobcreateby;
    }

    public void setJobcreateby(String jobcreateby) {
        Jobcreateby = jobcreateby;
    }

    public String getJobassignby() {
        return Jobassignby;
    }

    public void setJobassignby(String jobassignby) {
        Jobassignby = jobassignby;
    }

    public String getJobcreatedate() {
        return Jobcreatedate;
    }

    public void setJobcreatedate(String jobcreatedate) {
        Jobcreatedate = jobcreatedate;
    }

    public String getJobclosedate() {
        return Jobclosedate;
    }

    public void setJobclosedate(String jobclosedate) {
        Jobclosedate = jobclosedate;
    }

    public String getJobstatus() {
        return Jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        Jobstatus = jobstatus;
    }

    public String getJobname() {
        return Jobname;
    }

    public void setJobname(String jobname) {
        Jobname = jobname;
    }

    public String getJobimage() {
        return Jobimage;
    }

    public void setJobimage(String jobimage) {
        Jobimage = jobimage;
    }
}
