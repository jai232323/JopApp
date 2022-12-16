package com.example.fastleader.Model;

public class LabelData {

    private String L_ID,L_NewLabelName,L_Color;

    public LabelData() {
    }

    public LabelData(String l_ID, String l_NewLabelName, String l_Color) {
        L_ID = l_ID;
        L_NewLabelName = l_NewLabelName;
        L_Color = l_Color;
    }

    public String getL_ID() {
        return L_ID;
    }

    public void setL_ID(String l_ID) {
        L_ID = l_ID;
    }

    public String getL_NewLabelName() {
        return L_NewLabelName;
    }

    public void setL_NewLabelName(String l_NewLabelName) {
        L_NewLabelName = l_NewLabelName;
    }

    public String getL_Color() {
        return L_Color;
    }

    public void setL_Color(String l_Color) {
        L_Color = l_Color;
    }
}
