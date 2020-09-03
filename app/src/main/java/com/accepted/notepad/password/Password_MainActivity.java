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

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;
import com.accepted.notepad.main.MainActivity;
import com.accepted.notepad.papermemo.Papermemo_MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Password_MainActivity extends AppCompatActivity {


    Context mContext;
    int colorMode = 1;
    boolean isLostLock = false;

    String color1_basic = SaveSharedPreference.getBackColor1_basic();
    String color2_basic = SaveSharedPreference.getBackColor2_basic();
    String color3_basic = SaveSharedPreference.gettxtColor1_basic();
    String color4_basic = SaveSharedPreference.geticonColor1_basic();

    String color1_night = SaveSharedPreference.getBackColor1_night();
    String color2_night = SaveSharedPreference.getBackColor2_night();
    String color3_night = SaveSharedPreference.getTxtColor1_night();
    String color4_night = SaveSharedPreference.getIconColor1_night();

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

    String UserPassword = "123456";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);
        mContext = getApplicationContext();

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
        colorMode = intent.getIntExtra("ColorMode",1);
        isLostLock = intent.getBooleanExtra("isLostLock",false);

        ((ImageView)findViewById(R.id.iv_cancel_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(colorMode == 1)
        {
            choosedColor1 = color1_basic;
            choosedColor2 = color2_basic;
            choosedColor3 = color3_basic;
            choosedColor4 = color4_basic;
        }else if(colorMode ==2)
        {
            choosedColor1 = color1_night;
            choosedColor2 = color2_night;
            choosedColor3 = color3_night;
            choosedColor4 = color4_night;
        }

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
                            Intent intent = new Intent(mContext, MainActivity.class);
                            intent.putExtra("ColorMode",colorMode);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            if(InputPassword.equals(UserPassword))
                            {
                                Intent intent = new Intent(mContext,Papermemo_MainActivity.class);
                                intent.putExtra("ColorMode",colorMode);
                                intent.putExtra("isReal",2);
                                startActivity(intent);
                                finish();
                            }else
                            {
                                Intent intent = new Intent(mContext,Papermemo_MainActivity.class);
                                intent.putExtra("isReal",3);
                                intent.putExtra("ColorMode",colorMode);
                                startActivity(intent);
                                finish();
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



}
