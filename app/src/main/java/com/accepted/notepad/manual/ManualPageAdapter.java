package com.accepted.notepad.manual;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ManualPageAdapter extends FragmentPagerAdapter {

    int numberOfTabs;

    public ManualPageAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                Manual_Page1 manual_page1 = new Manual_Page1();
                return manual_page1;
            case 1:
                Manual_Page2 manual_page2 = new Manual_Page2();
                return manual_page2;
            case 2:
                Manual_Page3 manual_page3 = new Manual_Page3();
                return manual_page3;
            case 3:
                Manual_Page4 manual_page4 = new Manual_Page4();
                return manual_page4;
            case 4:
                Manual_Page5 manual_page5 = new Manual_Page5();
                return manual_page5;
            case 5:
                Manual_Page6 manual_page6 = new Manual_Page6();
                return manual_page6;
            case 6:
                Manual_Page7 manual_page7 = new Manual_Page7();
                return manual_page7;
            default: return null;
        }

    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
