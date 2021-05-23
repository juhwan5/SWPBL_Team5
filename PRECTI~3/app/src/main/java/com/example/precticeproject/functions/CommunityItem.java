package com.example.precticeproject.functions;

public class CommunityItem {
    //int profileImgID;
    int textnumber;
    String profileName;
    String profileDate;
    String textual;
    String comment;

    public CommunityItem(int number, String profileName, String profileDate, String textual, String comment) {
        this.textnumber = number;
        this.profileName = profileName;
        this.profileDate = profileDate;
        this.textual = textual;
        this.comment = comment;
    }

    public int getTextnumber() {return textnumber;}

    public String getProfileName() {
        return profileName;
    }

    public String getProfileDate() {
        return profileDate;
    }

    public String getTextual() {
        return textual;
    }

    public String getComment() {
        return comment;
    }
}
