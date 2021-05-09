package com.example.precticeproject.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CompassRequest extends StringRequest {

    final static private String URL = "http://yaya0147.dothome.co.kr/Testhis.php";
    private Map<String, String> map;

    public CompassRequest(Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);
        map = new HashMap<>();
       /* map.put("userID",userID);
        map.put("userPassword", userPass);
        map.put("userName", userName);
        map.put("userAge", userAge + ""); */

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
