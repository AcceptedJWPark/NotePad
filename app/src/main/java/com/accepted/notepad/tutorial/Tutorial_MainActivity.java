package com.accepted.notepad.tutorial;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.accepted.notepad.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class Tutorial_MainActivity extends AppCompatActivity {


    Context mContext;
    ViewPager viewPager;
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
}
