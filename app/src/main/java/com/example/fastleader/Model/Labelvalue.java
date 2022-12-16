package com.example.fastleader.Model;

public class Labelvalue {

   private String Lid;
   private String Labelname;
   private String Labelvalue;

   public Labelvalue() {
   }

   public Labelvalue(String lid, String labelname, String labelvalue) {
      Lid = lid;
      Labelname = labelname;
      Labelvalue = labelvalue;
   }

   public String getLid() {
      return Lid;
   }

   public void setLid(String lid) {
      Lid = lid;
   }

   public String getLabelname() {
      return Labelname;
   }

   public void setLabelname(String labelname) {
      Labelname = labelname;
   }

   public String getLabelvalue() {
      return Labelvalue;
   }

   public void setLabelvalue(String labelvalue) {
      Labelvalue = labelvalue;
   }
}
