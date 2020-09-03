package com.accepted.notepad.tutorial;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
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

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Tutorial_Page2 extends Fragment {

    Context mContext;
    LinearLayout ll_clicktype[] = new LinearLayout[4];
    ImageView iv_clicktype[] = new ImageView[4];
    TextView tv_clicktype[] = new TextView[4];
    Button btn_next;

    int clickType = 0;

    GradientDrawable shape1;
    GradientDrawable shape2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();

        View view = inflater.inflate(R.layout.tutorial_page2, container, false);
        findViewById(view);


        shape1 = (GradientDrawable) ContextCompat.getDrawable(mContext,R.drawable.bgr_mainbtn);
        shape2 = (GradientDrawable) ContextCompat.getDrawable(mContext,R.drawable.bgr_mainbtn_unactive);
        btn_next.setBackground(shape2);

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
                            tv_clicktype[j].setTextColor(Color.parseColor("#F37A00"));
                            tv_clicktype[j].setTypeface(Typeface.DEFAULT_BOLD);

                            if(j==0)
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clicktype1_2);
                            }else if(j==1)
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clicktype2_2);
                            }else if(j==2)
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clicktype3_2);
                            }else
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clicktype4_2);
                            }
                        }

                        else
                        {
                            tv_clicktype[j].setTextColor(Color.parseColor("#707070"));
                            tv_clicktype[j].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                            if(j==0)
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clicktype1_1);
                            }else if(j==1)
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clicktype2_1);
                            }else if(j==2)
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clicktype3_1);
                            }else
                            {
                                iv_clicktype[j].setImageResource(R.drawable.clicktype4_1);
                            }
                        }
                    }

                    if(clickType==0)
                    {
                        btn_next.setBackground(shape2);
                    }else
                    {
                        btn_next.setBackground(shape1);
                    }
                }
            });
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickType==0)
                {
                    Toast.makeText(mContext,"클릭 방식을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }else
                {
                    Tutorial_MainActivity mainActivity = (Tutorial_MainActivity) getActivity();
                    mainActivity.nextPage(1);
                }
            }
        });


        return view;
    }

    public void findViewById(View view)
    {
        ll_clicktype[0] = view.findViewById(R.id.ll_clicktype1);
        ll_clicktype[1] = view.findViewById(R.id.ll_clicktype2);
        ll_clicktype[2] = view.findViewById(R.id.ll_clicktype3);
        ll_clicktype[3] = view.findViewById(R.id.ll_clicktype4);

        tv_clicktype[0] = view.findViewById(R.id.tv_clicktype1);
        tv_clicktype[1] = view.findViewById(R.id.tv_clicktype2);
        tv_clicktype[2] = view.findViewById(R.id.tv_clicktype3);
        tv_clicktype[3] = view.findViewById(R.id.tv_clicktype4);

        iv_clicktype[0] = view.findViewById(R.id.iv_clicktype1);
        iv_clicktype[1] = view.findViewById(R.id.iv_clicktype2);
        iv_clicktype[2] = view.findViewById(R.id.iv_clicktype3);
        iv_clicktype[3] = view.findViewById(R.id.iv_clicktype4);

        iv_clicktype[3] = view.findViewById(R.id.iv_clicktype4);

        btn_next = view.findViewById(R.id.btn_next_page2);
    }

}
