package com.accepted.notepad.join;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.VolleySingleton;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class LostPW1_MainActivity extends AppCompatActivity {

    String certNum;
    Context context;
    boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_lostpw1_activity);

        context = getApplicationContext();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ((EditText)findViewById(R.id.et_id_lostpw)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        ((EditText)findViewById(R.id.et_phone_lostpw)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        ((EditText)findViewById(R.id.et_check_lostpw)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ((EditText)findViewById(R.id.et_phone_lostpw)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(((EditText)findViewById(R.id.et_phone_lostpw)).length() == 11)
                {
                    ((Button)findViewById(R.id.btn_phone_lostpw)).setBackgroundResource(R.drawable.bgr_mainbtn);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((EditText)findViewById(R.id.et_check_lostpw)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(((EditText)findViewById(R.id.et_check_lostpw)).length() == 6)
                {
                    ((Button)findViewById(R.id.btn_check_lostpw)).setBackgroundResource(R.drawable.bgr_mainbtn);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ((Button)findViewById(R.id.btn_phone_lostpw)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PhoneNum = ((EditText)findViewById(R.id.et_phone_lostpw)).getText().toString();

                if(!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", PhoneNum)) {
                    Toast.makeText(context,"휴대폰 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                } else {
                    InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    sendSMS();
                }
            }
        });
        ((Button)findViewById(R.id.btn_check_lostpw)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 인증 실행해야하는 구간
                if(((EditText)findViewById(R.id.et_check_lostpw)).length() != 6) {
                    Toast.makeText(context,"인증 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    String inputNum = ((EditText)findViewById(R.id.et_check_lostpw)).getText().toString();
                    if (inputNum.equals(String.valueOf(certNum))) {
                        isChecked = true;
                        Toast.makeText(context,"인증되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ((Button)findViewById(R.id.btn_next_lostpw)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( ((EditText)findViewById(R.id.et_id_lostpw)).length()==0)
                {
                    Toast.makeText(context,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else if(!isChecked)
                {
                    Toast.makeText(context,"휴대폰 인증을 진행해주세요",Toast.LENGTH_SHORT).show();
                }else
                {
                    getMemID();
                }

            }
        });



    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void sendSMS() {
        String PhoneNum = (((EditText)findViewById(R.id.et_phone_lostpw)).getText()).toString();


        RequestQueue postRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Member/sendJoinSMS.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    certNum = obj.getString("certNum");

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

    public void getMemID() {
        String userID = ((EditText)findViewById(R.id.et_id_lostpw)).getText().toString();

        String PhoneNum = (((EditText)findViewById(R.id.et_phone_lostpw)).getText()).toString();
        RequestQueue postRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Member/getMemID.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.has("MemID")) {
                        Toast.makeText(context, "해당 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        String memID = obj.getString("MemID");
                        if (memID.equals(userID)) {
                            Intent intent = new Intent(context,LostPW2_MainActivity.class);
                            intent.putExtra("MemID", memID);
                            startActivity(intent);
                        } else {
                            Toast.makeText(context, "해당 정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
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

}
