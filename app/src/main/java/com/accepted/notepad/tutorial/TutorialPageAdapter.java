package com.accepted.notepad.tutorial;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TutorialPageAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;

    public TutorialPageAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                Tutorial_Page1 tutorial_page1 = new Tutorial_Page1();
                return tutorial_page1;
            case 1:
                Tutorial_Page2 tutorial_page2 = new Tutorial_Page2();
                return tutorial_page2;
            case 2:
                Tutorial_Page3 tutorial_page3 = new Tutorial_Page3();
                return tutorial_page3;
            case 3:
                Tutorial_Page4 tutorial_page4 = new Tutorial_Page4();
                return tutorial_page4;

            default: return null;
        }

    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
