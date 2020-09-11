package com.accepted.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

    static final String backColor1 = "backColor1";
    static final String backColor2 = "backColor2";
    static final String txtColor1 = "txtColor1";
    static final String iconColor1 = "iconColor1";

    static final String MenubarFlag = "MenubarFlag";
    static final String RegDateFlag = "RegDateFlag";
    static final String SummaryFlag = "SummaryFlag";
    static final String SearchFlag = "SearchFlag";

    static final String colorMode = "colorMode";

    static final String appName = "appName";

    static final String PREF_USER_ID = "userid";
    static final String PREF_CLICK_TYPE = "clicktype";
    static final String SERVER_IP = "http://175.213.4.39";

    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static String getBackColor1(Context ctx) {
        return getSharedPreferences(ctx).getString(backColor1, "");
    }

    public static String getBackColor2(Context ctx) {
        return getSharedPreferences(ctx).getString(backColor2, "");
    }

    public static String gettxtColor1(Context ctx) {
        return getSharedPreferences(ctx).getString(txtColor1, "");
    }

    public static String geticonColor1(Context ctx) {
        return getSharedPreferences(ctx).getString(iconColor1, "");
    }

    public static String getMenubarFlag(Context ctx) {
        return getSharedPreferences(ctx).getString(MenubarFlag, "");
    }

    public static String getRegDateFlag(Context ctx) {
        return getSharedPreferences(ctx).getString(RegDateFlag, "");
    }

    public static String getSummaryFlag(Context ctx) {
        return getSharedPreferences(ctx).getString(SummaryFlag, "");
    }

    public static String getSearchFlag(Context ctx) {
        return getSharedPreferences(ctx).getString(SearchFlag, "");
    }

    public static String getColorMode(Context ctx) {
        return getSharedPreferences(ctx).getString(colorMode, "");
    }

    public static String getAppName(Context ctx) {
        return getSharedPreferences(ctx).getString(appName, "");
    }

    public static void setBackColor1(Context ctx, String color1){
        Log.d("perf BackColor1", color1);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(backColor1, color1);
        editor.commit();
    }

    public static void setBackColor2(Context ctx, String color2){
        Log.d("perf BackColor2", color2);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(backColor2, color2);
        editor.commit();
    }

    public static void setTxtColor1(Context ctx, String color1){
        Log.d("perf TxtColor", color1);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(txtColor1, color1);
        editor.commit();
    }

    public static void setIconColor1(Context ctx, String icon){
        Log.d("perf IconColor", icon);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(iconColor1, icon);
        editor.commit();
    }

    public static void setMenubarFlag(Context ctx, String flag){
        Log.d("perf MenubarFlag", flag);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(MenubarFlag, flag);
        editor.commit();
    }

    public static void setRegDateFlag(Context ctx, String flag){
        Log.d("perf RegDateFlag", flag);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(RegDateFlag, flag);
        editor.commit();
    }

    public static void setSummaryFlag(Context ctx, String flag){
        Log.d("perf SummaryFlag", flag);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SummaryFlag, flag);
        editor.commit();
    }

    public static void setSearchFlag(Context ctx, String flag){
        Log.d("perf SearchFlag", flag);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(SearchFlag, flag);
        editor.commit();
    }

    public static void setColorMode(Context ctx, String mode){
        Log.d("perf ColorMode", mode);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(colorMode, mode);
        editor.commit();
    }

    public static void setAppName(Context ctx, String name){
        Log.d("perf AppName", name);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(appName, name);
        editor.commit();
    }

    public static String getServerIp(){
        return SERVER_IP;
    }

    public static void setPrefUsrId(Context ctx, String useId){
        Log.d("perf userid", useId);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_ID, useId);
        editor.commit();
    }

    public static void setPrefClickType(Context ctx, String useId){
        Log.d("perf clicktype", useId);
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_CLICK_TYPE, useId);
        editor.commit();
    }

    public static String getPrefUserId(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_ID, "");
    }

    public static String getPrefClickType(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_CLICK_TYPE, "");
    }

    public static void setBackgroundColor(Context ctx, String bg1, String bg2, String txtColor, String iconColor) {
        setBackColor1(ctx, bg1);
        setBackColor2(ctx, bg2);
        setTxtColor1(ctx, txtColor);
        setIconColor1(ctx, iconColor);
    }

    public static void settingUserOption(Context ctx, String mFlag, String rFlag, String sFlag, String searchFlag, String appName) {
        setMenubarFlag(ctx, mFlag);
        setRegDateFlag(ctx, rFlag);
        setSummaryFlag(ctx, sFlag);
        setSearchFlag(ctx, searchFlag);
        setAppName(ctx, appName);
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