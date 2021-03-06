package com.accepted.notepad.manual;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.accepted.notepad.R;
import com.accepted.notepad.login.Login_MainActivity;
import com.accepted.notepad.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class Manual_MainActivity extends AppCompatActivity {


    Context mContext;
    ViewPager viewPager;
    ManualPageAdapter manualPageAdapter;
    TextView tv_comp_manual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_activity);
        mContext = getApplicationContext();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_comp_manual = findViewById(R.id.tv_comp_manual);

        viewPager = findViewById(R.id.vp_manual);
        manualPageAdapter = new ManualPageAdapter(getSupportFragmentManager(),7);
        viewPager.setAdapter(manualPageAdapter);

        ((TextView)findViewById(R.id.tv_manualtitle)).setText("비밀글 작성법 "+"("+1+"/"+viewPager.getAdapter().getCount()+")");
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0)
                {
                    ((TextView)findViewById(R.id.tv_manualtitle)).setText("비밀글 작성법 "+"("+(position+1)+"/"+viewPager.getAdapter().getCount()+")");
                }else if(position==1)
                {
                    ((TextView)findViewById(R.id.tv_manualtitle)).setText("비밀글 작성법 "+"("+(position+1)+"/"+viewPager.getAdapter().getCount()+")");
                }else if(position==2)
                {
                    ((TextView)findViewById(R.id.tv_manualtitle)).setText("비밀글 작성법 "+"("+(position+1)+"/"+viewPager.getAdapter().getCount()+")");
                }else if(position==3)
                {
                    ((TextView)findViewById(R.id.tv_manualtitle)).setText("비밀글 작성법 "+"("+(position+1)+"/"+viewPager.getAdapter().getCount()+")");
                }else if(position==4)
                {
                    ((TextView)findViewById(R.id.tv_manualtitle)).setText("클릭방식 변경 "+"("+(position+1)+"/"+viewPager.getAdapter().getCount()+")");
                }else if(position==5)
                {
                    ((TextView)findViewById(R.id.tv_manualtitle)).setText("클릭방식 변경 "+"("+(position+1)+"/"+viewPager.getAdapter().getCount()+")");
                }else
                {
                    ((TextView)findViewById(R.id.tv_manualtitle)).setText("메모 검색 "+"("+(position+1)+"/"+viewPager.getAdapter().getCount()+")");
                }

                if (position == 6) {
                    tv_comp_manual.setVisibility(View.VISIBLE);
                } else {
                    tv_comp_manual.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tv_comp_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ((ImageView)findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(mContext, Login_MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
