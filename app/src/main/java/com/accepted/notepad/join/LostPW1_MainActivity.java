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

import androidx.appcompat.app.AppCompatActivity;


public class LostPW1_MainActivity extends AppCompatActivity {


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

                if(((EditText)findViewById(R.id.et_phone_lostpw)).length() != 11)
                {
                    Toast.makeText(context,"휴대폰 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((Button)findViewById(R.id.btn_check_lostpw)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((EditText)findViewById(R.id.et_check_lostpw)).length() != 6)
                {
                    Toast.makeText(context,"인증 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }else
                {
                    isChecked = true;
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
                    Intent intent = new Intent(context,LostPW2_MainActivity.class);
                    startActivity(intent);
                }

            }
        });



    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
