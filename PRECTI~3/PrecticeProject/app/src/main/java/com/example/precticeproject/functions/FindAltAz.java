package com.example.precticeproject.functions;


import android.app.Activity;

import com.example.precticeproject.R;

public class FindAltAz {
    public static String[] findAltAz(Activity activity, String m, String c, String t){
        int meteor;
        String[] a;
        LoadRawCSV loadRawCSV = new LoadRawCSV(activity);
        meteor = findmeteor(m);
        if (meteor != -1 ) {
            String[][] rawdata = loadRawCSV.loadDB(meteor);

            int city = findcity(c);
            int time = findtime(t);
            if (city != -1 & time != -1) {
                String alt, az;
                alt = rawdata[city][(time * 2) - 1];
                az = rawdata[city][(time * 2)];
                a = new String[]{alt, az};
            } else{
                a = new String[]{"검출 불가", "검출 불가"};
            }
        } else {
            a = new String[]{"검출 불가", "검출 불가"};
        }
        return a;
    }


    //유성우 종류 확인 -> 참고할 raw파일 확인
    public static int findmeteor(String m){
        int key;
        switch(m){
            case "거문고자리 유성우":
                key = R.raw.lyrids;
                break;
            case "사분의자리 유성우":
                key = R.raw.quadrantids;
                break;
            case "페르세우스 유성우":
                key = R.raw.perseids;
                break;
            case "사자자리 유성우":
                key = R.raw.leonids;
                break;
            case "쌍둥이자리 유성우":
                key = R.raw.geminids;
                break;
            default:
                key = -1;
        }
        return key;
    }

    //지역 확인
    public static int findcity(String c){
        int key;
        switch(c){
            case "서울":
                key = 1;
                break;
            case "부산":
                key = 2;
                break;
            case "제주":
                key = 3;
                break;
            case "전북":
                key = 4;
                break;
            case "전남":
                key = 5;
                break;
            default:
                key = -1;
        }
        return key;
    }

    //시각 확인
    public static int findtime(String t){
        int key;
        switch(t){
            case "00시":
                key = 1;
                break;
            case "02시":
                key = 2;
                break;
            case "04시":
                key = 3;
                break;
            case "06시":
                key = 4;
                break;
            case "08시":
                key = 5;
                break;
            default:
                key = -1;
        }
        return key;
    }
}
