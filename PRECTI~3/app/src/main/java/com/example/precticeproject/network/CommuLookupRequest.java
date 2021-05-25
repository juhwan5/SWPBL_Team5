package com.example.precticeproject.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.precticeproject.functions.CommunityItem;
import com.example.precticeproject.functions.ProcessJSONData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommuLookupRequest extends StringRequest {

    final static private String URL = "http://yaya0147.dothome.co.kr/LookupPHP.php";
    private Map<String, String> map;


    public CommuLookupRequest(String username,Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);
        map = new HashMap<>();
        map.put("username", username);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
