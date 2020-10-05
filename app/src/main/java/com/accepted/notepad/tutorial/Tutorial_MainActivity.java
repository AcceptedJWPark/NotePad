package com.accepted.notepad.tutorial;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.accepted.notepad.R;

import androidx.appcompat.app.AppCompatActivity;


public class Tutorial_MainActivity extends AppCompatActivity {


    Context mContext;
    TutorialViewpager viewPager;
    TutorialPageAdapter tutorialPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);
        mContext = getApplicationContext();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        viewPager = findViewById(R.id.vp_tutorial);
        tutorialPageAdapter = new TutorialPageAdapter(getSupportFragmentManager(),4);
        viewPager.setAdapter(tutorialPageAdapter);


    }

    public void nextPage(int index)
    {
        if(index==0)
        {
            viewPager.setCurrentItem(1);
        }else if(index==1)
        {
            viewPager.setCurrentItem(2);
        }
        else if(index==2)
        {
            viewPager.setCurrentItem(3);
        }
        else
        {
            viewPager.setCurrentItem(4);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(mContext, "튜토리얼을 완료해주세요.", Toast.LENGTH_SHORT).show();
    }

}
