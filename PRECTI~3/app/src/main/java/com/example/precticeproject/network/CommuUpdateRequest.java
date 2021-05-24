package com.example.precticeproject.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommuUpdateRequest extends StringRequest {

    final static private String URL = "http://yaya0147.dothome.co.kr/UpdatePHP.php";
    private Map<String, String> map;


    public CommuUpdateRequest(String textnumber, String textual, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);
        map = new HashMap<>();
        map.put("textnumber", textnumber);
        map.put("textual",textual);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
