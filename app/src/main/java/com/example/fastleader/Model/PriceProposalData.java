package com.example.fastleader.Model;

public class PriceProposalData {

    private String P_ID,P_Description,P_Quantity,P_Amount,P_RandomNo,P_Date,P_To,P_Comment,P_CLName;

    public PriceProposalData() {
    }

    public PriceProposalData(String p_ID, String p_Description, String p_Quantity, String p_Amount, String p_RandomNo,
                             String p_Date, String p_To, String p_Comment, String p_CLName) {
        P_ID = p_ID;
        P_Description = p_Description;
        P_Quantity = p_Quantity;
        P_Amount = p_Amount;
        P_RandomNo = p_RandomNo;
        P_Date = p_Date;
        P_To = p_To;
        P_Comment = p_Comment;
        P_CLName = p_CLName;
    }

    public String getP_ID() {
        return P_ID;
    }

    public void setP_ID(String p_ID) {
        P_ID = p_ID;
    }

    public String getP_Description() {
        return P_Description;
    }

    public void setP_Description(String p_Description) {
        P_Description = p_Description;
    }

    public String getP_Quantity() {
        return P_Quantity;
    }

    public void setP_Quantity(String p_Quantity) {
        P_Quantity = p_Quantity;
    }

    public String getP_Amount() {
        return P_Amount;
    }

    public void setP_Amount(String p_Amount) {
        P_Amount = p_Amount;
    }

    public String getP_RandomNo() {
        return P_RandomNo;
    }

    public void setP_RandomNo(String p_RandomNo) {
        P_RandomNo = p_RandomNo;
    }

    public String getP_Date() {
        return P_Date;
    }

    public void setP_Date(String p_Date) {
        P_Date = p_Date;
    }

    public String getP_To() {
        return P_To;
    }

    public void setP_To(String p_To) {
        P_To = p_To;
    }

    public String getP_Comment() {
        return P_Comment;
    }

    public void setP_Comment(String p_Comment) {
        P_Comment = p_Comment;
    }

    public String getP_CLName() {
        return P_CLName;
    }

    public void setP_CLName(String p_CLName) {
        P_CLName = p_CLName;
    }
}
