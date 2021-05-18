package com.example.precticeproject.functions;

import org.json.JSONException;
import org.json.JSONObject;

public class ProcessJSONData {
    public static String[] processCompassData(JSONObject jsonObject) throws JSONException {
        String[] re = new String[2];

        re[0] = jsonObject.getString("alt");
        re[1] = jsonObject.getString("az");

        return re;
    }
}
