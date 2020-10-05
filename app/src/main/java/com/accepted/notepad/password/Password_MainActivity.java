package com.accepted.notepad.password;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.VolleySingleton;
import com.accepted.notepad.backgound.Background_MainActivity;
import com.accepted.notepad.main.MainActivity;
import com.accepted.notepad.papermemo.Papermemo_MainActivity;
import com.accepted.notepad.tutorial.Tutorial_MainActivity;
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

public class Password_MainActivity extends AppCompatActivity {


    Context mContext;
    int colorMode = 1;
    boolean isLostLock = false;

    String choosedColor1;
    String choosedColor2;
    String choosedColor3;
    String choosedColor4;

    TextView password[] = new TextView[6];
    Button btnPw[] = new Button[10];


    GradientDrawable shape1;
    GradientDrawable shape2;
    GradientDrawable shape3;
    GradientDrawable shape4;
    GradientDrawable shape5;

    String InputPassword = "";

    String UserPassword;

    String fTitle;
    String fContent;
    String rTitle;
    String rContent;
    int secureType;
    int clickType;
    int memoCode;
    boolean isSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);
        mContext = getApplicationContext();

        UserPassword = SaveSharedPreference.getSecurityCode(mContext);

        Intent paperMemoIntent = getIntent();
        fTitle = paperMemoIntent.getStringExtra("Title");
        fContent = paperMemoIntent.getStringExtra("Content");
        rTitle = paperMemoIntent.getStringExtra("RTitle");
        rContent = paperMemoIntent.getStringExtra("RContent");
        secureType = paperMemoIntent.getIntExtra("SecureType", 1);
        clickType = paperMemoIntent.getIntExtra("ClickType", 1);
        memoCode = paperMemoIntent.getIntExtra("MemoCode", -1);
        isSetting = paperMemoIntent.getBooleanExtra("isSetting", false);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        password[0] = findViewById(R.id.tv_password1);
        password[1] = findViewById(R.id.tv_password2);
        password[2] = findViewById(R.id.tv_password3);
        password[3] = findViewById(R.id.tv_password4);
        password[4] = findViewById(R.id.tv_password5);
        password[5] = findViewById(R.id.tv_password6);

        btnPw[0] = findViewById(R.id.btn_0);
        btnPw[1] = findViewById(R.id.btn_1);
        btnPw[2] = findViewById(R.id.btn_2);
        btnPw[3] = findViewById(R.id.btn_3);
        btnPw[4] = findViewById(R.id.btn_4);
        btnPw[5] = findViewById(R.id.btn_5);
        btnPw[6] = findViewById(R.id.btn_6);
        btnPw[7] = findViewById(R.id.btn_7);
        btnPw[8] = findViewById(R.id.btn_8);
        btnPw[9] = findViewById(R.id.btn_9);


        Intent intent = getIntent();
        colorMode = Integer.valueOf(SaveSharedPreference.getColorMode(mContext));

        choosedColor1 = SaveSharedPreference.getBackColor1(mContext);
        choosedColor2 = SaveSharedPreference.getBackColor2(mContext);
        choosedColor3 = SaveSharedPreference.gettxtColor1(mContext);
        choosedColor4 = SaveSharedPreference.geticonColor1(mContext);

        isLostLock = intent.getBooleanExtra("isLostLock",false);

        if(isSetting)
        {
            ((TextView)findViewById(R.id.tv_password1_main)).setVisibility(View.GONE);
        }else
        {
            ((TextView)findViewById(R.id.tv_password1_main)).setVisibility(View.VISIBLE);
        }

        ((ImageView)findViewById(R.id.iv_cancel_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(isLostLock) {
            ((TextView) findViewById(R.id.tv_password1_main)).setText("잠금 번호를 변경합니다");
        }else
        {
            ((TextView) findViewById(R.id.tv_password1_main)).setText("해당 메모는 보안되어 있습니다");
        }
        background(choosedColor1,choosedColor2, choosedColor3, choosedColor4);

        shape3 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_password_input);
        shape4 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_password_input);
        shape3.setColor(Color.parseColor(choosedColor4));
        shape4.setColor(Color.parseColor("#77"+choosedColor4.substring(1)));

        for(int i=0; i<password.length; i++)
        {
            password[i].setBackground(shape4);
        }

        for(int i=0; i<btnPw.length; i++)
        {
            btnPw[i].setBackgroundColor(Color.parseColor(choosedColor1));
            btnPw[i].setTextColor(Color.parseColor(choosedColor3));
            final int finalI = i;
            btnPw[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(InputPassword.length()<6)
                    {
                        InputPassword = InputPassword + btnPw[finalI].getText();
                    }else
                    {
                        return;
                    }

                    for(int j=0; j<InputPassword.length(); j++)
                    {
                        password[j].setBackground(shape3);
                    }

                    if(InputPassword.length()==6)
                    {
                        if(isLostLock)
                        {
                            updateSecurityCode();
                        }else
                        {
                            if (isSetting == true) {
                                if(InputPassword.equals(UserPassword)) {
                                    Intent intent = new Intent(mContext, Background_MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(mContext, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                    InputPassword = "";
                                    for(int j=0; j<6; j++)
                                    {
                                        password[j].setBackground(shape4);
                                    }
                                }
                            } else {
                                if(InputPassword.equals(UserPassword))
                                {
                                    Log.d("input", InputPassword.equals(UserPassword) + "");
                                    Intent intent = new Intent(mContext,Papermemo_MainActivity.class);
                                    intent.putExtra("ColorMode",colorMode);
                                    intent.putExtra("isReal",2);
                                    intent.putExtra("MemoCode", memoCode);
                                    intent.putExtra("Title", fTitle);
                                    intent.putExtra("Content", fContent);
                                    intent.putExtra("RTitle", rTitle);
                                    intent.putExtra("RContent", rContent);
                                    intent.putExtra("SecureType", secureType);
                                    intent.putExtra("ClickType", clickType);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.d("input", InputPassword.equals(UserPassword) + "");
                                    Intent intent = new Intent(mContext,Papermemo_MainActivity.class);
                                    intent.putExtra("isReal",3);
                                    intent.putExtra("ColorMode",colorMode);
                                    intent.putExtra("MemoCode", memoCode);
                                    intent.putExtra("Title", fTitle);
                                    intent.putExtra("Content", fContent);
                                    intent.putExtra("RTitle", rTitle);
                                    intent.putExtra("RContent", rContent);
                                    intent.putExtra("SecureType", secureType);
                                    intent.putExtra("ClickType", clickType);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }
                }
            });
        }

        ((LinearLayout)findViewById(R.id.ll_del_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InputPassword.length()==0)
                {

                }else
                {
                    InputPassword = InputPassword.substring(0,InputPassword.length()-1);
                    Log.d(InputPassword,"password");
                    for(int j=0; j<UserPassword.length(); j++)
                    {
                        if(j<InputPassword.length())
                        {
                            password[j].setBackground(shape3);
                        }
                        else
                        {
                            password[j].setBackground(shape4);
                        }
                    }
                }
            }
        });

        ((LinearLayout)findViewById(R.id.ll_pwrefresh)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputPassword = "";
                for(int j=0; j<UserPassword.length(); j++)
                {
                    if(j<InputPassword.length())
                    {
                        password[j].setBackground(shape3);
                    }
                    else
                    {
                        password[j].setBackground(shape4);
                    }
                }
            }
        });


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

        shape1 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_password_input);
        shape1.setColor(Color.parseColor("#77"+color2.substring(1)));
        shape2 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_password);
        shape2.setColor(Color.parseColor(color2));

        shape5 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_password);
        shape5.setColor(Color.parseColor(color4));

        ((TextView)findViewById(R.id.tv_password1_main)).setTextColor(Color.parseColor(color2));
        ((TextView)findViewById(R.id.tv_password1_main)).setBackground(shape5);

        ((ImageView)findViewById(R.id.iv_cancel_password)).setColorFilter(Color.parseColor(color2));
        ((LinearLayout)findViewById(R.id.rl_container)).setBackgroundColor(Color.parseColor(color2));
        ((TextView)findViewById(R.id.tv_password1)).setBackground(shape2);
        ((TextView)findViewById(R.id.tv_password1)).setTextColor(Color.parseColor(color4));
        ((TextView)findViewById(R.id.tv_password2_sub)).setTextColor(Color.parseColor(color4));

        ((LinearLayout)findViewById(R.id.ll_pwcontainer)).setBackgroundColor(Color.parseColor(color2));

        for(int i=0; i<btnPw.length;i++)
        {
            btnPw[i].setTextColor(Color.parseColor(color2));
        }
        ((LinearLayout)findViewById(R.id.ll_pwrefresh)).setBackgroundColor(Color.parseColor(color1));
        ((LinearLayout)findViewById(R.id.ll_del_password)).setBackgroundColor(Color.parseColor(color1));
        ((ImageView)findViewById(R.id.iv_pwrefresh)).setColorFilter(Color.parseColor(color3));
        ((ImageView)findViewById(R.id.iv_del_password)).setColorFilter(Color.parseColor(color3));

        for(int i=0; i<password.length;i++)
        {
            password[i].setBackground(shape1);
        }



    }

    public void updateSecurityCode() {
        RequestQueue postRequestQueue = VolleySingleton.getInstance(mContext).getRequestQueue();
        StringRequest postJsonRequest = new StringRequest(Request.Method.POST, SaveSharedPreference.getServerIp() + "/Member/updateSecurityCode.do", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.get("result").equals("success")) {
                        SaveSharedPreference.setSecurityCode(mContext, InputPassword);
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("ColorMode",colorMode);
                        startActivity(intent);
                        Toast.makeText(mContext,"잠금번호가 변경되었습니다.",Toast.LENGTH_SHORT).show();
                        finish();
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
                params.put("SecurityCode", InputPassword);
                return params;
            }
        };
        postRequestQueue.add(postJsonRequest);
    }



}
