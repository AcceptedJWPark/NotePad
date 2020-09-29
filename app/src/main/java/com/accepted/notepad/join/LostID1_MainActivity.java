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
import com.accepted.notepad.password.Password_MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class LostID1_MainActivity extends AppCompatActivity {


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

        choosedColor1 = SaveSharedPreference.getBackColor1(context);
        choosedColor2 = SaveSharedPreference.getBackColor2(context);
        choosedColor3 = SaveSharedPreference.gettxtColor1(context);
        choosedColor4 = SaveSharedPreference.geticonColor1(context);

        if(isLostLock)
        {
            background(choosedColor1,choosedColor2,choosedColor3,choosedColor4);
        }


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

                if(((EditText)findViewById(R.id.et_phone_lostid)).length() != 11)
                {
                    Toast.makeText(context,"휴대폰 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((Button)findViewById(R.id.btn_check_lostid)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((EditText)findViewById(R.id.et_check_lostid)).length() != 6)
                {
                    Toast.makeText(context,"인증 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }else
                {
                    isChecked = true;
                }
            }
        });

        ((Button)findViewById(R.id.btn_next_lostid)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLostLock)
                {
                    Intent intent = new Intent(context, Password_MainActivity.class);
                    intent.putExtra("ColorMode",colorMode);
                    intent.putExtra("isLostLock",true);
                    startActivity(intent);
                }else
                {
                    if(isChecked)
                    {
                        Intent intent = new Intent(context,LostID2_MainActivity.class);
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
        shape2 = (GradientDrawable) ContextCompat.getDrawable(this,R.drawable.bgr_edittext_login);
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

}
