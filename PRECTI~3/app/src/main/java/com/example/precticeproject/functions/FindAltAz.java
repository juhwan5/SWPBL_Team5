package com.example.precticeproject.functions;


import android.app.Activity;



public class FindAltAz {
    public static String[] findAltAz(String m, String c, String t){
        String[] keys = new String[4];
        keys[0] = findmeteor(m);
        keys[1] = findcity(c);
        keys[2] = findtime(t) + "-Alt.";
        keys[3] = findtime(t) + "-Az.";

        return keys;
    }


    //유성우 종류 확인
    public static String findmeteor(String m){
        String key;
        switch(m){
            case "거문고자리 유성우":
                key = "Lyrids";
                break;
            case "사분의자리 유성우":
                key = "Quadrantids";
                break;
            case "페르세우스 유성우":
                key = "Perseids";
                break;
            case "사자자리 유성우":
                key = "Leonids";
                break;
            case "쌍둥이자리 유성우":
                key = "Geminids";
                break;
            default:
                key = "fail";
        }
        return key;
    }

    //지역 확인
    public static String findcity(String c){
        String key;
        switch(c){
            case "서울":
                key = "Seoul";
                break;
            case "부산":
                key = "Busan";
                break;
            case "제주":
                key = "Cheju";
                break;
            case "전북":
                key = "Chollabuk";
                break;
            case "전남":
                key = "Chollanam";
                break;
            default:
                key = "fail";
        }
        return key;
    }

    //시각 확인
    public static String findtime(String t){
        String key = t.substring(0,2) + ":00";
        return key;
    }
}
