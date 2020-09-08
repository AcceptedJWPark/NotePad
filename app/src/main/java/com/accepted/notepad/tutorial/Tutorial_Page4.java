package com.accepted.notepad.tutorial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.backgound.Background_MainActivity;

import androidx.fragment.app.Fragment;

public class Tutorial_Page4 extends Fragment {

    Context mContext;

    LinearLayout ll_clicktype[] = new LinearLayout[2];
    ImageView iv_clicktype[] = new ImageView[2];
    TextView tv_clicktype[] = new TextView[2];
    Button btn_next;

    TextView tvSecType;
    LinearLayout llSecType2;
    LinearLayout llSecType3;

    boolean isSecType2Clicked;
    boolean isSecType3Clicked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();

        isSecType2Clicked = false;
        isSecType3Clicked = false;

        View view = inflater.inflate(R.layout.tutorial_page4, container, false);

        findViewById(view);
        tvSecType.setVisibility(View.VISIBLE);
        llSecType2.setVisibility(View.GONE);
        llSecType3.setVisibility(View.GONE);

        clickFakeType();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isSecType3Clicked)
                {
                    Toast.makeText(mContext,"높음 보안방식을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }else
                {
                    Intent intent = new Intent(mContext, Background_MainActivity.class);
                    intent.putExtra("isTutorial",true);
                    startActivity(intent);
                }

            }
        });


        return view;
    }

    public void clickFakeType()
    {
        ll_clicktype[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_clicktype[0].setImageResource(R.drawable.low2);
                iv_clicktype[1].setImageResource(R.drawable.high1);

                tv_clicktype[0].setTextColor(Color.parseColor("#039C17"));
                tv_clicktype[1].setTextColor(Color.parseColor("#949494"));

                tv_clicktype[0].setTypeface(Typeface.DEFAULT_BOLD);
                tv_clicktype[1].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                tvSecType.setVisibility(View.VISIBLE);
                llSecType3.setVisibility(View.GONE);
            }
        });


        ll_clicktype[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv_clicktype[0].setImageResource(R.drawable.low1);
                iv_clicktype[1].setImageResource(R.drawable.high2);

                tv_clicktype[0].setTextColor(Color.parseColor("#949494"));
                tv_clicktype[1].setTextColor(Color.parseColor("#FF0911"));

                tv_clicktype[1].setTypeface(Typeface.DEFAULT_BOLD);
                tv_clicktype[0].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                tvSecType.setVisibility(View.GONE);
                llSecType3.setVisibility(View.VISIBLE);

                isSecType3Clicked = true;
            }
        });
    }


    public void findViewById(View view)
    {
        ll_clicktype[0] = view.findViewById(R.id.ll_low);
        ll_clicktype[1] = view.findViewById(R.id.ll_high);

        tv_clicktype[0] = view.findViewById(R.id.tv_low);
        tv_clicktype[1] = view.findViewById(R.id.tv_high);

        iv_clicktype[0] = view.findViewById(R.id.iv_low);
        iv_clicktype[1] = view.findViewById(R.id.iv_high);

        tvSecType = view.findViewById(R.id.tv_secType1);
        llSecType2 = view.findViewById(R.id.ll_secType2);
        llSecType3 = view.findViewById(R.id.ll_secType2);

        btn_next = view.findViewById(R.id.btn_next_page4);
    }

}
