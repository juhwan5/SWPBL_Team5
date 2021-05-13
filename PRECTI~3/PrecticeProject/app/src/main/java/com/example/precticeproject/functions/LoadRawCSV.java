package com.example.precticeproject.functions;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;

public class LoadRawCSV {

    String[][] csvdata;
    Activity activity;

    public LoadRawCSV(Activity activity){
        this.activity = activity;
    }

    public String[][] loadDB(int rawdata){
        InputStream f1 = activity.getResources().openRawResource(rawdata);
        try {
            byte[] data = new byte[f1.available()];
            f1.read(data);
            f1.close();
            String s = new String(data, "UTF-8");
            fileSplit(s);
        }catch (IOException e){
            e.printStackTrace();
        }
        return csvdata;
    }

    public void fileSplit(String str){
        String tmp[] = str.split("\n");
        String tmp2[] = tmp[0].split(",");
        csvdata = new String[tmp.length][tmp2.length];

        String s;
        for(int i = 0; i < tmp.length; i++){
            s = tmp[i];
            tmp2 = s.split(",");
            for (int j=0; j<tmp2.length; j++){
                tmp2[j] = tmp2[j].trim();
                csvdata[i][j] = tmp2[j];
            }
        }
    }

}