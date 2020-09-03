package com.accepted.notepad.papermemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.SaveSharedPreference;

import androidx.appcompat.app.AppCompatActivity;


public class Papermemo_MainActivity extends AppCompatActivity {


    Context context;

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


    LinearLayout containerPaper;
    LinearLayout containerTxt;
    TextView tv_comp;
    EditText et_title;
    EditText et_contents;
    ImageView iv_pre;

    int colorMode = 1;

    int isReal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.papermemo_main);

        context = getApplicationContext();

        ((ImageView)findViewById(R.id.iv_pre)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent intent2 = getIntent();

        isReal = intent2.getIntExtra("isReal",1);

        if(isReal==2)
        {
            Toast.makeText(context,"real", Toast.LENGTH_SHORT).show();
        }else if(isReal==3)
        {
            Toast.makeText(context,"fake", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context,"new", Toast.LENGTH_SHORT).show();
        }

        containerPaper = findViewById(R.id.ll_cotainer_paper);
        containerTxt = findViewById(R.id.ll_txtcontainer_paper);
        tv_comp = findViewById(R.id.tv_comp);
        et_title = findViewById(R.id.et_title);
        et_contents = findViewById(R.id.et_contents);
        iv_pre = findViewById(R.id.iv_pre);

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


        colorChange(choosedColor1,choosedColor2,choosedColor3,choosedColor4);
    }


    public void colorChange(String color1, String color2, String color3, String color4)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color2));
            int flags = getWindow().getDecorView().getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            getWindow().getDecorView().setSystemUiVisibility(flags);
        }


        containerPaper.setBackgroundColor(Color.parseColor(color2));
        containerTxt.setBackgroundColor(Color.parseColor(color1));

        tv_comp.setTextColor(Color.parseColor(color4));

        et_title.setTextColor(Color.parseColor(color3));
        et_contents.setTextColor(Color.parseColor(color3));
        et_title.setHintTextColor(Color.parseColor(color3));
        et_contents.setHintTextColor(Color.parseColor("#949494"));

        iv_pre.setColorFilter(Color.parseColor(color3));

    }


}
