package com.accepted.notepad.join;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;


public class Join_MainActivity extends AppCompatActivity {

    Context context;
    int certNum;
    boolean isChecked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        context = getApplicationContext();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ((EditText)findViewById(R.id.et_id_join)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        ((EditText)findViewById(R.id.et_pw_join)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        ((EditText)findViewById(R.id.et_pw2_join)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ((EditText)findViewById(R.id.et_phone_join)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        ((EditText)findViewById(R.id.et_check_join)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ((Button)findViewById(R.id.btn_next_join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((EditText)findViewById(R.id.et_id_join)).length() == 0){
                    Toast.makeText(context,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else if(((EditText)findViewById(R.id.et_pw_join)).length() < 6){
                    Toast.makeText(context,"비밀번호는 6자 이상 입력해주세요",Toast.LENGTH_SHORT).show();
                }else if(((EditText)findViewById(R.id.et_pw_join)).length() == 0){
                    Toast.makeText(context,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else if(!((EditText)findViewById(R.id.et_pw_join)).getText().toString().equals(((EditText)findViewById(R.id.et_pw2_join)).getText().toString()))
                {
                    Toast.makeText(context,"비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }

                else if(!isChecked)
                {
                    Toast.makeText(context,"휴대폰 인증을 진행해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    insertNewMember();
                }


            }
        });
        ((Button)findViewById(R.id.btn_phone_join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PhoneNum = ((EditText)findViewById(R.id.et_phone_join)).getText().toString();

                if(!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", PhoneNum)) {
                    Toast.makeText(context,"휴대폰 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                } else {
                    InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    checkDuplicationPhone();
                }
            }
            });
        ((Button)findViewById(R.id.btn_check_join)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                // 인증 실행해야하는 구간
                if(((EditText)findViewById(R.id.et_check_join)).length() != 6) {
                    Toast.makeText(context,"인증 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                    InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }else {
                    String inputNum = ((EditText)findViewById(R.id.et_check_join)).getText().toString();
                    if (inputNum.equals(String.valueOf(certNum))) {
                        isChecked = true;
                        Toast.makeText(context,"인증되었습니다.",Toast.LENGTH_SHORT).show();
                        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    } else {
                        Toast.makeText(context,"인증 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        });

        ((EditText)findViewById(R.id.et_phone_join)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(((EditText)findViewById(R.id.et_phone_join)).length() == 11)
                {
                    ((Button)findViewById(R.id.btn_phone_join)).setBackgroundResource(R.drawable.bgr_mainbtn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((EditText)findViewById(R.id.et_check_join)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(((EditText)findViewById(R.id.et_check_join)).length() == 6)
                {
                    ((Button)findViewById(R.id.btn_check_join)).setBackgroundResource(R.drawable.bgr_mainbtn_login);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void checkDuplicationPhone() {
        String PhoneNum = (((EditText)findViewById(R.id.et_phone_join)).getText()).toString();


        RequestQueue postRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Member/checkDuplicationPhone.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    String result = obj.getString("result");

                    if(result.equals("success")) {
                        sendSMS();
                    } else {
                        Toast.makeText(context,"이미 가입한 휴대폰 번호입니다.",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SaveSharedPreference.getErrorListener(context)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Phone", PhoneNum);
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }

    public void sendSMS() {
        String PhoneNum = (((EditText)findViewById(R.id.et_phone_join)).getText()).toString();


        RequestQueue postRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Member/sendJoinSMS.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    certNum = (int) obj.get("certNum");

                    Toast.makeText(context,"인증번호가 발송되었습니다.",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SaveSharedPreference.getErrorListener(context)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sRecieveNum", PhoneNum);
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }

    public void insertNewMember() {
        String PhoneNum = (((EditText)findViewById(R.id.et_phone_join)).getText()).toString();
        RequestQueue postRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Member/insertNewMember.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.get("result").equals("success")) {
                        Toast.makeText(context,"튜토리얼을 진행해주세요.",Toast.LENGTH_SHORT).show();

                        SaveSharedPreference.setPrefUsrId(context, (((EditText)findViewById(R.id.et_id_join)).getText()).toString());
                        memberLogin();
                    } else if (obj.get("result").equals("has")) {
                        Toast.makeText(context,"이미 가입된 아이디입니다.",Toast.LENGTH_SHORT).show();
                    } else if (obj.get("result").equals("hasPhone")) {
                        Toast.makeText(context,"이미 가입된 핸드폰입니다.",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SaveSharedPreference.getErrorListener(context)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("MemID", (((EditText)findViewById(R.id.et_id_join)).getText()).toString());
                params.put("Password", (((EditText)findViewById(R.id.et_pw_join)).getText()).toString());
                params.put("Phone", PhoneNum);
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }

    public void memberLogin() {
        RequestQueue postRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Member/memberLogin.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.get("result").equals("success")) {
                        String idTxt = (((EditText)findViewById(R.id.et_id_join)).getText()).toString();
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

                        int BackgroundCode = (int) obj.get("BackgroundCode");
                        SaveSharedPreference.setColorMode(context, ""+BackgroundCode);
                        // 사용자 지정 정보 저장
                        SaveSharedPreference.setBackgroundColor(context, bg1, bg2, txtColor, iconColor);
                        SaveSharedPreference.settingUserOption(context, MenubarFlag, RegDateFlag, SummaryFlag, SearchFlag, AppName);
                        // 로그인 후 아이디 저장
                        SaveSharedPreference.setPrefUsrId(context, idTxt);

                        Intent intent = new Intent(context,Tutorial_MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SaveSharedPreference.getErrorListener(context)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("MemID", (((EditText)findViewById(R.id.et_id_join)).getText()).toString());
                params.put("Password", (((EditText)findViewById(R.id.et_pw_join)).getText()).toString());
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }
}
