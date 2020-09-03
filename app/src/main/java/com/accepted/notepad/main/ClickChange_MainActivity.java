package com.accepted.notepad.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ClickChange_MainActivity extends AppCompatActivity {


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
    int colorMode = 1;

    GradientDrawable shape1;
    Context context;

    int clickType = 0;

    LinearLayout ll_clicktype[] = new LinearLayout[4];
    ImageView iv_clicktype[] = new ImageView[4];
    TextView tv_clicktype[] = new TextView[4];
    Button btn_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clickchange_activity);

        context = getApplicationContext();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViewById();
        Intent intent = getIntent();
        colorMode = intent.getIntExtra("ColorMode",1);
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

        for(int i=0; i<ll_clicktype.length; i++)
        {
            final int finalI = i;
            final int finalI1 = i;
            ll_clicktype[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int j=0;j<ll_clicktype.length;j++)
                    {
                        clickType = finalI1 +1;
                        if(finalI ==j)
                        {
                            tv_clicktype[j].setTextColor(Color.parseColor(choosedColor4));
                            tv_clicktype[j].setTypeface(Typeface.DEFAULT_BOLD);

                            if(j==0)
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clickchange1_2);
                                iv_clicktype[j].setColorFilter(Color.parseColor(choosedColor4));
                            }else if(j==1)
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clickchange2_2);
                                iv_clicktype[j].setColorFilter(Color.parseColor(choosedColor4));
                            }else if(j==2)
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clickchange3_2);
                                iv_clicktype[j].setColorFilter(Color.parseColor(choosedColor4));
                            }else
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clickchange4_2);
                                iv_clicktype[j].setColorFilter(Color.parseColor(choosedColor4));
                            }
                        }

                        else
                        {
                            tv_clicktype[j].setTextColor(Color.parseColor(choosedColor3));
                            tv_clicktype[j].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                            if(j==0)
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clickchange1_1);
                                iv_clicktype[j].setColorFilter(Color.parseColor(choosedColor3));
                            }else if(j==1)
                            {

                                iv_clicktype[j].setImageResource(R.drawable.clickchange2_1);
                                iv_clicktype[j].setColorFilter(Color.parseColor(choosedColor3));
                            }else if(j==2)
                            {

                                iv_clicktype[j].setImageResource(R.drawable.clickchange3_1);
                                iv_clicktype[j].setColorFilter(Color.parseColor(choosedColor3));
                            }else
                            {

                                iv_clicktype[j].setImageResource(R.drawable.clickchange4_1);
                                iv_clicktype[j].setColorFilter(Color.parseColor(choosedColor3));
                            }
                        }
                    }

                }
            });
        }
        ((Button)findViewById(R.id.btn_next_page2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        colorChange(choosedColor1,choosedColor2,choosedColor3,choosedColor4);

    }

    public void colorChange(String color1, String color2, String color3, String color4)
    {

        shape1 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_password_input);
        shape1.setColor(Color.parseColor(color4));

        ((RelativeLayout)findViewById(R.id.container_clickchange)).setBackgroundColor(Color.parseColor(color1));

        ((TextView)findViewById(R.id.tv1)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv2)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_clicktype1)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_clicktype2)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_clicktype3)).setTextColor(Color.parseColor(color3));
        ((TextView)findViewById(R.id.tv_clicktype4)).setTextColor(Color.parseColor(color3));

        ((Button)findViewById(R.id.btn_next_page2)).setBackground(shape1);

    }

    public void findViewById()
    {
        ll_clicktype[0] = findViewById(R.id.ll_clicktype1);
        ll_clicktype[1] = findViewById(R.id.ll_clicktype2);
        ll_clicktype[2] = findViewById(R.id.ll_clicktype3);
        ll_clicktype[3] = findViewById(R.id.ll_clicktype4);

        tv_clicktype[0] = findViewById(R.id.tv_clicktype1);
        tv_clicktype[1] = findViewById(R.id.tv_clicktype2);
        tv_clicktype[2] = findViewById(R.id.tv_clicktype3);
        tv_clicktype[3] = findViewById(R.id.tv_clicktype4);

        iv_clicktype[0] = findViewById(R.id.iv_clicktype1);
        iv_clicktype[1] = findViewById(R.id.iv_clicktype2);
        iv_clicktype[2] = findViewById(R.id.iv_clicktype3);
        iv_clicktype[3] = findViewById(R.id.iv_clicktype4);

        btn_next = findViewById(R.id.btn_next_page2);
    }



}
