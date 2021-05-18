package com.example.precticeproject;


import com.example.precticeproject.functions.FindAltAz;
import com.example.precticeproject.functions.ProcessJSONData;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        String[] expect = {"Eta Aquariids","Daejeon","06:00-Alt.","06:00-Az."};
        String[] actual = FindAltAz.findAltAz("물병자리-에타 유성우","대전","06시");
        assertArrayEquals(expect ,actual);

        String[] expect2 = {"Orionids","Incheon","22:00-Alt.","22:00-Az."};
        String[] actual2 = FindAltAz.findAltAz("오리온자리 유성우","인천","22시");
        assertArrayEquals(expect2 ,actual2);

        String[] expect3 = {"fail","fail","22:00-Alt.","22:00-Az."};
        String[] actual3 = FindAltAz.findAltAz("게자리 유성우","파주","22시");
        assertArrayEquals(expect3 ,actual3);

        String[] keys = {"Orionids","fail", "success"};
        //assertTrue(FindAltAz.checkNotFail(keys));
    }

    @Test
    public void processJSONtest() {
        String text = "{\"alt\":\"AB\",\"az\":\"BC\"}";
        String a = "AB";
        String b = "BC";
        String[] expect = new String[]{a,b};
        try {
            JSONObject j1 = new JSONObject();
            JSONObject j2 = new JSONObject(text);
            j1.put("alt",a);
            j1.put("az",b);
            assertArrayEquals(expect,ProcessJSONData.processCompassData(j1));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //assertEquals("AB",text);

    }
}