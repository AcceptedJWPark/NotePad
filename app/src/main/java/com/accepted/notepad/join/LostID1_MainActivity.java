package com.accepted.notepad.join;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.VolleySingleton;
import com.accepted.notepad.password.Password_MainActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class LostID1_MainActivity extends AppCompatActivity {

    String certNum;
    Context context;
    boolean isChecked = false;

    GradientDrawable shape1;
    GradientDrawable shape2;


    int colorMode = 1;
    boolean isLostLock = false;

    String choosedColor1;
    String choosedColor2;
    String choosedColor3;
    String choosedColor4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_lostid1_activity);

        context = getApplicationContext();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        colorMode = intent.getIntExtra("ColorMode",1);
        isLostLock = intent.getBooleanExtra("isLostLock",false);



        if(isLostLock)
        {
            choosedColor1 = SaveSharedPreference.getBackColor1(context);
            choosedColor2 = SaveSharedPreference.getBackColor2(context);
            choosedColor3 = SaveSharedPreference.gettxtColor1(context);
            choosedColor4 = SaveSharedPreference.geticonColor1(context);
        }else
        {
            choosedColor1 = "#f5f5f5";
            choosedColor2 = "#ffffff";
            choosedColor3 = "#544f4f";
            choosedColor4 = "#f37a00";
        }


        background(choosedColor1,choosedColor2,choosedColor3,choosedColor4);

        ((EditText)findViewById(R.id.et_phone_lostid)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        ((EditText)findViewById(R.id.et_check_lostid)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        ((EditText)findViewById(R.id.et_phone_lostid)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(((EditText)findViewById(R.id.et_phone_lostid)).length() == 11)
                {
                    shape1 = (GradientDrawable) ContextCompat.getDrawable(context,R.drawable.bgr_mainbtn);
                    shape1.setColor(Color.parseColor(choosedColor4));
                    ((Button)findViewById(R.id.btn_phone_lostid)).setBackground(shape1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((EditText)findViewById(R.id.et_check_lostid)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(((EditText)findViewById(R.id.et_check_lostid)).length() == 6)
                {
                    shape1 = (GradientDrawable) ContextCompat.getDrawable(context,R.drawable.bgr_mainbtn);
                    shape1.setColor(Color.parseColor(choosedColor4));
                    ((Button)findViewById(R.id.btn_check_lostid)).setBackground(shape1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ((Button)findViewById(R.id.btn_phone_lostid)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PhoneNum = ((EditText)findViewById(R.id.et_phone_lostid)).getText().toString();

                if(!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", PhoneNum)) {
                    Toast.makeText(context,"휴대폰 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                } else {
                    InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    sendSMS();
                }
            }
        });
        ((Button)findViewById(R.id.btn_check_lostid)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 인증 실행해야하는 구간
                if(((EditText)findViewById(R.id.et_check_lostid)).length() != 6) {
                    Toast.makeText(context,"인증 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }else {
                    String inputNum = ((EditText)findViewById(R.id.et_check_lostid)).getText().toString();
                    if (inputNum.equals(String.valueOf(certNum))) {
                        isChecked = true;
                        Toast.makeText(context,"인증되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ((Button)findViewById(R.id.btn_next_lostid)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLostLock)
                {
                    if(isChecked) {
                        Intent intent = new Intent(context, Password_MainActivity.class);
                        intent.putExtra("ColorMode", colorMode);
                        intent.putExtra("isLostLock", true);
                        startActivity(intent);
                    } else {
                        Toast.makeText(context,"휴대폰 인증을 진행해주세요",Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    if(isChecked)
                    {
                        Intent intent = new Intent(context,LostID2_MainActivity.class);
                        String PhoneNum = ((EditText)findViewById(R.id.et_phone_lostid)).getText().toString();
                        intent.putExtra("Phone", PhoneNum);
                        startActivity(intent);
                    }else
                    {
                        Toast.makeText(context,"휴대폰 인증을 진행해주세요",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });



    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void background(String color1,String color2,String color3,String color4)
    {
        shape1 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_mainbtn);
        shape1.setColor(Color.parseColor(color4));
        shape2 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_edittext_change);
        shape2.setColor(Color.parseColor(color2));

        ((LinearLayout)findViewById(R.id.ll_container_lostid)).setBackgroundColor(Color.parseColor(color1));
        ((TextView)findViewById(R.id.tv1)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv2)).setTextColor(Color.parseColor(color3));

        ((Button)findViewById(R.id.btn_next_lostid)).setBackground(shape1);

        ((EditText)findViewById(R.id.et_phone_lostid)).setBackground(shape2);
        ((EditText)findViewById(R.id.et_check_lostid)).setBackground(shape2);

        ((EditText)findViewById(R.id.et_check_lostid)).setHintTextColor(Color.parseColor(color3));
        ((EditText)findViewById(R.id.et_check_lostid)).setTextColor(Color.parseColor(color3));
        ((EditText)findViewById(R.id.et_phone_lostid)).setHintTextColor(Color.parseColor(color3));
        ((EditText)findViewById(R.id.et_phone_lostid)).setTextColor(Color.parseColor(color3));


    }

    public void sendSMS() {
        String PhoneNum = (((EditText)findViewById(R.id.et_phone_lostid)).getText()).toString();


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

}
