package com.accepted.notepad;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class SaveSharedPreference {

    static final String backColor1_basic = "#F5F5F5";
    static final String backColor2_basic = "#FFFFFF";
    static final String txtColor1_basic = "#544F4F";
    static final String iconColor1_basic = "#F37A00";


    static final String backColor1_night = "#10161E";
    static final String backColor2_night = "#292D34";
    static final String txtColor1_night = "#e7e7e7";
    static final String iconColor1_night = "#00a8ff";

    static final String SERVER_IP = "http://175.213.4.39";

    public static String getBackColor1_basic() {
        return backColor1_basic;
    }

    public static String getBackColor2_basic() {
        return backColor2_basic;
    }

    public static String gettxtColor1_basic() {
        return txtColor1_basic;
    }

    public static String geticonColor1_basic() {
        return iconColor1_basic;
    }

    public static String getBackColor1_night() {
        return backColor1_night;
    }

    public static String getBackColor2_night() {
        return backColor2_night;
    }

    public static String getTxtColor1_night() {
        return txtColor1_night;
    }

    public static String getIconColor1_night() {
        return iconColor1_night;
    }

    public static String getServerIp(){
        return SERVER_IP;
    }

    public static Response.ErrorListener getErrorListener(final Context context){
        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        Log.d("res", res);
                        Toast.makeText(context, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();

                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    }
                }
            }
        };

        return errorListener;
    }
}