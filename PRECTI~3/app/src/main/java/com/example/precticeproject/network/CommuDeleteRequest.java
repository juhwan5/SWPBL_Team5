package com.example.precticeproject.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommuDeleteRequest extends StringRequest {

    final static private String URL = "http://yaya0147.dothome.co.kr/DeletePHP.php";
    private Map<String, String> map;


    public CommuDeleteRequest(String textnumber, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);
        map = new HashMap<>();
        map.put("textnumber", textnumber);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
