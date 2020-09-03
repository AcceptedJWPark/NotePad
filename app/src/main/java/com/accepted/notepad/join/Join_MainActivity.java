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
import com.accepted.notepad.tutorial.Tutorial_MainActivity;

import androidx.appcompat.app.AppCompatActivity;


public class Join_MainActivity extends AppCompatActivity {


    Context context;

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
                }else if(((EditText)findViewById(R.id.et_pw_join)).length() == 0){
                    Toast.makeText(context,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                }else if(!((EditText)findViewById(R.id.et_pw_join)).getText().toString().equals(((EditText)findViewById(R.id.et_pw2_join)).getText().toString()))
                {
                    Toast.makeText(context,"비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }else if(!isChecked)
                {
                    Toast.makeText(context,"휴대폰 인증을 진행해주세요",Toast.LENGTH_SHORT).show();
                }else
                {
                    Intent intent = new Intent(context,Tutorial_MainActivity.class);
                    startActivity(intent);
                }


            }
        });
        ((Button)findViewById(R.id.btn_phone_join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((EditText)findViewById(R.id.et_phone_join)).length() != 11)
                {
                    Toast.makeText(context,"휴대폰 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        ((Button)findViewById(R.id.btn_check_join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((EditText)findViewById(R.id.et_check_join)).length() != 6)
                {
                    Toast.makeText(context,"인증 번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                }else
                {
                    isChecked = true;
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
                    ((Button)findViewById(R.id.btn_phone_join)).setBackgroundResource(R.drawable.bgr_mainbtn);
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
                    ((Button)findViewById(R.id.btn_check_join)).setBackgroundResource(R.drawable.bgr_mainbtn);
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

}
