package com.example.precticeproject.functions;

public class CommunityItem {
    //int profileImgID;
    String profileName;
    String profileDate;
    String textual;
    String comment;

    public CommunityItem(String profileName, String profileDate, String textual, String comment) {
        this.profileName = profileName;
        this.profileDate = profileDate;
        this.textual = textual;
        this.comment = comment;
    }

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
