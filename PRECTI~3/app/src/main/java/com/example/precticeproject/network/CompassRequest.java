package com.example.precticeproject.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CompassRequest extends StringRequest {

    final static private String URL = "http://yaya0147.dothome.co.kr/CompassPHP.php";
    private Map<String, String> map;

    public CompassRequest(String[] keys, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);
        map = new HashMap<>();
        map.put("meteor", keys[0]);
        map.put("area", keys[1]);
        map.put("timeAlt", keys[2]);
        map.put("timeAz", keys[3]);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
