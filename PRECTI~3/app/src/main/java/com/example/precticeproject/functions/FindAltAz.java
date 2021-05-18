package com.example.precticeproject.functions;


import android.app.Activity;



public class FindAltAz {
    public static String[] findAltAz(String m, String c, String t){
        String[] keys = new String[4];
        keys[0] = findmeteor(m);
        keys[1] = findcity(c);
        keys[2] = findtime(t);
        keys[3] = findtime(t);

        if (! keys[2].equals("fail")) {
            keys[2] = keys[2] + "-Alt.";
            keys[3] = keys[3] + "-Az.";
        }
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
            case "물병자리-에타 유성우":
                key = "Eta Aquariids";
                break;
            case "물병자리-델타-남 유성우":
                key = "S Delta Aquariids";
                break;
            case "오리온자리 유성우":
                key = "Orionids";
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
            case "강원":
                key = "Gangwon";
                break;
            case "경기":
                key = "Gyeonggi";
                break;
            case "경남":
                key = "Gyeongsangnam";
                break;
            case "경북":
                key = "Gyeongsangbuk";
                break;
                //4
            case "광주":
                key = "Gwangju";
                break;
            case "대구":
                key = "Daegu";
                break;
            case "대전":
                key = "Daejeon";
                break;
            case "부산":
                key = "Busan";
                break;
            //8
            case "서울":
                key = "Seoul";
                break;
            case "울산":
                key = "Ulsan";
                break;
            case "인천":
                key = "Incheon";
                break;
            case "전남":
                key = "Chollanam";
                break;
            //12
            case "전북":
                key = "Chollabuk";
                break;
            case "제주":
                key = "Jeju";
                break;
            case "충남":
                key = "Chungcheongnam";
                break;
            case "충북":
                key = "Chungcheongbuk";
                break;
            //16
            default:
                key = "fail";
        }
        return key;
    }

    //시각 확인
    public static String findtime(String t){
        String key;
        if (t.length() >= 3) {
            key = t.substring(0, 2) + ":00";
        }else {
            return "fail";
        }
        return key;
    }
}
