package com.example.precticeproject;

import com.example.precticeproject.functions.FindAltAz;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String[] expect = {"Geminids","Busan","16:00-Alt.","16:00-Az."};
        String[] actual = FindAltAz.findAltAz("쌍둥이자리 유성우","부산","16시");
        assertArrayEquals(expect ,actual);
    }
}