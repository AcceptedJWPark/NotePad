package com.accepted.notepad.tutorial;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.accepted.notepad.R;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Tutorial_Page1 extends Fragment {

    Context mContext;

    TextView password[] = new TextView[6];
    Button btnPw[] = new Button[10];


    GradientDrawable shape1;
    GradientDrawable shape3;

    String InputPassword = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext=getActivity();

        shape1 = (GradientDrawable) ContextCompat.getDrawable(mContext,R.drawable.bgr_password_input);
        shape1.setColor(Color.parseColor("#66F37A00"));

        View view = inflater.inflate(R.layout.tutorial_page1, container, false);
        findViewById(view);

        shape3 = (GradientDrawable) ContextCompat.getDrawable(mContext,R.drawable.bgr_password_input);
        shape3.setColor(Color.parseColor("#F37A00"));

        for(int i=0; i<btnPw.length; i++)
        {
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
                        Tutorial_MainActivity mainActivity = (Tutorial_MainActivity) getActivity();
                        mainActivity.nextPage(0);
                    }
                }
            });
        }

        ((ImageView)view.findViewById(R.id.iv_del_password_tutorial)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(InputPassword.length()==0)
                {

                }else
                {
                    InputPassword = InputPassword.substring(0,InputPassword.length()-1);
                    for(int j=0; j<6; j++)
                    {
                        if(j<InputPassword.length())
                        {
                            password[j].setBackground(shape3);
                        }
                        else
                        {
                            password[j].setBackground(shape1);
                        }
                    }
                }
            }
        });



        return view;
    }

    public void findViewById(View view)
    {
        password[0] = view.findViewById(R.id.tv_password1_tutorial);
        password[1] = view.findViewById(R.id.tv_password2_tutorial);
        password[2] = view.findViewById(R.id.tv_password3_tutorial);
        password[3] = view.findViewById(R.id.tv_password4_tutorial);
        password[4] = view.findViewById(R.id.tv_password5_tutorial);
        password[5] = view.findViewById(R.id.tv_password6_tutorial);

        btnPw[0] = view.findViewById(R.id.btn_0_tutorial);
        btnPw[1] = view.findViewById(R.id.btn_1_tutorial);
        btnPw[2] = view.findViewById(R.id.btn_2_tutorial);
        btnPw[3] = view.findViewById(R.id.btn_3_tutorial);
        btnPw[4] = view.findViewById(R.id.btn_4_tutorial);
        btnPw[5] = view.findViewById(R.id.btn_5_tutorial);
        btnPw[6] = view.findViewById(R.id.btn_6_tutorial);
        btnPw[7] = view.findViewById(R.id.btn_7_tutorial);
        btnPw[8] = view.findViewById(R.id.btn_8_tutorial);
        btnPw[9] = view.findViewById(R.id.btn_9_tutorial);
    }

}
