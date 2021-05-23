package com.example.precticeproject.functions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ProcessJSONData {
    public static String[] processCompassData(JSONObject jsonObject) throws JSONException {
        String[] re = new String[2];

        re[0] = jsonObject.getString("alt");
        re[1] = jsonObject.getString("az");

        return re;
    }

    public static ArrayList<CommunityItem> processCommuLookupData(JSONArray jsonArray) throws JSONException{
        ArrayList<CommunityItem> itemlist = new ArrayList<>();

        for(int i=0; i< jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            CommunityItem item = new CommunityItem(jsonObject.getInt("textnumber"),
                    jsonObject.getString("profilename"),jsonObject.getString("profiledate"),
                    jsonObject.getString("textual"),i+"");
            itemlist.add(item);
        }

        return itemlist;
    }
}
