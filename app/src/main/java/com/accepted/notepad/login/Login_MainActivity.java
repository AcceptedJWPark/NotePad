package com.accepted.notepad.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.VolleySingleton;
import com.accepted.notepad.join.Join_MainActivity;
import com.accepted.notepad.join.LostID1_MainActivity;
import com.accepted.notepad.join.LostPW1_MainActivity;
import com.accepted.notepad.main.MainActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;


public class Login_MainActivity extends AppCompatActivity {


    Context context;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        context = getApplicationContext();
        String userID = SaveSharedPreference.getPrefUserId(context);
        if (userID != null && !userID.isEmpty()) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((EditText)findViewById(R.id.et_id)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        ((EditText)findViewById(R.id.et_pw)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ((Button)findViewById(R.id.btn_join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Join_MainActivity.class);
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((EditText)findViewById(R.id.et_id)).length() == 0){
                    Toast.makeText(context,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else if(((EditText)findViewById(R.id.et_pw)).length() == 0){
                    Toast.makeText(context,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    memberLogin();
                }
            }
        });

        ((TextView)findViewById(R.id.tv_lostid)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LostID1_MainActivity.class);
                startActivity(intent);
            }
        });


        ((TextView)findViewById(R.id.tv_lostpw)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LostPW1_MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {

            finish();
            super.onBackPressed();

        } else {
            backPressedTime = tempTime;
            Toast.makeText(context, "뒤로가기를 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void memberLogin() {
        RequestQueue postRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Member/memberLogin.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.get("result").equals("success")) {
                        String idTxt = ((EditText)findViewById(R.id.et_id)).getText().toString();
                        // 사용자 지정 색
                        String bg1 = (String) obj.get("BackColor1");
                        String bg2 = (String) obj.get("BackColor2");
                        String txtColor = (String) obj.get("TextColor");
                        String iconColor = (String) obj.get("IconColor");
                        // 사용자 지정 설정
                        String MenubarFlag = (String) obj.get("MenubarFlag");
                        String RegDateFlag = (String) obj.get("RegDateFlag");
                        String SummaryFlag = (String) obj.get("SummaryFlag");
                        String SearchFlag = (String) obj.get("SearchFlag");

                        String AppName = obj.has("AppName") ? (String) obj.get("AppName") : "";

                        String SecurityCode = (String) obj.get("SecurityCode");
                        int BackgroundCode = (int) obj.get("BackgroundCode");
                        SaveSharedPreference.setSecurityCode(context, SecurityCode);
                        SaveSharedPreference.setColorMode(context, ""+BackgroundCode);

                        // 사용자 지정 정보 저장
                        SaveSharedPreference.setBackgroundColor(context, bg1, bg2, txtColor, iconColor);
                        SaveSharedPreference.settingUserOption(context, MenubarFlag, RegDateFlag, SummaryFlag, SearchFlag, AppName);
                        // 로그인 후 아이디 저장
                        SaveSharedPreference.setPrefUsrId(context, idTxt);

                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(context, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        }, SaveSharedPreference.getErrorListener(context)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("MemID", ((EditText)findViewById(R.id.et_id)).getText().toString());
                params.put("Password", ((EditText)findViewById(R.id.et_pw)).getText().toString());
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }
}
