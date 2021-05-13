package com.example.precticeproject.functions;

public class AstroItem {
    int imgID;
    String astroName;
    String astroDate;

    public AstroItem(int imgID, String astroName, String astroDate) {
        this.imgID = imgID;
        this.astroName = astroName;
        this.astroDate = astroDate;
    }

    public int getImgID() {
        return imgID;
    }

    public String getAstroName() {
        return astroName;
    }

    public String getAstroDate() {
        return astroDate;
    }
}
