package com.accepted.notepad.backgound;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.VolleySingleton;
import com.accepted.notepad.main.MainActivity;
import com.accepted.notepad.tutorial.Tutorial_MainActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class Background_MainActivity extends AppCompatActivity {

    Context mContext;

    String choosedColor1;
    String choosedColor2;
    String choosedColor3;
    String choosedColor4;

    Background_MainActivity background_mainActivity;

    ArrayList<String> colorList;

    // 테마리스트 토탈
    int colorCount = 2;
    int colormode = 1;

    boolean isTutorial;

    boolean isMenu;
    boolean isDate;
    boolean isSearch;
    boolean isMemo;

    String mFlag;
    String rFlag;
    String sFlag;
    String searchFlag;

    GradientDrawable shape;
    GradientDrawable shape1;
    GradientDrawable shape2;

    Paint mPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.background_activity);

        mContext = getApplicationContext();
        background_mainActivity = this;

        Intent intent = getIntent();
        colormode = Integer.valueOf(SaveSharedPreference.getColorMode(mContext));
        choosedColor1 = SaveSharedPreference.getBackColor1(mContext);
        choosedColor2 = SaveSharedPreference.getBackColor2(mContext);
        choosedColor3 = SaveSharedPreference.gettxtColor1(mContext);
        choosedColor4 = SaveSharedPreference.geticonColor1(mContext);

        ((EditText)findViewById(R.id.et_apptitle)).setText(SaveSharedPreference.getAppName(mContext));

        isTutorial = intent.getBooleanExtra("isTutorial",false);

        mPaint = new Paint();

        if(isTutorial)
        {

            ((ImageView)findViewById(R.id.img_open_dl)).setVisibility(View.GONE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ((RelativeLayout)findViewById(R.id.rl_toolbar)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.tv1)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.tv2)).setVisibility(View.VISIBLE);
        }else
        {
            ((ImageView)findViewById(R.id.img_open_dl)).setVisibility(View.VISIBLE);
            ((RelativeLayout)findViewById(R.id.rl_toolbar)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.tv1)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.tv2)).setVisibility(View.GONE);

        }

        ((ImageView)findViewById(R.id.img_open_dl)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ((ImageView)findViewById(R.id.img_open_dl)).setImageResource(R.drawable.icon_pre);
        ((TextView)findViewById(R.id.tv_maintitle_home)).setText("배경설정");

        colorList = new ArrayList<>();
        colorList.add(choosedColor1);
        colorList.add(choosedColor2);
        colorList.add(choosedColor3);
        colorList.add(choosedColor4);

        isMenu = SaveSharedPreference.getMenubarFlag(mContext).equals("Y") ? true : false;
        isDate = SaveSharedPreference.getRegDateFlag(mContext).equals("Y") ? true : false;
        isSearch = SaveSharedPreference.getSearchFlag(mContext).equals("Y") ? true : false;
        isMemo = SaveSharedPreference.getSummaryFlag(mContext).equals("Y") ? true : false;

        switchClick(colorList.get(3));
        background(colorList.get(0),colorList.get(1),colorList.get(2),colorList.get(3));

        ((ImageView)findViewById(R.id.iv_ismenu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMenu)
                {
                    isMenu = false;
                    ((ImageView)findViewById(R.id.iv_ismenu)).setImageResource(R.drawable.icon_switchoff);
                    ((ImageView)findViewById(R.id.iv_ismenu)).setColorFilter(Color.parseColor("#c1c1c1"));
                    ((ImageView)findViewById(R.id.img_open_dl2)).setColorFilter(Color.parseColor(colorList.get(1)));
                }else
                {
                    isMenu = true;
                    ((ImageView)findViewById(R.id.iv_ismenu)).setImageResource(R.drawable.icon_switchon);
                    ((ImageView)findViewById(R.id.iv_ismenu)).setColorFilter(Color.parseColor(colorList.get(3)));
                    ((ImageView)findViewById(R.id.img_open_dl2)).setColorFilter(Color.parseColor(colorList.get(2)));
                }
            }
        });

        ((ImageView)findViewById(R.id.iv_issearch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSearch)
                {
                    isSearch = false;
                    ((ImageView)findViewById(R.id.iv_issearch)).setImageResource(R.drawable.icon_switchoff);
                    ((ImageView)findViewById(R.id.iv_issearch)).setColorFilter(Color.parseColor("#c1c1c1"));
                    ((LinearLayout)findViewById(R.id.ll_searchContainer)).setVisibility(View.GONE);
                }else
                {
                    isSearch = true;
                    ((ImageView)findViewById(R.id.iv_issearch)).setImageResource(R.drawable.icon_switchon);
                    ((ImageView)findViewById(R.id.iv_issearch)).setColorFilter(Color.parseColor(colorList.get(3)));
                    ((LinearLayout)findViewById(R.id.ll_searchContainer)).setVisibility(View.VISIBLE);
                }
            }
        });

        ((ImageView)findViewById(R.id.iv_isdate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDate)
                {
                    isDate = false;
                    ((ImageView)findViewById(R.id.iv_isdate)).setImageResource(R.drawable.icon_switchoff);
                    ((ImageView)findViewById(R.id.iv_isdate)).setColorFilter(Color.parseColor("#c1c1c1"));
                    ((TextView)findViewById(R.id.tv_preview3)).setVisibility(View.GONE);

                }else
                {
                    isDate = true;
                    ((ImageView)findViewById(R.id.iv_isdate)).setImageResource(R.drawable.icon_switchon);
                    ((ImageView)findViewById(R.id.iv_isdate)).setColorFilter(Color.parseColor(colorList.get(3)));
                    ((TextView)findViewById(R.id.tv_preview3)).setVisibility(View.VISIBLE);
                }
            }
        });
        ((ImageView)findViewById(R.id.iv_ismemo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMemo)
                {
                    isMemo = false;
                    ((ImageView)findViewById(R.id.iv_ismemo)).setImageResource(R.drawable.icon_switchoff);
                    ((ImageView)findViewById(R.id.iv_ismemo)).setColorFilter(Color.parseColor("#c1c1c1"));
                    ((TextView)findViewById(R.id.tv_preview2)).setVisibility(View.GONE);
                }else
                {
                    isMemo = true;
                    ((ImageView)findViewById(R.id.iv_ismemo)).setImageResource(R.drawable.icon_switchon);
                    ((ImageView)findViewById(R.id.iv_ismemo)).setColorFilter(Color.parseColor(colorList.get(3)));
                    ((TextView)findViewById(R.id.tv_preview2)).setVisibility(View.VISIBLE);
                }
            }
        });

        ((EditText)findViewById(R.id.et_apptitle)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(((EditText)findViewById(R.id.et_apptitle)).getText().length() == 0){
                    ((TextView)findViewById(R.id.tv_maintitle_home2)).setText("Notepad");
                }else
                {
                    ((TextView)findViewById(R.id.tv_maintitle_home2)).setText(((EditText)findViewById(R.id.et_apptitle)).getText());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ((Button)findViewById(R.id.btn_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBackground();
            }
        });

        ((ImageView)findViewById(R.id.iv_leftarrow)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(colormode==1) {
                    colormode = colorCount;
                }else {
                    colormode = colormode-1;
                }
                colorList.clear();
                getBackground(colormode);
            }
        });

        ((ImageView)findViewById(R.id.iv_rightarrow)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(colormode == colorCount) {
                    colormode = 1;
                }else {
                    colormode = colormode + 1;
                }
                colorList.clear();
                getBackground(colormode);
            }
        });

    }



    public void switchClick(String color4)
    {
        if(isMenu)
        {
            ((ImageView)findViewById(R.id.iv_ismenu)).setImageResource(R.drawable.icon_switchon);
            ((ImageView)findViewById(R.id.iv_ismenu)).setColorFilter(Color.parseColor(color4));
        }else
        {
            ((ImageView)findViewById(R.id.iv_ismenu)).setImageResource(R.drawable.icon_switchoff);
            ((ImageView)findViewById(R.id.iv_ismenu)).setColorFilter(Color.parseColor("#c1c1c1"));
        }

        if(isSearch)
        {
            ((ImageView)findViewById(R.id.iv_issearch)).setImageResource(R.drawable.icon_switchon);
            ((ImageView)findViewById(R.id.iv_issearch)).setColorFilter(Color.parseColor(color4));
        }else
        {
            ((ImageView)findViewById(R.id.iv_issearch)).setImageResource(R.drawable.icon_switchoff);
            ((ImageView)findViewById(R.id.iv_issearch)).setColorFilter(Color.parseColor("#c1c1c1"));
        }

        if(isDate)
        {
            ((ImageView)findViewById(R.id.iv_isdate)).setImageResource(R.drawable.icon_switchon);
            ((ImageView)findViewById(R.id.iv_isdate)).setColorFilter(Color.parseColor(color4));
        }else{
            ((ImageView)findViewById(R.id.iv_isdate)).setImageResource(R.drawable.icon_switchoff);
            ((ImageView)findViewById(R.id.iv_isdate)).setColorFilter(Color.parseColor("#c1c1c1"));
        }

        if(isMemo)
        {
            ((ImageView)findViewById(R.id.iv_ismemo)).setImageResource(R.drawable.icon_switchon);
            ((ImageView)findViewById(R.id.iv_ismemo)).setColorFilter(Color.parseColor(color4));
        }else
        {
            ((ImageView)findViewById(R.id.iv_ismemo)).setImageResource(R.drawable.icon_switchoff);
            ((ImageView)findViewById(R.id.iv_ismemo)).setColorFilter(Color.parseColor("#c1c1c1"));
        }
    }


    public void background(String color1, String color2, String color3, String color4){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color2));
            int flags = getWindow().getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);
        }


        switchClick(color4);

        shape= (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_edittext_login);
        shape.setColor(Color.parseColor(color2));

        shape1 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_mainbtn);
        shape1.setColor(Color.parseColor(color4));

        shape2 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_container);
        shape2.setColor(Color.parseColor(color1));

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius( 30 );
        shape.setColor(Color.parseColor(color1));


        ((LinearLayout)findViewById(R.id.containerBgr)).setBackgroundColor(Color.parseColor(color2));

        ((RelativeLayout)findViewById(R.id.rl_toolbar)).setBackgroundColor(Color.parseColor(color2));
        ((TextView)findViewById(R.id.tv_maintitle_home)).setText("배경설정");
        ((TextView)findViewById(R.id.tv_maintitle_home)).setTextColor(Color.parseColor(color3));
        ((ImageView)findViewById(R.id.img_open_dl)).setColorFilter(Color.parseColor(color3));
        ((ImageView)findViewById(R.id.img_open_dl)).setImageResource(R.drawable.icon_pre);

        ((RelativeLayout)findViewById(R.id.rl_background)).setBackgroundColor(Color.parseColor(color2));

        ((Button)findViewById(R.id.btn_next)).setBackground(shape1);
        ((Button)findViewById(R.id.btn_next)).setTextColor(Color.parseColor("#ffffff"));

        ((TextView)findViewById(R.id.tv_ismenu)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_isdate)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_bgr)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_ismemo)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_issearch)).setTextColor(Color.parseColor(color3));

        ((View)findViewById(R.id.v_apptitle)).setBackgroundColor(Color.parseColor(color1));
        ((View)findViewById(R.id.v_ismenu)).setBackgroundColor(Color.parseColor(color1));
        ((View)findViewById(R.id.v_isdate)).setBackgroundColor(Color.parseColor(color1));
        ((View)findViewById(R.id.v_ismemo)).setBackgroundColor(Color.parseColor(color1));
        ((View)findViewById(R.id.v_issearch)).setBackgroundColor(Color.parseColor(color1));

        ((TextView)findViewById(R.id.tv1)).setBackgroundColor(Color.parseColor(color2));
        ((TextView)findViewById(R.id.tv2)).setBackgroundColor(Color.parseColor(color2));

        ((TextView)findViewById(R.id.tv1)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv2)).setTextColor(Color.parseColor(color3));


        ((LinearLayout)findViewById(R.id.ll_container)).setBackgroundColor(Color.parseColor(color2));
        ((LinearLayout)findViewById(R.id.ll_searchContainer2)).setBackgroundColor(Color.parseColor(color2));

        ((View)findViewById(R.id.iv_color1)).setBackgroundColor(Color.parseColor(color1));
        ((View)findViewById(R.id.iv_color2)).setBackgroundColor(Color.parseColor(color2));
        ((View)findViewById(R.id.iv_color3)).setBackgroundColor(Color.parseColor(color3));
        ((View)findViewById(R.id.iv_color4)).setBackgroundColor(Color.parseColor(color4));

        ((ImageView)findViewById(R.id.iv_leftarrow)).setColorFilter(Color.parseColor(color4));
        ((ImageView)findViewById(R.id.iv_rightarrow)).setColorFilter(Color.parseColor(color4));

        ((EditText)findViewById(R.id.et_apptitle)).setHintTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_bgrmode)).setTextColor(Color.parseColor(color3));


        ((LinearLayout)findViewById(R.id.ll_container_preview)).setBackground(shape2);
        ((LinearLayout)findViewById(R.id.ll_container_preview2)).setBackgroundColor(Color.parseColor(color2));


        ((TextView)findViewById(R.id.tv_preview1)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_preview2)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_preview3)).setTextColor(Color.parseColor(color3));

        ((TextView)findViewById(R.id.tv_maintitle_home2)).setTextColor(Color.parseColor(color3));


        if(isMenu)
        {
            ((ImageView)findViewById(R.id.img_open_dl2)).setColorFilter(Color.parseColor(color3));
        }else
        {
            ((ImageView)findViewById(R.id.img_open_dl2)).setColorFilter(Color.parseColor(color2));
        }


        ((ImageView)findViewById(R.id.iv_search)).setColorFilter(Color.parseColor(color3));
        ((EditText)findViewById(R.id.et_search)).setHintTextColor(Color.parseColor(color3));
        ((EditText)findViewById(R.id.et_search)).setTextColor(Color.parseColor(color3));
        ((RelativeLayout)findViewById(R.id.rl_searchContainer)).setBackground(shape);


    }

    public void getBackground(int val) {
        RequestQueue postRequestQueue = VolleySingleton.getInstance(mContext).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Background/getBackground.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String bg1 = (String) obj.get("BackColor1");
                    String bg2 = (String) obj.get("BackColor2");
                    String txtColor = (String) obj.get("TextColor");
                    String iconColor = (String) obj.get("IconColor");

                    colorList.add(bg1);
                    colorList.add(bg2);
                    colorList.add(txtColor);
                    colorList.add(iconColor);

                    background(colorList.get(0),colorList.get(1),colorList.get(2),colorList.get(3));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SaveSharedPreference.getErrorListener(mContext)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("BackgroundCode", String.valueOf(val));
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }

    public void updateSetting() {
        String appName = ((TextView)findViewById(R.id.tv_maintitle_home2)).getText().toString();
        mFlag = isMenu == true ? "Y" : "N";
        rFlag = isDate == true ? "Y" : "N";
        sFlag = isMemo == true ? "Y" : "N";
        searchFlag = isSearch == true ? "Y" : "N";

        RequestQueue postRequestQueue = VolleySingleton.getInstance(mContext).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Setting/updateSetting.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.get("result").equals("success")) {
                        Toast.makeText(mContext, "설정이 저장되었습니다.", Toast.LENGTH_SHORT).show();

                        SaveSharedPreference.settingUserOption(mContext, mFlag, rFlag, sFlag, searchFlag, appName);

                        Intent intent = new Intent(mContext,MainActivity.class);
                        intent.putExtra("ColorMode",colormode);
                        intent.putExtra("ismemo",isMemo);
                        intent.putExtra("ismenu",isMenu);
                        intent.putExtra("isdate",isDate);
                        intent.putExtra("issearch",isSearch);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SaveSharedPreference.getErrorListener(mContext)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("AppName", appName);
                params.put("MenubarFlag", mFlag);
                params.put("RegDateFlag", rFlag);
                params.put("SummaryFlag", sFlag);
                params.put("SearchFlag", searchFlag);
                params.put("BackgroundCode", colormode + "");
                params.put("MemID", SaveSharedPreference.getPrefUserId(mContext));
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }

    public void updateBackground() {
        RequestQueue postRequestQueue = VolleySingleton.getInstance(mContext).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Setting/updateBackground.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.get("result").equals("success")) {
                        String bg1 = colorList.get(0);
                        String bg2 = colorList.get(1);
                        String txtColor = colorList.get(2);
                        String iconColor = colorList.get(3);

                        SaveSharedPreference.setBackgroundColor(mContext, bg1, bg2, txtColor, iconColor);
                        updateSetting();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SaveSharedPreference.getErrorListener(mContext)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("MemID", SaveSharedPreference.getPrefUserId(mContext));
                params.put("BackgroundCode", colormode+"");
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }

//    public void setTutorial() {
//        SaveSharedPreference.setBackgroundColor(mContext);
//        SaveSharedPreference.settingUserOption(mContext);
//    }
}
